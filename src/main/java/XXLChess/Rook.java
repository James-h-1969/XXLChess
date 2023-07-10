package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Rook extends Piece {
    public boolean hasMoved;

    public Rook(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-rook.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-rook.png"));
        }
        this.hasMoved = false;
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.traverseTiles(1, 0, pieces);
        this.traverseTiles(-1, 0, pieces);
        this.traverseTiles(0, 1, pieces);
        this.traverseTiles(0, -1, pieces);
    }

    public Rook copyPiece(Pieces newBoard) {
        Rook copy = new Rook(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }

    public void setHasMoved(){
        this.hasMoved = true;
    }

    public boolean getHasMoved(){
        return this.hasMoved;
    }

}