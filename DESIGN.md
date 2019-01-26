### High Level Design

* Current Design:
    * GamePlayer:
        * The main class that initializes the StageManager and sets up the animation
    * StageManager:
        * Initializes all the different types of possible screens
        * Change from screen to screen
        * Tells the main class (GamePlayer) what screen is currently displayed so GamePlayer can animate the scene 
        according to that screen's step function.
    * Generic Screen:
        * Currently implemented as a concrete class but after class discussion and the readings, I now realize it 
        should be an abstract class.
        * Defines the functionality for its subclasses: CheatKeyMode, GameLevel, MainScreen, PauseScreen, and 
        TutorialMode.
    * MainScreen:
        * Loads a welcome screen with buttons that can lead to loading a new level, beginning the tutorial, or 
        viewing the list of cheat keys.
    * PauseScreen:
        * The PauseScreen displays information about the state of the current game as well as the objective of the 
        game and the various cheat keys available to the player.
        * The PauseScreen also allows the player to change levels or change the difficulty.
    * TutorialMode:
        * Creates all the different scenes that are displayed during the tutorial mode
        * Allows user to advance from one tutorial to the next
        * In retrospect, after in class discussions I now know I need redesign the implementation of TutorialMode so 
        it is not dependent on the one long, messy strand of if statements either by creating separate classes for 
        the different screens or coming up with some way to better update the tutorial.
    * CheatKeyMode:
        * Displays all the different cheat keys and how to use them.
    * GameLevel:
        * Handles the game play (the simulation and the game state)
        * Updates the state of the screen and the state of the game through the step function
        * Detects when a player has won or lost and will update the game accordingly
        * Has instance variables of PowerUpManager, BrickManager, Paddle, and Bouncer that help handle the 
        functionality of playing a level.
    * Bouncer:
        * Moves around the screen
        * Updates its position based on detecting collisions with Paddle, Walls, and Bricks
        * Identifies the bricks with which it collides in a time step and can report this back to the GameLevel
            * From our in class discussions I now realize that the Bouncer perhaps has too much knowledge about the 
            state of the Bricks and Paddle and that it would be better to have a SpriteManager that knows everything 
            about the sprites on the screen but can keep the information hidden between sprites.
    * Paddle:
        * Controlled using arrow keys
        * Moves from side to side across the bottom of the screen and can wrap from one side to the other
    * BrickManager:
        * Reads in file specifying brick configuration for a given level
        * Generates and keeps track of all bricks in the current level
        * Determines how brick collisions affect the state of the game in terms of updating the score, updating the 
        number of lives remaining, and whether a 
        power-up should be given to the player
    * Bricks
        * There is a GenericBrick abstract class that outlines the functionality and characteristics of Bricks
        * There are five subclasses of GenericBrick: OneHitBrick, TwoHitBrick, ThreeHitBrick, PermanentBrick, and 
        DangerBrick.  The five subclasses all have different appearances on the screen and behave differently in 
        terms what happens when they collide with the Bouncer.
            * In retrospect, my code could have been much more readable and I could have had less duplicated code if 
            I implemented a handleCollisionWithBouncer() method for each kind of brick.  This would better leverage 
            inheritance and make my code much more open for extension to new kinds of bricks because the methods that
             handle brick collisions would be closed to modification because they would be flexible enough to adapt 
             to any subclass of GenericBrick.  Therefore, to create a new kind of brick I would just have to specify 
             its image, its score, and its handleCollisionWithBouncer() method.
    * PowerUpManager
        * Handles the number of power-ups of each kind that are available to the user
        * Updates the display based on power-up status
        * Provides the machinery to display animate and display the power-ups
            * The PowerUpManager could be better implemented if the power-ups themselves were classes.  This would 
            divide up the functionality much better into more understandable, self-contained pieces.
            
### How To Extend the Project
* How to add a new level:
    1. Create a txt file that uses '-' to denote where you want the bricks to appear.
    2. Specify the path to this file in the BrickManager class.
    3. Add a condition for the level based on the number you want assigned to the level in the BrickManagerConstructor
    4. Add a condition based on pressing the key corresponding to that level number in the PauseScreen class to 
    initialize a new level of that number.
    
    Note: I realize now that I should have created an inheritance hierarchy to handle levels in a more flexible, 
    extendable 
    way.  However, for not having done this, my implementation is not too terrible for adding new levels.
    
