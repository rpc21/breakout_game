package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite extends ImageView {

    private int myXSpeed;
    private int myYSpeed;

    public Sprite(Image image) {
        super(image);
        myXSpeed = 0;
        myYSpeed = 0;
    }

    public boolean spriteCollidesWithRight(Sprite otherObject) {
        return ((topLeftCornerIsInYBounds(otherObject) || bottomLeftCornerIsInYBounds(otherObject)) &&
                this.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() + 1 &&
                this.getX() >= otherObject.getX() + otherObject.getBoundsInParent().getWidth() - 1);
    }

    private boolean topLeftCornerIsInYBounds(Sprite otherObject) {
        return this.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                this.getY() >= otherObject.getY();
    }

    private boolean bottomLeftCornerIsInYBounds(Sprite otherObject) {
        return this.getY() + this.getBoundsInParent().getHeight() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                this.getY() + this.getBoundsInParent().getHeight() >= otherObject.getY();
    }


    public boolean spriteCollidesWithLeft(Sprite otherObject) {
        return ((topLeftCornerIsInYBounds(otherObject) || bottomLeftCornerIsInYBounds(otherObject)) &&
                this.getX() + this.getBoundsInParent().getWidth() <= otherObject.getX() + 1 &&
                this.getX() + this.getBoundsInParent().getWidth() >= otherObject.getX() - 1);
    }

    public boolean spriteCollidesWithBottom(Sprite otherObject) {
        return ((leftSideIsInXBounds(otherObject) || rightSideIsInXBounds(otherObject)) &&
                this.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getWidth() + 1 &&
                this.getY() >= otherObject.getY() + otherObject.getBoundsInParent().getHeight() - 1);
    }

    private boolean leftSideIsInXBounds(Sprite otherObject) {
        return this.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                this.getX() + this.getBoundsInParent().getWidth() >= otherObject.getX();
    }

    private boolean rightSideIsInXBounds(Sprite otherObject) {
        return this.getX() + this.getBoundsInParent().getWidth() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                this.getX() + this.getBoundsInParent().getWidth() >= otherObject.getX();
    }


    public boolean spriteCollidesWithTop(Sprite otherObject) {
        return ((leftSideIsInXBounds(otherObject) || rightSideIsInXBounds(otherObject)) &&
                this.getY() + this.getBoundsInParent().getHeight() <= otherObject.getY() + 1 &&
                this.getY() + this.getBoundsInParent().getHeight() >= otherObject.getY() - 1);
    }

    public int getMyXSpeed() {
        return myXSpeed;
    }

    public void setMyXSpeed(int myXspeed) {
        this.myXSpeed = myXspeed;
    }

    public int getMyYSpeed() {
        return myYSpeed;
    }

    public void setMyYSpeed(int myYSpeed) {
        this.myYSpeed = myYSpeed;
    }
}
