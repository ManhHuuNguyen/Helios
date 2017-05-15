package view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Board;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView {

    public static HashMap<String, Image> mapPiece = new HashMap<>();
    public static Pane boardscreen = new Pane();
    private static ArrayList<ImageView> pieces = new ArrayList<>();

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

    private static void addLabels(String[] strings, int[][] position){
        for (int i=0; i<strings.length; i++) {
            Label label = new Label(strings[i]);
            label.setTextFill(Color.WHITE);
            label.setTranslateX(position[i][0]);
            label.setTranslateY(position[i][1]);
            label.setFont(new Font("Cambria", 23));
            boardscreen.getChildren().add(label);
        }
    }
    public static void initBoard(Board boardModel){

        boardscreen.setStyle("-fx-background-color: #199928");
        for (int i=0; i<64; i++){
            Tile tile = new Tile(i);
            boardscreen.getChildren().add(tile);
        }
        addLabels(new String[]{"A","A","B","B","C","C","D","D","E","E","F","F","G","G","H","H",
                               "8","8","7","7","6","6","5","5","4","4","3","3","2","2","1","1"},
                new int[][]{{56,3},{56,546},{120,3},{120,546},{184,3},{184,546},{248,3},{248,546},{312,3},{312,546},
                        {376,3},{376,546},{440,3},{440,546},{504,3},{504,546},{8,48},{552,48},{8,112}, {552,112},
                        {8,176},{552,176},{8,240},{552,240},{8,304},{552,304},{8,372},{552,372},{8,436},{552,436},
                        {8,500},{552,500}});
        update(boardModel);
    }

    public static void update(Board boardModel){
        for (ImageView piece: pieces) piece.setImage(null);
        for (int row=0; row<boardModel.boardArray.length; row++){
            for (int column=0; column<boardModel.boardArray[row].length; column++){
                if (!boardModel.boardArray[row][column].equals(" ")){
                    ImageView pieceImg = new ImageView(mapPiece.get(boardModel.boardArray[row][column]));
                    pieces.add(pieceImg);
                    pieceImg.setTranslateX(30+column*64);
                    pieceImg.setTranslateY(30+row*64);
                    boardscreen.getChildren().add(pieceImg);
                }
            }
        }
    }
}
