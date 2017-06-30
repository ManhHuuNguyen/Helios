package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static view.GameView.createButtonImg;


public class PopUp {
    public static String promotionChoice = "";

    public static void createPromotionPopUp(){
        // new stage
        Stage PopUpWindow = new Stage();
        PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        PopUpWindow.setTitle("Choose a Promotion Piece");
        // layout inside scene
        HBox layout = new HBox();
        ImageView knightImg = createButtonImg("file:images/WhiteKnight.png");
        Button KnightButton = new Button("", knightImg);
        KnightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promotionChoice = "N";
                PopUpWindow.close();
            }
        });
        ImageView bishopImg = createButtonImg("file:images/WhiteBishop.png");
        Button BishopButton = new Button("", bishopImg);
        BishopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promotionChoice = "B";
                PopUpWindow.close();
            }
        });
        ImageView rookImg = createButtonImg("file:images/WhiteRook.png");
        Button RookButton = new Button("", rookImg);
        RookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promotionChoice = "R";
                PopUpWindow.close();
            }
        });
        ImageView queenImg = createButtonImg("file:images/WhiteQueen.png");
        Button QueenButton = new Button("", queenImg);
        QueenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promotionChoice = "Q";
                PopUpWindow.close();
            }
        });
        layout.getChildren().addAll(KnightButton, BishopButton, RookButton, QueenButton);
        // scene
        Scene scene = new Scene(layout, 326, 74);
        PopUpWindow.setScene(scene);
        PopUpWindow.showAndWait();
    }


    public static void createResultPopUp(String result, String move){
        String piece = Character.toString(move.charAt(4));
        Stage PopUpWindow = new Stage();
        PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        PopUpWindow.setTitle("RESULT");
        Pane layout = new Pane();
        Label label = new Label();
        label.setFont(new Font("Cambria", 16));
//        Button restart = new Button("Restart");
        switch(result){
            case "W":
                label.setTranslateX(52);
                label.setTranslateY(15);
                label.setText("White wins!");
                ImageView node = Animated.animate(GameView.mapPiece.get(piece));
                node.setFitHeight(140);
                node.setPreserveRatio(true);
                node.setTranslateX(30);
                node.setTranslateY(40);
                layout.getChildren().addAll(label, node);
                break;
            case "B":
                label.setTranslateX(52);
                label.setTranslateY(15);
                label.setText("Black wins!");
                node = Animated.animate(GameView.mapPiece.get(piece));
                node.setFitHeight(140);
                node.setPreserveRatio(true);
                node.setTranslateX(30);
                node.setTranslateY(40);
                layout.getChildren().addAll(label, node);
                break;
            default:
                label.setTranslateX(80);
                label.setTranslateY(15);
                label.setText("Draw...");
                ImageView node1 = Animated.animate(new Image("file:images/WhiteKing.png"));
                ImageView node2 = Animated.animate(new Image("file:images/BlackKing.png"));
                node1.setTranslateX(15);
                node1.setTranslateY(40);
                node1.setFitHeight(80);
                node1.setPreserveRatio(true);
                node2.setTranslateX(105);
                node2.setTranslateY(40);
                node2.setFitHeight(80);
                node2.setPreserveRatio(true);
                layout.getChildren().addAll(label, node1, node2);
        }
        Scene scene = new Scene(layout, 200, 200);
        PopUpWindow.setScene(scene);
        PopUpWindow.showAndWait();
    }
}
