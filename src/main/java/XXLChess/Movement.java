package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;

public class Movement{
    /**
     * whether a piece is in movement
     */
    boolean isMoving;
    /**
     * the max movement time the piece can have
     */
    int maxMovementTime;
    /**
     * the default movement speed of the piece
     */
    double pieceMovementSpeed;
    /**
     * the starting vector of the movement
     */
    double initialVectorSize;
    /**
     * is castling
     */
    boolean isCastling;
    /**
     * is the castle left
     */
    boolean isLeft;

    /**
     * The constructor of the movement
     * @param maxMove the maximum time for a move
     * @param pieceMove the normal movement speed of a piece
     */
    public Movement(int maxMove, double pieceMove){
        this.isMoving = false;
        this.maxMovementTime = maxMove;
        this.pieceMovementSpeed = pieceMove;
        this.initialVectorSize = 0;
        this.isCastling = false;
    }
    /**
     * sets whether a piece is in movement
     * @param moving is it moving
     */
    public void setIsMoving(boolean moving){
        this.isMoving = moving;

    }
    /**
     * gets whether a piece is moving
     * @return if a piecce is moving
     */
    public boolean getIsMoving(){
        return this.isMoving;
    }
    /**
     * sets the initial vector size 
     * @param size the intial size
     */
    public void setInitialVectorSize(double size){
        this.initialVectorSize = size;
    }
    /**
     * gets the initial vector size
     * @return the initialvectorsize
     */
    public double getInitialVectorSize(){
        return this.initialVectorSize;
    }
    /**
     * gets the movement speed of the pieces
     * @return the movement speed
     */
    public double getpieceMovementSpeed(){
        return this.pieceMovementSpeed;
    }
    /**
     * gets the max movement time of the pieces
     * @return the max movement time
     */
    public int getMaxMovementTime(){
        return this.maxMovementTime;
    }

    public void setIsCastling(boolean isCastling){
        this.isCastling = isCastling;
    }

    public boolean getIsCastling(){
        return this.isCastling;
    }

    public void setIsLeft(boolean isLeft){
        this.isLeft = isLeft;
    }

    public boolean getIsLeft(){
        return this.isLeft;
    }
}