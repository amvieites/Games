package es.vieites.javafx.slimeclone.slimeland;

import es.vieites.javafx.slimeclone.engine.AbstractSprite;
import es.vieites.javafx.slimeclone.engine.AbstractUniverse;
import es.vieites.javafx.slimeclone.engine.SpriteManager;
import es.vieites.javafx.slimeclone.scores.ScoresHolder;
import es.vieites.javafx.slimeclone.slimeland.sprites.Ball;
import es.vieites.javafx.slimeclone.slimeland.sprites.Floor;
import es.vieites.javafx.slimeclone.slimeland.sprites.Slime;
import es.vieites.javafx.slimeclone.slimeland.sprites.Wall;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The Slime Universe.
 */
public class SlimeUniverse extends AbstractUniverse {

    public static final double GRAVITY = 9.81d;
    private static final double SLIME_SPEED = 4;
    private static final double SLIME_JUMP_SPEED = 45;
    private static SlimeUniverse instance = null;
    private boolean paused = false;
    private ScoresHolder scoresHolder = null;

    private SlimeUniverse(int framesPerSecond, String windowTitle) {
        super(framesPerSecond, windowTitle);
        Ball ball = new Ball(225, 200, 15);
        Slime slime1 = new Slime(225, 470, 40);
        Slime slime2 = new Slime(675, 470, 40);
        setSpriteManager(new SpriteManager(ball, slime1, slime2));

    }

    public static SlimeUniverse getInstance(int framesPerSecond, String windowTitle) {
        if (instance == null) {
            instance = new SlimeUniverse(framesPerSecond, windowTitle);
        }

        return instance;
    }

    public static SlimeUniverse getInstance() {
        if (instance == null) {
            throw new IllegalStateException("No instance was created.");
        }

        return instance;
    }

    @Override
    public void initialize(Stage primaryStage) {
        primaryStage.setTitle(getWindowTitle());

        Group root = new Group();
        setGameField(new Scene(root, 900, 500));

        scoresHolder = ScoresHolder.getInstance(root, 900);

        addChildren(root);

        addEvents(getGameField());
        primaryStage.setScene(getGameField());
        primaryStage.show();
    }

    @Override
    protected void updateSprites(Event event) {
        if (!isPaused()) {
            for (AbstractSprite sprite : getSpriteManager().getAllSprites()) {
                sprite.update(event);
            }
        }
    }

    @Override
    protected void checkCollisions() {
        for (AbstractSprite sprite1 : getSpriteManager().getToCheckCollisionSprites()) {
            for (AbstractSprite sprite2 : getSpriteManager().getToCheckCollisionSprites()) {
                if (sprite1 != sprite2) {
                    sprite1.collide(sprite2);
                }
            }
        }
    }

    @Override
    protected void cleanUpSprites() {
    }

    private void addChildren(Group root) {
        Floor floor = new Floor(0, 470, 900, 30);
        Wall leftWall = new Wall(0, 0, 30, 470);
        Wall rightWall = new Wall(870, 0, 30, 470);
        Wall net = new Wall(448, 390, 4, 80);

        Ball ball = getSpriteManager().getSlimeBall();
        Slime p1 = getSpriteManager().getPlayer1();
        Slime p2 = getSpriteManager().getPlayer2();

        root.getChildren().add(floor.getNode());
        root.getChildren().add(leftWall.getNode());
        root.getChildren().add(rightWall.getNode());
        root.getChildren().add(net.getNode());
        root.getChildren().add(ball.getNode());
        root.getChildren().add(p1.getNode());
        root.getChildren().add(p2.getNode());

        getSpriteManager().addSprites(p1, p2, ball, floor, leftWall, rightWall, net, p1, p2);
        getSpriteManager().getToCheckCollisionSprites().add(floor);
        getSpriteManager().getToCheckCollisionSprites().add(rightWall);
        getSpriteManager().getToCheckCollisionSprites().add(leftWall);
        getSpriteManager().getToCheckCollisionSprites().add(net);
        getSpriteManager().getToCheckCollisionSprites().add(ball);
        getSpriteManager().getToCheckCollisionSprites().add(p1);
        getSpriteManager().getToCheckCollisionSprites().add(p2);
    }

    private void addEvents(Scene gameField) {
        gameField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case W:
                        // Just jump, not fly.
                        if (Math.abs(getSpriteManager().getPlayer1().getVy()) < 0.1) {
                            getSpriteManager().getPlayer1().setVy(-SLIME_JUMP_SPEED);
                        }
                        break;
                    case S:
                        break;
                    case A:
                        getSpriteManager().getPlayer1().setVx(-SLIME_SPEED);
                        break;
                    case D:
                        getSpriteManager().getPlayer1().setVx(SLIME_SPEED);
                        break;
                    case UP:
                        if (Math.abs(getSpriteManager().getPlayer2().getVy()) < 0.1) {
                            getSpriteManager().getPlayer2().setVy(-SLIME_JUMP_SPEED);
                        }
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        getSpriteManager().getPlayer2().setVx(-SLIME_SPEED);
                        break;
                    case RIGHT:
                        getSpriteManager().getPlayer2().setVx(SLIME_SPEED);
                        break;
                }
                key.consume();
            }
        });

        gameField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case W:
                        break;
                    case S:
                        break;
                    case A:
                        getSpriteManager().getPlayer1().setVx(0);
                        break;
                    case D:
                        getSpriteManager().getPlayer1().setVx(0);
                        break;
                    case UP:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        getSpriteManager().getPlayer2().setVx(0);
                        break;
                    case RIGHT:
                        getSpriteManager().getPlayer2().setVx(0);
                        break;
                }
                key.consume();
            }
        });
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public ScoresHolder getScoresHolder() {
        return scoresHolder;
    }
}
