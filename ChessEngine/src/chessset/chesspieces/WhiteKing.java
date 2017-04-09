package chessset.chesspieces;

import engine.Moves;

import java.util.ArrayList;

public class WhiteKing extends Piece {

    public WhiteKing(int size, int border, int i){
        super(size, border, i, "file:images/WhiteKing.png");
    }

    public boolean isWhite(){
        return true;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        long KingSpan=61814108848128L;
        long WQside = 2017612633061982208L;
        long WKside = 8070450532247928832L;

        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        long OCCUPIED = WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP;
        long NOT_WHITE_PIECES = ~(WK^WQ^WR^WB^WN^WP^BK);

        int k = row*8+column;
        ArrayList<Integer> list = new ArrayList<>();
        long KPP;
        if (k > 36) KPP = KingSpan << (k-36);
        else KPP = KingSpan >> (36-k);

        if (k%8<2) KPP &= ~(columns[6]|columns[7]);
        else if (k%8>5) KPP &= ~(columns[0]|columns[1]);

        long dangerZone = Moves.WKdangerZone(boards);
        KPP &= NOT_WHITE_PIECES & ~dangerZone;
        long possibility = KPP & ~(KPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            list.add(newPos);
            KPP &= ~possibility;
            possibility = KPP & ~(KPP-1);
        }
        // castle white king
        if (boards[12] != 0){
            if ((WKside&dangerZone)==0 && ((WKside^WK)&OCCUPIED)==0) list.add(62);
        }
        // castle white queen
        if (boards[13] != 0){
            if ((WQside&dangerZone)==0 && ((WQside^WK|(1L<<57))&OCCUPIED)==0) list.add(58);
        }
        return list;
    }

    public void isThreatened(long[] boards){
        int pos = row*8+column;
        long board = 1L<<pos;
        if ((board& Moves.WKdangerZone(boards))!=0) getTile().threatenColor();
    }
}
