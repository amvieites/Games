/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.vieites.javafx.slimeclone.engine;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author macbook
 */
public abstract class AbstractUniverse {

    private Scene gameField = null;
    private Timeline gameLoop = null;
    private int framesPerSecond = 60;
    private String windowTitle = null;
    private SpriteManager spriteManager = null;

    public AbstractUniverse(int framesPerSecond, String windowTitle) {
        this.framesPerSecond = framesPerSecond;
        this.windowTitle = windowTitle;
        
        buildAndSetGameLoop();
    }

    protected final void buildAndSetGameLoop() {
        final Duration oneFrameAmt = Duration.millis(1000 / framesPerSecond);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler() {

                    @Override
                    public void handle(Event event) {
                        updateSprites(event);
                        checkCollisions();
                        cleanUpSprites();
                    }
                });
        // sets the game world's game loop (Timeline)
        setGameLoop(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).delay(new Duration(500)).build());

    }

    public abstract void initialize(final Stage primaryStage);

    protected abstract void updateSprites(Event event);

    protected abstract void checkCollisions();

    protected abstract void cleanUpSprites();

    public void beginGameLoop() {
        this.gameLoop.play();
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public Scene getGameField() {
        return gameField;
    }

    public void setGameField(Scene gameField) {
        this.gameField = gameField;
    }

    public Timeline getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(Timeline gameLoop) {
        this.gameLoop = gameLoop;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }
}
