package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Amazon extends Piece {
    public Amazon(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-amazon.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-amazon.png"));
        }
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.addMove(-2, -1, pieces);
        this.addMove(-1, -2, pieces);
        this.addMove(-2, 1, pieces);
        this.addMove(-1, 2, pieces);
        this.addMove(2, -1, pieces);
        this.addMove(1, -2, pieces);
        this.addMove(2, 1, pieces);
        this.addMove(1, 2, pieces);     
        this.traverseTiles(1, 0, pieces);
        this.traverseTiles(-1, 0, pieces);
        this.traverseTiles(0, 1, pieces);
        this.traverseTiles(0, -1, pieces);
        this.traverseTiles(1, 1, pieces);
        this.traverseTiles(1, -1, pieces);
        this.traverseTiles(-1, 1, pieces);
        this.traverseTiles(-1, -1, pieces);
    }

    public Amazon copyPiece(Pieces newBoard) {
        Amazon copy = new Amazon(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }
}