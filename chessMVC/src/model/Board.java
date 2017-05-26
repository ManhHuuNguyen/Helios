package model;

import java.util.Arrays;

public class Board {

    public long WK = 0L, WQ = 0L, WR = 0L, WB = 0L, WN = 0L, WP = 0L;
    public long BK = 0L, BQ = 0L, BR = 0L, BB = 0L, BN = 0L, BP = 0L;
    public boolean castleWK = true, castleBK = true, castleWQ = true, castleBQ = true;
    public boolean WhiteTurn = true;
    public String boardArray[][] = {
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "}
    };
    public String history = "";

    public Board(String fenString){
        String[] items = fenString.split("[/\\s]");
        // PIECES
        for (int i=0; i<8; i++){
            String row = items[i];
            int index = 0;
            for (int k=0; k<row.length(); k++){
                if (Character.isLetter(row.charAt(k))){
                    switch(row.charAt(k)){
                        case 'K':
                            WK += 1L<<(i*8+index);
                            break;
                        case 'Q':
                            WQ += 1L<<(i*8+index);
                            break;
                        case 'R':
                            WR += 1L<<(i*8+index);
                            break;
                        case 'B':
                            WB += 1L<<(i*8+index);
                            break;
                        case 'N':
                            WN += 1L<<(i*8+index);
                            break;
                        case 'P':
                            WP += 1L<<(i*8+index);
                            break;
                        case 'k':
                            BK += 1L<<(i*8+index);
                            break;
                        case 'q':
                            BQ += 1L<<(i*8+index);
                            break;
                        case 'r':
                            BR += 1L<<(i*8+index);
                            break;
                        case 'b':
                            BB += 1L<<(i*8+index);
                            break;
                        case 'n':
                            BN += 1L<<(i*8+index);
                            break;
                        case 'p':
                            BP += 1L<<(i*8+index);
                            break;
                    }
                    index += 1;
                }
                if (Character.isDigit(row.charAt(k))){
                    index += row.charAt(k)-'0';
                }
            }
        }
        // TURN
        WhiteTurn = items[8].equals("w");
        // CASTLE
        castleWK = items[9].indexOf('K')!=-1;
        castleWQ = items[9].indexOf('Q')!=-1;
        castleBK = items[9].indexOf('k')!=-1;
        castleBQ = items[9].indexOf('q')!=-1;
        // BOARD ARRAY INIT
        for (int i = 0; i < 64; i++) {
            if (((WK >> i) & 1) == 1) boardArray[i / 8][i % 8] = "K";
            if (((WQ >> i) & 1) == 1) boardArray[i / 8][i % 8] = "Q";
            if (((WR >> i) & 1) == 1) boardArray[i / 8][i % 8] = "R";
            if (((WB >> i) & 1) == 1) boardArray[i / 8][i % 8] = "B";
            if (((WN >> i) & 1) == 1) boardArray[i / 8][i % 8] = "N";
            if (((WP >> i) & 1) == 1) boardArray[i / 8][i % 8] = "P";
            if (((BK >> i) & 1) == 1) boardArray[i / 8][i % 8] = "k";
            if (((BQ >> i) & 1) == 1) boardArray[i / 8][i % 8] = "q";
            if (((BR >> i) & 1) == 1) boardArray[i / 8][i % 8] = "r";
            if (((BB >> i) & 1) == 1) boardArray[i / 8][i % 8] = "b";
            if (((BN >> i) & 1) == 1) boardArray[i / 8][i % 8] = "n";
            if (((BP >> i) & 1) == 1) boardArray[i / 8][i % 8] = "p";
        }
    }

    public void displayBoard(){
        for (String[] row : boardArray) System.out.println(Arrays.toString(row));
        System.out.println();
    }

    public static boolean makeCastleMove(String moveString, boolean castleRight, long RookBitBoard, String type){
        if (castleRight){
            if (moveString.charAt(4)=='K' && type.charAt(1)=='S'){
                return false;
            }
            else if (moveString.charAt(4)=='k' && type.charAt(1)=='s'){
                return false;
            }
            else if (moveString.charAt(4)=='R'){
                if (type.equals("KS") && (RookBitBoard & (1L<<63))==0) return false;
                else if (type.equals("QS") && (RookBitBoard & (1L<<56))==0) return false;
            }
            else if (moveString.charAt(4)=='r'){
                if (type.equals("ks") && (RookBitBoard & (1L<<7))==0) return false;
                else if (type.equals("qs") && (RookBitBoard & 1L)==0) return false;
            }
        }
        return castleRight;
    }