* How to add a new brick: (before the bricks were refactored for the coding masterpiece)
    1. Create a new brick class that extends GenericBrick specifying its image and point value
    2. Incorporate the brick's functionality into the handleBricksToBeRemoved method in BrickManager by adding a new 
    if instance of statement then specifying how you want the brick to respond to a collision with the bouncer
    3. Specify the brick's frequency of appearing for the various difficulty modes in each of the generate brick 
    methods.
    
* How to add a new power-up:
    1. Add a space for the new power-up in the next index of the array that stores power-ups in the PowerUpManager class
    2. Add the ability to add this power-up to the addPowerUp function in the PowerUpManager class
    3. Add an if statement and a corresponding power-up number to the usePowerUp function
    4. Create a function to use the power-up and implement its functionality
    5. Make it possible for the bricks to generate this power-up in the generate power-up method in BrickManager
    6. Depending on the power-up's functionality you may have to adjust the step function in the GameLevel class
    
* How to add a new sprite of any kind:
    1. Start from scratch
    

Note: From the in-class discussions and the readings I now see how my implementation is not very extendable and in my
 Analysis.md file I have addressed ways in which I could leverage polymorphism and inheritance to make the 
 implementation much easier to extend.
 
### Design Choices

* Design Choice 1: StageManager Class

    * I designed the StageManager as a way to be able to change scenes from any Screen to another Screen.  However, 
    this 
    means that each Screen needed to be passed the StageManager and that the StageManager needed to be able to 
    initialize Screens.  This lead to the following circular dependencies that can be seen in the StageManager 
    constructor:
    
    ```java
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
  ```
     * As mentioned before, I am sure that passing itself as the parameter to initialize another object in its constructor 
  is a bad practice but I was unsure of any other way to get the same functionality other than constantly adding and 
  removing objects from the root's children every time I wanted to make a sweeping change of what was being displayed
  .  The way the StageManager is implemented and used makes it essentially a global variable across all classes, 
  which from the readings and class I now know is not okay to have.
     * The StageManager I have implemented, however, does have many advantages.  First of all, it makes the process of 
  switching screens very easy.  All that one has to do it is call stageManager.switchScene() and specify which scene 
  to switch to.  Another benefit of the StageManager is that it allows the main class to be very simple and easy to 
  understand.  All that is being done in the main class is initializing the StageManager setting up the animation.
  
  ```java
  /**
       * Initialize the stageManager and switch scenes to load the Main Screen of the application
       * Establish the animation loop
       */
      @Override
      public void start (Stage stage) {
          stageManager = new StageManager(stage);
  
          stageManager.switchScene(stageManager.getMainScreen());
  
          //attach "game loop" to timeline to play it
          var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
          var animation = new Timeline();
          animation.setCycleCount(Timeline.INDEFINITE);
          animation.getKeyFrames().add(frame);
          animation.play();
  
      }
  
      private void step(double secondDelay) {
          stageManager.getCurrentScreen().step(secondDelay);
      }
  ```
    * Furthermore, the StageManager class feels like a very readable abstraction.  The nuances of how the 
  StageManager is implemented can be kind of difficult to understand, but whenever it is used in the code, its 
  purpose is clear and readable.  An example of this readability is shown below:
  ```java
  else if (code == KeyCode.SPACE){
              myStageManager.switchScene(myStageManager.getPauseScreen());
          }
  ```
     * A final advantage of using the StageManager is that it is easy to save the current state of the game because the 
  StageManager will have access to the most recent instance of GameLevel.  This makes pause and resume functionality 
  much easier and allows me to better integrate the game state to what is being displayed in the pause screen.
    * As mentioned before, the main drawbacks of the StageManager are its confusing dependencies and its implementation
   as almost a global variable.
   
    * An alternative to the StageManager that I was considering was to simply remove everything from old contents from 
  the root and add new objects to the root every time I wanted to make a large change in what was being displayed.  
  This method for controlling what is being displayed has the advantages of not having any huge or confusing 
  dependencies like the StageManager.  However, this strategy has many disadvantages.  First, there would be a lot of 
  repetition of code when it comes to clearing out the root and re-establishing what is supposed to be displayed.  
  Second, all classes would still have to access the root somehow so I wouldn't be able to avoid having some kind of 
  implementation of a practically global variable (it would be the root instead of the StageManager).  This method 
  does not allow you to maintain state as well because each time you change from screen to screen, it would remove 
  everything from the screen and you would have to work much harder to keep track of which objects are in what state
   and how the objects that are included in the root will have to change going from each possible screen to every 
   other possible screen.  This method would lead to a lot more hard-coding of transitions.
    * Ultimately, I decided that te StageManager rote was the way to go, but I am not convinced that it would be the 
  best option in the future but I am unsure as to what would be a better way to implement and handle changing the 
  display.


