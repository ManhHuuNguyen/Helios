package view;


import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animated {

    public static ImageView animate(Image image){
        ImageView node = new ImageView(image);
        RotateTransition rotation = new RotateTransition();
        rotation.setDuration(Duration.millis(1000));
        rotation.setNode(node);
        rotation.setByAngle(360);
        rotation.setCycleCount(50);
        rotation.setAutoReverse(false);
        rotation.play();
        return node;
    }
}
