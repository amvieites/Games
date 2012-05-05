package es.vieites.javafx.slimeclone.scores;

import es.vieites.javafx.slimeclone.slimeland.SlimeUniverse;
import es.vieites.javafx.slimeclone.slimeland.sprites.Ball;
import es.vieites.javafx.slimeclone.slimeland.sprites.Slime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class holds the scores table of the match.
 */
public class ScoresHolder {

    public static final short MAX_POINTS = 5;
    private static ScoresHolder instance = null;
    private double fieldLength = 0d;
    private int slime1Score = 0;
    private List<Circle> slime1ScoreBar = null;
    private int slime2Score = 0;
    private List<Circle> slime2ScoreBar = null;

    private ScoresHolder(double fieldLength, Group root) {
        this.fieldLength = fieldLength;

        initialize(root);
    }

    public static synchronized ScoresHolder getInstance(Group root, double fieldLength) {
        if (instance == null) {
            instance = new ScoresHolder(fieldLength, root);
        }

        return instance;
    }

    /**
     * Adds a point to the right slime based on the X position of the collision
     * between the ball and the floor.
     *
     * @param coordinateOfPoint x-pos of the collision
     */
    public void addPoint(double coordinateOfPoint) {
        Timer timer = new Timer();
        SlimeUniverse.getInstance().setPaused(true);
        EndPointTimerTask.Slimes winner = null;

        if (coordinateOfPoint < fieldLength / 2) {
            slime2Score++;
            winner = EndPointTimerTask.Slimes.slime2;
        } else {
            slime1Score++;
            winner = EndPointTimerTask.Slimes.slime1;
        }

        if (slime1Score == MAX_POINTS) {
            // TODO slime1 wins
        } else if (slime2Score == MAX_POINTS) {
            // TODO slime2 wins
        } else {
            timer.schedule(new EndPointTimerTask(winner), 2000l);
        }

        paintPoints();
    }

    private void paintPoints() {
        if (slime1Score > 0) {
            this.slime1ScoreBar.get(slime1Score - 1).setFill(Color.BLACK);
        }
        if (slime2Score > 0) {
            this.slime2ScoreBar.get(slime2Score - 1).setFill(Color.BLACK);
        }
    }

    /**
     * Paints the initial scores for both slimes.
     *
     * @param root
     */
    private void initialize(Group root) {
        slime1ScoreBar = new ArrayList<Circle>();
        slime2ScoreBar = new ArrayList<Circle>();

        for (int xpos = 50, points = 0; points < MAX_POINTS; points++, xpos += 40) {
            slime1ScoreBar.add(makeEmptyPoint(xpos, 30));
            slime2ScoreBar.add(makeEmptyPoint(this.fieldLength - xpos, 30));
            root.getChildren().add(slime1ScoreBar.get(points));
            root.getChildren().add(slime2ScoreBar.get(points));
        }
    }

    private Circle makeEmptyPoint(double x, double y) {
        Circle circle = new Circle(10d);
        circle.setTranslateX(x);
        circle.setTranslateY(y);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
        circle.setSmooth(true);

        return circle;
    }
}

/**
 * TimerTask class for the waiting time after a point. When this task is
 * invoked, the field resets ready for play the next point of the same match.
 */
class EndPointTimerTask extends TimerTask {

    private Slimes winner = null;

    public EndPointTimerTask(Slimes slime) {
        winner = slime;
    }

    @Override
    public void run() {
        SlimeUniverse su = SlimeUniverse.getInstance();

        // Set the slimes at their start point.
        Slime p1 = su.getSpriteManager().getPlayer1();
        p1.setX(225);
        p1.setY(470);
        Slime p2 = su.getSpriteManager().getPlayer2();
        p2.setX(675);
        p2.setY(470);

        Ball ball = su.getSpriteManager().getSlimeBall();

        // Set the ball over the winner.
        if (Slimes.slime1.equals(winner)) {
            ball.setX(225);
            ball.setY(200);
        } else {
            ball.setX(675);
            ball.setY(200);
        }

        ball.setVx(0);
        ball.setVy(0);

        su.setPaused(false);
    }

    public enum Slimes {

        slime1, slime2;
    }
}
