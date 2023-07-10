package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Bishop extends Piece {
    public Bishop(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-bishop.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-bishop.png"));
        }
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.traverseTiles(1, 1, pieces);
        this.traverseTiles(1, -1, pieces);
        this.traverseTiles(-1, 1, pieces);
        this.traverseTiles(-1, -1, pieces);
    }

    public Bishop copyPiece(Pieces newBoard) {
        Bishop copy = new Bishop(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }
}