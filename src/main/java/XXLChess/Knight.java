package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Knight extends Piece {
    public Knight(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-knight.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-knight.png"));
        }
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.addMove(-2, -1, pieces);
        this.addMove(-1, -2,pieces);
        this.addMove(-2, 1, pieces);
        this.addMove(-1, 2, pieces);
        this.addMove(2, -1, pieces);
        this.addMove(1, -2, pieces);
        this.addMove(2, 1, pieces);
        this.addMove(1, 2, pieces); 
    }

    public Knight copyPiece(Pieces newBoard) {
        Knight copy = new Knight(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }

    
}