package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Board;
import view.GameView;


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
                if (boardmodel.WhiteTurn && mousePos[0]<=7 && mousePos[1]<=7){
                    Character square = boardmodel.boardArray[mousePos[1]][mousePos[0]].charAt(0);
                    if (Character.isUpperCase(square)){
                        System.out.println(square);
                        chosenPiece = square;
                        oldPos = mousePos;
                    }
                    else if (chosenPiece != null){
                        newPos = mousePos;
                        boolean empty = square==' ';
                        String moveString = generateMoveString(chosenPiece, oldPos, newPos, empty);
                        System.out.println(moveString);
                        boardmodel.movePiece(moveString);
                        chosenPiece = null;
                        oldPos = null;
                        newPos = null;
                        GameView.update(boardmodel);
                    }
                }
            }
        });
    }

    private static String generateMoveString(Character piece, int[] oldPos, int[] newPos, boolean empty){
        if (piece == 'K' && Math.abs(oldPos[0]-newPos[0])==2) return "4" + newPos[0] + " CK";
        else if (piece == 'P' && newPos[1]==0) return oldPos[0] + + newPos[0] + "QPP";
        else if (piece == 'P' && empty && Math.abs(oldPos[0]-newPos[0])==1) return oldPos[0] + oldPos[1] + " EP";
        else return "" + oldPos[1] + oldPos[0] + newPos[1] + newPos[0] + piece;
    }
}
