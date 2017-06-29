package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public int id, row, column;
    private Color color1, color2, chosenColor=Color.BLUE, inCheckColor=Color.RED;
    private static final int border = 30;

    public Tile(int id){
        this.id = id;
        setWidth(64);
        setHeight(64);
        this.row = id / 8;
        this.column = id % 8;
        relocate(64*column+border, 64*row+border);
        color1 = (row+column)%2==0? Color.rgb(237, 237, 23): Color.rgb(26, 179, 26);
        color2 = (row+column)%2==0? Color.rgb(239, 182, 11): Color.rgb(23, 238, 23);
        setFill(color1);
        setStroke(Color.BLACK);
    }

    public void changeColor(int i){
        if (i==1) setFill(color1);
        else if (i==2) setFill(color2);
        else if (i==3) setFill(chosenColor);
        else setFill(inCheckColor);
    }
}