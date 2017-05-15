import javafx.application.Application;
import model.Board;
import model.Helper;
import model.Perft;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.GameView;

public class ChessGame extends Application{

    public void start (Stage stage) throws Exception{

        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        GameView.initBoard();
        Scene scene = new Scene(GameView.board, 572, 572);
        GameView.initMapPiece();
        GameView.update(board);
        stage.setTitle("Helios");
        stage.getIcons().add(new Image("file:images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public static void main2 (String[] args){
        Board board = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        long startTime = System.nanoTime();
        int result = Perft.perftRoot(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
        board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP,
        board.castleWK, board.castleWQ, board.castleBK, board.castleBQ,
        board.WhiteTurn, board.history, 0, 4);
        long endTime = System.nanoTime();
        System.out.println("There are: " + result + " nodes");
        System.out.println("It takes " + ((endTime-startTime)/1000000000) + " seconds");
        System.out.println("The speed is: " + result/((endTime-startTime)/1000000000) + " nodes per second");
        Helper.BitBoardtoBoardArray(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
                                    board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP);
        System.out.println("Number of castling: " + Perft.testingSpecial);
    }

}
