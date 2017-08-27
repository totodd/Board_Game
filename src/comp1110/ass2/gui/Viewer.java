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
    private final Image background = new Image(URI_BASE+"locations.png");
    private final String[] imageList = {"AA","AE","BA","BE","CA","CE","DA","DE","EA","EE","FA","FE","GA","GE","HA","HE"};

    private static final String URI_BASE = "file:assets/";
    private static final String URI_BASE2 = "file:src/comp1110/ass2/gui/assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    TextField textField;
    private Image piece;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        char[] pieces = placement.toCharArray();
        char[] Mask = new char[pieces.length/3];
        char[] Rot = new char[pieces.length/3];
        char[] Pos = new char[pieces.length/3];
        int j=0;
        int k=0;
        int l=0;
        int Pos_index=0;
        int row_index=0;

        for(int i = 0; i < pieces.length; i=i+3){
            //if
            Mask[j] = pieces[i];
            j++;
        }
        for(int i = 1; i < pieces.length; i=i+3){
            //if
            Rot[k] = pieces[i];
            k++;
        }
        for(int i = 2; i < pieces.length; i=i+3){
            //if
            Pos[l] = pieces[i];
            Pos_index = StepsGame.BOARD_STRING.indexOf(Pos[l]);
            row_index = Pos_index/10;
            Pos_index = Pos_index%10;
            l++;
        }
        for(int i =0;i<pieces.length/3;i++){
            ImageView[] pc = new ImageView[pieces.length/3];
            piece = new Image(URI_BASE2+Character.toString(Mask[i])+Character.toString(Rot[i])+".png");

            pc[i] = new ImageView();
            pc[i].setImage(piece);
            pc[i].setFitWidth(PIECE_IMAGE_SIZE);
            pc[i].setFitHeight(PIECE_IMAGE_SIZE);
            pc[i].setX(MARGIN_X+SQUARE_SIZE*Pos_index-SQUARE_SIZE);
            pc[i].setY(MARGIN_Y+SQUARE_SIZE*row_index-SQUARE_SIZE);
            controls.getChildren().add(pc[i]);
        }
    }


    void BGInitizlizor(){
        ImageView[] pc = new ImageView[16];
        for(int i =0;i<16;i++){
            piece = new Image(URI_BASE2+imageList[i]+".png");
            pc[i] = new ImageView();
            pc[i].setImage(piece);
            pc[i].setFitWidth(SQUARE_SIZE*0.75);
            pc[i].setFitHeight(SQUARE_SIZE*0.75);
            pc[i].setX(15+SQUARE_SIZE*i*0.75);
            pc[i].setY(10);
            controls.getChildren().add(pc[i]);
        }
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        ImageView bg = new ImageView();
        bg.setImage(background);
        bg.setFitWidth(PIECE_IMAGE_SIZE * 10/3.429);
        bg.setFitHeight(PIECE_IMAGE_SIZE * 5/3);
        bg.setX(30);
        bg.setY(52.5);
        controls.getChildren().add(bg);
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
        controls.getChildren().add(hb);

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
