package model;

import java.util.Arrays;

public class Helper {

    public static void BitBoardtoBoardArray(long WK, long WQ, long WR, long WB, long WN, long WP,
                                            long BK, long BQ, long BR, long BB, long BN, long BP){
        String board[][] = {
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "}
        };
        for (int i = 0; i < 64; i++) {
            if (((WK >> i) & 1) == 1) board[i / 8][i % 8] = "K";
            if (((WQ >> i) & 1) == 1) board[i / 8][i % 8] = "Q";
            if (((WR >> i) & 1) == 1) board[i / 8][i % 8] = "R";
            if (((WB >> i) & 1) == 1) board[i / 8][i % 8] = "B";
            if (((WN >> i) & 1) == 1) board[i / 8][i % 8] = "N";
            if (((WP >> i) & 1) == 1) board[i / 8][i % 8] = "P";
            if (((BK >> i) & 1) == 1) board[i / 8][i % 8] = "k";
            if (((BQ >> i) & 1) == 1) board[i / 8][i % 8] = "q";
            if (((BR >> i) & 1) == 1) board[i / 8][i % 8] = "r";
            if (((BB >> i) & 1) == 1) board[i / 8][i % 8] = "b";
            if (((BN >> i) & 1) == 1) board[i / 8][i % 8] = "n";
            if (((BP >> i) & 1) == 1) board[i / 8][i % 8] = "p";
        }
        for (String[] row : board) System.out.println(Arrays.toString(row));
        System.out.println();
    }
}
