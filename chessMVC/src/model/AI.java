package model;

public class AI {

    public static void AImakeMove(Board board){
        String move = PrincipalVariation.pvsRoot(board.WK,board.WQ,board.WR,board.WB,board.WN,board.WP,
                board.BK,board.BQ,board.BR,board.BB,board.BN,board.BP,board.castleWK,board.castleWQ,board.castleBK,
                board.castleBQ, board.history);
        board.movePiece(move);
    }
}
