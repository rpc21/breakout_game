package example;

import javafx.stage.Stage;

public class StageManager {

    public static final String TITLE = "Ryan Culhane Game";

    private MainScreen mainScreen;
    private PauseScreen pauseScreen;
    private CheatKeyMode cheatKeyMode;
    private TutorialMode tutorialMode;
    private Stage stage;
    private GenericScreen currentScreen;

    public StageManager(MainScreen mainScreen, PauseScreen pauseScreen, CheatKeyMode cheatKeyMode,
                        TutorialMode tutorialMode, Stage stage) {
        this.mainScreen = mainScreen;
        this.pauseScreen = pauseScreen;
        this.cheatKeyMode = cheatKeyMode;
        this.tutorialMode = tutorialMode;
        this.stage = stage;
        this.currentScreen = mainScreen;
        stage.setScene(currentScreen.getMyScene());
    }

    public void switchScene(GenericScreen newScene){
        stage.setScene(newScene.getMyScene());
        stage.show();
        stage.setTitle(TITLE);
        currentScreen = newScene;
    }

    public GenericScreen getCurrentScreen() {
        return currentScreen;
    }
}
