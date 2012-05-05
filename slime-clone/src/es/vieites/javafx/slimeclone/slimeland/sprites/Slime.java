package es.vieites.javafx.slimeclone.slimeland.sprites;

import es.vieites.javafx.slimeclone.engine.AbstractSprite;
import es.vieites.javafx.slimeclone.slimeland.SlimeUniverse;
import javafx.event.Event;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;

/**
 * The Slime class doll.
 */
public class Slime extends AbstractSprite {

    public Slime(double x, double y, double r) {
        setNode(ArcBuilder.create().radiusX(r).radiusY(r).startAngle(0).length(180).type(ArcType.ROUND).build());
        setX(x);
        setY(y);
        setType(EnumSprite.SLIME);
    }

    @Override
    public void update(Event event) {
        setX(getX() + getVx());
        
        double timeIncrement = 10d / 50d;
        double newY = getY() + getVy() * timeIncrement + SlimeUniverse.GRAVITY * Math.pow(timeIncrement, 2) / 2d;
        double vyf = getVy() + SlimeUniverse.GRAVITY * timeIncrement;
        setY(newY);
        setVy(vyf);
    }

    @Override
    public void collide(AbstractSprite sprite) {
        switch (sprite.getType()) {
            case BALL:
                collide((Ball) sprite);
                break;
        }
    }

    private void collide(Ball ball) {
        if (intersects(ball)) {
            if (!isIgnoreCollisionWith(ball)) {
                double dx = ball.getX() - this.getX();
                double dy = ball.getY() - this.getY();
                double dist = Math.sqrt(dx * dx + dy * dy);
                double something = (dx * ball.getVx() + dy * ball.getVy()) / dist;

                ball.setVx(ball.getVx() + getVx() - 2 * dx * something / dist);
                ball.setVy(ball.getVy() + getVy() - 2 * dy * something / dist);
                addIgnoreCollisionWith(ball);
            }

        } else {
            removeIgnoreCollisionWith(ball);
        }
    }

    private boolean intersects(Ball ball) {
        double dx = ball.getX() - getX();
        double dy = ball.getY() - getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        return dist < ball.getAsCircle().getRadius() + this.getAsArc().getRadiusX();
    }

    public Arc getAsArc() {
        return (Arc) getNode();
    }
}
