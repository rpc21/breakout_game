package example;

import javafx.stage.Stage;

/**
 * Class to handle all the scene changes and to initialize the application
 */
public class StageManager {

    private static final String TITLE = "Ryan Culhane Game";

    private MainScreen mainScreen;
    private PauseScreen pauseScreen;
    private CheatKeyMode cheatKeyMode;
    private TutorialMode tutorialMode;
    private Stage stage;
    private GenericScreen currentScreen;
    private GameLevel gameLevel;


    /**
     * StageManager Constructor creates the StageManager and initializes all screens being managed
     * Sets the currentScreen to a GenericScreen in teh beginning.
     * @param stage
     */
    public StageManager(Stage stage) {
        this.mainScreen = new MainScreen(this);
        this.gameLevel = new GameLevel(this,1,GameDifficulty.BEGINNING_MODE);
        this.pauseScreen = new PauseScreen(this);
        this.cheatKeyMode = new CheatKeyMode(this);
        this.tutorialMode = new TutorialMode(this);
        this.stage = stage;
        this.currentScreen = new GenericScreen();
        stage.setScene(currentScreen.getMyScene());
    }

    /**
     * Switches from the current screen to display the new screen that is passed to it
     * Updates the Stage Manager to know what screen is currently being displayed
     * @param newScene
     */
    public void switchScene(GenericScreen newScene){
        stage.setScene(newScene.getMyScene());
        stage.setTitle(TITLE);
        stage.show();
        currentScreen = newScene;
        if (newScene instanceof GameLevel){
            this.gameLevel = (GameLevel) newScene;
        }
    }

    /**
     * Getter for a fully initialized MainScreen
     * @return
     */
    public MainScreen getMainScreen() {
        return mainScreen;
    }

    /**
     * Getter for an up to date PauseScreen
     * @return
     */
    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    /**
     * Getter for the current screen
     * @return the current screen being displayed
     */
    public GenericScreen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Getter for the stage
     * @return stage
     */
    public Stage getStage(){
        return stage;
    }

    /**
     * Getter for the current game level - helps maintain and display important parts of game state
     * @return current gameLevel
     */
    public GameLevel getGameLevel() {
        return gameLevel;
    }
}
