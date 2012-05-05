package es.vieites.javafx.slimeclone.slimeland.sprites;

import es.vieites.javafx.slimeclone.engine.AbstractSprite;
import javafx.event.Event;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the walls of the playground. Left wall, right wall and
 * the net share the same behavior.
 */
public class Wall extends AbstractSprite {

    /**
     * Creates a Rectangle.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Wall(double x, double y, double width, double height) {
        Rectangle wall = new Rectangle();
        wall.setTranslateX(x);
        wall.setTranslateY(y);
        wall.setWidth(width);
        wall.setHeight(height);
        setNode(wall);
        setType(EnumSprite.WALL);
    }

    @Override
    public void update(Event event) {
    }

    @Override
    public void collide(AbstractSprite sprite) {
        switch (sprite.getType()) {
            case BALL:
                collide((Ball) sprite);
                break;
            case SLIME:
                collide((Slime) sprite);
                break;
            case FLOOR:
                // Floors and walls don't collide.
                break;
            case WALL:
                // Walls and walls don't collide.
                break;
            default:
                System.out.println("No defined collision between Wall and " + sprite.getType());
                break;
        }
    }

    private void collide(Ball sprite) {
        if (intersects((Circle) sprite.getNode())) {
            if (!isIgnoreCollisionWith(sprite)) {
                sprite.setVx(-sprite.getVx());
                addIgnoreCollisionWith(sprite);
            }
        } else {
            removeIgnoreCollisionWith(sprite);
        }
    }

    private void collide(Slime sprite) {
        if (intersects((Arc) sprite.getNode())) {
            if (sprite.getVx() > 0) {
                sprite.setX(this.getX() - ((Arc) sprite.getNode()).getRadiusX());
            } else {
                sprite.setX(this.getX() + this.getAsRectangle().getWidth()
                        + ((Arc) sprite.getNode()).getRadiusX());
            }
        }
    }

    private boolean intersects(Arc arc) {
        double wallCenterX = getAsRectangle().getTranslateX() + getAsRectangle().getWidth() / 2d;

        double minDistance = Math.abs(getAsRectangle().getWidth() / 2d + arc.getRadiusX());

        if (minDistance > Math.abs(arc.getTranslateX() - wallCenterX)) {
            return true;
        }

        return false;
    }

    public boolean intersects(Circle circle) {
        double rectCenterX = getAsRectangle().getTranslateX() + getAsRectangle().getWidth() / 2d;
        double rectCenterY = getAsRectangle().getTranslateY() + getAsRectangle().getHeight() / 2d;

        double dX = Math.abs(circle.getTranslateX() - rectCenterX);
        double dY = Math.abs(circle.getTranslateY() - rectCenterY);

        if (dX > (getAsRectangle().getWidth() / 2d + circle.getRadius())) {
            return false;
        }

        if (dY > (getAsRectangle().getHeight() / 2d + circle.getRadius())) {
            return false;
        }

        if (dX <= (getAsRectangle().getWidth() / 2d)) {
            return true;
        }
        if (dY <= (getAsRectangle().getHeight() / 2d)) {
            return true;
        }

        double cornerDistance_sq = Math.pow(dX - getAsRectangle().getWidth() / 2d, 2)
                + Math.pow(dY - getAsRectangle().getHeight() / 2d, 2);

        return (cornerDistance_sq <= Math.pow(circle.getRadius(), 2));
    }

    public Rectangle getAsRectangle() {
        return (Rectangle) getNode();
    }
}
