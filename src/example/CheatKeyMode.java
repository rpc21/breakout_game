package example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CheatKeyMode {

    private final int SIZE = 500;
    private final Color BACKGROUND = Color.AZURE;
    private final String CHEAT_KEY_DESCRIPTIONS = "'L' - add another life\n" +
                                                    "'R' - reset the level\n" +
                                                    "'T' - give infinite time\n" +
                                                    "'D' - Destroy any block by clicking on it\n" +
                                                    "# keys - Jump to level of that number\n" +
                                                    "'P' - gain access to all power ups";


    private Scene myScene;

    public Scene getMyScene() {
        return myScene;
    }

    public CheatKeyMode() {
        myScene = setUpScene(SIZE, SIZE, BACKGROUND);
    }

    private Scene setUpScene(int width, int height, Color background) {
        var root = new StackPane();
        var scene = new Scene(root,width,height,background);

        VBox vBox = new VBox(SIZE/40.0);
        vBox.getChildren().add(new Text(CHEAT_KEY_DESCRIPTIONS));
        vBox.setAlignment(Pos.CENTER);

        root.getChildren().add(vBox);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return scene;

    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.BACK_SPACE){

        }
    }
}
