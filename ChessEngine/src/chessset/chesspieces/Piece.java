package chessset.chesspieces;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public abstract class Piece extends StackPane {

    protected int tileSize;
    protected int row, column;
    protected Tile curTile;
    protected ImageView img;

    static long[] columns ={72340172838076673L, 144680345676153346L, 289360691352306692L, 578721382704613384L,
            1157442765409226768L, 2314885530818453536L, 4629771061636907072L, -9187201950435737472L};
    static long[] rows = {255L, 65280L, 16711680L, 4278190080L, 1095216660480L,
            280375465082880L, 71776119061217280L, -72057594037927936L};

    public Piece(int size, int border, int i, String filepath){
        Image image = new Image(filepath);
        this.img = new ImageView(image);
        this.row = i/8;
        this.column = i%8;
        this.tileSize = size;
        getChildren().add(img);
        relocate(tileSize*column+border, tileSize*row+border);
    }

    public void moveImage(Tile newTile){
        row = newTile.row;
        column = newTile.column;
        int coordX = column*tileSize;
        int coordY = row*tileSize;
        relocate(coordX, coordY);
        setTile(newTile);
    }

    public Tile getTile(){
        return curTile;
    }

    public void setTile(Tile newTile){
        this.curTile = newTile;
        newTile.setPiece(this);
    }

    public void destroy(){
        this.img.setImage(null);
        curTile.setPiece(null);
    }

    public abstract boolean isWhite();

    public abstract ArrayList<Integer> generateMoves(long[] boards, String history);
}
