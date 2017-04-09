package chessset.chesspieces;

import java.util.ArrayList;

public class WhiteKnight extends Piece {

    public WhiteKnight(int size, int border, int i){
        super(size, border, i, "file:images/WhiteKnight.png");
    }

    public boolean isWhite(){
        return true;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        long KnightSpan = 11333835722063872L;
        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6];

        long NOT_WHITE_PIECES = ~(WK^WQ^WR^WB^WN^WP^BK);

        int k = row*8+column;
        ArrayList<Integer> list = new ArrayList<>();
        long NPP;
        if (k > 36) NPP = KnightSpan << (k-36);
        else NPP = KnightSpan >> (36-k);

        if (k%8<2) NPP &= ~(columns[6]|columns[7]);
        else if (k%8>5) NPP &= ~(columns[0]|columns[1]);

        NPP &= NOT_WHITE_PIECES;
        long possibility = NPP & ~(NPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            list.add(newPos);
            NPP &= ~possibility;
            possibility = NPP & ~(NPP-1);
        }
        return list;
    }
}
