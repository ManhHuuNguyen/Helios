import chessset.chesspieces.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class GameGeneration extends Application {
    // UI
    protected static final int border = 0;
    protected static final int TILE_SIZE = 64;
    protected static final int WIDTH = 8;
    protected static final int HEIGHT = 8;
    protected static Group tileGroup = new Group();
    protected static Group pieceGroup = new Group();
    //                     WK  WQ  WR  WB  WN  WP  BK  BQ  BR  BB  BN  BP castleWK, castleWQ, castleBK, castleBQ
    static long[] boards = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
    protected static Tile[] tileList = new Tile[64];
    public static Piece chosenPiece = null;
    static String history = "";
    private static WhiteKing whiteking; // this is just placeholder
    private static BlackKing blackking; // this is just placeholder
    protected static boolean WhiteTurn = true;

    protected static HashMap<Character, String> map = new HashMap<>();
    // methods
    public static void boardInitialize(String fenString){
        mapInitiallize();
        String boardArray[][] = {
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "}
        };
        String[] items = fenString.split("[/\\s]");
        for (int i=0; i<8; i++){
            String row = items[i];
            int index = 0;
            for (int k=0; k<row.length(); k++){
                if (Character.isLetter(row.charAt(k))){
                    boardArray[i][index] = Character.toString(row.charAt(k));
                    index += 1;
                }
                if (Character.isDigit(row.charAt(k))){
                    index += row.charAt(k)-'0';
                }
            }
        }

        for (int i=0; i<64; i++){
            Tile tile = new Tile(TILE_SIZE, i, 0);
            addTileClickEvent(tile);
            tileGroup.getChildren().add(tile);
            tileList[i] = tile;
        }

        for (int i=0; i<64; i++){
            switch(boardArray[i/8][i%8]){
                case "K": boards[0] += 1L<<i;
                    WhiteKing wk = new WhiteKing(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wk);
                    wk.setTile(tileList[i]);
                    addPieceClickEvent(wk);
                    whiteking = wk;
                    break;
                case "Q": boards[1] += 1L<<i;
                    WhiteQueen wq = new WhiteQueen(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wq);
                    wq.setTile(tileList[i]);
                    addPieceClickEvent(wq);
                    break;
                case "R": boards[2] += 1L<<i;
                    WhiteRook wr = new WhiteRook(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wr);
                    wr.setTile(tileList[i]);
                    addPieceClickEvent(wr);
                    break;
                case "B": boards[3] += 1L<<i;
                    WhiteBishop wb = new WhiteBishop(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wb);
                    wb.setTile(tileList[i]);
                    addPieceClickEvent(wb);
                    break;
                case "N": boards[4] += 1L<<i;
                    WhiteKnight wn = new WhiteKnight(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wn);
                    wn.setTile(tileList[i]);
                    addPieceClickEvent(wn);
                    break;
                case "P": boards[5] += 1L<<i;
                    WhitePawn wp = new WhitePawn(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(wp);
                    wp.setTile(tileList[i]);
                    addPieceClickEvent(wp);
                    break;
                case "k": boards[6] += 1L<<i;
                    BlackKing bk = new BlackKing(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(bk);
                    bk.setTile(tileList[i]);
                    addPieceClickEvent(bk);
                    blackking = bk;
                    break;
                case "q": boards[7] += 1L<<i;
                    BlackQueen bq = new BlackQueen(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(bq);
                    bq.setTile(tileList[i]);
                    addPieceClickEvent(bq);
                    break;
                case "r": boards[8] += 1L<<i;
                    BlackRook br = new BlackRook(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(br);
                    br.setTile(tileList[i]);
                    addPieceClickEvent(br);
                    break;
                case "b": boards[9] += 1L<<i;
                    BlackBishop bb = new BlackBishop(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(bb);
                    bb.setTile(tileList[i]);
                    addPieceClickEvent(bb);
                    break;
                case "n": boards[10] += 1L<<i;
                    BlackKnight bn = new BlackKnight(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(bn);
                    bn.setTile(tileList[i]);
                    addPieceClickEvent(bn);
                    break;
                case "p": boards[11] += 1L<<i;
                    BlackPawn bp = new BlackPawn(TILE_SIZE, 0, i);
                    pieceGroup.getChildren().add(bp);
                    bp.setTile(tileList[i]);
                    addPieceClickEvent(bp);
                    break;
            }
        }
        // turn
        if (items[8].equals("w")) WhiteTurn =true;
        else WhiteTurn = false;
        //castling status
        if (items[9].indexOf('K')!=-1) boards[12] =1L;
        if (items[9].indexOf('Q')!=-1) boards[13] =1L;
        if (items[9].indexOf('k')!=-1) boards[14] =1L;
        if (items[9].indexOf('q')!=-1) boards[15] =1L;
        // enpassant
        if (!items[10].equals("-")){
            String column = map.get(items[10].charAt(0));
            String row = " " + (8-(items[10].charAt(1)-'0'));
            if (row.equals("2")) history += "1" + column + "3" + column;
            else if (row.equals("5")) history += "6" + column + "4" + column;
        }
        Helper.drawArray(boards);
    }

    // selection events
    public static void addPieceClickEvent(Piece piece){
        piece.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){
                if (chosenPiece!=null && (piece.isWhite() != chosenPiece.isWhite()) && piece.getTile().getStatus()){
                    String moveString = Helper.generateMoveString(chosenPiece, piece.getTile().getIndex(), tileList);
                    boards = Helper.makeMove(chosenPiece, moveString, tileList, boards);
                    clearSelection(tileList);
                    chosenPiece = null;
                    Helper.drawArray(boards);
                    history += moveString;
                    blackking.isThreatened(boards);
                    whiteking.isThreatened(boards);
                }
                else {
                    clearSelection(tileList);
                    piece.getTile().changeColor();
                    chosenPiece = piece;
                    ArrayList<Integer> possibleMoves = chosenPiece.generateMoves(boards, history);
                    for (Integer i : possibleMoves) {
                        tileList[i].changeStatus(true);
                        tileList[i].changeColor();
                    }
                    blackking.isThreatened(boards);
                    whiteking.isThreatened(boards);
                }
            }
        });
    }

    private static void addTileClickEvent(Tile tile){
        tile.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (chosenPiece!=null && tile.getStatus()){
                    String moveString = Helper.generateMoveString(chosenPiece, tile.getIndex(), tileList);
                    boards = Helper.makeMove(chosenPiece, moveString, tileList, boards);
                    clearSelection(tileList);
                    chosenPiece = null;
                    Helper.drawArray(boards);
                    history += moveString;
                    blackking.isThreatened(boards);
                    whiteking.isThreatened(boards);
                }
            }
        });
    }

    private static void clearSelection(Tile[] tileList){
        for (Tile tile: tileList){
            tile.clear();
        }
    }

    private static void mapInitiallize(){
        map.put('0', "a");
        map.put('1', "b");
        map.put('2', "c");
        map.put('3', "d");
        map.put('4', "e");
        map.put('5', "f");
        map.put('6', "g");
        map.put('7', "h");
    }
}