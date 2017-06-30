package model;

import java.util.Arrays;
import java.util.HashMap;

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

    public static String convertMove(String move){
        if (move.equals("None"))return move;
        HashMap<String, String> map = new HashMap<String, String>(){{
            put("0", "a");
            put("1", "b");
            put("2", "c");
            put("3", "d");
            put("4", "e");
            put("5", "f");
            put("6", "g");
            put("7", "h");
        }};
        if (move.charAt(3)=='P'){
            if (move.charAt(4)=='P') return map.get(Character.toString(move.charAt(0))) + "7" + map.get(Character.toString(move.charAt(1))) + "8" + move.charAt(2);
            else return map.get(Character.toString(move.charAt(0))) + "2" + map.get(Character.toString(move.charAt(1))) + "1" + move.charAt(2);
        }
        else if (move.charAt(3)=='E'){
            if (move.charAt(4)=='P') return map.get(Character.toString(move.charAt(0))) + "5" + map.get(Character.toString(move.charAt(1))) + "6";
            else return map.get(Character.toString(move.charAt(0))) + "4" + map.get(Character.toString(move.charAt(1))) + "3";
        }
        else if (move.charAt(3)=='C'){
            if (move.substring(0, 2).equals("46")) return "0-0";
            else return "0-0-0";
        }
        return map.get(Character.toString(move.charAt(1))) + (8-(move.charAt(0)-'0')) + map.get(Character.toString(move.charAt(3))) + (8-(move.charAt(2)-'0'));
    }
}
