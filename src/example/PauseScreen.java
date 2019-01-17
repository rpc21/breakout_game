package example;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class PauseScreen extends GenericScreen {

    public PauseScreen(StageManager stageManager){
        myStageManager = stageManager;
        setUpScene(this.SIZE,this.SIZE,this.BACKGROUND);
        this.myStage = stageManager.getStage();
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        super.setUpScene(width, height,background);
    }
}
