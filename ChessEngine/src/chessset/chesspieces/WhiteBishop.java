package chessset.chesspieces;

import engine.Moves;

import java.util.ArrayList;

public class WhiteBishop extends Piece {

    public WhiteBishop(int size, int border, int i){
        super(size, border, i, "file:images/WhiteBishop.png");
    }

    public boolean isWhite(){
        return true;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        long OCCUPIED = WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP;
        long NOT_WHITE_PIECES = ~(WK^WQ^WR^WB^WN^WP^BK);

        int k = row*8+column;
        ArrayList<Integer> list = new ArrayList<>();
        long BPP = Moves.DiagonalMoves(k, OCCUPIED)&NOT_WHITE_PIECES;
        long possibility = BPP & ~(BPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            list.add(newPos);
            BPP &= ~possibility;
            possibility = BPP & ~(BPP-1);
        }
        return list;
    }
}
