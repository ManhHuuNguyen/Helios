package controller;

import javafx.beans.binding.When;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import model.*;
import view.GameView;
import view.PopUp;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private static Character chosenPiece;
    private static int[] oldPos;
    private static int[] newPos;
    private static boolean hasMoved = false;
    private static String currentMove;

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

    public static void addSecondaryClickEvent(Button retractButton, Button makeMoveButton, Board board) {
        makeMoveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hasMoved) {
                    GameView.updateMove(Helper.convertMove(currentMove), Color.GREEN);
                    hasMoved = false;
                    String result = checkResult(board);
                    if (result.equals("x")) {
                        currentMove = AI.AImakeMove(board);
                        GameView.updatePieces(board);
                        ArrayList<Integer> checkedKings = isChecked(board);
                        GameView.updateTiles(new ArrayList<>(), -1, checkedKings);
                        GameView.updateMove(Helper.convertMove(currentMove), Color.RED);
                        result = checkResult(board);
                        if (!result.equals("x")) PopUp.createResultPopUp(result);
                    }
                    else PopUp.createResultPopUp(result);
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
                }
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
}
