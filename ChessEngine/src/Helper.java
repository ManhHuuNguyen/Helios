import chessset.chesspieces.*;

import java.util.Arrays;

public class Helper {

    public static void drawArray(long[] boards) {

        long WK = boards[0];long WQ = boards[1];long WR = boards[2];long WB = boards[3];
        long WN = boards[4];long WP = boards[5];long BK = boards[6];long BQ = boards[7];
        long BR = boards[8];long BB = boards[9];long BN = boards[10];long BP = boards[11];
        String[][] chessBoard = new String[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                chessBoard[y][x] = " ";
            }
        }
        for (int i = 0; i < 64; i++) {
            if (((WK >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "K";
            if (((WQ >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "Q";
            if (((WR >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "R";
            if (((WB >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "B";
            if (((WN >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "N";
            if (((WP >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "P";
            if (((BK >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "k";
            if (((BQ >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "q";
            if (((BR >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "r";
            if (((BB >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "b";
            if (((BN >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "n";
            if (((BP >> i) & 1) == 1) chessBoard[i / 8][i % 8] = "p";
        }
        for (String[] row : chessBoard) System.out.println(Arrays.toString(row));
        System.out.println();
    }

    public static long[] makeMove(Piece chosenPiece, String moveString, Tile[] tileList, long[] boards){
        if (chosenPiece.isWhite()) {
            // use chosen piece to determine turn for now. switch later
            int oldPos; int newPos; Piece movePiece;
            if (moveString.charAt(3) == 'E'){
                oldPos = 3*8 + (moveString.charAt(0)-'0');
                newPos = 2*8 + (moveString.charAt(1)-'0');
                tileList[newPos+8].getPiece().destroy();
            }
            else if (moveString.charAt(3) == 'P') {
                oldPos = 8 + (moveString.charAt(0)-'0');
                newPos = moveString.charAt(1) -'0';
                if (tileList[newPos].getPiece()!=null){
                    tileList[newPos].getPiece().destroy();
                }
            }
            else if (moveString.charAt(3)=='C'){
                oldPos = 60;
                newPos = 7*8 + (moveString.charAt(1)-'0');
                if (oldPos > newPos){
                    Piece rook = tileList[56].getPiece();
                    tileList[56].setPiece(null);
                    rook.moveImage(tileList[59]);
                }
                else {
                    Piece rook = tileList[63].getPiece();
                    tileList[63].setPiece(null);
                    rook.moveImage(tileList[61]);
                }

            }
            else {
                oldPos = (moveString.charAt(0) - '0') * 8 + (moveString.charAt(1) - '0');
                newPos = (moveString.charAt(2) - '0') * 8 + (moveString.charAt(3) - '0');
                if (tileList[newPos].getPiece()!=null){
                    tileList[newPos].getPiece().destroy();
                }
            }
            movePiece = tileList[oldPos].getPiece();
            tileList[oldPos].setPiece(null);
            movePiece.moveImage(tileList[newPos]);
            if (moveString.charAt(3)=='P'){
                movePiece.destroy();
                promote(moveString.charAt(2), newPos, tileList);
            }
            return makeMoveBitBoard(boards, moveString);
        }

        else{// chosen piece is black
            int oldPos; int newPos; Piece movePiece;
            if (moveString.charAt(3) == 'E') {
                oldPos = 4*8 + (moveString.charAt(0)-'0');
                newPos = 5*8 + (moveString.charAt(1)-'0');
                tileList[newPos-8].getPiece().destroy();
            }
            else if (moveString.charAt(3) == 'P'){
                oldPos = 6*8 + (moveString.charAt(0)-'0');
                newPos = 7*8 + (moveString.charAt(1) -'0');
                if (tileList[newPos].getPiece()!=null){
                    tileList[newPos].getPiece().destroy();
                }
            }
            else if (moveString.charAt(3)=='C'){
                oldPos = 4;
                newPos = moveString.charAt(1)-'0';
                if (oldPos > newPos){
                    Piece rook = tileList[0].getPiece();
                    tileList[0].setPiece(null);
                    rook.moveImage(tileList[3]);
                }
                else {
                    Piece rook = tileList[7].getPiece();
                    tileList[7].setPiece(null);
                    rook.moveImage(tileList[5]);
                }
            }
            else {
                oldPos = (moveString.charAt(0) - '0') * 8 + (moveString.charAt(1) - '0');
                newPos = (moveString.charAt(2) - '0') * 8 + (moveString.charAt(3) - '0');
                if (tileList[newPos].getPiece()!=null){
                    tileList[newPos].getPiece().destroy();
                }
            }
            movePiece = tileList[oldPos].getPiece();
            tileList[oldPos].setPiece(null);
            movePiece.moveImage(tileList[newPos]);
            if (moveString.charAt(3)=='P'){
                movePiece.destroy();
                promote(moveString.charAt(2), newPos, tileList);
            }
            return makeMoveBitBoard(boards, moveString);
        }
    }

    public static long[] makeMoveBitBoard(long[] boards, String moveString) {
        // moveString 4 character: old row old column, new row new column
        int oldPos; int newPos;
        if (moveString.charAt(3)=='P'){
            if (Character.isUpperCase(moveString.charAt(2))){ // white pawn
                oldPos = 8 + (moveString.charAt(0)-'0');
                newPos = moveString.charAt(1)-'0';
            }
            else{ // black pawn
                oldPos = 48 + (moveString.charAt(0)-'0');
                newPos = 56 + moveString.charAt(1)-'0';
            }
        }
        else if (moveString.charAt(3)=='E'){
            if (moveString.charAt(2)==' '){// white
                oldPos = 24 + (moveString.charAt(0)-'0');
                newPos = 16 + (moveString.charAt(1)-'0');
                boards[11] = boards[11] ^ (1L<<(newPos+8));
            }
            else{// black
                oldPos = 32 + (moveString.charAt(0)-'0');
                newPos = 40 + (moveString.charAt(1)-'0');
                boards[5] = boards[5] ^ (1L<<(newPos-8));
            }
        }
        else if (moveString.charAt(3)=='C'){
            if (moveString.charAt(2)==' '){// white
                oldPos = 60;
                newPos = 56 + moveString.charAt(1)-'0';
                if (oldPos > newPos) boards[2] = (boards[2]^(1L<<56)) | (1L<<59);

                else boards[2] = (boards[2]^(1L<<63)) | (1L<<61);
            }
            else{// black
                oldPos = 4;
                newPos = moveString.charAt(1)-'0';
                if (oldPos > newPos) boards[8] = (boards[8]^1L) | (1L<<3);
                else boards[8] = (boards[8]^(1L<<7)) | (1L<<5);
            }
        }
        else {
            oldPos = (moveString.charAt(0)-'0')*8 + (moveString.charAt(1)-'0');
            newPos = (moveString.charAt(2)-'0')*8 + (moveString.charAt(3)-'0');
        }

        long oldBoard = 1L << oldPos;
        long newBoard = 1L << newPos;
        for (int i = 0; i < 12; i++) {
            if ((boards[i] & oldBoard) != 0) {
                boards[i] = (boards[i] ^ oldBoard) | newBoard;
            } else if ((boards[i] & newBoard) != 0) {
                boards[i] = boards[i] ^ newBoard;
            }
        }
        if (moveString.charAt(3)=='P'){
            if (Character.isUpperCase(moveString.charAt(2))) { // white pawn
                boards[5] = boards[5] ^ (1L << newPos); // delete white pawn
                switch (moveString.charAt(2)) { // add promotion piece
                    case 'Q':
                        boards[1] = boards[1] | (1L << newPos);
                        break;
                    case 'R':
                        boards[2] = boards[2] | (1L << newPos);
                        break;
                    case 'B':
                        boards[3] = boards[3] | (1L << newPos);
                        break;
                    case 'N':
                        boards[4] = boards[4] | (1L << newPos);
                        break;
                }
            }
            else {
                boards[11] = boards[11] ^ (1L << newPos); // delete black pawn
                switch (moveString.charAt(2)) { // add promotion piece
                    case 'q':
                        boards[7] = boards[7] | (1L << newPos);
                        break;
                    case 'r':
                        boards[8] = boards[8] | (1L << newPos);
                        break;
                    case 'b':
                        boards[9] = boards[9] | (1L << newPos);
                        break;
                    case 'n':
                        boards[10] = boards[10] | (1L << newPos);
                        break;
                }
            }
        }
        if (boards[12] != 0 | boards[13] != 0) {
            if ((boards[0] & (1L << 60)) == 0) { // if white king has moved
                boards[12] = 0;
                boards[13] = 0;
            }
            if ((boards[2] & (1L << 56)) == 0) boards[13] = 0; // castle white queen
            if ((boards[2] & (1L << 63)) == 0) boards[12] = 0; // castle white king
        }
        if (boards[14] != 0 | boards[15] != 0) {
            if ((boards[6] & (1L << 4)) == 0) { // if black king has moved
                boards[14] = 0;
                boards[15] = 0;
            }
            if ((boards[8] & 1L) == 0) boards[15] = 0; // castle black queen
            if ((boards[8] & (1L << 7)) == 0) boards[14] = 0; // castle black king
        }

        return boards;
    }

    public static String generateMoveString(Piece chosenPiece, int newPos, Tile[] tileList){
        // for human player entirely, as AI will generate moves in string form by default
        String returnStr = "";
        if (chosenPiece instanceof WhitePawn && tileList[newPos+8].getPiece() instanceof BlackPawn &&
                tileList[newPos].getPiece()==null && 16<= newPos && newPos<= 23){
            return returnStr + chosenPiece.column +""+ (newPos%8) + " E";
        }
        else if (chosenPiece instanceof BlackPawn && tileList[newPos-8].getPiece() instanceof WhitePawn &&
                tileList[newPos].getPiece()==null && 40<= newPos && newPos<= 47){
            return returnStr + chosenPiece.column +""+ (newPos%8) + "bE";
        }
        else if (chosenPiece instanceof WhitePawn && newPos <= 7){
            return returnStr + chosenPiece.column + "" + (newPos%8) + "QP"; // for the time being lets leave promotion default
        }
        else if (chosenPiece instanceof BlackPawn && newPos >= 56){
            return returnStr + chosenPiece.column + "" + (newPos%8) + "qP"; // for the time being lets leave promotion default
        }
        else if (chosenPiece instanceof WhiteKing && Math.abs(chosenPiece.getTile().getIndex()-newPos)==2){
            return returnStr + "4" + (newPos%8) + " C";
        }
        else if (chosenPiece instanceof BlackKing && Math.abs(chosenPiece.getTile().getIndex()-newPos)==2)
            return returnStr + "4" + (newPos%8) + "bC";
        return returnStr + chosenPiece.row +""+ chosenPiece.column +""+ (newPos/8) +""+ (newPos%8);
    }

    private static void promote(Character piece, int position, Tile[] tileList){
        Piece newPiece;
        switch (piece){
            case 'Q': newPiece = new WhiteQueen(GameGeneration.TILE_SIZE, 0, position); break;
            case 'R': newPiece = new WhiteRook(GameGeneration.TILE_SIZE, 0, position); break;
            case 'B': newPiece = new WhiteBishop(GameGeneration.TILE_SIZE, 0, position); break;
            case 'N': newPiece = new WhiteKnight(GameGeneration.TILE_SIZE, 0, position); break;
            case 'q': newPiece = new BlackQueen(GameGeneration.TILE_SIZE, 0, position); break;
            case 'r': newPiece = new BlackRook(GameGeneration.TILE_SIZE, 0, position); break;
            case 'b': newPiece = new BlackBishop(GameGeneration.TILE_SIZE, 0, position);break;
            default: newPiece = new BlackKnight(GameGeneration.TILE_SIZE, 0, position); break;// case 'n'
        }
        GameGeneration.pieceGroup.getChildren().add(newPiece);
        newPiece.setTile(tileList[position]);
        GameGeneration.addPieceClickEvent(newPiece);
    }
}