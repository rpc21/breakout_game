package example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PauseScreen extends GenericScreen {

    private final String PAUSE_SCREEN_TEXTS = "'L' - add another life\n" +
            "'R' - reset the level\n" +
            "'T' - give infinite time\n" +
            "'D' - Destroy any block by clicking on it\n" +
            "# keys - Jump to level of that number\n" +
            "'P' - gain access to all power ups\n"+
            "BACKSPACE always will return to the start menu";


    public PauseScreen(StageManager stageManager, GameLevel currentLevel){
        super(stageManager);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        var root = new StackPane();
        var scene = new Scene(root,width,height,BACKGROUND);

        VBox vBox = new VBox(SIZE/40.0);
        vBox.getChildren().add(new Text(PAUSE_SCREEN_TEXTS));
        vBox.getChildren().add(new Text())
        vBox.setAlignment(Pos.CENTER);

        root.getChildren().add(vBox);

        myScene = scene;

    }
    }
}
