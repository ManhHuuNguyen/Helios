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
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Scene scene = new Scene(GameView.boardscreen, 572, 572);
        GameController.addClickEvent(GameView.boardscreen, board);
        GameView.initMapPiece();
        GameView.initBoard(board);
        stage.setTitle("Helios");
        stage.getIcons().add(new Image("file:images/icon.png"));
        stage.setScene(scene);
        stage.show();
        // secondary stage
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Helios");
        secondaryStage.getIcons().add(new Image("file:images/icon.png"));
        GameView.initSecondaryScene();
        GameController.addSecondaryClickEvent(GameView.retractMoveButton, GameView.makeMoveButton, board);
        Scene secondaryScene = new Scene(GameView.secondaryscreen, 250, 572);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.show();

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
    }
}
