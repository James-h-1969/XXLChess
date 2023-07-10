package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Camel extends Piece {
    public Camel(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-camel.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-camel.png"));
        }
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.addMove(-3, -1,  pieces);
        this.addMove(-1, -3, pieces);
        this.addMove(-3, 1, pieces);
        this.addMove(-1, 3, pieces);
        this.addMove(3, -1, pieces);
        this.addMove(1, -3, pieces);
        this.addMove(3, 1, pieces);
        this.addMove(1, 3, pieces); 
    }

    public Camel copyPiece(Pieces newBoard) {
        Camel copy = new Camel(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }

}