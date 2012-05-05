package es.vieites.javafx.slimeclone;

import es.vieites.javafx.slimeclone.engine.AbstractUniverse;
import es.vieites.javafx.slimeclone.slimeland.SlimeUniverse;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author macbook
 */
public class SlimeClone extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                System.exit(0);
            }
        });
        
        AbstractUniverse slimeLand = SlimeUniverse.getInstance(50, "Slime!");
        slimeLand.initialize(primaryStage);
        primaryStage.show();
        slimeLand.beginGameLoop();
    }
}
