package example;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class PauseScreen extends GenericScreen {

    private final String PAUSE_SCREEN_TEXTS = "'L' - add another life\n" +
            "'R' - reset the level\n" +
            "'T' - give infinite time\n" +
            "'D' - Destroy any block by clicking on it\n" +
            "# keys - Jump to level of that number\n" +
            "'P' - gain access to all power ups\n"+
            "BACKSPACE always will return to the start menu";


    public PauseScreen(StageManager stageManager){
        super(stageManager);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        super.setUpScene(width, height,background);
    }
}
