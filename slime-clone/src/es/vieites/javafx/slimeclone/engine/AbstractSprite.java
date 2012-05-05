package es.vieites.javafx.slimeclone.engine;

import es.vieites.javafx.slimeclone.slimeland.sprites.EnumSprite;
import java.util.HashSet;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.Node;

/**
 * Abstract custom nodes of the universe.
 */
public abstract class AbstractSprite {
    private Node node = null;
    private EnumSprite type = null;
    private double vx = 0d;
    private double vy = 0d;
    private Set<AbstractSprite> collisionsToIgnore = null;
    
    public AbstractSprite() {
        collisionsToIgnore = new HashSet<AbstractSprite>();
    }
    
    public abstract void update(Event event);
    
    public abstract void collide(AbstractSprite sprite);

    public Node getNode() {
        return node;
    }

    protected void setNode(Node node) {
        this.node = node;
    }

    public EnumSprite getType() {
        return type;
    }

    protected void setType(EnumSprite type) {
        this.type = type;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getX() {
        return node.getTranslateX();
    }
    
    public double getY() {
        return node.getTranslateY();
    }
    
    public void setX(double x) {
        this.node.setTranslateX(x);
    }
    
    public void setY(double y) {
        this.node.setTranslateY(y);
    }
    
    public void addIgnoreCollisionWith(AbstractSprite sprite) {
        collisionsToIgnore.add(sprite);
    }
    
    public boolean isIgnoreCollisionWith(AbstractSprite sprite) {
        return collisionsToIgnore.contains(sprite);
    }
    
    public void removeIgnoreCollisionWith(AbstractSprite sprite) {
        collisionsToIgnore.remove(sprite);
    }
}
