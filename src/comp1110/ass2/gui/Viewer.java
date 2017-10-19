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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * A very simple viewer for piece placements in the steps game.
 *
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int PIECE_IMAGE_SIZE = (int) ((3*SQUARE_SIZE)*1.33);
    private static final int VIEWER_WIDTH = 750;
    private static final int VIEWER_HEIGHT = 500;
    private static final int MARGIN_X = 50;
    private static final int MARGIN_Y = 70;
    private final String[] imageList = {"AA","AE","BA","BE","CA","CE","DA","DE","EA","EE","FA","FE","GA","GE","HA","HE"};
    private static final String URI_BASE = "file:src/comp1110/ass2/gui/assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group shapes = new Group();
    private final Group initializor = new Group();
    TextField textField;
    private Image piece;
    private Circle peg;
    private Text T_peg;


    /**
     * Author: Sheng Xu
     *
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer

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

    /**
     * Author: Sheng Xu
     *
     * Initialize the "board" image as the background; list all original pieces
     */
    void BGInitizlizor(){
        ImageView[] pc = new ImageView[16];
        for(int i =0;i<16;i++){
            piece = new Image(URI_BASE+imageList[i]+".png");
            pc[i] = new ImageView();
            pc[i].setImage(piece);
            pc[i].setFitWidth(SQUARE_SIZE*0.75);
            pc[i].setFitHeight(SQUARE_SIZE*0.75);
            pc[i].setX(15+SQUARE_SIZE*i*0.75);
            pc[i].setY(10);
            initializor.getChildren().add(pc[i]);
        }

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
     * Author: Sheng Xu
     *
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        BGInitizlizor();
        Label label1 = new Label("Placement:");
        textField = new TextField ();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().addAll(hb,initializor,shapes);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
