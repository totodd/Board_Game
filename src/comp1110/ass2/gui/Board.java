// This code and its idea are created and own by the following authors:
// Tao Chen (u6074544),
// Sheng Xu (u5538588),
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.
package comp1110.ass2.gui;

import comp1110.ass2.StepsGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;



public class Board extends Application{
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
    private int hintCount=5;
    //    private boolean requireCal = false;
//    private Set<String> lastHint = null;
    private Circle highlighted = null;
    //    private LinkedList<String> pieceOnBoard = new LinkedList<>();
    private LinkedHashMap<Character, String> pieceOnBoardMap = new LinkedHashMap<>();
    private Group root = new Group();
    private String[] startDictionary={"BGKFCNCFlAFn","CGOGGQEDI","CFjBGKGAgHEl","EEfDHnBCT","DFOGGQEDI","EEfCHSAHQFDN","BGSHGQEHuGEO","BFOHBLADgCEnGGQ","CGOGDLAGjHEQ"};
    private final Slider difficulty = new Slider(1,3,2);

    // music source http://www.orangefreesounds.com/short-electronic-backgroud-music/
    private static final String LOOP_URI = Board.class.getResource("assets/" + "Short-electronic-background-music (1).wav").toString();
    private AudioClip loop;

    /* game variables */
    private boolean loopPlaying = false;

    private void setStart(){

        Double a = difficulty.getValue();
        int b =(6-a.intValue())*3;
        int n;
        Random rnd =new Random();
        do{
            n=rnd.nextInt(startDictionary.length);
            startString = startDictionary[n];
        }while(startString.length()!=b);

        pieceOnBoardMap.clear();
    }

