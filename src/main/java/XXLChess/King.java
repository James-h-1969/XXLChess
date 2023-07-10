package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class King extends Piece {
    public boolean hasMoved;
    public boolean isLeftpossible;
    public boolean isRightpossible;

    public King(int xCoord, int yCoord, String colourInput, PApplet parent, Board board, String playerColour){ 
        super(xCoord, yCoord, colourInput, parent, board, playerColour);
        if (colourInput.equals("white")){
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/w-king.png"));
        } else {
            this.setSprite(parent.loadImage("src/main/resources/XXLChess/b-king.png"));
        }
        this.hasMoved = false;
        this.isLeftpossible = false;
        this.isRightpossible = false;
    }
    public void tick(){}

    public void getMoves(Pieces pieces){
        this.addMove(-1, -1, pieces);
        this.addMove(-1, 0, pieces);
        this.addMove(-1, 1, pieces);
        this.addMove(0, -1, pieces);
        this.addMove(0, 1, pieces);
        this.addMove(1, -1, pieces);
        this.addMove(1, 0, pieces);
        this.addMove(1, 1, pieces);
        this.addCastlingMove(pieces);
    }

    public King copyPiece(Pieces newBoard) {
        King copy = new King(this.getX(), this.getY(), this.getColour(), this.parent, this.board, this.playerColour);
        return copy;
    }    

    public void setHasMoved(){
        this.hasMoved = true;
        this.isLeftpossible = false;
        this.isRightpossible = false;
    }
    public boolean getHasMoved(){
        return this.hasMoved;
    }

    public void addCastlingMove(Pieces pieces){
        if (!this.getHasMoved() && this.getColumn() == 7){
            Piece checkPiece = pieces.getPiece(this.getRow(), 0); //left rook
            if (checkPiece instanceof Rook){
                Rook leftRook = (Rook) pieces.getPiece(this.getRow(), 0);
                if (!leftRook.getHasMoved()){
                    //check whether space in between
                    boolean castlingLeftPossible = true;
                    int colChange = -1;
                    while (this.getColumn() + colChange > 1){
                        if (pieces.getPiece(this.getRow(), this.getColumn() + colChange) != null){
                            castlingLeftPossible = false;
                        }
                        colChange--;
                    }
                    if (castlingLeftPossible){
                        this.isLeftpossible = true;
                    } else {
                        this.isLeftpossible = false;
                    }
                }
            }


            checkPiece = pieces.getPiece(this.getRow(), 13); //right rook
            if (checkPiece instanceof Rook){
                Rook rightRook = (Rook) pieces.getPiece(this.getRow(), 13);
                if (!rightRook.getHasMoved()){
                    //check whether space in between
                    boolean castlingRightPossible = true;
                    int colChange = 1;
                    while (this.getColumn() + colChange < 13){
                        if (pieces.getPiece(this.getRow(), this.getColumn() + colChange) != null){
                            castlingRightPossible = false;
                        }
                        colChange++;
                    }
                    if (castlingRightPossible){
                        this.isRightpossible = true;
                    } else {
                        this.isRightpossible = false;
                    }
                }
            }
            
        }
    }
    
    public void isCastlingValid(Pieces pieces){
        if (this.isLeftpossible){
            pieces.movePiece(this.getRow(), this.getColumn(), this.getRow(), this.getColumn() - 2, false);
            
            this.isLeftpossible = !pieces.inCheck(this.getColour(), false);

            pieces.undoMove();

        }
        if (this.isRightpossible){
            pieces.movePiece(this.getRow(), this.getColumn(), this.getRow(), this.getColumn() + 2, false);
            
            this.isRightpossible = !pieces.inCheck(this.getColour(), false);

            pieces.undoMove();

        }
    }

    public void showCastling(Board board){
        if (this.isLeftpossible){
            int colChange = -2;
            while (this.getColumn() + colChange > 0){
                this.board.getTile(this.getRow(), this.getColumn() + colChange).setBlue(true);
                colChange--;
            }
        }
        if (this.isRightpossible){
            int colChange = 2;
            while (this.getColumn() + colChange < 13){
                this.board.getTile(this.getRow(), this.getColumn() + colChange).setBlue(true);
                colChange++;
            }
        }
    }

}

