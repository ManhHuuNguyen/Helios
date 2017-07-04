package model;

public class Perft {
    private static int perftCounter = 0;
    public static int testingSpecial = 0;

    public static int perftRoot(long WK, long WQ, long WR, long WB, long WN, long WP,
                                long BK, long BQ, long BR, long BB, long BN, long BP,
                                boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                                boolean WhiteTurn, String history, int depth, int maxDepth){
        String moves;
        int totalMoves = 0;
        if (WhiteTurn) moves = Moves.WhitePossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,history);
        else moves = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN, BP,castleBK,castleBQ,history);
        for (int i=0; i<moves.length(); i+=5){
            String move = moves.substring(i, i+5);
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
            if ((WhiteTurn && (WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0) ||
                    ((!WhiteTurn && (BKt&Moves.BKdangerZone(WKt,WQt,WRt,WBt,WNt,WPt,BQt,BRt,BBt,BNt,BPt))==0))){
                perft(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,move,!WhiteTurn,depth+1,maxDepth);
                totalMoves += perftCounter;
                perftCounter = 0;
            }
        }
        return totalMoves;
    }

    public static void perft(long WK, long WQ, long WR, long WB, long WN, long WP,
                             long BK, long BQ, long BR, long BB, long BN, long BP,
                             boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ,
                             String history, boolean WhiteTurn, int depth, int maxDepth){
        if (depth< maxDepth){
            String moves;
            if (WhiteTurn) moves = Moves.WhitePossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleWK,castleWQ,history);
            else moves = Moves.BlackPossibleMoves(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,castleBK,castleBQ,history);
            for (int i=0; i<moves.length(); i+=5){
                String move = moves.substring(i, i+5);
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
                if ((WhiteTurn && (WKt&Moves.WKdangerZone(WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt))==0) ||
                        ((!WhiteTurn && (BKt&Moves.BKdangerZone(WKt,WQt,WRt,WBt,WNt,WPt,BQt,BRt,BBt,BNt,BPt))==0))) {
                    if (depth + 1 == maxDepth) perftCounter += 1;

                    if (move.charAt(3)=='C' && depth+1==maxDepth) testingSpecial += 1;
                    perft(WKt,WQt,WRt,WBt,WNt,WPt,BKt,BQt,BRt,BBt,BNt,BPt,castleWKt,castleWQt,castleBKt,castleBQt,move,!WhiteTurn,depth+1,maxDepth);
                }
            }
        }
    }
}