* Design Choice Two: Handling of Collisions Between Sprites

    * Collisions between sprites (the bouncer, the paddle, bricks, etc.) are at the heart of the game because pretty 
    much every significant event is a collision between sprites.  Seeing as the bouncer would be in every collision, 
    I determined that the best way to handle the collisions would be through a method in the Bouncer class that 
    updates the Bouncer.  This method is shown below:
    
    ```java
    /**
         * This method updates the Bouncer's position by computing new position from current position and velocity
         * This method also handles Bouncer's collisions with all other objects in the scene
         * These include walls, paddles, bricks
         * @param elapsedTime
         * @param scene
         * @param paddle
         * @param bricks
         * @param root
         * @return the bricks that the Bouncer collided with so they can be handled by the BrickManager
         */
        public List<GenericBrick> handleBouncerCollisions(double elapsedTime, Scene scene, Paddle paddle, ArrayList<GenericBrick> bricks,
                                                          Group root){
            updatePosition(elapsedTime);
            handleWallCollisions(scene);
            handlePaddleCollisions(paddle);
            List<GenericBrick> effectedBricks = findEffectedBricks(bricks, root);
            return effectedBricks;
        }
  ```
    * Originally, the goal of this method would be to check all the objects on the screen in every time step and see 
    if the bouncer had collided with the object and if it had, make the bouncer bounce.  However, as I was coding, I 
    realized that much more can happen on a bouncer collision than just the bouncer bouncing.  For example, a brick 
    may disappear, or a brick may generate a power-up.  I had already implemented the method to make the bouncer 
    bounce and I did not want to redo that functionality so I determined that since the other key events all involve 
    a bouncer colliding with a brick, I would have this method simply return the list of bricks that the bouncer 
    collided with and that I could pass this list of effected bricks to the BrickManager to be handled and update the
     game accordingly.  At the time this felt like good design because everything that involved the bouncer was 
     happening using Bouncer class methods and everything that involved the management of Bricks was using the 
     BrickManager methods.  Now however, I see that there is a lot of sharing that has to be done across classes 
     seeing as the Bouncer has to know about all the Bricks, send the effected bricks back to the GameLevel class 
     which then has to send them to the BrickManager which then sends information back to the GameLevel class.  
     Furthermore, the Bouncer has to know everything about the paddle and the scene to be able to handle collisions. 
      Ultimately, it happens to be the case that the Bouncer and BrickManager end up knowing pretty much everything 
      about the current state of the game suggesting that functionality and information of each of these classes is 
      not encapsulated very well.
    * As mentioned earlier, creating a SpriteManager class and making Bouncer, Paddle, and GenericBrick all extend 
    Sprite would have been a solution that better isolated the functionality of handling collisions and updating the 
    state of the sprites on the screen.  With a SpriteManager class, the state of the bricks would be hidden from the
     Bouncer, and the states of the Bouncer, Bricks and Paddle would all be hidden from the GameLevel class.  The 
     SpriteManager would just handle all the collisions and report back to the game what needs to be updated in terms
      of power-ups, number of lives, score, etc.  This would allow for better encapsulation and classes would only 
      have the information they need and other information would be hidden from classes that don't really need it 
      which goes along with the principles we were practicing in the hangman lab.
      
* Design Choice 3: Not Creating Separate Level Classes
     * I contemplated making a class for each level but when I realized that the class would just be storing 
     information (the file specifying the brick layout) I realized that it would not be useful as a class because it 
     is just holding and distributing information rather than having actions.  I think this choice was justifiable 
     because all my levels had so much in common such as time remaining, number of lives, available power-ups, etc.  
     The only thing different between the levels were the brick configurations which I decided to just handle in the 
     BrickManager.  If I were to have created levels with more differences, or in the future were to extend the 
     project to include levels with a wider range of characteristics, it would be wise to create separate level 
     classes to add for easier extendability.
     
### Ambiguities in Functionality

* I purposely decided that the bricks should not drop power-up sprites that the paddle had to catch because when I 
tried to do this I found it was so difficult to catch the power-up and keep the ball in the air that the power-ups 
would be more harmful than helpful and that it is easier to program and easier for the user to just have the 
power-ups be automatically added from destroying bricks.

* Bricks removed by power-ups do not contribute to the user's score, nor do they cost the player a life if they are 
DangerBricks.

* Some cheat keys are only accessible via the PauseScreen and some are available during game play.  The PauseScreen 
specifies which are which.