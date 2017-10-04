package comp1110.ass2.gui;

import comp1110.ass2.StepsGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    public static final int SQUARE_SIZE = 60;
    public static final int PIECE_IMAGE_SIZE = (int) ((3*SQUARE_SIZE)*1.33);
    private static final int MARGIN_X = BOARD_WIDTH/20;
    private static final int MARGIN_Y = BOARD_HEIGHT/20;
    public static final String[] imageList = {"AA","AE","BA","BE","CA","CE","DA","DE","EA","EE","FA","FE","GA","GE","HA","HE"};
    public static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";

    private final Group root = new Group();
    private Group button = new Group();
    private final Group controls = new Group();
    private final Group shapes = new Group();
    private final Group initializor = new Group();
    TextField textField;
    private static Image piece;
    private static Circle peg;
    private static Text T_peg;
    private double oldX, oldY;


    // FIXME Task 7: Implement a basic playable Steps Game in JavaFX that only allows pieces to be placed in valid places


    // FIXME Task 8: Implement starting placements

    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting starting placements
    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) {

        shapes.getChildren().clear();
        if(!StepsGame.isPlacementSequenceValid(placement)){
            System.out.println("Invalid placement: "+placement);
            System.out.println("Please try again!");
        }
        else {
            char[] pieces = placement.toCharArray();
            char[] Mask = new char[pieces.length / 3];
            char[] Rot = new char[pieces.length / 3];
            char[] Pos = new char[pieces.length / 3];
            int j = 0;
            int[] Pos_index = new int[pieces.length / 3];
            int[] row_index = new int[pieces.length / 3];
            int[] rotDegree = new int[pieces.length / 3];
            ImageView[] pc = new ImageView[pieces.length / 3];
            for (int i = 0; i < pieces.length; i = i + 3) {
                Mask[j] = pieces[i];
                Rot[j] = pieces[i + 1];
                if (Rot[j] >= 'A' && Rot[j] < 'E') {
                    rotDegree[j] = 90 * (Rot[j] - 'A');
                    Rot[j] = 'A';
                } else if (Rot[j] >= 'E' && Rot[j] <= 'H') {
                    rotDegree[j] = 90 * (Rot[j] - 'A');
                    Rot[j] = 'E';
                }
                Pos[j] = pieces[i + 2];
                Pos_index[j] = StepsGame.BOARD_STRING.indexOf(Pos[j]);
                row_index[j] = Pos_index[j] / 10;
                Pos_index[j] = Pos_index[j] % 10;
                j++;
            }
            for (int i = 0; i < pieces.length / 3; i++) {
                piece = new Image(URI_BASE + Character.toString(Mask[i]) + Character.toString(Rot[i]) + ".png");
                pc[i] = new ImageView();
                pc[i].setImage(piece);
                pc[i].setFitWidth(PIECE_IMAGE_SIZE);
                pc[i].setFitHeight(PIECE_IMAGE_SIZE);
                pc[i].setX(MARGIN_X + SQUARE_SIZE * Pos_index[i] - SQUARE_SIZE);
                pc[i].setY(MARGIN_Y + SQUARE_SIZE * row_index[i] - SQUARE_SIZE);
                pc[i].setOpacity(0.75f);
                pc[i].setRotate(rotDegree[i]);
                shapes.getChildren().add(pc[i]);
            }
        }
    }


    void pieceInitializor(ImageView pc, int count){
        //ImageView[] pc = new ImageView[16];


        //for(int i =0;i<16;i++){
            //piece = new Image(URI_BASE+imageList[i]+".png");
            //pc[i] = new ImageView();
            //pc[i].setImage(piece);
        //    pc[i].setFitWidth(PIECE_IMAGE_SIZE*0.5);
//            pc[i].setFitHeight(PIECE_IMAGE_SIZE*0.5);
//            pc[i].setX(PIECE_IMAGE_SIZE*0.45*(i%8));
//            pc[i].setY(BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8));
        piece = new Image(URI_BASE+imageList[count]+".png");
        pc.setImage(piece);
        pc.setFitWidth(PIECE_IMAGE_SIZE*0.5);
        pc.setFitHeight(PIECE_IMAGE_SIZE*0.5);
        pc.setX(PIECE_IMAGE_SIZE*0.45*(count%8));
        pc.setY(BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(count/8));

            initializor.getChildren().add(pc);
            pc.setOnMousePressed(event -> {
                oldX = event.getSceneX();
                oldY = event.getSceneY();
                pc.setFitWidth(PIECE_IMAGE_SIZE);
                pc.setFitHeight(PIECE_IMAGE_SIZE);
            });
            pc.setOnMouseDragged(event -> {
                double X = event.getSceneX() - oldX;
                double Y = event.getSceneY() - oldY;
                pc.setLayoutX(pc.getLayoutX()+X);
                pc.setLayoutY(pc.getLayoutY()+Y);
                oldX = event.getSceneX();
                oldY = event.getSceneY();
                event.consume();
            });

        //}
    }
    /**
     * Initialize the "board" image as the background; list all original pieces
     */
    void BGInitizlizor(){
        for(int i =0; i <25; i++) {
            if(i<15){
                peg = new Circle(21.5, Color.LIGHTGRAY);
                peg.setCenterX(MARGIN_X + SQUARE_SIZE + 2 * SQUARE_SIZE*(i%5));
                peg.setCenterY(MARGIN_Y + SQUARE_SIZE + 2 * SQUARE_SIZE*(i/5));
                initializor.getChildren().add(peg);
            }
            else{
                peg = new Circle(21.5, Color.LIGHTGRAY);
                peg.setCenterX(MARGIN_X + SQUARE_SIZE*2 + 2 * SQUARE_SIZE*(i%5));
                peg.setCenterY(MARGIN_Y + SQUARE_SIZE*2 + 2 * SQUARE_SIZE*((i-15)/5));
                initializor.getChildren().add(peg);
            }
        }
        for(int i = 0; i < 50; i++){
            T_peg = new Text(String.valueOf(StepsGame.BOARD_STRING.charAt(i)));
            T_peg.setX(MARGIN_X-5 + SQUARE_SIZE + SQUARE_SIZE*(i%10));
            T_peg.setY(MARGIN_Y+5 + SQUARE_SIZE + SQUARE_SIZE*(i/10));
            T_peg.setFont(Font.font(20));
            initializor.getChildren().add(T_peg);
        }
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                shapes.getChildren().clear();
                makePlacement("");
            }
        });
        reset.setLayoutX(BOARD_WIDTH * 0.85);
        reset.setLayoutY(MARGIN_Y*2);
        Button hint = new Button("Hint");
        hint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // display all viable pieces
            }
        });
        hint.setLayoutX(BOARD_WIDTH * 0.85);
        hint.setLayoutY(MARGIN_Y*3);
        Button starting = new Button("Starting\nPlacements");
        starting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // set starting placements
            }
        });
        starting.setLayoutX(BOARD_WIDTH * 0.85);
        starting.setLayoutY(MARGIN_Y*4);

        button.getChildren().addAll(hint,reset,starting);
        controls.getChildren().addAll(button,initializor,shapes);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(controls);
        for(int i = 0; i <16; i ++) {
            pieceInitializor(new ImageView(), i);
        }
        BGInitizlizor();
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