    /**
     * Author: Tao Chen
     * set all the pegs on the board and make them aligned with each other
     * @return board group
     */
    private Group setBoard(){
        int distance = 60;
        double margin_x = BOARD_WIDTH/5.5;
        double margin_y = BOARD_HEIGHT/8;
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

            Text text=new Text(Character.toString(StepsGame.BOARD_STRING.charAt(i)));
            text.setOpacity(0);
            pegs.getChildren().addAll(new Circle(PEG_SIZE,color),text);
            pegs.setLayoutX(margin_x + i%10*distance);
            pegs.setLayoutY(margin_y + i/10*distance);
            board.getChildren().add(pegs);
            pegList.add(pegs);
        }
        return board;
    }

    /**
     *Author: Chen Chen
     * Add a piece placement to a fixed group which cannot be moved by mouse
     *
     * @param placement
     * @param pieces
     */
    private void addFixed(String placement,Group pieces){
        Image im = new Image(URI_BASE + placement.charAt(0)+"A" + ".png");
        DraggbleImageView pc = new DraggbleImageView(im, 10,10,placement,placement.charAt(1));
        pc.setFitWidth(PIECE_IMAGE_SIZE);
        pc.setFitHeight(PIECE_IMAGE_SIZE);
        int x;
        x=placement.charAt(2);
        if(x>90){
            x=x-65-7;
        }else{
            x=x-65;
        }

        pc.setLayoutX(pegList.get(x).getLayoutX()- PIECE_IMAGE_SIZE/2 + PEG_SIZE);
        pc.setLayoutY(pegList.get(x).getLayoutY()- PIECE_IMAGE_SIZE/2 + PEG_SIZE);


        pc.setRotate(((int)placement.charAt(1)-65)%4*90);

        if((int)placement.charAt(1)>=69){
//                System.out.println(i);
            pc.setImage(new Image(URI_BASE + placement.charAt(0) + "E.png"));
        }
        pieces.getChildren().add(pc);
    }

    /**
     * Author: Tao Chen, Chen Chen
     * set the pieces in default rotation both in the stock and on the board
     * @return piece group
     */
    private Group setPieces(){
        Group pieces = new Group();
        ArrayList<String> usedPiece = new ArrayList<>();
        String[] startArray = null;
        ArrayList<String> viablePiece = new ArrayList<>();
        if(startString!="") {
            startArray = startString.split("(?<=\\G.{3})");
//            Collections.addAll(pieceOnBoard, startArray);
            for (String s : startArray) {
                usedPiece.add(s);
                pieceOnBoardMap.put(s.charAt(0),s);
            }
        }

        for(int i = 0; i < usedPiece.size(); i++){
            addFixed(usedPiece.get(i),pieces);
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
            DraggbleImageView pc = new DraggbleImageView(im, PIECE_IMAGE_SIZE_SMALL+PIECE_IMAGE_SIZE*0.45*(i%8),20+BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8),viablePiece.get(i));
            pc.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
            pc.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
            pieces.getChildren().add(pc);
        }
        return pieces;
    }


    /**
     * Author: Tao Chen, Chen Chen, Sheng Xu
     *
     * Make the pieces draggable and full function for the use of mouse
     */
    class DraggbleImageView extends ImageView{
        private boolean pieceBigFlag = false;
        private boolean moveFlag = false;
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

        /**
         * Author: Chen Chen
         *  Constructor for pieces with initial rotation and will keep fixed on board
         * @param image image of piece
         * @param posX location x to put
         * @param posY location y to put
         * @param name piece name
         * @param rotate piece rotation
         */
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

        /**
         *  Constructor for pieces in the stock,  are movable pieces, with mouse functions
         * @param image image of piece
         * @param posX location x to put
         * @param posY location y to put
         * @param name piece name
         */
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
                    if(pieceOnBoardMap.containsKey(this.name))
                        pieceOnBoardMap.remove(this.name);
                }

                if(moveFlag){
                    this.setFitHeight(PIECE_IMAGE_SIZE*1.1);
                    this.setFitWidth(PIECE_IMAGE_SIZE*1.1);
                }else {
                    if (pieceBigFlag) {
                        this.setFitHeight(PIECE_IMAGE_SIZE);
                        this.setFitWidth(PIECE_IMAGE_SIZE);
                    } else {
                        this.setFitHeight(PIECE_IMAGE_SIZE_SMALL);
                        this.setFitWidth(PIECE_IMAGE_SIZE_SMALL);
                    }
                }
                if(pieceOnBoardMap.size() == 8){
                    root.getChildren().get(2).setOpacity(0);
                    root.getChildren().get(3).setOpacity(0);
                    root.getChildren().add(completion());
                }
                System.out.println(pieceOnBoardMap.values().toString());
                System.out.println(nearPegText);
            });

            this.setOnScroll(event -> {            // scroll to change orientation
                if(!placeFlag) {
                    System.out.println(pieceString);
                    if(pieceBigFlag) {
                        moveFlag = true;
                    }else{
                        moveFlag = false;
                    }
                    this.setRotate((this.getRotate() + 90) % 360);
                    this.rotAdd += 1;
                    char startChar = this.flipState ? 'E' : 'A';
                    if (this.rotAdd > 3) this.rotAdd = 0;
                    this.rot = (char) ((int) startChar + this.rotAdd);
                    this.pieceString = "" + this.name + this.rot + this.nearPegText;
                    event.consume();
                }
            });


            this.setOnMousePressed(event -> {
                if(event.getButton()== MouseButton.SECONDARY) { //flip image when right clicked
                    if(!placeFlag) {
//                        moveFlag = true;
                        Flip(this.name, this.getLayoutX(), this.getLayoutY());
                        this.flipState = !this.flipState;
                    }
                }else { //left click
                    moveFlag = true;
                    placeFlag = false;
                    pieceBigFlag = true;
                    this.mouseX = event.getSceneX();
                    this.mouseY = event.getSceneY();
                    if(!pieceBigFlag) {
                        this.posX = this.getLayoutX();
                        this.posY = this.getLayoutY();
                        this.setLayoutX(2 * this.posX - mouseX);
                        this.setLayoutY(2 * this.posY - mouseY);
                    }
                    else{
                        this.setLayoutX(event.getSceneX()-PIECE_IMAGE_SIZE/2-PEG_SIZE*1.4);
                        this.setLayoutY(event.getSceneY()-PIECE_IMAGE_SIZE/2);
                    }
                }
            });

            this.setOnMouseDragged(event -> {
                if(event.getButton()!= MouseButton.SECONDARY) { // drag only for left click
                    placeFlag = false;
                    moveFlag = true;
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
                if(event.getButton()!= MouseButton.SECONDARY) { // only for left click
                    boolean pegFlag = false;
                    moveFlag = false;
                    String tryPlacement = "";
                    for(String s:pieceOnBoardMap.values()) tryPlacement += s;
//                    for(String s:pieceOnBoard) tryPlacement += s;
                    tryPlacement += "" + this.name + this.rot + this.nearPegText;
//                    System.out.println(tryPlacement);
                    pegFlag = StepsGame.isPlacementSequenceValid(tryPlacement);

                    if(pegFlag){
                        // put on peg
                        this.setFitHeight(PIECE_IMAGE_SIZE);
                        this.setFitWidth(PIECE_IMAGE_SIZE);
                        this.posX = nearPeg.getLayoutX() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                        this.posY = nearPeg.getLayoutY() - PIECE_IMAGE_SIZE/2 + PEG_SIZE;
                        this.setLayoutX(this.posX);
                        this.setLayoutY(this.posY);

                        pieceBigFlag = true;
                        placeFlag = true;
                        this.pieceString = "" + this.name + this.rot + this.nearPegText;
//                        pieceOnBoard.add(this.pieceString);
                        pieceOnBoardMap.put(this.name,this.pieceString);
//                        requireCal = true;


                    }else {
                        // return to stock
                        this.setLayoutX(this.orig_posX);
                        this.setLayoutY(this.orig_posY);
                        pieceBigFlag = false;
                        placeFlag = false;
                    }
                }
            });


        }

        /**
         * Author: Sheng Xu
         * Flip the piece image according to the click times
         * @param pcs   First character of the current piece
         * @param x     X layout of the piece
         * @param y     Y layout of the piece
         */
        void Flip(char pcs, double x, double y){
            if(check % 2 == 0)
                this.setImage(new Image(URI_BASE + pcs + "E.png"));
            else if(check % 2 == 1)
                this.setImage(new Image(URI_BASE + pcs + "A.png"));
            check ++;
            this.setLayoutX(x);
            this.setLayoutY(y);
        }

        /**
         * Author: Tao Chen
         * Calculate the distance between piece and given location
         * @param x location x
         * @param y location y
         * @return the distance
         */
        private double distance(double x, double y){
            double centerX = getLayoutX()+PIECE_IMAGE_SIZE/2;
            double centerY = getLayoutY()+PIECE_IMAGE_SIZE/2;
            double deltaX = (x+PEG_SIZE/2) - centerX;
            double deltaY = (y+PEG_SIZE/2) - centerY;
            return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        }
    }

    /**
     * Author: Tao Chen
     * find the nearest peg from the dragged piece
     * @param piece the dragged piece
     * @param pegs the peg list on the board
     * @return the nearest peg
     */
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
     * Author: Tao Chen
     * hightlight the nearest Peg for DEBUG
     * @param nearPeg nearest peg
     */
    private void highlightNearestPeg(Circle nearPeg){
        if(highlighted!=null){
            highlighted.setFill(Color.LIGHTGRAY);
        }
        highlighted = nearPeg;
        highlighted.setFill(Color.GREEN);

    }

    /**
     * Author: Sheng Xu
     * Get the viable hint by given placed pieces
     * @return set of viable pieces
     */
    private Set<String> getHint(){
//        if(lastHint==null) {
        System.out.println("new hint");
//            requireCal = false;
        String a = "";
        for (String s : pieceOnBoardMap.values()) a += s;
        StepsGame.viableSinglePlacement();
        Set<String> nextPc = new HashSet<>();
        String[] fin;
        try {
            fin = StepsGame.getSolutions(a);
            for (String f : fin) {
                Set<String> temp = StepsGame.getViablePiecePlacements(a, f);
                nextPc.addAll(temp);
            }
//                lastHint = nextPc;
            return nextPc;
        } catch (IndexOutOfBoundsException x) {
            System.out.println("Bad placement, not solution!");
        } catch (Exception x) {
            System.out.println("Bad placement, not solution!");
        }
        return null;
//        }else{
//            System.out.println("old hint");
//            System.out.println(lastHint.toString());
//            return lastHint;
//        }
    }

    /**
     * Author: Tao Chen
     * Print the congratulation message after game finished
     * @return the congratulation group
     */
    private Group completion(){
        double margin_x = BOARD_WIDTH/5.5;
        double margin_y = BOARD_HEIGHT/8;
        Group congra = new Group();
        Text congText = new Text( margin_x,BOARD_HEIGHT/2 +120,"Congratulations!!!");
        congText.setFill(Color.RED);
        congText.setFont(Font.font ("Serif", 80));
        congra.getChildren().add(congText);
        return congra;
    }

    private Group instruction(){
        Group inst = new Group();
        String inst_text = "left mouse click --> pick up piece\n" +
                "right mouse click --> flip the piece\n" +
                "mouse scroll --> rotate the piece\n" +
                "left mouse release --> drop the piece\n";
        Text congText = new Text( BOARD_WIDTH/2.8,BOARD_HEIGHT -278,inst_text);
        congText.setFill(Color.GRAY);
        congText.setFont(Font.font ("Serif", 12));
        inst.getChildren().add(congText);

        return inst;
    }


    /**
     * Author: Chen Chen
     * add all buttons to Group button
     *
     */
    private Group setButtons(){
        Group button = new Group();
        Button newGame = new Button("NewGame");
        Group inst = instruction();
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setStart();
                root.getChildren().clear();
                root.getChildren().addAll(setBoard(),setPieces(),setButtons(), inst);
//                lastHint = null;
                hintCount = 5;
            }
        });
        newGame.setLayoutX(BOARD_WIDTH * 0.85-100);
        newGame.setLayoutY(355+BOARD_HEIGHT*0.2);

        Button retry = new Button("Retry");
        retry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().clear();
                root.getChildren().addAll(setBoard(),setPieces(),setButtons(), inst);
                hintCount = 5;
