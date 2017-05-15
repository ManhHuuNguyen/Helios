package view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Board;

import java.util.HashMap;

public class GameView {

    public static HashMap<String, Image> mapPiece = new HashMap<>();
    public static Pane board = new Pane();

    public static void initMapPiece(){
        mapPiece.put("K", new Image("file:images/WhiteKing.png"));
        mapPiece.put("Q", new Image("file:images/WhiteQueen.png"));
        mapPiece.put("R", new Image("file:images/WhiteRook.png"));
        mapPiece.put("B", new Image("file:images/WhiteBishop.png"));
        mapPiece.put("N", new Image("file:images/WhiteKnight.png"));
        mapPiece.put("P", new Image("file:images/WhitePawn.png"));
        mapPiece.put("k", new Image("file:images/BlackKing.png"));
        mapPiece.put("q", new Image("file:images/BlackQueen.png"));
        mapPiece.put("r", new Image("file:images/BlackRook.png"));
        mapPiece.put("b", new Image("file:images/BlackBishop.png"));
        mapPiece.put("n", new Image("file:images/BlackKnight.png"));
        mapPiece.put("p", new Image("file:images/BlackPawn.png"));
    }

    public static void addLabel(String string, int x, int y){
        Label label = new Label(string);
        label.setTextFill(Color.WHITE);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setFont(new Font("Cambria", 23));
        board.getChildren().add(label);
    }
    public static void initBoard(){

        board.setStyle("-fx-background-color: #199928");
        for (int i=0; i<64; i++){
            Tile tile = new Tile(i);
            board.getChildren().add(tile);
        }
        addLabel("A", 56, 3);
        addLabel("A", 56, 546);
        addLabel("B", 120, 3);
        addLabel("B", 120, 546);
        addLabel("C", 184, 3);
        addLabel("C", 184, 546);
        addLabel("D", 248, 3);
        addLabel("D", 248, 546);
        addLabel("E", 312, 3);
        addLabel("E", 312, 546);
        addLabel("F", 376, 3);
        addLabel("F", 376, 546);
        addLabel("G", 440, 3);
        addLabel("G", 440, 546);
        addLabel("H", 504, 3);
        addLabel("H", 504, 546);
        addLabel("8", 8, 48);
        addLabel("8", 552, 48);
        addLabel("7", 8, 112);
        addLabel("7", 552, 112);
        addLabel("6", 8, 176);
        addLabel("6", 552, 176);
        addLabel("5", 8, 240);
        addLabel("5", 552, 240);
        addLabel("4", 8, 304);
        addLabel("4", 552, 304);
        addLabel("3", 8, 372);
        addLabel("3", 552, 372);
        addLabel("2", 8, 436);
        addLabel("2", 552, 436);
        addLabel("1", 8, 500);
        addLabel("1", 552, 500);
    }

    public static void update(Board boardModel){
        for (int row=0; row<boardModel.boardArray.length; row++){
            for (int column=0; column<boardModel.boardArray[row].length; column++){
                if (!boardModel.boardArray[row][column].equals(" ")){
                    ImageView pieceImg = new ImageView(mapPiece.get(boardModel.boardArray[row][column]));
                    pieceImg.setTranslateX(30+column*64);
                    pieceImg.setTranslateY(30+row*64);
                    board.getChildren().add(pieceImg);
                }
            }
        }
    }
}
