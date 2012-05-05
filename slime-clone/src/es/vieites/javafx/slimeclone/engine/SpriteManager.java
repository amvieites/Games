package es.vieites.javafx.slimeclone.engine;

import es.vieites.javafx.slimeclone.slimeland.sprites.Ball;
import es.vieites.javafx.slimeclone.slimeland.sprites.Slime;
import java.util.*;

/**
 * The Sprite manager.
 */
public class SpriteManager {

    private static final List<AbstractSprite> SPRITES = Collections.synchronizedList(new ArrayList<AbstractSprite>());
    private static final List<AbstractSprite> CHECK_COLLISIONS = Collections.synchronizedList(new ArrayList<AbstractSprite>());
    private static final Set<AbstractSprite> CLEAN_UP_SPRITES = Collections.synchronizedSet(new HashSet<AbstractSprite>());
    private Ball slimeBall = null;
    private Slime player1 = null;
    private Slime player2 = null;

    public SpriteManager(Ball ball, Slime p1, Slime p2) {
        this.slimeBall = ball;
        this.player1 = p1;
        this.player2 = p2;
    }

    public Ball getSlimeBall() {
        return slimeBall;
    }

    public Slime getPlayer1() {
        return player1;
    }

    public Slime getPlayer2() {
        return player2;
    }

    public List<AbstractSprite> getAllSprites() {
        return SPRITES;
    }

    public void addSprites(AbstractSprite... sprites) {
        this.SPRITES.addAll(Arrays.asList(sprites));
    }

    public void removeSprites(AbstractSprite... sprites) {
        this.SPRITES.removeAll(Arrays.asList(sprites));
    }

    public Set<AbstractSprite> getToCleanSprites() {
        return CLEAN_UP_SPRITES;
    }

    public List<AbstractSprite> getToCheckCollisionSprites() {
        return CHECK_COLLISIONS;
    }

    public static void cleanUpSprites() {
        SPRITES.removeAll(CLEAN_UP_SPRITES);
        CLEAN_UP_SPRITES.clear();
    }
}
