package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Queen extends Piece {
    public Queen(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-queen.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-queen.png"));
        }
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.traverseTiles(1, 0, pieces);
        this.traverseTiles(-1, 0, pieces);
        this.traverseTiles(0, 1, pieces);
        this.traverseTiles(0, -1, pieces);
        this.traverseTiles(1, 1, pieces);
        this.traverseTiles(1, -1, pieces);
        this.traverseTiles(-1, 1, pieces);
        this.traverseTiles(-1, -1, pieces);
    }

    public Queen copyPiece(Pieces newBoard) {
        Queen copy = new Queen(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }
}