package chessset.chesspieces;

import engine.Moves;

import java.util.ArrayList;

public class BlackKing extends Piece {

    public BlackKing(int size, int border, int i){
        super(size, border, i, "file:images/chessset.chesspieces.BlackKing.png");
    }

    public boolean isWhite(){
        return false;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        long KingSpan=61814108848128L;
        long BKside = 112L;
        long BQside = 28L;

        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        long OCCUPIED = WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP;
        long NOT_BLACK_PIECES = ~(BK^BQ^BR^BB^BN^BP^WK);

        int k = row*8+column;
        ArrayList<Integer> list = new ArrayList<>();
        long KPP;
        if (k > 36) KPP = KingSpan << (k-36);
        else KPP = KingSpan >> (36-k);

        if (k%8<1) KPP &= ~columns[7];
        else if (k%8>6) KPP &= ~columns[0];

        long dangerZone = Moves.BKdangerZone(boards);
        KPP &= NOT_BLACK_PIECES & ~dangerZone;
        long possibility = KPP & ~(KPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            list.add(newPos);
            KPP &= ~possibility;
            possibility = KPP & ~(KPP-1);
        }
        // castle black king
        if (boards[14] != 0){
            if ((BKside&dangerZone)==0 && ((BKside^BK)&OCCUPIED)==0) list.add(6);
        }
        // castle black queen
        if (boards[15] != 0){
            if ((BQside&dangerZone)==0 && ((BQside^BK|(1L<<1))&OCCUPIED)==0) list.add(2);
        }
        return list;
    }

    public void isThreatened(long[] boards){
        int pos = row*8+column;
        long board = 1L<<pos;
        if ((board& Moves.BKdangerZone(boards))!=0) getTile().threatenColor();
    }
}
