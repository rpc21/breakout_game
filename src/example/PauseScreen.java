package example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class PauseScreen extends GenericScreen {

    private final String PAUSE_SCREEN_TEXTS = "'L' - add another life\n" +
            "'R' - reset the level\n" +
            "'T' - give infinite time\n" +
            "'D' - Destroy any block by clicking on it\n" +
            "# keys - Jump to level of that number\n" +
            "'P' - gain access to all power ups\n"+
            "BACKSPACE always will return to the start menu";

    private VBox myVBox;

    private Text myScoreToDisplay;
    private Text myCurrentLevelToDisplay;
    private Text myCurrentDifficultToDisplay;
    private Text myNumberOfLivesRemainingToDisplay;
    private Text actionCompleteText;
    private Text myAmountOfTimeRemainingToDisplay;

    private GameLevel myCurrentLevel;

    public PauseScreen(StageManager stageManager){
        this(stageManager, stageManager.getGameLevel());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    }


    public PauseScreen(StageManager stageManager, GameLevel currentLevel){
        this.myStageManager = stageManager;
        myCurrentLevel = currentLevel;
        actionCompleteText = new Text("");
        initializeStatsFromLevel();
        setUpScene(GenericScreen.SIZE,GenericScreen.SIZE,GenericScreen.BACKGROUND);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    private void initializeStatsFromLevel() {
        myScoreToDisplay = new Text("Player Score: "+myCurrentLevel.getPlayerScore());
        myCurrentLevelToDisplay = new Text("Current Level: "+myCurrentLevel.getMyLevelNumber());
        myCurrentDifficultToDisplay =
                new Text("Current Difficulty: "+myCurrentLevel.getCurrentMode().getCurrentDifficultyString());
        myNumberOfLivesRemainingToDisplay = new Text("Number of Lives Remaining: "+myCurrentLevel.getMyNumberOfLivesRemaining());
        myAmountOfTimeRemainingToDisplay =
                new Text("Time Remaining: " + ((int) (myCurrentLevel.getTimeRemaining() / 60000)) + ":"+ ((int) (myCurrentLevel.getTimeRemaining() % 60000)));
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        var root = new StackPane();
        var scene = new Scene(root,width,height,BACKGROUND);

        myVBox = new VBox(SIZE/40.0);

        if(myCurrentLevel != null) {
            System.out.println("My current lives remaining are: "+myCurrentLevel.getMyNumberOfLivesRemaining());
            myVBox.getChildren().add(myCurrentLevelToDisplay);
            myVBox.getChildren().addAll(myCurrentDifficultToDisplay, myScoreToDisplay,
                    myNumberOfLivesRemainingToDisplay, myAmountOfTimeRemainingToDisplay,actionCompleteText);
        }
        myVBox.getChildren().add(new Text(PAUSE_SCREEN_TEXTS));
        myVBox.setAlignment(Pos.CENTER);

        root.getChildren().add(myVBox);

        myScene = scene;

    }


    @Override
    protected void handleKeyInput(KeyCode code) {
        if (code == KeyCode.SPACE){
            myStageManager.switchScene(myStageManager.getGameLevel());
        }
        if (code == KeyCode.L){
            myCurrentLevel.setMyNumberOfLivesRemaining(myCurrentLevel.getMyNumberOfLivesRemaining() + 1);
            myNumberOfLivesRemainingToDisplay.setText("Number of Lives Remaining: "+myCurrentLevel.getMyNumberOfLivesRemaining());
            actionCompleteText.setText("Added another life!");
        }
        if (code == KeyCode.R){
            myStageManager.switchScene(new GameLevel(myStageManager, myCurrentLevel.getMyLevelNumber()));
        }
        if (code == KeyCode.T){
            myCurrentLevel.setTimeRemaining(Double.MAX_VALUE);
            myAmountOfTimeRemainingToDisplay.setText("Infinite Time (Practically)");
            actionCompleteText.setText("Increased time limit!");

        }
        if (code == KeyCode.DIGIT1){
            myStageManager.switchScene(new GameLevel(myStageManager,1));
        }
        if (code == KeyCode.DIGIT2){
            myStageManager.switchScene(new GameLevel(myStageManager, 2));
        }
        if (code == KeyCode.DIGIT3){
            myStageManager.switchScene(new GameLevel(myStageManager, 3));
        }
        if (code == KeyCode.BACK_SPACE){
            myStageManager.switchScene(new MainScreen(myStageManager));
        }
    }

}
