import engine.Moves;

import java.util.HashMap;

public class Perft {

    private static int perftCounter = 0;

    public static int perftRoot(long[] boards, String history, boolean WhiteTurn, int depth, int maxDepth, HashMap<Character, String> map){
        String moves;
        int totalMoves = 0;
        if (WhiteTurn) moves = Moves.WhitePossibleMoves(boards, history);
        else moves = Moves.BlackPossibleMoves(boards, history);
        for (int i=0; i<moves.length(); i+=4){
            String move = moves.substring(i, i+4);
            long[] newBoards = new long[16];
            for(int k=0; k<16; k++) newBoards[k] = boards[k];
            Helper.makeMoveBitBoard(newBoards, move);
            if ((WhiteTurn && (newBoards[0]& Moves.WKdangerZone(newBoards))==0) ||
                    ((!WhiteTurn && (newBoards[6]&Moves.BKdangerZone(newBoards))==0))){
                perft(newBoards, history+move, !WhiteTurn, depth+1, maxDepth);
                System.out.println(moveRepr(move, map) + ":" + perftCounter);
                totalMoves += perftCounter;
                perftCounter = 0;
            }
        }
        return totalMoves;
    }

    public static String moveRepr(String move, HashMap<Character, String> map){
        String moveRepr = "";
        if (move.charAt(3)=='E'){
            if (move.charAt(2)=='b') moveRepr += map.get(move.charAt(0)) + "5" + map.get(move.charAt(1))+"6";
            else moveRepr += map.get(move.charAt(0)) + "4" + map.get(move.charAt(1)) + "3";
        }
        else if (move.charAt(3)=='C'){
            if (move.charAt(2)=='b') moveRepr += map.get(move.charAt(0)) + "8" + map.get(move.charAt(1)) + "8";
            else moveRepr += map.get(move.charAt(0)) + "1" + map.get(move.charAt(1)) + "1";
        }
        else if (move.charAt(3)=='P'){
            if (Character.isLowerCase(move.charAt(2))) moveRepr +=map.get(move.charAt(0))+"2"+map.get(move.charAt(1))+"1";
            else moveRepr +=map.get(move.charAt(0))+"7"+map.get(move.charAt(1))+"8";
        }
        else{
            moveRepr += map.get(move.charAt(1)) +(8-(move.charAt(0)-'0')) + map.get(move.charAt(3))+(8-(move.charAt(2)-'0'));
        }
        return moveRepr;
    }

    public static void perft(long[] boards, String history, boolean WhiteTurn, int depth, int maxDepth){
        if (depth< maxDepth){
            String moves;
            if (WhiteTurn) moves = Moves.WhitePossibleMoves(boards, history);
            else moves = Moves.BlackPossibleMoves(boards, history);
            for (int i=0; i<moves.length(); i+=4){
                String move = moves.substring(i, i+4);
                long[] newBoards = new long[16];
                for(int k=0; k<16; k++) newBoards[k] = boards[k];
                Helper.makeMoveBitBoard(newBoards, move);
                if ((WhiteTurn && (newBoards[0]&Moves.WKdangerZone(newBoards))==0) ||
                        ((!WhiteTurn && (newBoards[6]&Moves.BKdangerZone(newBoards))==0))) {
                    if (depth + 1 == maxDepth) perftCounter += 1;
                    perft(newBoards, history+move, !WhiteTurn, depth+1, maxDepth);
                }
            }
        }
    }
}

