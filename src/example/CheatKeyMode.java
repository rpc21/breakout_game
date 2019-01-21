package example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


/**
 * CheatKeyMode is a class that displays a screen with all the Cheat Keys on it and how to use them
 */
public class CheatKeyMode extends GenericScreen{

    private final int SIZE = 500;
    private final Paint BACKGROUND = Color.AZURE;
    private final String CHEAT_KEY_DESCRIPTIONS = "'L' - add another life (only accessible from splash screen)\n" +
                                                    "'R' - reset the level (only accessible from splash screen)\n" +
                                                    "'T' - give infinite time (only accessible from splash screen)\n" +
                                                    "# keys - Jump to level of that number (only accessible from " +
                                                    "splash screen)\n" +
                                                    "'P' - gain access to all power ups (accessible in level)\n"+
                                                    "BACKSPACE always will return to the start menu";


    private Scene myScene;

    /**
     * Getter for myScene
     * @return myScene
     */
    public Scene getMyScene() {
        return myScene;
    }

    /**
     * Initialize the Cheat Key Screen
     * @param stageManager
     */
    public CheatKeyMode(StageManager stageManager) {
        super(stageManager);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        var root = new StackPane();
        var scene = new Scene(root,width,height,BACKGROUND);

        VBox vBox = new VBox(SIZE/40.0);
        vBox.getChildren().add(new Text(CHEAT_KEY_DESCRIPTIONS));
        vBox.setAlignment(Pos.CENTER);

        root.getChildren().add(vBox);

        myScene = scene;

    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        super.handleKeyInput(code);
    }
}
