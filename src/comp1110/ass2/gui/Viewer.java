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

    }



    void BGInitizlizor(){
        ImageView[] pc = new ImageView[16];
        String[] imageList = {"AA","AE","BA","BE","CA","CE","DA","DE","EA","EE","FA","FE","GA","GE","HA","HE"};
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

        ImageView bg = new ImageView();
        bg.setImage(background);
        bg.setFitWidth(PIECE_IMAGE_SIZE * 10/3.429);
        bg.setFitHeight(PIECE_IMAGE_SIZE * 5/3);
        bg.setX(30);
        bg.setY(52.5);

        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        controls.getChildren().add(bg);
        BGInitizlizor();
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
