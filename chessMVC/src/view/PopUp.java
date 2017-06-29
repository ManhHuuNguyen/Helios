package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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


    public static void createResultPopUp(String result){
        Stage PopUpWindow = new Stage();
        PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        PopUpWindow.setTitle("RESULT");
        TextFlow layout = new TextFlow();
        Text resultTxt;
        switch(result){
            case "W":
                resultTxt = new Text("White wins!");
                break;
            case "B":
                resultTxt = new Text("Black wins!");
                break;
            default:
                resultTxt = new Text("Draw...");
        }
        layout.getChildren().add(resultTxt);
        Scene scene = new Scene(layout, 326, 74);
        PopUpWindow.setScene(scene);
        PopUpWindow.showAndWait();
    }
}
