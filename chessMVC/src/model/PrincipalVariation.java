package model;

public class PrincipalVariation {
    private static int moveCounter = 0;
    private static int maxDepth = 6;

    private static String sortMoves(String moveString){
        String sortedString = "";
        return moveString;
    }

    private static int getLegalMove(String sortedMoveString, long WK, long WQ, long WR, long WB, long WN, long WP,
                                    long BK, long BQ, long BR, long BB, long BN, long BP, boolean castleWK, boolean castleWQ,
                                    boolean castleBK, boolean castleBQ, boolean WhiteTurn){
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
            boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
            boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
            boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
            boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
            if (WhiteTurn){
                if ((WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0) return i;
            }
            else{
                if ((BKt&Moves.BKdangerZone(WKt,WQt,WRt,WBt,WNt,WPt,BQt,BRt,BBt,BNt,BPt))==0) return i;
            }
        }
        return -1; // if found none
    }

    public static String PVsearchRoot(long WK, long WQ, long WR, long WB, long WN, long WP,
                                   long BK, long BQ, long BR, long BB, long BN, long BP,
                                   boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                                   String history, boolean WhiteTurn, int depth){
        String moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN, BP,castleBK,castleBQ,history);
        String sortedMoveString = sortMoves(moveString);
        int bestMoveIndex =  getLegalMove(sortedMoveString,WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,castleBK,castleBQ,WhiteTurn);
        int currentScore = -1000000000;
        String bestMovesoFar = moveString.substring(bestMoveIndex, bestMoveIndex+5);
        for (int i=bestMoveIndex;i<moveString.length();i+=5){
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
            boolean castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
            boolean castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
            boolean castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
            boolean castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
            if (Math.abs(currentScore) == Evaluation.mateScore) return bestMovesoFar;
            int score = PVSearch(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,history+move,
                    true,depth+1,1000000000,-1000000000);
            if (score > currentScore) bestMovesoFar = move;
        }
        return bestMovesoFar;
    }

    public static int PVSearch(long WK, long WQ, long WR, long WB, long WN, long WP,
                          long BK, long BQ, long BR, long BB, long BN, long BP,
                          boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                          String history, boolean WhiteTurn, int depth, int alpha, int beta){
        if (depth == maxDepth) return Evaluation.evaluate(WK, WQ, WR, WB, WN, WP, BK, BQ, BR, BB, BN, BP);

        String moveString;
        if (WhiteTurn) moveString = Moves.WhitePossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,history);
        else moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN, BP,castleBK,castleBQ,history);

        String sortedMoveString = sortMoves(moveString);
        int bestMoveIndex =  getLegalMove(sortedMoveString,WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,castleBK,castleBQ,WhiteTurn);

        /* this might be problematic */
        if (bestMoveIndex == -1) return (WhiteTurn)? -Evaluation.mateScore: Evaluation.mateScore;

        String bestMoveSoFar = moveString.substring(bestMoveIndex*5, bestMoveIndex*5+5);

        long WKt = Board.makeTestMove(bestMoveSoFar, WK, 'K');
        long WQt = Board.makeTestMove(bestMoveSoFar, WQ, 'Q');
        long WRt = Board.makeTestMove(bestMoveSoFar, WR, 'R');
        long WBt = Board.makeTestMove(bestMoveSoFar, WB, 'B');
        long WNt = Board.makeTestMove(bestMoveSoFar, WN, 'N');
        long WPt = Board.makeTestMove(bestMoveSoFar, WP, 'P');
        long BKt = Board.makeTestMove(bestMoveSoFar, BK, 'k');
        long BQt = Board.makeTestMove(bestMoveSoFar, BQ, 'q');
        long BRt = Board.makeTestMove(bestMoveSoFar, BR, 'r');
        long BBt = Board.makeTestMove(bestMoveSoFar, BB, 'b');
        long BNt = Board.makeTestMove(bestMoveSoFar, BN, 'n');
        long BPt = Board.makeTestMove(bestMoveSoFar, BP, 'p');
        boolean castleWKt = Board.makeCastleMove(bestMoveSoFar, castleWK, WRt, "KS");
        boolean castleWQt = Board.makeCastleMove(bestMoveSoFar, castleWQ, WRt, "QS");
        boolean castleBKt = Board.makeCastleMove(bestMoveSoFar, castleBK, BRt, "ks");
        boolean castleBQt = Board.makeCastleMove(bestMoveSoFar, castleBQ, BRt, "qs");

        int bestScore = -PVSearch(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,history+bestMoveSoFar,
        !WhiteTurn,depth+1,-beta,-alpha);
        moveCounter += 1;
        if (Math.abs(bestScore) == Evaluation.mateScore) return bestScore;
        if (bestScore > alpha){
            if (bestScore >= beta) return bestScore;
            alpha = bestScore;
        }
        for (int i=bestMoveIndex+5;i<moveString.length();i+=5){
            moveCounter += 1;
            String move = moveString.substring(i, i+5);
            WKt = Board.makeTestMove(move, WK, 'K');
            WQt = Board.makeTestMove(move, WQ, 'Q');
            WRt = Board.makeTestMove(move, WR, 'R');
            WBt = Board.makeTestMove(move, WB, 'B');
            WNt = Board.makeTestMove(move, WN, 'N');
            WPt = Board.makeTestMove(move, WP, 'P');
            BKt = Board.makeTestMove(move, BK, 'k');
            BQt = Board.makeTestMove(move, BQ, 'q');
            BRt = Board.makeTestMove(move, BR, 'r');
            BBt = Board.makeTestMove(move, BB, 'b');
            BNt = Board.makeTestMove(move, BN, 'n');
            BPt = Board.makeTestMove(move, BP, 'p');
            castleWKt = Board.makeCastleMove(move, castleWK, WRt, "KS");
            castleWQt = Board.makeCastleMove(move, castleWQ, WRt, "QS");
            castleBKt = Board.makeCastleMove(move, castleBK, BRt, "ks");
            castleBQt = Board.makeCastleMove(move, castleBQ, BRt, "qs");
            int score = -PVSearch(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,history+bestMoveSoFar,
                    !WhiteTurn,depth+1,-alpha,-alpha-1);
            if (score > alpha && score < beta){
                score = -PVSearch(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,history+bestMoveSoFar,
                        !WhiteTurn,depth+1,-beta,-alpha);
                if (score > alpha) {
                    bestMoveIndex = i;
                    alpha = score;
                }
            }
            if (score > bestScore){
                if (score >= beta) return score;
                bestScore = score;
            }
        }
        return bestScore;
    }

}
