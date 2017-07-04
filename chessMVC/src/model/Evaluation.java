package model;

public class Evaluation {

    public static int mateScore = 100000000;

    private static int[] WPtable = {
            0,  0,  0,  0,  0,  0,  0,  0,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5,  5, 10, 25, 25, 10,  5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            5, 10, 10,-20,-20, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    },
    BPtable =   {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5,  5, 10, 25, 25, 10,  5,  5,
            10, 10, 20, 30, 30, 20, 10, 10,
            50, 50, 50, 50, 50, 50, 50, 50,
            0,  0,  0,  0,  0,  0,  0,  0,
    },
    WNtable = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    },
    BNtable = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    },
    WBtable = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    },
    BBtable = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    },

    WRtable = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10, 10, 10, 10, 10,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    },
    BRtable = {
            0,  0,  0,  5,  5,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 10, 10, 10, 10, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0,
    },
    WQtable = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -5,  0,  5,  5,  5,  5,  0, -5,
             0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    },
    BQtable = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -5,  0,  5,  5,  5,  5,  0,  0, //this line
            -5,  0,  5,  5,  5,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20,
    },
    WKmiddleGame = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            20, 20,  0,  0,  0,  0, 20, 20,
            20, 30, 10,  0,  0, 10, 30, 20
    },
    BKmiddleGame = {
            20, 30, 10,  0,  0, 10, 30, 20,
            20, 20,  0,  0,  0,  0, 20, 20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
    },
    WKendGame = {
            -50,-40,-30,-20,-20,-30,-40,-50,
            -30,-20,-10,  0,  0,-10,-20,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-30,  0,  0,  0,  0,-30,-30,
            -50,-30,-30,-30,-30,-30,-30,-50
    },
    BKendGame = {
            -50,-30,-30,-30,-30,-30,-30,-50,
            -30,-30,  0,  0,  0,  0,-30,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-20,-10,  0,  0,-10,-20,-30,
            -50,-40,-30,-20,-20,-30,-40,-50,
    };

    public static double evaluate(long WK, long WQ, long WR, long WB, long WN, long WP,
                                  long BK, long BQ, long BR, long BB, long BN, long BP, int moveLength){
        return rateMaterialScore(WQ,WR,WB,WN,WP,BQ,BR,BB,BN,BP) +
                ratePositionalScore(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP) +
                rateControlScore(WK,WQ,WR,WB,WN,WP,BK,BQ,BR,BB,BN,BP,moveLength);
    }

    public static double rateControlScore(long WK, long WQ, long WR, long WB, long WN, long WP,
                                         long BK, long BQ, long BR, long BB, long BN, long BP, int moveLength){
        return moveLength + Long.bitCount(Moves.BKdangerZone(WK, WQ, WR, WB, WN, WP, BQ, BR, BB, BN, BP))*5
                - Long.bitCount(Moves.WKdangerZone(WQ, WR, WB, WN, WP, BK, BQ, BR, BB, BN, BP))*5;
    }

    public static double ratePositionalScore(long WK, long WQ, long WR, long WB, long WN, long WP,
                                             long BK, long BQ, long BR, long BB, long BN, long BP){
        int Q = Long.bitCount(WQ);
        int q = Long.bitCount(BQ);
        double positionScore = 0;
        while (WP != 0){
            int position = Long.numberOfTrailingZeros(WP);
            positionScore -= WPtable[position];
            WP = WP ^ (1L<<position);
        }
        while (WQ != 0){
            int position = Long.numberOfTrailingZeros(WQ);
            positionScore -= WQtable[position];
            WQ = WQ ^ (1L<<position);
        }
        while (WR != 0){
            int position = Long.numberOfTrailingZeros(WR);
            positionScore -= WRtable[position];
            WR = WR ^ (1L<<position);
        }
        while (WB != 0){
            int position = Long.numberOfTrailingZeros(WB);
            positionScore -= WBtable[position];
            WB = WB ^ (1L<<position);
        }
        while (WN != 0){
            int position = Long.numberOfTrailingZeros(WN);
            positionScore -= WNtable[position];
            WN = WN ^ (1L<<position);
        }
        while (BP != 0){
            int position = Long.numberOfTrailingZeros(BP);
            positionScore += BPtable[position];
            BP = BP ^ (1L<<position);
        }
        while (BQ != 0){
            int position = Long.numberOfTrailingZeros(BQ);
            positionScore += BQtable[position];
            BQ = BQ ^ (1L<<position);
        }
        while (BR != 0){
            int position = Long.numberOfTrailingZeros(BR);
            positionScore += BRtable[position];
            BR = BR ^ (1L<<position);
        }
        while (BB != 0){
            int position = Long.numberOfTrailingZeros(BB);
            positionScore += BBtable[position];
            BB = BB ^ (1L<<position);
        }
        while (BN != 0){
            int position = Long.numberOfTrailingZeros(BN);
            positionScore += BNtable[position];
            BN = BN ^ (1L<<position);
        }

        int WKposition = Long.numberOfTrailingZeros(WK);
        int BKposition = Long.numberOfTrailingZeros(BK);
        if(Q==0 && q==0){
            positionScore -= WKendGame[WKposition];
            positionScore += BKendGame[BKposition];
        }
        else{
            positionScore -= WKmiddleGame[WKposition];
            positionScore += BKmiddleGame[BKposition];
        }
        return positionScore;
    }

    private static double rateMaterialScore(long WQ, long WR, long WB, long WN, long WP,
                                            long BQ, long BR, long BB, long BN, long BP){
        int Q = Long.bitCount(WQ);
        int R = Long.bitCount(WR);
        int B = Long.bitCount(WB);
        int N = Long.bitCount(WN);
        int P = Long.bitCount(WP);
        int q = Long.bitCount(BQ);
        int r = Long.bitCount(BR);
        int b = Long.bitCount(BB);
        int n = Long.bitCount(BN);
        int p = Long.bitCount(BP);
        int blackBishopValue = (b==1) ? 300 : 330;
        int whiteBishopValue = (B==1) ? 300 : 330;
        return (q-Q)*900 +(r-R)*500 + b*blackBishopValue - B*whiteBishopValue + (n-N)*320 + (p-P)*100;
    }

}
