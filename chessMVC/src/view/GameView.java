package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import model.Board;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView {

    public static HashMap<String, Image> mapPiece = new HashMap<>();
    public static Pane boardscreen = new Pane();
    private static ArrayList<ImageView> pieces = new ArrayList<>();
    public static Tile[] tileList = new Tile[64];
    public static Button makeMoveButton = new Button("", createButtonImg("file:images/forward_btn.png"));
    public static Button retractMoveButton = new Button("", createButtonImg("file:images/backward_btn.png"));
    public static Button restartButton = new Button("", createButtonImg("file:images/restart_btn.png"));
    public static TextField textarea = new TextField();

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
        for (int i=0; i<strings.length; i++){
            Label label = new Label(strings[i]);
            label.setTextFill(Color.WHITE);
            label.setTranslateX(position[i][0]);
            label.setTranslateY(position[i][1]);
            label.setFont(new Font("Cambria", 23));
            boardscreen.getChildren().add(label);
        }
        Label label = new Label("Last move:");
        label.setTranslateX(150);
        label.setTranslateY(586);
        label.setFont(new Font("Cambria", 15));
        boardscreen.getChildren().add(label);
    }
    private static void addLines(){
        Line lineDown = new Line();
        lineDown.setStartX(0);
        lineDown.setStartY(574);
        lineDown.setEndX(572);
        lineDown.setEndY(574);
        boardscreen.getChildren().add(lineDown);
    }

    private static void addButtons(){
        retractMoveButton.setTranslateX(30);
        retractMoveButton.setTranslateY(582);
        makeMoveButton.setTranslateX(65);
        makeMoveButton.setTranslateY(582);
        restartButton.setTranslateX(100);
        restartButton.setTranslateY(582);
        retractMoveButton.setPadding(Insets.EMPTY);
        makeMoveButton.setPadding(Insets.EMPTY);
        restartButton.setPadding(Insets.EMPTY);
        boardscreen.getChildren().addAll(retractMoveButton, makeMoveButton, restartButton);
    }

    private static void addTextInput(){
        textarea.setEditable(false);
        textarea.setTranslateX(235);
        textarea.setTranslateY(582);
        textarea.setStyle("-fx-text-fill: white; -fx-background-color: black");
        textarea.setText("None");
        boardscreen.getChildren().add(textarea);
    }

    public static void initBoard(Board boardModel){

        boardscreen.setStyle("-fx-background-color: #199928");
        for (int i=0; i<64; i++){
            Tile tile = new Tile(i);
            boardscreen.getChildren().add(tile);
            tileList[i] = tile;
        }
        addLabels(new String[]{"A","A","B","B","C","C","D","D","E","E","F","F","G","G","H","H",
                               "8","8","7","7","6","6","5","5","4","4","3","3","2","2","1","1"},
                new int[][]{{56,3},{56,546},{120,3},{120,546},{184,3},{184,546},{248,3},{248,546},{312,3},{312,546},
                        {376,3},{376,546},{440,3},{440,546},{504,3},{504,546},{8,48},{552,48},{8,112}, {552,112},
                        {8,176},{552,176},{8,240},{552,240},{8,304},{552,304},{8,372},{552,372},{8,436},{552,436},
                        {8,500},{552,500}});
        addLines();
        addButtons();
        addTextInput();
        updatePieces(boardModel);
    }

    public static void updateTiles(ArrayList<Integer> possibleDestination, int pieceSquare, ArrayList<Integer> checked){
        for (Tile tile: tileList) tile.changeColor(1);
        for (int i: possibleDestination) tileList[i].changeColor(2);
        if (pieceSquare != -1) tileList[pieceSquare].changeColor(3);
        for (int i: checked) tileList[i].changeColor(4);
    }

    public static void updatePieces(Board boardModel){
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

    public static void updateMove(String moveString, String turn){
        if (turn.equals("W")) {
            textarea.setStyle("-fx-text-fill: black; -fx-background-color: white");
        }
        else{
            textarea.setStyle("-fx-text-fill: white; -fx-background-color: black");
        }
        textarea.setText(moveString);
    }

    public static ImageView createButtonImg(String imageLink){
        Image image=new Image(imageLink);
        return new ImageView(image);
    }
}
