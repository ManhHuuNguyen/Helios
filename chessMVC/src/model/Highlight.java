package model;

import java.util.ArrayList;

public class Highlight {

    private static final long[] rows = {255L, 65280L, 16711680L, 4278190080L, 1095216660480L,
            280375465082880L, 71776119061217280L, -72057594037927936L},
            columns ={72340172838076673L, 144680345676153346L, 289360691352306692L, 578721382704613384L,
            1157442765409226768L, 2314885530818453536L, 4629771061636907072L, -9187201950435737472L};

    public static ArrayList<Integer> generateMoves(Character chosenPiece, int[] oldPos, Board board) {

        ArrayList<Integer> list = new ArrayList<>();
        if (chosenPiece != null) {
            long OCCUPIED = board.WK ^ board.WQ ^ board.WR ^ board.WB ^ board.WN ^ board.WP ^ board.BK ^ board.BQ ^ board.BR ^ board.BB ^ board.BN ^ board.BP;
            long NOT_WHITE_PIECES = ~(board.WK ^ board.WQ ^ board.WR ^ board.WB ^ board.WN ^ board.WP ^ board.BK);
            long BLACK_PIECES = (board.BQ ^ board.BR ^ board.BB ^ board.BN ^ board.BP);
            long EMPTY = ~(board.WK ^ board.WQ ^ board.WR ^ board.WB ^ board.WN ^ board.WP ^ board.BK ^ board.BQ ^ board.BR ^ board.BB ^ board.BN ^ board.BP);
            int k = oldPos[1] * 8 + oldPos[0];

            if (chosenPiece == 'B') { // white bishop
                long BPP = Moves.DiagonalMoves(k, OCCUPIED) & NOT_WHITE_PIECES;
                long possibility = BPP & ~(BPP - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(possibility);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "B";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    BPP &= ~possibility;
                    possibility = BPP & ~(BPP - 1);
                }
            }
            else if (chosenPiece == 'K') { // white rook
                long KingSpan = 61814108848128L, WQside = 2017612633061982208L, WKside = 8070450532247928832L;

                long KPP;
                if (k > 36) KPP = KingSpan << (k - 36);
                else KPP = KingSpan >> (36 - k);

                if (k % 8 < 2) KPP &= ~(columns[6] | columns[7]);
                else if (k % 8 > 5) KPP &= ~(columns[0] | columns[1]);

                long dangerZone = Moves.WKdangerZone(board.WQ, board.WR, board.WB, board.WN, board.WP,
                        board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP);
                KPP &= NOT_WHITE_PIECES & ~dangerZone;
                long possibility = KPP & ~(KPP - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(possibility);
                    list.add(newPos);
                    KPP &= ~possibility;
                    possibility = KPP & ~(KPP - 1);
                }
                // castle white king
                if (board.castleWK) {
                    if ((WKside & dangerZone) == 0 && ((WKside ^ board.WK) & OCCUPIED) == 0) list.add(62);
                }
                // castle white queen
                if (board.castleWQ) {
                    if ((WQside & dangerZone) == 0 && ((WQside ^ board.WK | (1L << 57)) & OCCUPIED) == 0) list.add(58);
                }
            }
            else if (chosenPiece == 'N') {
                long KnightSpan = 11333835722063872L;

                long NPP;
                if (k > 36) NPP = KnightSpan << (k - 36);
                else NPP = KnightSpan >> (36 - k);

                if (k % 8 < 2) NPP &= ~(columns[6] | columns[7]);
                else if (k % 8 > 5) NPP &= ~(columns[0] | columns[1]);

                NPP &= NOT_WHITE_PIECES;
                long possibility = NPP & ~(NPP - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(possibility);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "N";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    NPP &= ~possibility;
                    possibility = NPP & ~(NPP - 1);
                }
            }
            else if (chosenPiece == 'P') {
                long singlePieceBoard = 1L << k;

                // eat right
                long pawnMoves = ((singlePieceBoard & (~columns[7])) >> 7) & BLACK_PIECES;
                long possibility = pawnMoves & ~(pawnMoves - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(pawnMoves);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "P";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    pawnMoves &= (~possibility);
                    possibility = pawnMoves & ~(pawnMoves - 1);
                }

                // eat left
                pawnMoves = ((singlePieceBoard & (~columns[0])) >> 9) & BLACK_PIECES;
                possibility = pawnMoves & ~(pawnMoves - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(pawnMoves);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "P";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    pawnMoves &= (~possibility);
                    possibility = pawnMoves & (~(pawnMoves - 1));
                }

                // one move forward
                pawnMoves = ((singlePieceBoard & (~rows[0])) >> 8) & EMPTY;
                possibility = pawnMoves & ~(pawnMoves - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(pawnMoves);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "P";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    pawnMoves &= (~possibility);
                    possibility = pawnMoves & (~(pawnMoves - 1));
                }

                // 2 move forward
                pawnMoves = (singlePieceBoard >> 16) & EMPTY & (EMPTY >> 8) & rows[4];
                possibility = pawnMoves & ~(pawnMoves - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(pawnMoves);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "P";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    pawnMoves &= (~possibility);
                    possibility = pawnMoves & (~(pawnMoves - 1));
                }
                // enpassant
                int lastIndex = board.history.length() - 1;
                if (lastIndex >= 3) {
                    if ((board.history.charAt(lastIndex) == board.history.charAt(lastIndex - 2)) &&
                            ((board.history.charAt(lastIndex - 3) - '0') + 2 == (board.history.charAt(lastIndex - 1) - '0'))) {
                        int enpassantColumn = board.history.charAt(lastIndex) - '0';
                        // en passant right
                        possibility = ((((singlePieceBoard & (~columns[7])) << 1) & board.BP & rows[3] & columns[enpassantColumn]) >> 8) & EMPTY;
                        if (possibility != 0){
                            String move = (enpassantColumn-1) + "" + enpassantColumn + " EP";
                            long WKt = Board.makeTestMove(move, board.WK, 'K');
                            long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                            long WRt = Board.makeTestMove(move, board.WR, 'R');
                            long WBt = Board.makeTestMove(move, board.WB, 'B');
                            long WNt = Board.makeTestMove(move, board.WN, 'N');
                            long WPt = Board.makeTestMove(move, board.WP, 'P');
                            long BKt = Board.makeTestMove(move, board.BK, 'k');
                            long BQt = Board.makeTestMove(move, board.BQ, 'q');
                            long BRt = Board.makeTestMove(move, board.BR, 'r');
                            long BBt = Board.makeTestMove(move, board.BB, 'b');
                            long BNt = Board.makeTestMove(move, board.BN, 'n');
                            long BPt = Board.makeTestMove(move, board.BP, 'p');
                            if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                                list.add(2 * 8 + enpassantColumn);
                            }
                        }
                        // en passant left
                        possibility = ((((singlePieceBoard & (~columns[0])) >> 1) & board.BP & rows[3] & columns[enpassantColumn]) >> 8) & EMPTY;
                        if (possibility != 0){
                            String move = (enpassantColumn+1) + "" + enpassantColumn + " EP";
                            long WKt = Board.makeTestMove(move, board.WK, 'K');
                            long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                            long WRt = Board.makeTestMove(move, board.WR, 'R');
                            long WBt = Board.makeTestMove(move, board.WB, 'B');
                            long WNt = Board.makeTestMove(move, board.WN, 'N');
                            long WPt = Board.makeTestMove(move, board.WP, 'P');
                            long BKt = Board.makeTestMove(move, board.BK, 'k');
                            long BQt = Board.makeTestMove(move, board.BQ, 'q');
                            long BRt = Board.makeTestMove(move, board.BR, 'r');
                            long BBt = Board.makeTestMove(move, board.BB, 'b');
                            long BNt = Board.makeTestMove(move, board.BN, 'n');
                            long BPt = Board.makeTestMove(move, board.BP, 'p');
                            if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                                list.add(2 * 8 + enpassantColumn);
                            }
                        }
                    }
                }
            }
            else if (chosenPiece == 'Q') {
                long QPP = (Moves.DiagonalMoves(k, OCCUPIED) & NOT_WHITE_PIECES) ^ (Moves.CrossMoves(k, OCCUPIED) & NOT_WHITE_PIECES);
                long possibility = QPP & ~(QPP - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(possibility);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "Q";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    QPP &= ~possibility;
                    possibility = QPP & ~(QPP - 1);
                }
            }
            else if (chosenPiece == 'R') {
                long RPP = Moves.CrossMoves(k, OCCUPIED) & NOT_WHITE_PIECES;
                long possibility = RPP & ~(RPP - 1);
                while (possibility != 0) {
                    int newPos = Long.numberOfTrailingZeros(possibility);
                    String move = (oldPos[1]) +""+ (oldPos[0]) +""+ (newPos/8) +""+ (newPos%8) + "R";
                    long WKt = Board.makeTestMove(move, board.WK, 'K');
                    long WQt = Board.makeTestMove(move, board.WQ, 'Q');
                    long WRt = Board.makeTestMove(move, board.WR, 'R');
                    long WBt = Board.makeTestMove(move, board.WB, 'B');
                    long WNt = Board.makeTestMove(move, board.WN, 'N');
                    long WPt = Board.makeTestMove(move, board.WP, 'P');
                    long BKt = Board.makeTestMove(move, board.BK, 'k');
                    long BQt = Board.makeTestMove(move, board.BQ, 'q');
                    long BRt = Board.makeTestMove(move, board.BR, 'r');
                    long BBt = Board.makeTestMove(move, board.BB, 'b');
                    long BNt = Board.makeTestMove(move, board.BN, 'n');
                    long BPt = Board.makeTestMove(move, board.BP, 'p');
                    if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0){
                        list.add(newPos);
                    }
                    RPP &= ~possibility;
                    possibility = RPP & ~(RPP - 1);
                }
            }
            return list;
        }
        return list;
    }
}
