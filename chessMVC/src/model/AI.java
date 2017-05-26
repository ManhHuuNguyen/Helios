package model;

public class AI {

    public static void AImakeMove(Board board){

        String bestMove = Minimax.minimaxRoot(board.WK,board.WQ,board.WR,board.WB,board.WN,board.WP,
                                              board.BK,board.BQ,board.BR,board.BB,board.BN,board.BP,
                                              board.castleWK,board.castleWQ,board.castleBK,board.castleBQ,board.history);
        board.movePiece(bestMove);
    }
}
