package comp1110.ass2.gui;

import comp1110.ass2.StepsGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Board_test extends Application{
    private double FIND_RANGE = 60;
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final double PEG_SIZE = 21.5;
    public static final String[] imageList = {"AA","BA","CA","DA","EA","FA","GA","HA"};
    public static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";
    private static final int PIECE_IMAGE_SIZE = (int) ((3*60)*1.33);
    private static final int PIECE_IMAGE_SIZE_SMALL = (int) ((3*60)*1.33*0.5);
    private static ArrayList<StackPane> pegList = new ArrayList<>();
    private boolean findNearFlag = false;
    private boolean pieceBigFlag = false;
    private Circle highlighted = null;
    Group root = new Group();


    private Group setBoard(){
        int distance = 60;
        double margin_x = BOARD_WIDTH/20;
        double margin_y = BOARD_HEIGHT/20;
        Group board = new Group();
        for (int i = 0; i < StepsGame.BOARD_STRING.length();i++){
            StackPane pegs = new StackPane();
            Color color = Color.LIGHTGRAY;
            if((i/10)%2==0) { //row 0 2 4
                if(i%2 != 0)
                    color = Color.GRAY;
            }else{
                if(i%2 == 0) // row 1 3
                    color = Color.GRAY;
            }
            pegs.getChildren().addAll(new Circle(PEG_SIZE,color), new Text(Character.toString(StepsGame.BOARD_STRING.charAt(i)) ));
            pegs.setLayoutX(margin_x + i%10*distance);
            pegs.setLayoutY(margin_y + i/10*distance);
            board.getChildren().add(pegs);
            pegList.add(pegs);
        }
        return board;
    }

    private Group setPieces(){
        Group pieces = new Group();
        for(int i = 0; i < imageList.length; i++){
            Image im = new Image(URI_BASE + imageList[i] + ".png");
            DraggbleImageView pc = new DraggbleImageView(im, PIECE_IMAGE_SIZE*0.45*(i%8),BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8),imageList[i]);
            pc.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
            pc.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
            pieces.getChildren().add(pc);
        }
        return pieces;
    }



    class DraggbleImageView extends ImageView{
        private boolean pieceBigFlag = false;
        private double mouseX;
        private double mouseY;
        private double posX;
        private double orig_posX;
        private double orig_posY;
        private double posY;
        private int check = 0;
        private String name;
        private char rot;
        private boolean flipState;
        private int rotAdd;
        private String pieceString;

        private StackPane nearPeg;

        DraggbleImageView(Image image, double posX, double posY, String name) {
            super(image);
            if (name.charAt(1) =='E')
                check = 1;
            this.name = name;
            this.orig_posX = posX;
            this.orig_posY = posY;
            this.posX = posX;
            this.posY = posY;
            this.setLayoutX(this.orig_posX);
            this.setLayoutY(this.orig_posY);
            this.rot = 'A';
            this.flipState = false;
            this.rotAdd = 0;

            this.setOnScroll(event -> {            // scroll to change orientation
                this.setRotate((this.getRotate()+90)%360);

                this.rotAdd += 1;
                char startChar = this.flipState? 'E':'A';
                if(this.rotAdd > 3) this.rotAdd = 0;
                this.rot = (char)((int)startChar + this.rotAdd);
                this.pieceString = this.name + this.rot;
                System.out.println(pieceString);
                event.consume();
            });

            this.setOnMousePressed(event -> {
                if(event.getButton()== MouseButton.SECONDARY) { //test: flip image when right clicked
                    Flip(this.name.charAt(0),this.posX, this.posY);
                    this.flipState = !this.flipState;

                }else {
                    this.mouseX = event.getSceneX();
                    this.mouseY = event.getSceneY();
                    if(!pieceBigFlag) {
                        this.posX = this.getLayoutX();
                        this.posY = this.getLayoutY();
                        this.setLayoutX(2 * this.posX - mouseX);
                        this.setLayoutY(2 * this.posY - mouseY);
                        this.setFitHeight(PIECE_IMAGE_SIZE);
                        this.setFitWidth(PIECE_IMAGE_SIZE);
                    }
                }
            });

            this.setOnMouseDragged(event -> {
                if(event.getButton()!= MouseButton.SECONDARY) { // drag only for left click
                    this.setLayoutX(getLayoutX() + event.getSceneX() - mouseX);
                    this.setLayoutY(getLayoutY() + event.getSceneY() - mouseY);
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    nearPeg = findNearestPeg(this, pegList);
                    this.toFront();
                    Circle nearCircle = (Circle) nearPeg.getChildren().get(0);
                    Text nearText = (Text) nearPeg.getChildren().get(1);
                    if (findNearFlag) {
//                        highlightNearestPeg(nearCircle);
                        System.out.println(name);
                        System.out.println(nearText.getText());
                    }
                }
            });

            this.setOnMouseReleased((MouseEvent event) -> {
                boolean pegFlag = false;
                if(event.getButton()!= MouseButton.SECONDARY) { // only for left click
                    if(pegFlag){
//                    Node nearPeg = NearestPeg();
                        //put on nearPeg
                    }else {
                        if(findNearFlag){
                            this.posX = nearPeg.getLayoutX() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                            this.posY = nearPeg.getLayoutY() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                            this.setLayoutX(this.posX);
                            this.setLayoutY(this.posY);
                            pieceBigFlag = true;
                        }else {
                            this.setLayoutX(this.orig_posX);
                            this.setLayoutY(this.orig_posY);
                            this.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
                            this.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
                            pieceBigFlag = false;
                        }
                    }
                }
            });


        }
        void Flip(char pcs, double x, double y){
            if(check % 2 == 0)
                this.setImage(new Image(URI_BASE + pcs + "E.png"));
            else if(check % 2 == 1)
                this.setImage(new Image(URI_BASE + pcs + "A.png"));
            check ++;
            this.setLayoutX(x);
            this.setLayoutY(y);
        }
        void get(){

        }
        private double distance(double x, double y){
            double centerX = getLayoutX()+PIECE_IMAGE_SIZE/2;
            double centerY = getLayoutY()+PIECE_IMAGE_SIZE/2;
            double deltaX = (x+PEG_SIZE/2) - centerX;
            double deltaY = (y+PEG_SIZE/2) - centerY;
            return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        }
    }


    private StackPane findNearestPeg(DraggbleImageView piece, List<StackPane> pegs) {
        double minDis = 10000;
        StackPane res = pegs.get(0);
        for (StackPane tryPeg : pegs) {
            double temp = piece.distance(tryPeg.getLayoutX(), tryPeg.getLayoutY());
            if (temp < minDis) {
                res = tryPeg;
                minDis = temp;
            }
        }
        findNearFlag = minDis<FIND_RANGE;
        return res;
    }

    /**
     * hightlight the nearest Peg for debug
     * @param nearPeg nearest peg
     */
    private void highlightNearestPeg(Circle nearPeg){
        if(highlighted!=null){
            highlighted.setFill(Color.LIGHTGRAY);
        }
        highlighted = nearPeg;
        highlighted.setFill(Color.GREEN);

    }

    private Group setButtons(){
        Group button = new Group();
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().clear();
                root.getChildren().addAll(setBoard(),setPieces(),setButtons());
            }
        });
        reset.setLayoutX(BOARD_WIDTH * 0.85);
        reset.setLayoutY(BOARD_HEIGHT*0.2);


        Button hint = new Button("Hint");
        hint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // display all viable pieces
            }
        });
        hint.setLayoutX(BOARD_WIDTH * 0.85);
        hint.setLayoutY(BOARD_HEIGHT*0.3);
        button.getChildren().addAll(hint,reset);

        return button;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
//        StackPane t = new StackPane();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        Group board = setBoard();

        Group pieces = setPieces();

        Group button = setButtons();

//        String url = URI_BASE + imageList[0] + ".png";

//        pieces.getChildren().add(pi);

        root.getChildren().addAll(board,pieces,button);






        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
