package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bouncer extends ImageView {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private int myXDirection;
    private int myYDirection;
    private int myXSpeed;
    private int myYSpeed;
    private int mySpeed;
    private GameDifficulty myCurrentMode;


    public Bouncer(Scene scene){
        this(scene, new GameDifficulty(GameDifficulty.BEGINNING_MODE));
    }

    public Bouncer(Scene scene, GameDifficulty currentMode) {
        super(new Image(BOUNCER_IMAGE));
        myCurrentMode = currentMode;
        myXSpeed = 0;
        myYSpeed = 0;
        setX(scene.getWidth() / 2 - this.getBoundsInLocal().getWidth() / 2);
        setY(scene.getHeight() * 0.6D);
    }

    public List<GenericBrick> handleBouncerCollisions(double elapsedTime, Scene scene, Paddle paddle, ArrayList<GenericBrick> bricks,
                                                      Group root){
        updatePosition(elapsedTime);
        handleWallCollisions(scene);
        handlePaddleCollisions(paddle);
        List<GenericBrick> effectedBricks = findEffectedBricks(bricks, root);
        return effectedBricks;
    }

    private List<GenericBrick> findEffectedBricks(List<GenericBrick> bricks, Group root) {
        List<GenericBrick> effectedBricks = new ArrayList<>();
        boolean bounceInXDirection = false;
        boolean bounceInYDirection = false;
        // https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an
        for (Iterator<GenericBrick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (bouncerCollidesWithTop(this,brick) || bouncerCollidesWithBottom(this,brick)){
                effectedBricks.add(brick);
//                root.getChildren().removeAll(brick);
//                iterator.remove();
                bounceInYDirection = true;
            }
            if (bouncerCollidesWithLeft(this,brick) || bouncerCollidesWithRight(this,brick)){
                effectedBricks.add(brick);
//                root.getChildren().removeAll(brick);
//                iterator.remove();
                bounceInXDirection = true;
            }
        }
        if(bounceInXDirection){
            this.myXSpeed *= -1;
        }
        if (bounceInYDirection){
            this.myYSpeed *= -1;
        }
        return effectedBricks;
    }

    private boolean bouncerCollidesWithRight(ImageView bouncer, ImageView otherObject) {
        return ((topLeftCornerIsInYBounds(bouncer, otherObject) || bottomLeftCornerIsInYBounds(bouncer, otherObject)) &&
                bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() + 1 &&
                bouncer.getX() >= otherObject.getX() + otherObject.getBoundsInParent().getWidth() - 1);
    }

    private boolean topLeftCornerIsInYBounds(ImageView bouncer, ImageView otherObject) {
        return bouncer.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                bouncer.getY() >= otherObject.getY();
    }

    private boolean bottomLeftCornerIsInYBounds(ImageView bouncer, ImageView otherObject) {
        return bouncer.getY() + bouncer.getBoundsInParent().getHeight() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() >= otherObject.getY();
    }


    private boolean bouncerCollidesWithLeft(ImageView bouncer, ImageView otherObject) {
        return ((topLeftCornerIsInYBounds(bouncer, otherObject) || bottomLeftCornerIsInYBounds(bouncer, otherObject)) &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() <= otherObject.getX() + 1 &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX() - 1);
    }

    private boolean bouncerCollidesWithBottom(ImageView bouncer, ImageView otherObject) {
        return ((leftSideIsInXBounds(bouncer, otherObject) || rightSideIsInXBounds(bouncer, otherObject))&&
                bouncer.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getWidth() + 1 &&
                bouncer.getY() >= otherObject.getY() + otherObject.getBoundsInParent().getHeight() - 1);
    }

    private boolean leftSideIsInXBounds(ImageView bouncer, ImageView otherObject) {
        return bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX();
    }

    private boolean rightSideIsInXBounds(ImageView bouncer, ImageView otherObject) {
        return bouncer.getX() + bouncer.getBoundsInParent().getWidth() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX();
    }


    public void handlePaddleCollisions(Paddle paddle) {
        if (bouncerCollidesWithTop(this, paddle)){
            this.myXSpeed += paddle.getMyVelocity() / 2;
            this.myYSpeed *= -1;
        }
        if (myXSpeed > myCurrentMode.getMaxBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMaxBouncerXSpeed();
        }
        if (myXSpeed < myCurrentMode.getMinBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMinBouncerXSpeed();
        }
    }

    private boolean bouncerCollidesWithTop(ImageView bouncer, ImageView otherObject) {
        return ((leftSideIsInXBounds(bouncer, otherObject) || rightSideIsInXBounds(bouncer, otherObject)) &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() <= otherObject.getY() + 1 &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() >= otherObject.getY() - 1);
    }

    private void updatePosition(double elapsedTime) {
        this.setX(this.getX() + myXSpeed * elapsedTime);
        this.setY(this.getY() + myYSpeed * elapsedTime);
    }

    private void handleWallCollisions(Scene scene) {
        if (this.getX() <= 0 || this.getX() + this.getBoundsInLocal().getWidth() >= scene.getWidth()) {
            this.myXSpeed *= -1;
        }
        if (this.getY() <= 0 || this.getY() + this.getBoundsInLocal().getHeight() >= scene.getHeight()) {
            this.myYSpeed *= -1;
        }
    }


    public void setMyXSpeed(int myXSpeed) {
        this.myXSpeed = myXSpeed;
    }

    public void setMyYSpeed(int myYSpeed) {
        this.myYSpeed = myYSpeed;
    }
}
