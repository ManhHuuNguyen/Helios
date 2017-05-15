package model;

import java.util.Arrays;

public class Helper {

    public static void BitBoardtoBoardArray(Board boardmodel){

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
            if (((boardmodel.WK >> i) & 1) == 1) board[i / 8][i % 8] = "K";
            if (((boardmodel.WQ >> i) & 1) == 1) board[i / 8][i % 8] = "Q";
            if (((boardmodel.WR >> i) & 1) == 1) board[i / 8][i % 8] = "R";
            if (((boardmodel.WB >> i) & 1) == 1) board[i / 8][i % 8] = "B";
            if (((boardmodel.WN >> i) & 1) == 1) board[i / 8][i % 8] = "N";
            if (((boardmodel.WP >> i) & 1) == 1) board[i / 8][i % 8] = "P";
            if (((boardmodel.BK >> i) & 1) == 1) board[i / 8][i % 8] = "k";
            if (((boardmodel.BQ >> i) & 1) == 1) board[i / 8][i % 8] = "q";
            if (((boardmodel.BR >> i) & 1) == 1) board[i / 8][i % 8] = "r";
            if (((boardmodel.BB >> i) & 1) == 1) board[i / 8][i % 8] = "b";
            if (((boardmodel.BN >> i) & 1) == 1) board[i / 8][i % 8] = "n";
            if (((boardmodel.BP >> i) & 1) == 1) board[i / 8][i % 8] = "p";
        }
        for (String[] row : board) System.out.println(Arrays.toString(row));
        System.out.println();
    }
}