//                lastHint = null;
            }
        });
        retry.setLayoutX(BOARD_WIDTH * 0.85-100);
        retry.setLayoutY(355+BOARD_HEIGHT*0.25);

        /**
         * Author: Chen Chen, Tao Chen, Xu Sheng
         * Implement hint with fading effect
         */
        Button hint = new Button("Hint");
        hint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("start calculating hint");
                Group hint = new Group();
                Set<String> hintPlaces = getHint();
                if(hintPlaces != null & hintCount != 0) {
                    hintCount -= 1;
                    for (String s : hintPlaces) addFixed(s, hint);
                    double red = 0.05;
                    Double count = (1 - 0.3) / red;

                    root.getChildren().add(hint);

                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),
                            ae -> {

                                hint.setOpacity(reduceOpa(hint.getOpacity(), red));
                                if (hint.getOpacity() < 0.4) {
                                    root.getChildren().remove(hint);
                                    hint.setOpacity(1.0);
                                }
                            }));

                    timeline.setCycleCount(count.intValue());
                    timeline.play();
                }
            }

        });
        hint.setLayoutX(BOARD_WIDTH * 0.85-100);
        hint.setLayoutY(355+BOARD_HEIGHT*0.3);
        Text count = new Text(BOARD_WIDTH * 0.745, BOARD_HEIGHT*0.68, "Chances: "+String.valueOf(hintCount));
        count.setFill(Color.BLACK);
        count.setFont(Font.font ("Serif", 12));
        count.setOpacity(0);
        button.getChildren().addAll(hint,newGame,retry,count);

        /**
         * Author: Chen Chen
         * All difficulty slider
         */
        difficulty.setMin(1);
        difficulty.setMax(3);
        //difficulty.setValue(0);
        difficulty.setShowTickLabels(false);
        difficulty.setShowTickMarks(false);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(1);
        difficulty.setSnapToTicks(true);

        difficulty.setLayoutX(BOARD_WIDTH/2 - 80);
        difficulty.setLayoutY(BOARD_HEIGHT - 80);
        button.getChildren().add(difficulty);

        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GREY);
        difficultyCaption.setLayoutX(BOARD_WIDTH/2 - 150);
        difficultyCaption.setLayoutY(BOARD_HEIGHT - 80);
        button.getChildren().add(difficultyCaption);

        final Label difficultyEasy = new Label("Easy");
        difficultyEasy.setTextFill(Color.GREY);
        difficultyEasy.setLayoutX(BOARD_WIDTH/2 - 80);
        difficultyEasy.setLayoutY(BOARD_HEIGHT - 60);
        button.getChildren().add(difficultyEasy);
        final Label difficultyHard = new Label("Hard");
        difficultyHard.setTextFill(Color.GREY);
        difficultyHard.setLayoutX(BOARD_WIDTH/2+40);
        difficultyHard.setLayoutY(BOARD_HEIGHT - 60);
        button.getChildren().add(difficultyHard);
        return button;
    }
    /**
     * Author: Chen Chen
     * method used in fading effect
     */
    double reduceOpa(double opa, double reduceAmount){
        return opa-reduceAmount;
    }

    /**
     * Set up event handlers for the main game
     * This code is adopted from Assignment 1
     * @param scene  The Scene used by the game.
     */
    private void setUpHandlers(Scene scene) {
        /* create handlers for key press and release events */
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                toggleSoundLoop();
                event.consume();
            } else if (event.getCode() == KeyCode.Q) {
                Platform.exit();
                event.consume();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                event.consume();
            }
        });
    }

    /**
     * Set up the sound loop (to play when the 'M' key is pressed)
     * This code is adopted from Assignment 1
     */
    private void setUpSoundLoop() {
        try {
            loop = new AudioClip(LOOP_URI);
            loop.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened ("+LOOP_URI+"): "+e);
        }
    }

    /**
     * Turn the sound loop on or off
     */
    private void toggleSoundLoop() {
        if (loopPlaying)
            loop.stop();
        else
            loop.play();
        loopPlaying = !loopPlaying;
    }



    /**
     * Author: Chen Chen, Tao Chen, Xu Sheng
     * run at start
     * basic board function
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ-STEPS");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        setUpHandlers(scene);
        setUpSoundLoop();
        Group button = setButtons();
        setStart();
        Group board = setBoard();
        Group pieces = setPieces();
        Group inst = instruction();
        root.getChildren().addAll(board,pieces,button, inst);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
