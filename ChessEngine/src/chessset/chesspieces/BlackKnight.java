package chessset.chesspieces;

import java.util.ArrayList;

public class BlackKnight extends Piece {

    public BlackKnight(int size, int border, int i){
        super(size, border, i, "file:images/BlackKnight.png");
    }

    public boolean isWhite(){
        return false;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        long KnightSpan = 11333835722063872L;
        long WK=boards[0];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        long NOT_BLACK_PIECES = ~(BK^BQ^BR^BB^BN^BP^WK);

        int k = row*8+column;
        ArrayList<Integer> list = new ArrayList<>();
        long NPP;
        if (k > 36) NPP = KnightSpan << (k-36);
        else NPP = KnightSpan >> (36-k);

        if (k%8<2) NPP &= ~(columns[6]|columns[7]);
        else if (k%8>5) NPP &= ~(columns[0]|columns[1]);

        NPP &= NOT_BLACK_PIECES;
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
