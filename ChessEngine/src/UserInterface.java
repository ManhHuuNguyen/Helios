import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserInterface extends GameGeneration{

    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());
        long startTime = System.nanoTime();
        int result = Perft.perftRoot(boards, history, WhiteTurn, 0, 7, map);
        System.out.println("Total possibilities: " + result);
        long endTime = System.nanoTime();
        System.out.println("The speed is: " + result/((endTime-startTime)/1000000000) + " nodes per second");
        primaryStage.setTitle("Helios");
        primaryStage.getIcons().add(new Image("file:images/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    protected Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
        boardInitialize("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        root.getChildren().add(tileGroup);
        root.getChildren().add(pieceGroup);
        return root;
    }
}
