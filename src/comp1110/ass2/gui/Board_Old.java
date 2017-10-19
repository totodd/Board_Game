// This code and its idea are created and own by the following authors:
// Tao Chen (u6074544),
// Sheng Xu (u5538588),
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.

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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Board_Old extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    public static final int SQUARE_SIZE = 60;
    public static final int PIECE_IMAGE_SIZE = (int) ((3*SQUARE_SIZE)*1.33);
    private static final int MARGIN_X = BOARD_WIDTH/20;
    private static final int MARGIN_Y = BOARD_HEIGHT/20;
    public static String[] imageList = {"AA","BA","CA","DA","EA","FA","GA","HA"};
    public static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";
    public static String img = "";
    private final Group root = new Group();
    private Group button = new Group();
    private final Group controls = new Group();
    private final Group shapes = new Group();
    private final Group initializor = new Group();
    TextField textField;
    private static Image piece;
    private static Circle peg;
    private static Text T_peg;
    double oldX,oldY;


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
    void pieceEvents(ImageView pc, double homeX, double homeY,String img){
        pc.setOnScroll(event -> {            // scroll to change orientation
            pc.setRotate((pc.getRotate()+90)%360);;
            event.consume();
        });
        pc.setOnMousePressed(event -> {
            if(event.getButton()== MouseButton.PRIMARY) {
                if (pc.getFitWidth() != PIECE_IMAGE_SIZE) {
                    pc.setFitWidth(PIECE_IMAGE_SIZE);
                    pc.setFitHeight(PIECE_IMAGE_SIZE);
                    pc.setLayoutX(pc.getLayoutX() - PIECE_IMAGE_SIZE / 4);
                    pc.setLayoutY(pc.getLayoutY() - PIECE_IMAGE_SIZE / 4);
                } else {
                    pc.setLayoutX(pc.getLayoutX());
                    pc.setLayoutY(pc.getLayoutY());
                }
                oldX = event.getSceneX();
                oldY = event.getSceneY();
            }
            else if(event.getButton()== MouseButton.SECONDARY){ //change image (flip) when right click
                System.out.println(img);
                String piec = String.valueOf(img.charAt(0));
                if(img.charAt(1) == 'A'){
                    img.replace(img.charAt(1), 'E');
                }
                if(img.charAt(1) == 'E'){
                    img.replace(img.charAt(1), 'A');
                }
                System.out.println(img);
                piece = new Image(URI_BASE+img+".png");
                pc.setImage(piece);
            }
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

        pc.setOnMouseReleased(event -> {     // snap to nearest peg or home position
            snapToPeg(pc);

        });
    }
    private void snapToPeg(ImageView pc){

    }
    void pieceInitializor(){
        ImageView pc;
        for(int i =0;i<imageList.length;i++){
            double homeX = PIECE_IMAGE_SIZE*0.45*(i%8);
            double homeY = BOARD_HEIGHT-PIECE_IMAGE_SIZE+PIECE_IMAGE_SIZE*0.45*(i/8);
            img = imageList[i];
            piece = new Image(URI_BASE+img+".png");
            pc = new ImageView();
            pc.setImage(piece);
            pc.setFitWidth(PIECE_IMAGE_SIZE/2);
            pc.setFitHeight(PIECE_IMAGE_SIZE/2);
            pc.setX(homeX);
            pc.setY(homeY);
            pieceEvents(pc,homeX,homeY,img);
            initializor.getChildren().add(pc);
        }
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

        BGInitizlizor();
        pieceInitializor();
//        controls.getChildren().addAll(initializor);
//        Label label1 = new Label("Placement:");
//        textField = new TextField ();
//        textField.setPrefWidth(300);
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                shapes.getChildren().clear();
//                makePlacement("");
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
        button.getChildren().addAll(hint,reset);
//        HBox hb = new HBox();
//        hb.getChildren().add(button);
//        hb.setSpacing(100);
//        hb.setLayoutX(BOARD_WIDTH * 0.85);
//        hb.setLayoutY(MARGIN_Y);
        controls.getChildren().addAll(button,initializor,shapes);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
