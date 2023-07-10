package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-pawn.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-pawn.png"));
        }
 
        this.firstMove = yCoord/ 48 == 1 || yCoord/48 == 12;


    }
    public void tick(){}

    public void setFirstMove(){
        this.firstMove = false;
    }


    public void getMoves(Pieces pieces){
        if (this.colour.equals("white")){
            if (this.firstMove){
            this.addMove(-2, 0, pieces);
            } 
            this.addMove(-1, 0, pieces);
            this.addMove(-1, 1, pieces);
            this.addMove(-1, -1, pieces);
        } else {
            if (this.firstMove){
                this.addMove(2, 0, pieces);
            } 
            this.addMove(1, 0, pieces);
            this.addMove(1, 1, pieces);
            this.addMove(1, -1, pieces);
        }
        
    }

    public Pawn copyPiece(Pieces newBoard) {
        Pawn copy = new Pawn(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        copy.firstMove = this.firstMove;
        return copy;
    }

}