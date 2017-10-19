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

import java.util.*;

public class Board_test extends Application{
    private double FIND_RANGE = 60;
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final double PEG_SIZE = 21.5;
    private static final String[] imageList = {"AA","BA","CA","DA","EA","FA","GA","HA"};
    private String startString = "";
    private static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";
    private static final int PIECE_IMAGE_SIZE = (int) ((3*60)*1.33);
    private static final int PIECE_IMAGE_SIZE_SMALL = (int) ((3*60)*1.33*0.5);
    private static ArrayList<StackPane> pegList = new ArrayList<>();
    private boolean findNearFlag = false;
    private Circle highlighted = null;
    private LinkedList<String> pieceOnBoard = new LinkedList<>();
    private Group root = new Group();
    private String[] startDictionary={"BGKFCNCFlAFn","CGOGGQEDI","CFjBGKGAgHEl","EEfDHnBCT","DFOGGQEDI","EEfCHSAHQFDN","BGSHGQEHuGEO","BFOHBLADgCEnGGQ","CGOGDLAGjHEQ"};

    private void setStart(){
        int n;
        Random rnd =new Random();
        n=rnd.nextInt(startDictionary.length);
        startString = startDictionary[n];
        System.out.println(startString);
    }


    private Group setBoard(){
        int distance = 60;
        double margin_x = BOARD_WIDTH/20;
        double margin_y = BOARD_HEIGHT/20;
        Group board = new Group();
        for (int i = 0; i < StepsGame.BOARD_STRING.length();i++){
            StackPane pegs = new StackPane();
            Color color = Color.GRAY;
            if((i/10)%2==0) { //row 0 2 4
                if(i%2 != 0)
                    color = Color.LIGHTGRAY;
            }else{
                if(i%2 == 0) // row 1 3
                    color = Color.LIGHTGRAY;
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
        //String[] startArray = startString.split("(?<=\\G.{3})");
        //Collections.addAll(pieceOnBoard, startArray);
        ArrayList<String> usedPiece = new ArrayList<>();
        String[] startArray = null;
        //ArrayList<Character> usedPiece = new ArrayList<>();
        ArrayList<String> viablePiece = new ArrayList<>();
        //for(String s : startArray){
            //usedPiece.add(s);
        if(startString!="") {
            startArray = startString.split("(?<=\\G.{3})");
            Collections.addAll(pieceOnBoard, startArray);

            for (String s : startArray) {
                usedPiece.add(s);
            }
        }

        for(int i = 0; i < usedPiece.size(); i++){
            Image im = new Image(URI_BASE + usedPiece.get(i).charAt(0)+"A" + ".png");
            DraggbleImageView pc = new DraggbleImageView(im, PIECE_IMAGE_SIZE*0.45*(i%8),BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8),usedPiece.get(i),usedPiece.get(i).charAt(1));
            pc.setFitWidth(PIECE_IMAGE_SIZE);
            pc.setFitHeight(PIECE_IMAGE_SIZE);
            int x;
            x=usedPiece.get(i).charAt(2);
            if(x>90){
                x=x-65-7;
            }else{
                x=x-65;
            }

            pegList.get(x).getLayoutX();
            pc.setLayoutX(pegList.get(x).getLayoutX()- PIECE_IMAGE_SIZE/2 + PEG_SIZE);
            pc.setLayoutY(pegList.get(x).getLayoutY()- PIECE_IMAGE_SIZE/2 + PEG_SIZE);


            pc.setRotate(((int)usedPiece.get(i).charAt(1)-65)%4*90);

            if((int)usedPiece.get(i).charAt(1)>=69){
                System.out.println(i);
                pc.setImage(new Image(URI_BASE + usedPiece.get(i).charAt(0) + "E.png"));
            }
            pieces.getChildren().add(pc);
        }

        for(String s : imageList){
            boolean isUsed=false;
            for(int i=0;i<usedPiece.size();i++){
                if((int)s.charAt(0)==(int)usedPiece.get(i).charAt(0)){
                    isUsed=true;
                }
            }
            if(!isUsed){
                viablePiece.add(s);
        }}

        for(int i = 0; i < viablePiece.size(); i++){
            Image im = new Image(URI_BASE + viablePiece.get(i) + ".png");
            DraggbleImageView pc = new DraggbleImageView(im, PIECE_IMAGE_SIZE*0.45*(i%8),BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8),viablePiece.get(i));
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
        private char name;
        private char rot;
        private boolean flipState;
        private int rotAdd;
        private String pieceString;
        private boolean placeFlag;
        private char nearPegText;

        private StackPane nearPeg;
        DraggbleImageView(Image image, double posX, double posY, String name,char rotate){
            super(image);
            if (name.charAt(1) =='E')
                check = 1;
            this.name = name.charAt(0);
            this.orig_posX = posX;
            this.orig_posY = posY;
            this.posX = posX;
            this.posY = posY;
            this.setLayoutX(this.orig_posX);
            this.setLayoutY(this.orig_posY);
            this.rot = rotate;
            this.flipState = false;
            this.rotAdd = 0;
            this.placeFlag = false;
        }

        DraggbleImageView(Image image, double posX, double posY, String name) {
            super(image);
            if (name.charAt(1) =='E')
                check = 1;
            this.name = name.charAt(0);
            this.orig_posX = posX;
            this.orig_posY = posY;
            this.posX = posX;
            this.posY = posY;
            this.setLayoutX(this.orig_posX);
            this.setLayoutY(this.orig_posY);
            this.rot = 'A';
            this.flipState = false;
            this.rotAdd = 0;
            this.placeFlag = false;

            this.addEventHandler(MouseEvent.ANY, event -> {
                if(!placeFlag){
                    if(pieceOnBoard.contains(this.pieceString))
                        pieceOnBoard.remove(this.pieceString);
                }
//                System.out.println(pieceOnBoard);
            });

            this.setOnScroll(event -> {            // scroll to change orientation
                this.setRotate((this.getRotate()+90)%360);
                this.rotAdd += 1;
                char startChar = this.flipState? 'E':'A';
                if(this.rotAdd > 3) this.rotAdd = 0;
                this.rot = (char)((int)startChar + this.rotAdd);
                this.pieceString = placeFlag?  "" + this.name + this.rot + this.nearPegText :"" + this.name + this.rot;
                System.out.println(pieceString);
                event.consume();
            });

            this.setOnMousePressed(event -> {
                if(event.getButton()== MouseButton.SECONDARY) { //flip image when right clicked
                    placeFlag = false;
                    Flip(this.name,this.getLayoutX(), this.getLayoutY());
                    this.flipState = !this.flipState;
                }else { //left click
                    this.mouseX = event.getSceneX();
                    this.mouseY = event.getSceneY();

                    this.setLayoutX(2 * this.posX - mouseX);
                    this.setLayoutY(2 * this.posY - mouseY);
                    this.setFitHeight(PIECE_IMAGE_SIZE*1.1);
                    this.setFitWidth(PIECE_IMAGE_SIZE*1.1);
                }
            });

            this.setOnMouseDragged(event -> {
                if(event.getButton()!= MouseButton.SECONDARY) { // drag only for left click
                    placeFlag = false;
                    this.setLayoutX(getLayoutX() + event.getSceneX() - mouseX);
                    this.setLayoutY(getLayoutY() + event.getSceneY() - mouseY);
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    nearPeg = findNearestPeg(this, pegList);
                    this.toFront();
                    this.pieceString = placeFlag?  "" + this.name + this.rot + this.nearPegText :"" + this.name + this.rot;
//                    Circle nearCircle = (Circle) nearPeg.getChildren().get(0);
                    Text nearText = (Text) nearPeg.getChildren().get(1);
                    if (findNearFlag) {
//                        highlightNearestPeg(nearCircle);
                        this.nearPegText = nearText.getText().charAt(0);
                    }
                }
            });

            this.setOnMouseReleased((MouseEvent event) -> {
                boolean pegFlag = false;
                if(event.getButton()!= MouseButton.SECONDARY) { // only for left click
                    String tryPlacement = "";
                    for(String s:pieceOnBoard) tryPlacement += s;
                    tryPlacement += this.pieceString + this.nearPegText;
                    System.out.println(tryPlacement);
                    pegFlag = StepsGame.isPlacementSequenceValid(tryPlacement);
                    if(pegFlag){
                        // put on peg
                        this.setFitHeight(PIECE_IMAGE_SIZE);
                        this.setFitWidth(PIECE_IMAGE_SIZE);
                        this.posX = nearPeg.getLayoutX() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                        this.posY = nearPeg.getLayoutY() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                        this.setLayoutX(this.posX);
                        this.setLayoutY(this.posY);
                        if(!placeFlag) {
                            pieceBigFlag = true;
                            placeFlag = true;
                            this.pieceString = "" + this.name + this.rot + this.nearPegText;
                            pieceOnBoard.add(this.pieceString);
                        }
                    }else {
                        // return to stock
                        this.setLayoutX(this.orig_posX);
                        this.setLayoutY(this.orig_posY);
                        this.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
                        this.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
                        pieceBigFlag = false;
                        placeFlag = false;
                        if(pieceOnBoard.contains(this.pieceString))
                            pieceOnBoard.remove(this.pieceString);
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
        Button newGame = new Button("NewGame");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setStart();
                root.getChildren().clear();
                root.getChildren().addAll(setBoard(),setPieces(),setButtons());
            }
        });
        newGame.setLayoutX(BOARD_WIDTH * 0.85);
        newGame.setLayoutY(BOARD_HEIGHT*0.2);

        Button retry = new Button("Retry");
        retry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().clear();
                root.getChildren().addAll(setBoard(),setPieces(),setButtons());
            }
        });
        retry.setLayoutX(BOARD_WIDTH * 0.85);
        retry.setLayoutY(BOARD_HEIGHT*0.25);


        Button hint = new Button("Hint");
        hint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // display all viable pieces
            }
        });
        hint.setLayoutX(BOARD_WIDTH * 0.85);
        hint.setLayoutY(BOARD_HEIGHT*0.3);
        button.getChildren().addAll(hint,newGame,retry);

        return button;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
//        StackPane t = new StackPane();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        setStart();
        Group board = setBoard();

        Group pieces = setPieces();
        Group button = setButtons();

        DraggbleImageView p =  (DraggbleImageView) pieces.getChildren().get(0);
        Button b = (Button) button.getChildren().get(0);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(pieceOnBoard.toString());
            }
        });

        scene.addEventHandler(MouseEvent.ANY,event -> {

        });



        root.getChildren().addAll(board,pieces,button);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
