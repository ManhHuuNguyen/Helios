package chessset.chesspieces;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private int id;
    private Piece piece;
    public int row; public int column;
    private boolean moveable = false;
    private Color color1;
    private Color color2;
    private Color checkColor;

    public Tile(int size, int id, int border){
        this.id = id;
        this.piece = null;
        setWidth(size);
        setHeight(size);
        this.row = id / 8;
        this.column = id % 8;
        relocate(size*column+border, size*row+border);
        color1 = (row+column)%2==0? Color.rgb(237, 237, 23): Color.rgb(26, 179, 26);
        color2 = (row+column)%2==0? Color.rgb(239, 182, 11): Color.rgb(23, 238, 23);
        checkColor = Color.RED;
        setFill(color1);
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece newPiece){
        this.piece = newPiece;
    }

    public void clear(){
        setFill(color1);
        moveable = false;
    }

    public void changeColor(){
        setFill(color2);
    }

    public boolean getStatus(){
        return moveable;
    }

    public void changeStatus(boolean i){
        moveable = i;
    }

    public int getIndex(){return this.id;}

    public void threatenColor(){
        setFill(checkColor);
    }
}
