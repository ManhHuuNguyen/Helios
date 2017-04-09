import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserInterface extends GameGeneration{

    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());
        long startTime = System.nanoTime();
        int result = Perft.perftRoot(boards, history, WhiteTurn, 0, 5, map);
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
        boardInitialize("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - -");
        root.getChildren().add(tileGroup);
        root.getChildren().add(pieceGroup);
        return root;
    }
}
