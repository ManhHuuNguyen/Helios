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

        Board board = new Board("3b4/8/8/KpP5/8/1n6/8/k7 w KQkq - 0 1");
        Scene scene = new Scene(GameView.boardscreen, 572, 572);
        GameController.addClickEvent(GameView.boardscreen, board);
        GameView.initMapPiece();
        GameView.initBoard(board);
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
        Helper.BitBoardtoBoardArray(board);
        System.out.println("Number of castling: " + Perft.testingSpecial);

//        String move = "46 CK";
//        long WKt = Board.makeTestMove(move, board.WK, 'K');
//        long WQt = Board.makeTestMove(move, board.WQ, 'Q');
//        long WRt = Board.makeTestMove(move, board.WR, 'R');
//        long WBt = Board.makeTestMove(move, board.WB, 'B');
//        long WNt = Board.makeTestMove(move, board.WN, 'N');
//        long WPt = Board.makeTestMove(move, board.WP, 'P');
//        long BKt = Board.makeTestMove(move, board.BK, 'k');
//        long BQt = Board.makeTestMove(move, board.BQ, 'q');
//        long BRt = Board.makeTestMove(move, board.BR, 'r');
//        long BBt = Board.makeTestMove(move, board.BB, 'b');
//        long BNt = Board.makeTestMove(move, board.BN, 'n');
//        long BPt = Board.makeTestMove(move, board.BP, 'p');
//        boolean castleWKt = Board.makeCastleMove(move, board.castleWK, WRt, "KS");
//        boolean castleWQt = Board.makeCastleMove(move, board.castleWQ, WRt, "QS");
//        boolean castleBKt = Board.makeCastleMove(move, board.castleBK, BRt, "ks");
//        boolean castleBQt = Board.makeCastleMove(move, board.castleBQ, BRt, "qs");
//        Helper.BitBoardToBoardArray2(WKt, WQt, WRt, WBt, WNt, WPt, BKt, BQt, BRt, BBt, BNt, BPt);
//        System.out.println(castleWKt + " " + castleWQt + " " + castleBKt + " " + castleBQt);
    }
}
