package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;



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

    public static ImageView createButtonImg(String imageLink){
        Image image=new Image(imageLink);
        return new ImageView(image);
    }

    public static void createResultPopUp(){
        Stage PopUpWindow = new Stage();
        PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        PopUpWindow.setTitle("RESULT");

    }
}
