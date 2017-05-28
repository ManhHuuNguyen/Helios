package model;

import java.util.Arrays;

/*for testing purposes*/
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

    public static void BitBoardToBoardArray2(long WK, long WQ, long WR, long WB, long WN, long WP,
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

    public static void BitBoardToBoardArray3(long bitboard){
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
        for (int i=0;i<64;i++){
            if(((bitboard>>i)&1)==1) board[i/8][i%8]="X";
        }
        for (String[] row : board) System.out.println(Arrays.toString(row));
        System.out.println();
    }

    public static String[][] clone2DArray(String[][] array){
        String[][] newArray = new String[8][8];
        for (int i=0;i<8;i++){
            for (int k=0;k<8;k++){
                newArray[i][k] = array[i][k];
            }
        }
        return newArray;
    }
}
