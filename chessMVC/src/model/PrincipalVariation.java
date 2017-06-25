package model;

public class PrincipalVariation {
    private static int moveCounter = 0;
    private static final int maxDepth = 6;

    private static String sortMoves(String moveString){
        String sortedString = "";
        return moveString;
    }

    private static int getLegalMove(String sortedMoveString,
                                    long WK, long WQ, long WR, long WB, long WN, long WP,
                                    long BK, long BQ, long BR, long BB, long BN, long BP,
                                    boolean WhiteTurn){
        for (int i=0; i< sortedMoveString.length(); i+=5){
            String move = sortedMoveString.substring(i, i+5);
            long WKt = Board.makeTestMove(move, WK, 'K');
            long WQt = Board.makeTestMove(move, WQ, 'Q');
            long WRt = Board.makeTestMove(move, WR, 'R');
            long WBt = Board.makeTestMove(move, WB, 'B');
            long WNt = Board.makeTestMove(move, WN, 'N');
            long WPt = Board.makeTestMove(move, WP, 'P');
            long BKt = Board.makeTestMove(move, BK, 'k');
            long BQt = Board.makeTestMove(move, BQ, 'q');
            long BRt = Board.makeTestMove(move, BR, 'r');
            long BBt = Board.makeTestMove(move, BB, 'b');
            long BNt = Board.makeTestMove(move, BN, 'n');
            long BPt = Board.makeTestMove(move, BP, 'p');
            if (WhiteTurn){
                if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0) return i;
            }
            else{
                if ((BKt&Moves.BKdangerZone(WKt,WQt,WRt,WBt,WNt,WPt,BQt,BRt,BBt,BNt,BPt))==0) return i;
            }
        }
        return -1; // if found none
    }

    public static String pvsRoot(long WK, long WQ, long WR, long WB, long WN, long WP,
                                 long BK, long BQ, long BR, long BB, long BN, long BP,
                                 boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                                 String history){
        String moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleBK,castleBQ,history);
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        String bestMove = "";
        int firstLegalMove = getLegalMove(moveString,WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,false);
        if (firstLegalMove != -1){
            for (int i=firstLegalMove;i<moveString.length();i+=5) {
                String move = moveString.substring(i, i + 5);
                double score;
                if (i != firstLegalMove) {
                    long WKt = Board.makeTestMove(move, WK, 'K');
                    long WQt = Board.makeTestMove(move, WQ, 'Q');
                    long WRt = Board.makeTestMove(move, WR, 'R');
                    long WBt = Board.makeTestMove(move, WB, 'B');
                    long WNt = Board.makeTestMove(move, WN, 'N');
                    long WPt = Board.makeTestMove(move, WP, 'P');
                    long BKt = Board.makeTestMove(move, BK, 'k');
                    long BQt = Board.makeTestMove(move, BQ, 'q');
                    long BRt = Board.makeTestMove(move, BR, 'r');
                    long BBt = Board.makeTestMove(move, BB, 'b');
                    long BNt = Board.makeTestMove(move, BN, 'n');
                    long BPt = Board.makeTestMove(move, BP, 'p');
                    boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
                    boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
                    boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
                    boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
                    score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                            true, history+move, 1, -alpha-1, -alpha);
                    if (alpha<score && score<beta){
                        score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                                true, history+move, 1, -beta, -score);
                    }
                }
                else{
                    long WKt = Board.makeTestMove(move, WK, 'K');
                    long WQt = Board.makeTestMove(move, WQ, 'Q');
                    long WRt = Board.makeTestMove(move, WR, 'R');
                    long WBt = Board.makeTestMove(move, WB, 'B');
                    long WNt = Board.makeTestMove(move, WN, 'N');
                    long WPt = Board.makeTestMove(move, WP, 'P');
                    long BKt = Board.makeTestMove(move, BK, 'k');
                    long BQt = Board.makeTestMove(move, BQ, 'q');
                    long BRt = Board.makeTestMove(move, BR, 'r');
                    long BBt = Board.makeTestMove(move, BB, 'b');
                    long BNt = Board.makeTestMove(move, BN, 'n');
                    long BPt = Board.makeTestMove(move, BP, 'p');
                    boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
                    boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
                    boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
                    boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
                    score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                            true, history+move, 1, -beta, -alpha);
                }
                if (score > alpha){
                    alpha = score;
                    bestMove = move;
                }
                if (alpha >= beta) break;
            }
        }
        return bestMove;
    }

    private static double pvs(long WK, long WQ, long WR, long WB, long WN, long WP,
                              long BK, long BQ, long BR, long BB, long BN, long BP,
                              boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                              boolean WhiteTurn, String history, int depth, double alpha, double beta){
        int turn = (WhiteTurn)?-1:1;
        String moveString = (WhiteTurn)?Moves.WhitePossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,history):
                Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleBK,castleBQ,history);
        int firstLegalMove = getLegalMove(moveString,WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,WhiteTurn);

        // check end note
        if (firstLegalMove == -1){
            if (WhiteTurn && (WK & Moves.WKdangerZone(WQ, WR, WB, WN, WP, BK, BQ, BR, BB, BN, BP)) != 0){
                return -(7 - depth) * Evaluation.mateScore;
            }
            else if (!WhiteTurn && (BK & Moves.BKdangerZone(WK, WQ, WR, WB, WN, WP, BQ, BR, BB, BN, BP)) != 0) {
                return (7 - depth) * Evaluation.mateScore;
            }
            else return 0;
        }

        if (WhiteTurn && (BK&Moves.BKdangerZone(WK, WQ, WR, WB, WN, WP, BQ, BR, BB, BN, BP))!=0)
            return (7 - depth) * Evaluation.mateScore;
        if (!WhiteTurn && (WK& Moves.WKdangerZone(WQ, WR, WB, WN, WP, BK, BQ, BR, BB, BN, BP))!=0)
            return -(7 - depth) * Evaluation.mateScore;
        if (depth==maxDepth) return turn*Evaluation.evaluate(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP);


        for (int i=firstLegalMove;i<moveString.length();i+=5){
            String move = moveString.substring(i,i+5);
            double score;
            if (i!=firstLegalMove){
                long WKt = Board.makeTestMove(move, WK, 'K');
                long WQt = Board.makeTestMove(move, WQ, 'Q');
                long WRt = Board.makeTestMove(move, WR, 'R');
                long WBt = Board.makeTestMove(move, WB, 'B');
                long WNt = Board.makeTestMove(move, WN, 'N');
                long WPt = Board.makeTestMove(move, WP, 'P');
                long BKt = Board.makeTestMove(move, BK, 'k');
                long BQt = Board.makeTestMove(move, BQ, 'q');
                long BRt = Board.makeTestMove(move, BR, 'r');
                long BBt = Board.makeTestMove(move, BB, 'b');
                long BNt = Board.makeTestMove(move, BN, 'n');
                long BPt = Board.makeTestMove(move, BP, 'p');
                boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
                boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
                boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
                boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
                score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                        !WhiteTurn, history+move, depth+1, -alpha-1, -alpha);
                if (alpha<score && score<beta){
                    score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                            !WhiteTurn, history+move, depth+1, -beta, -score);
                }
            }
            else{
                long WKt = Board.makeTestMove(move, WK, 'K');
                long WQt = Board.makeTestMove(move, WQ, 'Q');
                long WRt = Board.makeTestMove(move, WR, 'R');
                long WBt = Board.makeTestMove(move, WB, 'B');
                long WNt = Board.makeTestMove(move, WN, 'N');
                long WPt = Board.makeTestMove(move, WP, 'P');
                long BKt = Board.makeTestMove(move, BK, 'k');
                long BQt = Board.makeTestMove(move, BQ, 'q');
                long BRt = Board.makeTestMove(move, BR, 'r');
                long BBt = Board.makeTestMove(move, BB, 'b');
                long BNt = Board.makeTestMove(move, BN, 'n');
                long BPt = Board.makeTestMove(move, BP, 'p');
                boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
                boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
                boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
                boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
                score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                        !WhiteTurn, history+move, depth+1, -beta, -alpha);
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta) break;
        }
        return alpha;
    }

}
