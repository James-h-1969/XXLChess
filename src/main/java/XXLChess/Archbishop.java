package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Archbishop extends Piece {
    public Archbishop(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-archbishop.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-archbishop.png"));
        }
    }
    public void tick(){}
    
    public void getMoves(Pieces pieces){
        this.addMove(-1, -2,  pieces);
        this.addMove(-2, 1,  pieces);
        this.addMove(-2, -1, pieces);
        this.addMove(-1, 2, pieces);
        this.addMove(2, -1, pieces);
        this.addMove(1, -2, pieces);
        this.addMove(2, 1, pieces);
        this.addMove(1, 2, pieces);     
        this.traverseTiles(1, 1, pieces);
        this.traverseTiles(1, -1, pieces);
        this.traverseTiles(-1, 1, pieces);
        this.traverseTiles(-1, -1, pieces);
    }

    public Archbishop copyPiece(Pieces newBoard) {
        Archbishop copy = new Archbishop(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }
}