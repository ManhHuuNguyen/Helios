package model;

public class Minimax {
    private final static int maxDepth = 6;

    public static String minimaxRoot(long WK, long WQ, long WR, long WB, long WN, long WP,
                                  long BK, long BQ, long BR, long BB, long BN, long BP,
                                  boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                                  String history){

        String moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN, BP,castleBK,castleBQ,history);
        int currentMaxScore = -1000000000;
        String bestMove = "";
        for (int i=0; i<moveString.length();i+=5){
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
            int score = minimax(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                    history+move,true,1,-1000000000,1000000000);
            if (score > currentMaxScore){
                currentMaxScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public static int minimax(long WK, long WQ, long WR, long WB, long WN, long WP,
                              long BK, long BQ, long BR, long BB, long BN, long BP,
                              boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                              String history, boolean WhiteTurn, int depth, int lowerbound, int upperbound){
        if (WhiteTurn && (BK&Moves.BKdangerZone(WK,WQ,WR,WB,WN,WP,BQ,BR,BB,BN,BP))!=0) return -Evaluation.mateScore;
        if (!WhiteTurn && (WK&Moves.WKdangerZone(WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP))!= 0) return Evaluation.mateScore;

        if (depth==maxDepth) return Evaluation.evaluate(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP);

        String moveString;
        if (WhiteTurn) moveString = Moves.WhitePossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,history);
        else moveString = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN, BP,castleBK,castleBQ,history);
        int result = (WhiteTurn)?1000000000:-1000000000;
        for (int i=0; i<moveString.length(); i+=5){
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

            int score = minimax(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,
                    history+move,!WhiteTurn,depth+1,lowerbound, upperbound);

            if (WhiteTurn){
                if (score<result) result = score;

                if (score < upperbound) upperbound = score;

                if (lowerbound > upperbound) break;
            }
            else{
                if (score > result) result = score;

                if (score > lowerbound) lowerbound = score;

                if (lowerbound > upperbound) break;
            }
        }
        return result;
    }


}
