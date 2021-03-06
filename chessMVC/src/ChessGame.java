import controller.GameController;
import javafx.application.Application;
import model.Board;
import model.Helper;
import model.Perft;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.GameView;


public class ChessGame extends Application{

    public void start (Stage stage) throws Exception{
        Scene scene = GameController.startNewGame();
        stage.setTitle("Helios");
        stage.getIcons().add(new Image("file:images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public static void main2 (String[] args){
        Board board = new Board("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
        long startTime = System.nanoTime();
        int result = Perft.perftRoot(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
        board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP,
        board.castleWK, board.castleWQ, board.castleBK, board.castleBQ,
        board.WhiteTurn, board.history, 0, 5);
        long endTime = System.nanoTime();
        System.out.println("There are: " + result + " nodes");
        System.out.println("It takes " + ((endTime-startTime)/1000000000) + " seconds");
        System.out.println("The speed is: " + result/((endTime-startTime)/1000000000) + " nodes per second");
        Helper.BitBoardtoBoardArray(board);
        System.out.println("Number of castling: " + Perft.testingSpecial);
    }
}