    public static long makeTestMove(String moveString, long oldBitBoard, char type){
        // could it be slow bc I test too much? Because I could be relied to match exactly in perft...
        if (moveString.charAt(3)=='P'){
            if (type == moveString.charAt(4)){
                return (type=='P')?oldBitBoard^(1L<<(8+(moveString.charAt(0)-'0'))):oldBitBoard^(1L<<(48+(moveString.charAt(0)-'0')));
            }
            else if (type == moveString.charAt(2)){
                return (moveString.charAt(4)=='P')?oldBitBoard|(1L<<(moveString.charAt(1)-'0')):oldBitBoard|(1L<<(56+(moveString.charAt(1)-'0')));
            }
            // wrong here somehow... it deletes 2 pieces of both line 0 and 7 of similar row to the promotion
            else if ((oldBitBoard & (1L<<(moveString.charAt(1)-'0')))!=0 && !Character.isUpperCase(type) && moveString.charAt(4)=='P'){//! before character
                return oldBitBoard ^ (1L<<(moveString.charAt(1)-'0'));
            }
            else if ((oldBitBoard & (1L<<(56+(moveString.charAt(1)-'0'))))!=0 && Character.isUpperCase(type) && moveString.charAt(4)=='p'){//no ! before character
                return oldBitBoard ^ (1L<<(56+(moveString.charAt(1)-'0')));
            }
            else return oldBitBoard;
        }
        else if (moveString.charAt(3)=='E'){
            if (type=='P' && moveString.charAt(4)=='P'){
                return (oldBitBoard^(1L<<(24+(moveString.charAt(0)-'0'))))|(1L<<(16+(moveString.charAt(1)-'0')));
            }
            else if (type=='p' && moveString.charAt(4)=='P'){
                return oldBitBoard ^ (1L<<(24+(moveString.charAt(1)-'0')));
            }
            else if (type=='p' && moveString.charAt(4)=='p'){
                return (oldBitBoard^(1L<<(32+(moveString.charAt(0)-'0'))))|(1L<<(40+(moveString.charAt(1)-'0')));
            }
            else if (type=='P' && moveString.charAt(4)=='p'){
                return oldBitBoard ^ (1L<<(32+(moveString.charAt(1)-'0')));
            }
            else return oldBitBoard;
        }
        else if (moveString.charAt(3)=='C'){
            // castling is still wrong
            if (type=='K' && moveString.charAt(4)=='K'){
                return (oldBitBoard^(1L<<60)) | (1L<<(56+(moveString.charAt(1)-'0')));
            }
            else if (type=='R' && moveString.charAt(4)=='K'){
                if (moveString.charAt(1)=='2') return (oldBitBoard^(1L<<56)) | (1L<<59);
                else return (oldBitBoard^(1L<<63)) | (1L<<61);
            }
            else if (type=='k' && moveString.charAt(4)=='k'){
                return (oldBitBoard^(1L<<4)) | (1L<<(moveString.charAt(1)-'0'));
            }
            else if (type=='r' && moveString.charAt(4)=='k'){
                if (moveString.charAt(1)=='2') return (oldBitBoard^1L) | (1L<<3);
                else return (oldBitBoard^(1L<<7)) | (1L<<5);
            }
            else return oldBitBoard;
        }
        else{
            if (type==moveString.charAt(4)){
                return (oldBitBoard ^ (1L<<((moveString.charAt(0)-'0')*8+(moveString.charAt(1)-'0')))) |
                        (1L<<((moveString.charAt(2)-'0')*8+(moveString.charAt(3)-'0')));
            }
            else if ((oldBitBoard & (1L<<((moveString.charAt(2)-'0')*8+(moveString.charAt(3)-'0'))))!=0){
                return oldBitBoard ^ (1L<<((moveString.charAt(2)-'0')*8+(moveString.charAt(3)-'0')));
            }
            else return oldBitBoard;
        }
    }

