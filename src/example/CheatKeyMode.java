package example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheatKeyMode extends GenericScreen{

    private final int SIZE = 500;
    private final Paint BACKGROUND = Color.AZURE;
    private final String CHEAT_KEY_DESCRIPTIONS = "'L' - add another life\n" +
                                                    "'R' - reset the level\n" +
                                                    "'T' - give infinite time\n" +
                                                    "'D' - Destroy any block by clicking on it\n" +
                                                    "# keys - Jump to level of that number\n" +
                                                    "'P' - gain access to all power ups\n"+
                                                    "BACKSPACE always will return to the start menu";


    private Scene myScene;

    public Scene getMyScene() {
        return myScene;
    }

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
