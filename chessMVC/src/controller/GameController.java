package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.*;
import view.GameView;
import view.PopUp;
import java.util.ArrayList;

public class GameController {

    private static Character chosenPiece;
    private static int[] oldPos;
    private static int[] newPos;
    private static boolean hasMoved = false;
    private static String currentMove = "";
    private static String oCurrentMove = "None"; // to cache last move

    public static ArrayList<Integer> isChecked(Board board) {
        ArrayList<Integer> list = new ArrayList<>();
        if ((board.WK & Moves.WKdangerZone(board.WQ, board.WR, board.WB, board.WN, board.WP,
                board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP)) != 0) {
            list.add(Long.numberOfTrailingZeros(board.WK));
        }
        if ((board.BK & Moves.BKdangerZone(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
                board.BQ, board.BR, board.BB, board.BN, board.BP)) != 0) {
            list.add(Long.numberOfTrailingZeros(board.BK));
        }
        return list;
    }

    public static void addSecondaryClickEvent(Button retractButton, Button makeMoveButton, Button restartButton, Board board) {
        makeMoveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hasMoved) {
                    hasMoved = false;
                    String result = checkResult(board);
                    if (result.equals("x")) {
                        currentMove = AI.AImakeMove(board);
                        oCurrentMove = currentMove;
                        GameView.updatePieces(board);
                        ArrayList<Integer> checkedKings = isChecked(board);
                        GameView.updateTiles(new ArrayList<>(), -1, checkedKings);
                        GameView.updateMove(Helper.convertMove(currentMove), "B");
                        result = checkResult(board);
                        if (!result.equals("x")) PopUp.createResultPopUp(result, currentMove, board);
                    }
                    else PopUp.createResultPopUp(result, currentMove, board);
                }
            }
        });
        retractButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hasMoved) {
                    hasMoved = false;
                    board.retractMove();
                    GameView.updatePieces(board);
                    ArrayList<Integer> checkedKings = isChecked(board);
                    GameView.updateTiles(new ArrayList<>(), -1, checkedKings);
                    GameView.updateMove(Helper.convertMove(oCurrentMove), "B");
                }
            }
        });
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                restartGame(board);
            }
        });
    }

    public static void addClickEvent(Pane boardscreen, Board boardmodel) {
        boardscreen.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int[] mousePos = new int[]{(int) (mouseEvent.getX() - 30) / 64, (int) (mouseEvent.getY() - 30) / 64};
                if (boardmodel.WhiteTurn && mousePos[0] <= 7 && mousePos[1] <= 7) {
                    Character square = boardmodel.boardArray[mousePos[1]][mousePos[0]].charAt(0);
                    if (Character.isUpperCase(square)) {
                        chosenPiece = square;
                        oldPos = mousePos;
                        ArrayList<Integer> highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                        ArrayList<Integer> checkedKings = isChecked(boardmodel);
                        GameView.updateTiles(highlightedTiles, oldPos[1] * 8 + oldPos[0], checkedKings);
                    }
                    else if (chosenPiece != null && mousePos[0] <= 7 && mousePos[1] <= 7) {
                        newPos = mousePos;
                        boolean empty = square == ' ';
                        ArrayList<Integer> highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                        if (highlightedTiles.contains(newPos[1] * 8 + newPos[0])) {
                            boardmodel.copyOldBoard();
                            String moveString = generateMoveString(chosenPiece, oldPos, newPos, empty);
                            currentMove = moveString;
                            boardmodel.movePiece(moveString);
                            GameView.updatePieces(boardmodel);
                            chosenPiece = null;
                            oldPos = null;
                            newPos = null;
                            highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                            ArrayList<Integer> checkedKings = isChecked(boardmodel);
                            GameView.updateTiles(highlightedTiles, -1, checkedKings);
                            hasMoved = true;
                            GameView.updateMove(Helper.convertMove(currentMove), "W");
                        }
                        else {
                            chosenPiece = null;
                            oldPos = null;
                            newPos = null;
                            highlightedTiles = Highlight.generateMoves(chosenPiece, oldPos, boardmodel);
                            ArrayList<Integer> checkedKings = isChecked(boardmodel);
                            GameView.updateTiles(highlightedTiles, -1, checkedKings);
                        }
                    }
                }
            }
        });
    }

    private static String generateMoveString(Character piece, int[] oldPos, int[] newPos, boolean empty) {
        if (piece == 'K' && Math.abs(oldPos[0] - newPos[0]) == 2) return "4" + newPos[0] + " CK";
        else if (piece == 'P' && newPos[1] == 0) {
            PopUp.createPromotionPopUp();
            return "" + oldPos[0] + newPos[0] + PopUp.promotionChoice + "PP";
        } else if (piece == 'P' && empty && Math.abs(oldPos[0] - newPos[0]) == 1)
            return "" + oldPos[0] + newPos[0] + " EP";
        else return "" + oldPos[1] + oldPos[0] + newPos[1] + newPos[0] + piece;
    }

    private static String checkResult(Board board) {
        if (board.WhiteTurn) {
            String moves = Moves.WhitePossibleMoves(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
                    board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP,
                    board.castleWK, board.castleWQ, board.history);
            if (PrincipalVariation.getLegalMove(moves, board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP, board.BK, board.BQ,
                    board.BR, board.BB, board.BN, board.BP, board.WhiteTurn) == -1) {
                if ((board.WK & Moves.WKdangerZone(board.WQ, board.WR, board.WB, board.WN, board.WP,
                        board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP)) != 0) {
                    return "B";
                }
                return "-"; // means game draw
            }
            return "x"; // means game continue
        }
        else {
            String moves = Moves.BlackPossibleMoves(board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP,
                    board.BK, board.BQ, board.BR, board.BB, board.BN, board.BP,
                    board.castleBK, board.castleBQ, board.history);
            if (PrincipalVariation.getLegalMove(moves, board.WK, board.WQ, board.WR, board.WB, board.WN, board.WP, board.BK, board.BQ,
                    board.BR, board.BB, board.BN, board.BP, board.WhiteTurn) == -1) {
                if ((board.BK & Moves.BKdangerZone(board.WK, board.WQ, board.WR, board.WB, board.WN,
                        board.WP, board.BQ, board.BR, board.BB, board.BN, board.BP)) != 0) {
                    return "W";
                }
                return "-"; // means game draw
            }
            return "x"; // means game continue
        }
    }

    public static void restartGame(Board board){
        board.reset("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        GameView.updatePieces(board);
        GameView.updateTiles(new ArrayList<>(), -1, new ArrayList<>());
        currentMove="";
        oCurrentMove="None";
        GameView.updateMove(oCurrentMove , "B");
    }

    public static Scene startNewGame(){
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Scene scene = new Scene(GameView.boardscreen, 572, 620);
        addClickEvent(GameView.boardscreen, board);
        addSecondaryClickEvent(GameView.retractMoveButton, GameView.makeMoveButton, GameView.restartButton, board);
        GameView.initMapPiece();
        GameView.initBoard(board);
        return scene;
    }
}
