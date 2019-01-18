package example;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class PauseScreen extends GenericScreen {

    public PauseScreen(StageManager stageManager){
        super(stageManager);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        super.setUpScene(width, height,background);
    }
}
