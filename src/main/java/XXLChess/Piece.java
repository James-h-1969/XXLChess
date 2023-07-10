package XXLChess;

import java.lang.Math;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.List;
import java.util.ArrayList;

public abstract class Piece {
    /**
     * x coordinate of the piece
     */
    protected int xCoordinate;
    /**
     * y coordinate of the piece
     */
    protected int yCoordinate;
    /**
     * projected x coordinate of where the piece is going
     */
    protected int projectedX;
    /**
     * projected y coordinate of where the piece is going
     */
    protected int projectedY;
    /**
     * row of the piece
     */

    protected int Row;
    /**
     * column of the piece
     */
    protected int Column;
    /**
     * the sprite of the piece
     */
    private PImage sprite;
    /**
     * whether the piece is selected
     */
    protected boolean isSelected;
    /**
     * colour of the piece
     */
    protected String colour;
    /**
     * the colour of the player
     */
    protected String playerColour;
    /**
     * the parent app
     */
    protected PApplet parent;
    /**
     * the board
     */
    protected Board board;
    /**
     * the list of legal moves the piece can make
     */
    protected List<int[]> legalMoves;
    /**
     * list of attackingmoves the piece can make
     */
    protected List<int[]> attackingMoves;


    /**
     * The constructor for the piece
     * @param x the x coord
     * @param y the y coord
     * @param colour the colour of the piece
     * @param parent the parent app
     * @param board the board
     * @param PlayerColour the colour of the player
     */
    public Piece(int x, int y, String colour, PApplet parent, Board board, String PlayerColour){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.projectedX = x;
        this.projectedY = y;
        this.Column = x/48;
        this.Row = y/48;
        this.colour = colour;
        this.parent = parent;
        this.isSelected = false;
        this.board = board;
        this.legalMoves = new ArrayList<>();
        this.attackingMoves = new ArrayList<>();
        this.playerColour = PlayerColour;
    }

    /**
     * abstract function that gets the possible moves of the pieces
     * @param pieces the pieces
     */
    public abstract void getMoves(Pieces pieces);

    /**
     * ticks the piece
     */
    //public void tick(){};

    /**
     * finds the final x and y coords that the piece should end up at and moves the piece towards it at the right speed
     * @param pieces the pieces to move
     * @param GameLogic the logic that the game is using
     */
    public void updatePos(Pieces pieces, GameLogic GameLogic){
        if (this.xCoordinate != this.projectedX || this.yCoordinate != this.projectedY){
            Movement movement = pieces.getMovement();
            int vectorX = this.projectedX - this.xCoordinate;
            int vectorY = this.projectedY - this.yCoordinate;
            double vectorSize = Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));
            if (vectorSize < 20){
                int[] lastmove = pieces.getlastmove();
                this.xCoordinate = projectedX;
                this.yCoordinate = projectedY;
                if (this instanceof Rook && movement.getIsCastling()){
                    pieces.setPiece(projectedY/48, projectedX/48, this);
                    if (movement.getIsLeft()){
                        pieces.setPiece(projectedY/48, 0, null);
                    } else {
                        pieces.setPiece(projectedY/48, 13, null);
                    }
                } else {
                    pieces.setPiece(lastmove[2], lastmove[3], this);
                    pieces.setPiece(lastmove[0], lastmove[1], null);
                }

                if (this.getColour() == this.playerColour){
                    pieces.getPiece(lastmove[2], lastmove[3]).setActive(false);
                }
                this.clearMoves();
                this.getMoves(pieces);

                if (!(this instanceof King) || !movement.getIsCastling()){
                    movement.setIsCastling(false);
                    String opposingColour = this.getColour().equals("white") ? "black" : "white";

                    for (int i = 0; i < this.getAttackingMoves().size(); i++){
                        int[] currentMove = this.getAttackingMoves().get(i);
                        Piece attackingPiece = pieces.getPiece(currentMove[2], currentMove[3]);
                        if (attackingPiece instanceof King){
                            System.out.println("CHECKING CHECKMATE");
                            pieces.CheckCheckmate(GameLogic, opposingColour);
                        }
                    }
                    movement.setIsMoving(false);
                    pieces.convertQueens();
                    pieces.inCheck(opposingColour, true);
                    this.board.resetBoardColours(false, true, true, true, false);
                    this.board.updateBoardColours();
                    GameLogic.switchTimer();
                    if (!pieces.anyMovePossible(opposingColour)){
                        GameLogic.setStalemate();
                    }
                }
                return;
            }

            double speed;

