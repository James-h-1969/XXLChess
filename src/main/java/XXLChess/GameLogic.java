package XXLChess;

import java.lang.Math;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class GameLogic{
    /**
     * Pieces on the board
     */
    Pieces Pieces;
    /**
     * the board
     */
    Board Board;
    /**
     * Parent app
     */
    PApplet parent;
    /**
     * the timer interface
     */
    Timer Timer;
    /**
     * variable that holds whether player1 is moving
     */
    boolean player1Moving;
    /**
     * variable that holds whether checkmate is on the board
     */
    boolean checkMate;
    /**
     * variable that holds the losing colour
     */
    String losingColour;
    /**
     * variable that holds whether a forfeit takes place
     */
    boolean playerForfeit;
    /**
     * the colour of the player1
     */
    String player1Colour;
    /**
     * the colour of player2
     */
    String player2Colour;
    /**
     * the row of the losing king
     */

    int losingKingRow;
    /**
     * the column of the losing king
     */
    int losingKingCol;

    /**
     * a variable that holds whether two player or not
     */
    boolean isTwoPlayer;
    /**
     * a variable that holds whether to show the checkmate helpers
     */

    boolean showingCheckMateHelpers;
    /**
     * a variable holding whether in stalemate
     */
    boolean inStalemate;
    /**
     * whether flashing the king
     */
    boolean isFlashingKing;
    /**
     * the movement class
     */
    Movement Movement;

    
    /**
     * Constructor for the gamelogic
     * @param Pieces the pieces
     * @param Board the board
     * @param parent the parent app
     * @param Timer the timer
     * @param player1Colour the colour of player 1
     * @param isTwoPlayer the mode which the chess is played
     */
    public GameLogic(Pieces Pieces, Board Board, PApplet parent, Timer Timer, String player1Colour, Boolean isTwoPlayer, Movement Movement){
        this.Pieces = Pieces;
        this.Board = Board;
        this.parent = parent;
        this.Timer = Timer;
        this.player1Moving = true;
        this.checkMate = false;
        this.losingColour = "";
        this.playerForfeit = false;
        this.losingKingRow = 0;
        this.losingKingCol = 0;
        this.showingCheckMateHelpers = false;
        this.inStalemate = false;
        this.player1Colour = player1Colour;
        if (player1Colour.equals("white")){
            this.player2Colour = "black";
        } else {
            this.player2Colour = "white";
        }
        this.isTwoPlayer = isTwoPlayer;
        this.isFlashingKing = false;
        this.Movement = Movement;
    }

    /**
     * Logic for when the mouse is clicked at any point
     * @param row the row the mouse is clicked
     * @param column the column the mous is clicked
     */
    public void mousePressedGameLogic(int row, int column){
        Tile TileClicked = this.Board.getTile(row, column);
        String currentColourMoving;
        if (this.isTwoPlayer){
            currentColourMoving = this.player1Moving ? this.player1Colour : this.player2Colour;
        } else {
            currentColourMoving = this.player1Colour;
        }
        
        if (this.Pieces.getPiece(row, column) != null && !this.Movement.getIsMoving()  && !this.isKingFlashing()){ //check if a piece has been pressed
            //get the piece pressed
            Piece PieceClicked = this.Pieces.getPiece(row, column);
            if (PieceClicked.getColour().equals(currentColourMoving)){ //if the piece pressed is white
                if (PieceClicked.getActive()){ //if the piece clicked was already clicked
                    PieceClicked.setActive(false);
                    this.Board.resetBoardColours(false, true, true, true, false);
                }
                else {
                    this.Board.resetBoardColours(false, true, true, true, false);
                    this.Pieces.setActivePiece(row, column); //set the new piece as active
                    PieceClicked.ShowPotentialMoves(this.Pieces);
                }
                this.Board.updateBoardColours();
            } else if (TileClicked.isRed()){ //check if can be attacked
                this.Board.resetBoardColours(true, true, true, true, false);
                this.Board.updateBoardColours();
                this.Pieces.movePiece(this.Pieces.getActiveCoords()[0], this.Pieces.getActiveCoords()[1], row, column, true); //
                this.player1Moving = !this.player1Moving;
            } else {
                if(this.Pieces.checkInvalidMove(row, column)){
                    this.setFlashingKing(true);
                }
            }
        } 
        else if (this.Board.getTile(row, column).isBlue()){
            //this needs to swap the position of the current active piece and the row + column pressed
            this.Board.resetBoardColours(true, true, true, true, false);
            this.Board.updateBoardColours();
            this.Timer.setStarted();
            int[] activeCoords = this.Pieces.getActiveCoords();
            Piece movingPiece = this.Pieces.getPiece(activeCoords[0], activeCoords[1]);
            if (movingPiece instanceof King && Math.abs(activeCoords[1] - column) >= 2){
                if (activeCoords[1] - column > 0){ //left move
                    this.Pieces.movePiece(activeCoords[0], activeCoords[1], activeCoords[0], activeCoords[1] - 2, true); //
                    this.Pieces.makeCastleMove(true, activeCoords[0]);
                } else { //right move
                    this.Pieces.movePiece(activeCoords[0], activeCoords[1], activeCoords[0], activeCoords[1] + 2, true); //
                    this.Pieces.makeCastleMove(false, activeCoords[0]);
                }
                this.player1Moving = !player1Moving;
                return;
            }
            this.Pieces.movePiece(activeCoords[0], activeCoords[1], row, column, true); //
            this.player1Moving = !player1Moving;

        } else {
            //check whether pressed the invalid move
            if(this.Pieces.checkInvalidMove(row, column)){
                this.setFlashingKing(true);
            }
            //get coords of active
            int[] activeCoords = this.Pieces.getActiveCoords();
            if (activeCoords[0] != -1){
                Piece activePiece = this.Pieces.getPiece(activeCoords[0], activeCoords[1]);
                if (activePiece != null){
                    activePiece.setActive(false);
                }
            }
            

        }

    }

    /**
     * Gets whether player 1 is moving
     * @return if player1 is moving
     */
    public boolean getPlayer1Move(){
        return this.player1Moving;
    }
    /**
     * Sets if player 1 is moving
     * @param player1Moving are they moving
     */
    public void setPlayer1Move(boolean player1Moving){
        this.player1Moving = player1Moving;
    }

    /**
     * returns whether there is a checkmate
     * @return if in checkmae
     */
    public boolean getCheckMate(){
        return this.checkMate;
    }

    /**
     * Sets a checkmate
     * @param row the row of the king
     * @param column the column of the king
     * @param losingColour the colour of the losing side
     */
    public void setCheckmate(int row, int column, String losingColour){
        this.checkMate = true;
        this.Board.getTile(row, column).setDarkRed(true); //set to dark red for checkmate
        this.losingKingCol = column;
        this.losingKingRow = row;
        this.losingColour = losingColour;
    }

    /**
     * Retunrs the losing colour
     * @return the losing colour
     */
    public String getLosingColour(){
        return this.losingColour;
    }
    /**
     * sets the player forfeiting
     */
    public void setPlayerForfeit(){
        this.playerForfeit = true;
    }
    /**
     * gets whether the player forfeited
     * @return if the player has forfeited
     */
    public boolean getPlayerForfeit(){
        return this.playerForfeit;
    }
    /**
     * gets the losing king row
     * @return the losing king row
     */
    public int getLosingKingRow(){
        return this.losingKingRow;
    }
    /**
     * gets the losing kind column
     * @return the row of the losing king
     */
    public int getLosingKingCol(){
        return this.losingKingCol;
    }
    /**
     * sets whether to show the checkmate helpers
     */
    public void setShowingCheckMateHelpers(){
        this.showingCheckMateHelpers = true;
    }
    /**
     * gets whether to show the checkmate helpers
     * @return whether showing the checkmate helpers
     */
    public boolean getShowingCheckMateHelpers(){
        return this.showingCheckMateHelpers;
    }

    /**
     * switches the times between each other
     */
    public void switchTimer(){
        this.Timer.setOnTime1(!this.Timer.getOntime1());
    }

    /**
     * is the pieces in stalemate
     * @return if in stalemate
     */
    public boolean isInStaleMate(){
        return this.inStalemate;
    }

    /**
     * sets the board to a stalemate position
     */
    public void setStalemate(){
        this.inStalemate = true;
    }

        /**
     * is the pieces in stalemate
     * @return if in stalemate
     */
    public boolean isKingFlashing(){
        return this.isFlashingKing;
    }

    /**
     * sets the board to a stalemate position
     */
    public void setFlashingKing(boolean isFlashing){
        this.isFlashingKing = isFlashing;
    }
}