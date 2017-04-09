package chessset.chesspieces;

import java.util.ArrayList;

public class WhitePawn extends Piece {

    public WhitePawn(int size, int border, int i) {
        super(size, border, i, "file:images/chessset.chesspieces.WhitePawn.png");
    }

    public boolean isWhite(){
        return true;
    }

    public ArrayList<Integer> generateMoves(long[] boards, String history){
        // this method generates and highlight possible routes for white pawns. Thus it doesn't need to highlight
        // the distinction between pawn promotion and normal moves(unlike WPpossibleMoves). Hence though this and
        // WPpossibleMoves in engine.Moves class may seem similar, they serve entirely different purposes.

        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        long BLACK_PIECES = (BQ^BR^BB^BN^BP);
        long EMPTY = ~(WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP);

        int k = row*8+column;
        long singlePieceBoard = 1L << k;

        ArrayList<Integer> list = new ArrayList<>();

        // eat right
        long pawnMoves = ((singlePieceBoard&(~columns[7]))>>7)&BLACK_PIECES;
        long possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            list.add(pos);
            pawnMoves &= (~possibility);
            possibility = pawnMoves & ~(pawnMoves-1);
        }

        // eat left
        pawnMoves = ((singlePieceBoard&(~columns[0]))>>9)&BLACK_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            list.add(pos);
            pawnMoves &= (~possibility);
            possibility = pawnMoves & (~(pawnMoves-1));
        }

        // one move forward
        pawnMoves = ((singlePieceBoard&(~rows[0]))>>8)&EMPTY;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            list.add(pos);
            pawnMoves &= (~possibility);
            possibility = pawnMoves & (~(pawnMoves-1));
        }

        // 2 move forward
        pawnMoves = (singlePieceBoard>>16)&EMPTY&(EMPTY>>8)&rows[4];
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            list.add(pos);
            pawnMoves &= (~possibility);
            possibility = pawnMoves & (~(pawnMoves-1));
        }
        // enpassant
        int lastIndex = history.length()-1;
        if (lastIndex >= 3){
            if ((history.charAt(lastIndex)==history.charAt(lastIndex-2))&&
                    ((history.charAt(lastIndex-3)-'0')+2==(history.charAt(lastIndex-1)-'0'))){
                int enpassantColumn = history.charAt(lastIndex)-'0';
                // en passant right
                possibility = ((((singlePieceBoard&(~columns[7]))<<1)&BP&rows[3]&columns[enpassantColumn])>>8)&EMPTY;
                if (possibility != 0) list.add(2*8+enpassantColumn);
                // en passant left
                possibility = ((((singlePieceBoard&(~columns[0]))>>1)&BP&rows[3]&columns[enpassantColumn])>>8)&EMPTY;
                if (possibility != 0) list.add(2*8+enpassantColumn);
            }
        }
        return list;
    }
}

