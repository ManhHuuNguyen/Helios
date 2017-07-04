package model;

public class PrincipalVariation {
    private static int moveCounter = 0;
    private static final int maxDepth = 6;

    private static String sortMoves(String moveString, long WK, long WQ, long WR, long WB, long WN, long WP,
                                    long BK, long BQ, long BR, long BB, long BN, long BP,
                                    boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ){
        double[] scorelist = new double[moveString.length()/5];
        for (int i=0; i< moveString.length(); i+=5){
            String move = moveString.substring(i, i+5);
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
//            boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
//            boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
            boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
            boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
            String newMoveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleBKt,castleBQt,move);
            double score = Evaluation.evaluate(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,newMoveString.length());
            scorelist[i/5] = score;
        }
        String bestMoves = ""; String otherMoves = moveString;
        for (int i=0; i<Math.min(5, moveString.length()/5); i++){
            double scoreMax = Double.NEGATIVE_INFINITY;
            int maxLocation = 0;
            for (int k=0; k< moveString.length()/5; k++){
                if (scorelist[k] > scoreMax){
                    scoreMax = scorelist[k];
                    maxLocation = k;
                }
            }
            scorelist[maxLocation] = Double.NEGATIVE_INFINITY;
            bestMoves += moveString.substring(maxLocation*5, maxLocation*5+5);
            otherMoves = otherMoves.replace(moveString.substring(maxLocation*5, maxLocation*5+5), "");
        }
        return bestMoves + otherMoves;
    }

    public static int getLegalMove(String sortedMoveString,
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
        long startTime = System.nanoTime();
        String moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleBK,castleBQ,history);
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        String bestMove = "";
        int firstLegalMove = getLegalMove(moveString,WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,false);
        if (firstLegalMove != -1){
            for (int i=firstLegalMove;i<moveString.length();i+=5) {
                String move = moveString.substring(i, i+5);
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
                            true, move, 1, -alpha-1, -alpha);
                    if (alpha<score && score<beta){
                        score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                                true, move, 1, -beta, -score);
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
                            true, move, 1, -beta, -alpha);
                }
                if (score > alpha){
                    alpha = score;
                    bestMove = move;
                }
                if (alpha >= beta) break;
            }
        }
        long endTime = System.nanoTime();
        System.out.println("It takes " + ((endTime-startTime)/1000000000) + " seconds");
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
        if (depth==maxDepth) return turn*Evaluation.evaluate(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP, moveString.length());


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
                        !WhiteTurn, move, depth+1, -alpha-1, -alpha);
                if (alpha<score && score<beta){
                    score = -pvs(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                            !WhiteTurn, move, depth+1, -beta, -score);
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
                        !WhiteTurn, move, depth+1, -beta, -alpha);
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta) break;
        }
        return alpha;
    }

}
