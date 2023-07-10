package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Guard extends Piece {
    public Guard(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-knight-king.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-knight-king.png"));
        }
    }
    
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.addMove(-2, -1, pieces );
        this.addMove(-1, -2,  pieces);
        this.addMove(-2, 1, pieces);
        this.addMove(-1, 2, pieces);
        this.addMove(2, -1, pieces);
        this.addMove(1, -2, pieces);
        this.addMove(2, 1, pieces);
        this.addMove(1, 2, pieces);     
        this.addMove(-1, -1, pieces);
        this.addMove(-1, 0, pieces);
        this.addMove(-1, 1, pieces);
        this.addMove(0, -1, pieces);
        this.addMove(0, 1, pieces);
        this.addMove(1, -1, pieces);
        this.addMove(1, 0, pieces);
        this.addMove(1, 1, pieces);
    }

    public Guard copyPiece(Pieces newBoard) {
        Guard copy = new Guard(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }
}