    public void movePiece(String moveString){
        // moveString 5 characters: old row old column, new row new column, piece name
        int oldRow, oldColumn, newRow, newColumn;
        history += moveString;
        WhiteTurn = !WhiteTurn;
        if (moveString.charAt(3) == 'P'){ // promotion
            if (moveString.charAt(4)=='P'){ // white pawn
                oldRow = 1;
                oldColumn = moveString.charAt(0)-'0';
                newRow = 0;
                newColumn = moveString.charAt(1)-'0';
            }
            else{ // black pawn
                oldRow = 6;
                oldColumn = moveString.charAt(0)-'0';
                newRow = 7;
                newColumn = moveString.charAt(1)-'0';
            }
        }
        else if (moveString.charAt(3)=='E'){ // enpassant
            if (moveString.charAt(4)=='P'){// white pawn
                oldRow = 3;
                oldColumn = moveString.charAt(0)-'0';
                newRow = 2;
                newColumn = moveString.charAt(1)-'0';
                // destroy the enpassed pawn
                BP = BP ^ (1L<<(newRow*8+newColumn+8));
                boardArray[oldRow][newColumn] = " ";
            }
            else{// black
                oldRow = 4;
                oldColumn = moveString.charAt(0)-'0';
                newRow = 5;
                newColumn = moveString.charAt(1)-'0';
                // destroy the enpassed pawn
                WP = WP ^ (1L<<(newRow*8+newColumn-8));
                boardArray[oldRow][newColumn] = " ";
            }
        }
        else if (moveString.charAt(3)=='C'){ // castle
            if (moveString.charAt(4)=='K'){// white
                oldRow = 7;
                oldColumn = 4;
                newRow = 7;
                newColumn = moveString.charAt(1)-'0';
                if (oldColumn > newColumn){
                    WR = (WR^(1L<<56)) | (1L<<59);
                    boardArray[7][0] = " ";
                    boardArray[7][3] = "R";
                }
                else {
                    WR = (WR^(1L<<63)) | (1L<<61);
                    boardArray[7][7] = " ";
                    boardArray[7][5] = "R";
                }
            }
            else{// black
                oldRow = 0;
                oldColumn = 4;
                newRow = 0;
                newColumn = moveString.charAt(1)-'0';
                if (oldColumn > newColumn){
                    BR = (BR^1L) | (1L<<3);
                    boardArray[0][0] = " ";
                    boardArray[0][3] = "R";
                }
                else{
                    BR = (BR^(1L<<7)) | (1L<<5);
                    boardArray[0][7] = " ";
                    boardArray[0][5] = "R";
                }
            }
        }
        else {
            oldRow = moveString.charAt(0) - '0';
            oldColumn = moveString.charAt(1) - '0';
            newRow = moveString.charAt(2) - '0';
            newColumn = moveString.charAt(3) - '0';
        }
        long oldBoard = 1L << (oldRow*8+oldColumn);
        long newBoard = 1L << (newRow*8+newColumn);

        switch(boardArray[oldRow][oldColumn]){
            case "K":
                WK = (WK ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "K";
                castleWK = false;
                castleWQ = false;
                break;
            case "Q":
                WQ = (WQ ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "Q";
                break;
            case "R":
                WR = (WR ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "R";
                if (castleWK && (WR & (1L<<63))==0) castleWK = false;
                if (castleBK && (WR & (1L<<56))==0) castleWQ = false;
                break;
            case "B":
                WB = (WB ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "B";
                break;
            case "N":
                WN = (WN ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "N";
                break;
            case "P":
                WP = (WP ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "P";
                break;
            case "k":
                BK = (BK ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "k";
                castleBK = false;
                castleBQ = false;
                break;
            case "q":
                BQ = (BQ ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "q";
                break;
            case "r":
                BR = (BR ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "r";
                if (castleBK && (BR & (1L<<7))==0) castleBK = false;
                if (castleBQ && (BR & 1L)==0) castleBQ = false;
                break;
            case "b":
                BB = (BB ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "b";
                break;
            case "n":
                BN = (BN ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "n";
                break;
            case "p":
                BP = (BP ^ oldBoard) | newBoard;
                removePiece(newRow, newColumn, newBoard);
                boardArray[oldRow][oldColumn] = " ";
                boardArray[newRow][newColumn] = "p";
                break;
        }
        if (moveString.charAt(3)=='P'){
            if (moveString.charAt(4)=='P') { // white pawn
                WP ^= (1L << (newRow*8+newColumn)); // delete white pawn
                switch (moveString.charAt(2)) { // add promotion piece
                    case 'Q':
                        WQ |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "Q";
                        break;
                    case 'R':
                        WR |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "R";
                        break;
                    case 'B':
                        WB |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "B";
                        break;
                    case 'N':
                        WN |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "N";
                        break;
                }
            }
            else {
                BP ^= (1L << (newRow*8+newColumn)); // delete black pawn
                switch (moveString.charAt(2)) { // add promotion piece
                    case 'q':
                        BQ |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "q";
                        break;
                    case 'r':
                        BR |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "r";
                        break;
                    case 'b':
                        BB |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "b";
                        break;
                    case 'n':
                        BN |= (1L << (newRow*8+newColumn));
                        boardArray[newRow][newColumn] = "n";
                        break;
                }
            }
        }
    }

    public void removePiece(int newRow, int newColumn, long newBoard){
        // to destroy caught piece in bit board
        switch(boardArray[newRow][newColumn]){
            // no kings because kings can't be caught.
            case " ":
                break;
            case "Q":
                WQ ^= newBoard;
                break;
            case "R":
                WR ^= newBoard;
                break;
            case "B":
                WB ^= newBoard;
                break;
            case "N":
                WN ^= newBoard;
                break;
            case "P":
                WP ^= newBoard;
                break;
            case "q":
                BQ ^= newBoard;
                break;
            case "r":
                BR ^= newBoard;
                break;
            case "b":
                BB ^= newBoard;
                break;
            case "n":
                BN ^= newBoard;
                break;
            case "p":
                BP ^= newBoard;
                break;
        }
    }
}

