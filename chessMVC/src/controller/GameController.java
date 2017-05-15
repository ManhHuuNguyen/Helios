package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Board;
import model.Helper;
import model.Highlight;
import view.GameView;

import java.util.ArrayList;

public class GameController {

    private static Character chosenPiece;
    private static int[] oldPos;
    private static int[] newPos;

    public static void takeTurn(Board board){
        if (board.WhiteTurn){
        }
        else {

        }
    }

    public static void isCheckMate(){}

    public static void isChecked(){}

    public static void addClickEvent(Pane boardscreen, Board boardmodel){
        boardscreen.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int[] mousePos = new int[]{(int)(mouseEvent.getX()-30)/64, (int)(mouseEvent.getY()-30)/64};
//                if (boardmodel.WhiteTurn && mousePos[0]<=7 && mousePos[1]<=7){
                if (mousePos[0]<=7 && mousePos[1]<=7){
                    Character square = boardmodel.boardArray[mousePos[1]][mousePos[0]].charAt(0);
                    if (Character.isUpperCase(square)){
                        chosenPiece = square;
                        oldPos = mousePos;
                        ArrayList<Integer> highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                        GameView.updateTiles(highlightedTiles, oldPos[1]*8+oldPos[0]);
                    }
                    else if (chosenPiece != null && mousePos[0]<=7 && mousePos[1]<=7){
                        newPos = mousePos;
                        boolean empty = square==' ';
                        ArrayList<Integer> highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                        if (highlightedTiles.contains(newPos[1]*8+newPos[0])){
                            String moveString = generateMoveString(chosenPiece, oldPos, newPos, empty);
                            boardmodel.movePiece(moveString);
                            GameView.updatePieces(boardmodel);
                        }
                        chosenPiece = null;
                        oldPos = null;
                        newPos = null;
                        highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                        GameView.updateTiles(highlightedTiles, -1);
                    }
                }
            }
        });
    }

    private static String generateMoveString(Character piece, int[] oldPos, int[] newPos, boolean empty){
        if (piece == 'K' && Math.abs(oldPos[0]-newPos[0])==2) return "4" + newPos[0] + " CK";
        else if (piece == 'P' && newPos[1]==0) return ""+ oldPos[0] + newPos[0] + "QPP";
        else if (piece == 'P' && empty && Math.abs(oldPos[0]-newPos[0])==1) return ""+ oldPos[0] + newPos[0] + " EP";
        else return "" + oldPos[1] + oldPos[0] + newPos[1] + newPos[0] + piece;
    }
}
