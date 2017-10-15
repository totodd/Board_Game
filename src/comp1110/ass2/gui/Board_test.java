package comp1110.ass2.gui;

import comp1110.ass2.StepsGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Board_test extends Application{
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final double PEG_SIZE = 21.5;
    public static final String[] imageList = {"AA","AE","BA","BE","CA","CE","DA","DE","EA","EE","FA","FE","GA","GE","HA","HE"};
    public static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";
    private static final int PIECE_IMAGE_SIZE = (int) ((3*60)*1.33);
    private static final int PIECE_IMAGE_SIZE_SMALL = (int) ((3*60)*1.33*0.5);

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
                    color = Color.TRANSPARENT;
            }else{
                if(i%2 == 0) // row 1 3
                    color = Color.TRANSPARENT;
            }
            pegs.getChildren().addAll(new Circle(PEG_SIZE,color), new Text(Character.toString(StepsGame.BOARD_STRING.charAt(i)) ));
            pegs.setLayoutX(margin_x + i%10*distance);
            pegs.setLayoutY(margin_y + i/10*distance);
            board.getChildren().add(pegs);
        }
        return board;
    }

    private Group setPieces(){
        Group pieces = new Group();
        for(int i = 0; i < imageList.length; i++){
            Image im = new Image(URI_BASE + imageList[i] + ".png");
            DraggbleImageView pc = new DraggbleImageView(im, PIECE_IMAGE_SIZE*0.45*(i%8),BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8));
            pc.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
            pc.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
            pieces.getChildren().add(pc);
        }
        return pieces;
    }

    class DraggbleImageView extends ImageView{
        private double mouseX;
        private double mouseY;
        private double posX;
        private double posY;

        public DraggbleImageView(Image image, double posX, double posY) {
            super(image);
            this.posX = posX;
            this.posY = posY;

            this.setX(this.posX);
            this.setY(this.posY);
            this.setOnScroll(event -> {            // scroll to change orientation
                this.setRotate((this.getRotate()+90)%360);;
                event.consume();
            });
            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
                this.setLayoutX(this.posX-mouseX);
                this.setLayoutY(this.posY-mouseY);
                this.setFitHeight(PIECE_IMAGE_SIZE);
                this.setFitWidth(PIECE_IMAGE_SIZE);
            });

            this.setOnMouseDragged(event -> {
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(diffX + this.posX - mouseX);
                this.setLayoutY(diffY + this.posY - mouseY);
            });

            this.setOnMouseReleased((MouseEvent event) -> {
                boolean pegFlag = false;



                if(pegFlag){
//                    Node nearPeg = NearestPeg();
                    //put on nearPeg
                }else {
                    this.setLayoutX(0);
                    this.setLayoutY(0);
                    this.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
                    this.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
                }
            });

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Group root = new Group();//        StackPane t = new StackPane();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        Group board = setBoard();
        for(Node st : board.getChildren()){
        }
        Group pieces = setPieces();
//        String url = URI_BASE + imageList[0] + ".png";

//        pieces.getChildren().add(pi);
        root.getChildren().addAll(board,pieces);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