            if (movement.getInitialVectorSize()/(60 * movement.getpieceMovementSpeed()) > movement.getMaxMovementTime()){
                speed = movement.getInitialVectorSize()/(60*movement.getMaxMovementTime());
            } else {
                speed = movement.getpieceMovementSpeed();
            }


            vectorX = (int) ((speed * vectorX) / vectorSize);
            vectorY = (int) ((speed * vectorY) / vectorSize);



            this.xCoordinate += vectorX;
            this.yCoordinate += vectorY;



        } 
    }

    /**
     * abstract function that makes a copy of the piece
     * @param newBoard new board that it is copying onto
     * @return the copied piece
     */
    public abstract Piece copyPiece(Pieces newBoard);


    /**
     * Sets the sprite of the piece
     * @param sprite the sprite of the piece
     */
    public void setSprite(PImage sprite){
        sprite.resize(48, 48);
        this.sprite = sprite;
    }

    /**
     * sets the playercolour
     * @param colour the colour of the player
     */
    // public void setPlayerColour(String colour){
    //     this.playerColour = colour;
    // }

    /**
     * draws the piece onto the screen
     * @param app the app
     */
    public void draw(PApplet app){
        app.image(this.sprite, this.xCoordinate, this.yCoordinate);
    }

    /**
     * gets the x coord
     * @return the x position
     */
    public int getX(){
        return this.xCoordinate;
    }
    /**
     * gets the y coord
     * @return the y position
     */
    public int getY(){
        return this.yCoordinate;
    }
    /**
     * gets the row
     * @return the row of the piece
     */
    public int getRow(){
        return this.Row;
    }
    /**
     * gets the column
     * @return the column of the piece
     */
    public int getColumn(){
        return this.Column;
    }
    /**
     * gets whether the piece is active
     * @return is the piece active
     */
    public boolean getActive(){
        return this.isSelected;
    }
    /**
     * gets the colour of the piece
     * @return the colour of the piece
     */
    public String getColour(){
        return this.colour;
    }
    /**
     * gets the legal moves the piece can make
     * @return the legal moves
     */
    public List<int[]> getLegalMoves(){
        return this.legalMoves;
    }
    /**
     * gets the attacking moves the piece can make
     * @return the attackingMoves
     */
    public List<int[]> getAttackingMoves(){
        return this.attackingMoves;
    }

    /**
     * sets the coordinates of the piece
     * @param row the new row
     * @param column the new column
     */
    public void setCoords(int row, int column){
        this.Row = row;
        this.Column = column;
        this.xCoordinate = column * 48;
        this.yCoordinate = row * 48;
    }
    /**
     * sets the projected coordinates of the piece
     * @param row the new row
     * @param column the new column
     */
    public void setProjected(int row, int column){
        this.Row = row;
        this.Column = column;
        this.projectedX = column * 48;
        this.projectedY = row * 48;
    }

    /**
     * sets the piece as active 
     * @param active whether it is active or not
     */
    public void setActive(boolean active){
        this.isSelected = active;
    }

    /**
     * shows the potential moves of the specific piece
     * @param pieces the pieces
     */
    public void ShowPotentialMoves(Pieces pieces){
        this.clearMoves(); //clear the current board
        this.getMoves(pieces); //get the moves
        if (this instanceof King){
            King thisPiece = (King) this;
            thisPiece.isCastlingValid(pieces);
            thisPiece.showCastling(this.board);
        }
        this.cleanMoveSet(pieces);
        this.changeTileColours(pieces); //change the colours of each tile

    }


    /**
     * changes the neccessay tiles to the correct colour
     * @param pieces the pieces
     */
    public void changeTileColours(Pieces pieces){
        //show the legal non-attacking moves
        for (int i = 0; i < this.legalMoves.size(); i++)
        { 
            //get the tile to change colour
            int[] currentMove = this.legalMoves.get(i);
            int currentRow = currentMove[2];
            int currentColumn = currentMove[3];
            Tile toChange = this.board.getTile(currentRow, currentColumn);
            
            //change the colour of the tile
            toChange.setBlue(true);
        }
        //show the legal attacking moves
        for (int i = 0; i < this.attackingMoves.size();i++)
        { 
            //get the tile to change colour
            int[] currentMove = this.attackingMoves.get(i);
            int currentRow = currentMove[2];
            int currentColumn = currentMove[3];
            Tile toChange = this.board.getTile(currentRow, currentColumn);

            //change the colour of the tile
            toChange.setRed(true);
        }
    }

    /**
     * checks whether the given row and column is on the board
     * @param row the row input
     * @param col the column input
     * @return if one the board
     */
    public boolean isOnBoard(int row, int col) {
        return row >= 0 && row < 14 && col >= 0 && col < 14;
    }

    /**
     * adds moves in a certain direction
     * @param rowChange the row direction to change
     * @param colChange the column direction to change
     * @param pieces the pieces
     */
    public void traverseTiles(int rowChange, int colChange, Pieces pieces){
        //get the variables 
        int currentRow = this.getRow();
        int currentColumn = this.getColumn();
        int newRow = currentRow + rowChange;
        int newCol = currentColumn + colChange;

        //if its valid then add it to legalmoves list

        while (isOnBoard(newRow, newCol) && pieces.getPieces()[newRow][newCol] == null){ //check if still on the board
            int[] move = {currentRow, currentColumn, newRow, newCol};
            this.legalMoves.add(move);
            newRow += rowChange; //update the row and col
            newCol += colChange;
        }
        //when it is not null check whether the piece is being attacked
        if (isOnBoard(newRow, newCol)){
            Piece targetPiece = pieces.getPieces()[newRow][newCol];
            if (targetPiece.getColour() != this.getColour()){ //if the piece that it is looking that is the opposite colour
                int[] move = {currentRow, currentColumn, newRow, newCol};
                this.attackingMoves.add(move);

            }
        }
    }

    /**
     * adds the row and column into the moves
     * @param rowChange the new row
     * @param colChange the new column
     * @param pieces the pieces
     */
    public void addMove(int rowChange, int colChange, Pieces pieces){
        //get the variables
        int currentRow = this.getRow();
        int currentColumn = this.getColumn();
        int newRow = currentRow + rowChange;
        int newCol = currentColumn + colChange;
        
        if (isOnBoard(newRow, newCol)){ //check whether the 
            Piece targetPiece = pieces.getPieces()[newRow][newCol];
            
            
            if (targetPiece == null){
                int[] move = {this.getRow(), this.getColumn(), newRow, newCol};
                if (this instanceof Pawn){
                    if (colChange == 0){
                        if (Math.abs(rowChange) == 2){
                            if (pieces.getPieces()[newRow - rowChange/2][newCol] == null){
                                this.legalMoves.add(move);
                            }
                        } else {
                            this.legalMoves.add(move);
                        }
                        
                    }
                } else {
                    this.legalMoves.add(move);

                }


            } else if (targetPiece.getColour() != this.getColour()){
                if (this instanceof Pawn){ //cant take forward if pawn
                    if (colChange != 0){
                        int[] move = {this.getRow(), this.getColumn(), newRow, newCol};
                        this.attackingMoves.add(move);
                    }
                } else {
                    int[] move = {this.getRow(), this.getColumn(), newRow, newCol};
                    this.attackingMoves.add(move);
                }
            }
        }
    }

    // public void printMoves(){
    //     for (int i = 0; i < this.getLegalMoves().size(); i++){
    //         int[] move = this.getLegalMoves().get(i);
    //         System.out.printf("%d, %d, %d, %d\n", move[0], move[1], move[2], move[3]);
    //     }
    // }

    /**
     * gets rid of all the currentmoves
     */
    public void clearMoves(){
        this.legalMoves.clear();
        this.attackingMoves.clear();
    }

    /**
     * removes any moves that cause a check on its own king
     * @param pieces the pieces
     */
    public void cleanMoveSet(Pieces pieces){
        //setup the board
        List<int[]> newMoves = new ArrayList<>();
        //validate legal moves
        for (int i = 0; i < this.getLegalMoves().size(); i++){
            int[] move = this.getLegalMoves().get(i);


            pieces.movePiece(move[0], move[1], move[2], move[3], false);
            
            if (!pieces.inCheck(this.getColour(), false)){
                newMoves.add(move);
            }

            pieces.undoMove();
        }

        this.legalMoves.clear();
        for (int i = 0; i < newMoves.size();i++){
            this.legalMoves.add(newMoves.get(i));
        }
        newMoves.clear();
       

        for (int i = 0; i < this.getAttackingMoves().size(); i++){
            int[] move = this.getAttackingMoves().get(i);
            pieces.movePiece(move[0], move[1], move[2], move[3], false);
            if (!pieces.inCheck(this.getColour(), false)){
                newMoves.add(move);
            }
            pieces.undoMove();
        }

        this.attackingMoves.clear();
        for (int i = 0; i < newMoves.size();i++){
            this.attackingMoves.add(newMoves.get(i));
        }   
    }
    
}
