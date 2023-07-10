package XXLChess;

import java.util.*;
import java.io.*;
import java.lang.Thread;

import processing.core.PImage;
import processing.core.PApplet;


/*

PIECES: the purpose of this class is to hold each pieces and deal with their interaction due to user input

*/

public class Pieces{
    /**
     * array of pieces
    */
    protected Piece[][] Pieces;
    /**
     * the parent app
    */
    protected PApplet parent;
    /**
     * the board
    */
    protected Board board;
    /**
     * the current active piece
    */
    protected int[] currentActive;
    /**
     * the last move
    */
    protected int [] lastmove;
    /**
     * the colour of the player
    */
    protected String playerColour;
    /**
     * whether the player is in check or not
    */
    protected boolean playerInCheck;
    /**
     * whether anyone is in check
    */
    protected boolean inCheck;
    /**
     * the last piece taken
    */
    Piece lastTaken;
    /**
     * the movement class
    */
    Movement Movement;
    /**
     * the timer
    */
    Timer Timer;
        /**
     * the row of the checked king
     */
    int inCheckKingRow;
    /**
     * the column of the cheked king
     */
    int inCheckKingCol;



    /**
     * The constructor for the pieces class
     * @param parent the parent app
     * @param board the board
     * @param playerColour the colour of the player
     * @param Movement the movement class
     * @param Timer the timer
    */
    public Pieces(PApplet parent, Board board, String playerColour, Movement Movement, Timer Timer){
        this.parent = parent;
        this.board = board;

        //set the active to one off the board
        this.currentActive = new int[]{-1, -1};

        //setup the pieces of the board 
        this.Pieces = new Piece[14][14];      

        this.lastmove = new int[4];  
        this.lastTaken = null;

        this.playerColour = playerColour;

        this.Movement = Movement;

        this.Timer = Timer;

        this.playerInCheck = false;

        this.inCheck = false;

        this.inCheckKingCol = -1;
        this.inCheckKingRow = -1;

    }
    /**
     * sets default positions of the pieces
     * @param fileName the input file
    */
    public void setDefaultPositions(String fileName){
        File f = new File(fileName);
        try {
            Scanner scan = new Scanner(f);
            int row = 0;
            while (scan.hasNextLine()){
                String currentLine = scan.nextLine(); //get the next line
                int size = currentLine.length();
                if (size > 14 || row >= 14){
                    this.handleInvalidBoard();
                }

                if (size < 1){ //if the line is empty
                    for (int column = 0; column < 14; column++){ //fill with empty cells
                        this.Pieces[row][column] = null;
                    }
                } else {
                    for (int column = 0; column < size; column++){
                        switch(currentLine.charAt(column)){
                            case 'R':
                                this.Pieces[row][column] = new Rook(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'N':
                                this.Pieces[row][column] = new Knight(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'B':
                                this.Pieces[row][column] = new Bishop(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'H':
                                this.Pieces[row][column] = new Archbishop(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'C':
                                this.Pieces[row][column] = new Camel(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'G':
                                this.Pieces[row][column] = new Guard(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'A':
                                this.Pieces[row][column] = new Amazon(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'K':
                                this.Pieces[row][column] = new King(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'E':
                                this.Pieces[row][column] = new Chancellor(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'P':
                                this.Pieces[row][column] = new Pawn(column*48, row*48, "black", this.parent, this.board, this.playerColour);
                                break;
                            case 'p':
                                this.Pieces[row][column] = new Pawn(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'r':
                                this.Pieces[row][column] = new Rook(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'n':
                                this.Pieces[row][column] = new Knight(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'b':
                                this.Pieces[row][column] = new Bishop(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'h':
                                this.Pieces[row][column] = new Archbishop(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'c':
                                this.Pieces[row][column] = new Camel(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'g':
                                this.Pieces[row][column] = new Guard(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'a':
                                this.Pieces[row][column] = new Amazon(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'k':
                                this.Pieces[row][column] = new King(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case 'e':
                                this.Pieces[row][column] = new Chancellor(column*48, row*48, "white", this.parent, this.board, this.playerColour);
                                break;
                            case ' ':
                                this.Pieces[row][column] = null;
                                break;
                            default:
                                this.handleInvalidBoard();
                        }
                    }
                    for (int i = 0; i < 14 - size; i++){
                        this.Pieces[row][i + size] = null;
                    }
                }
                row++;
            }
        } catch (FileNotFoundException e){
            System.out.println("Cannot find file");
        }
        this.convertQueens();
    }
    /**
     * Handles when the input of the board is wrong
     * @param ErrorMessage what to display
    */
    public void handleInvalidBoard(){
        System.out.println("The board provided is invalid. Please try again.");
        System.exit(0);
    }

    /**
     * gets the piece given a location
     * @param row the row of the piece
     * @param column the column of the piece
     * @return the piece at the desired positon
    */
    public Piece getPiece(int row, int column){

        return this.Pieces[row][column];
    }
    /**
     * gets the active coords 
     * @return the active coordinates
    */
    public int[] getActiveCoords(){
        return this.currentActive;
    }
    /**
     * gets the array of Pieces
     * @return the piece array
    */
    public Piece[][] getPieces(){
        return this.Pieces;
    }
    /**
     * gets the last move made
     * @return the last move
    */
    public int[] getlastmove(){
        return this.lastmove;
    }
    /**
     * gets the movement class
     * @return the Movement
    */
    public Movement getMovement(){
        return this.Movement;
    }

    /**
     * sets the active piece
     * @param row the row of the active piece
     * @param column the column of the active piece
    */
    public void setActivePiece(int row, int column){
        Piece newActivePiece = this.Pieces[row][column]; //piece that will become active
        newActivePiece.setActive(true); //activate the new one
        this.currentActive = new int[]{row, column}; //set the new active coords  
        this.board.setGreen(row, column);             
    }
    /**
     * sets the active coords 
     * @param row new row
     * @param column new column
    */
    // public void setActiveCoords(int row, int column){
    //     this.currentActive[0] = row;
    //     this.currentActive[1] = column;
    // }

    
    
    // /**
    //  * sets all of the pieces as a new array
    //  * @param newBoard the new array to copy
    // */
    // public void setAllPieces(Piece[][] newBoard){
    //     for (int row = 0; row < 14; row++){ //go through every position
    //         for (int col = 0; col < 14; col++){
    //             this.Pieces[row][col] = newBoard[row][col];
    //         }
    //     }
    // }
    /**
     * sets the new piece
     * @param row the row to set
     * @param column the column to set
     * @param setPiece the piece to set
    */
    public void setPiece(int row, int column, Piece setPiece){
        this.Pieces[row][column] = setPiece;
        if (setPiece != null){
            this.Pieces[row][column].setCoords(row, column);
        }
    }

    /**
     * draws the pieces onto the screen
    */
    public void drawPieces(){     
        for (int row = 0; row < 14; row++){
            for (int column = 0; column < 14; column++){
                Piece currentPiece = this.Pieces[row][column];
                if (currentPiece != null){
                    currentPiece.draw(this.parent);
                }
            }
        }
    }

    /**
     * moves the piece from one point to another
     * @param prevRow the previous row
     * @param prevColumn the previous column
     * @param newRow the new row
     * @param newCol the new column
     * @param actuallyMove whether the move is actually being made or not
    */
    public void movePiece(int prevRow, int prevColumn, int newRow, int newCol, boolean actuallyMove){
        Piece toChange = this.Pieces[prevRow][prevColumn]; //moving piece
        this.lastTaken = this.Pieces[newRow][newCol]; //piece getting taken

        if (actuallyMove){
            this.setInCheck(false);
            this.board.resetBoardColours(false, true, true, true, true);
            if (toChange instanceof Pawn && (prevRow == 12 || prevRow == 1)){
                Pawn Pawnmoved = (Pawn) toChange;
                Pawnmoved.setFirstMove();           
            }
            if (toChange instanceof King){
                King Kingmoved = (King) toChange;
                Kingmoved.setHasMoved();  
            }
            if (toChange instanceof Rook){
                Rook Rookmoved = (Rook) toChange;
                Rookmoved.setHasMoved();  
            }
            this.Movement.setIsMoving(true);

            int vectorX = newCol*48 - prevColumn*48;
            int vectorY = newRow*48 - prevRow*48;
            double vectorSize = Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));

            this.Movement.setInitialVectorSize(vectorSize);

            if (toChange.getColour().equals(this.playerColour)){
                this.Timer.incrementPlayerTime();
            } else {
                this.Timer.incrementComputerTime();
            }

           
        } else {
            toChange.setCoords(newRow, newCol);
            this.Pieces[newRow][newCol] = toChange;
            this.Pieces[prevRow][prevColumn] = null;
        }


        
        toChange.setProjected(newRow, newCol);


        //set the lastmove
        this.lastmove[0] = prevRow;
        this.lastmove[1] = prevColumn;
        this.lastmove[2] = newRow;
        this.lastmove[3] = newCol;

        if (actuallyMove){
            this.setOrange();
            this.board.updateBoardColours();
        }
   
    }
    /**
     * sets any pawns that need to be queens into queens
    */
    public void convertQueens(){
        for (int row = 0; row < 14; row++){
            for (int col = 0; col < 14; col++){
                Piece currentPiece = this.Pieces[row][col];
                if (currentPiece != null && currentPiece instanceof Pawn ){
                    if ((this.playerColour.equals(currentPiece.getColour()) && currentPiece.getY() < 384) || (!this.playerColour.equals(currentPiece.getColour()) && currentPiece.getY() > 336)){
                        this.Pieces[row][col] = new Queen(currentPiece.getX(), currentPiece.getY(), currentPiece.getColour(), this.parent, this.board, this.playerColour);
                    }       
                }
            }
        }
    }

    /**
     * returns whether the position is one with a check involved
     * @param colour the colour attackign the check
     * @param ActuallyMove whether its an actual move or not
     * @return if in check or not
    */
    public boolean inCheck(String colour, boolean ActuallyMove){
        int counter = 0;
        for (int row = 0; row < 14; row++){ //go through every position
            for (int col = 0; col < 14; col++){
                //get the piece
                Piece currentPiece = this.getPieces()[row][col];
                if (currentPiece != null && !currentPiece.getColour().equals(colour)){ //if its a rival attacking
                    currentPiece.getMoves(this);
                    for (int move = 0; move < currentPiece.getAttackingMoves().size(); move++){
                        int[] moveCheck = currentPiece.getAttackingMoves().get(move);
                        Piece attackingPiece = this.getPieces()[moveCheck[2]][moveCheck[3]];
                        if (attackingPiece instanceof King && attackingPiece.getColour() != currentPiece.getColour()){
                            if (ActuallyMove){
                                this.board.getTile(moveCheck[2], moveCheck[3]).setDarkRed(true);
                                this.setKingCheckCoords(moveCheck[2], moveCheck[3]);
                                if (colour == this.playerColour){
                                    this.playerInCheck = true;
                                }
                                //show the words for check
                                this.inCheck = true;
                            }

                            currentPiece.clearMoves();
                            return true;
                        }

                    }
                }
            }
        }

        return false;
    }

    /**
     * returns the position by back one go
    */
    public void undoMove(){
        int prevRow = this.lastmove[0];
        int prevCol = this.lastmove[1];
        int newRow = this.lastmove[2];
        int newCol = this.lastmove[3];

        this.getPieces()[prevRow][prevCol] = this.getPieces()[newRow][newCol];
        this.getPieces()[prevRow][prevCol].setCoords(prevRow, prevCol);
        this.getPieces()[prevRow][prevCol].setProjected(prevRow, prevCol);

        this.getPieces()[newRow][newCol] = this.lastTaken;
    }

    /**
     * sets the orange tiles
    */
    public void setOrange(){
        int[] lastmove = this.getlastmove();

        Tile tile1 = this.board.getTile(lastmove[0], lastmove[1]);
        Tile tile2 = this.board.getTile(lastmove[2], lastmove[3]);
      
        tile1.setOrange(true);
        tile2.setOrange(true);
    }
    /**
     * checks whether the currentposition is now a checkmate
     * @param GameLogic logic of the game
     * @param colour the colour the checkmate is happening on
    */
    public void CheckCheckmate(GameLogic GameLogic, String colour){
        if (!this.anyMovePossible(colour)){
            System.out.println("no moves possible for:");
            System.out.println(colour);

            int kingRow = 0;
            int kingCol = 0;
            for (int row = 0; row < 14; row++){ //go through every position
                for (int col = 0; col < 14; col++){
                    Piece currentPiece = this.Pieces[row][col];
                    if (currentPiece != null && currentPiece instanceof King && currentPiece.getColour().equals(colour)){
                        kingRow = row;
                        kingCol = col;
                    }
                }
            }
            GameLogic.setCheckmate(kingRow, kingCol, colour);
        } 
        
    }
    /**
     * ticks all of the pieces (updates pos)
     * @param Gamelogic the logic of the game
    */
    public void tickPieces(GameLogic Gamelogic){
        for (int row = 0; row < 14; row++){ //go through every position
            for (int col = 0; col < 14; col++){
                Piece currentPiece = this.getPieces()[row][col];
                if (currentPiece != null){
                    currentPiece.updatePos(this, Gamelogic);
                }
            }
        }
    }

    // public void printPieces(){
    //     for (int row = 0; row < 14; row++){ //go through every position
    //         for (int col = 0; col < 14; col++){
    //             Piece currentPiece = this.getPieces()[row][col];
    //             System.out.println(currentPiece);
    //         }
    //     }
    // }
    /**
     * shows which pieces contributed to the checkmate
     * @param losingColour the colour that got checkmated
     * @param row the row of the king
     * @param column the column of the king
    */
    public void showCheckmateHelpers(String losingColour, int row, int column){
        this.board.resetBoardColours(false, true, true, true, true);
        //checkmate occurs on row, column being the king
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (row + i < 14 && row + i > 0 && column + j < 14 && column + j > 0 && this.getPiece(row + i, column + j) == null && !(i == 0 && j == 0)){
                    boolean searchingForAttackingPiece = true;
                    System.out.println("BeginningSearch");
                    for (int k = 0; k < 14; k++){
                        for (int m = 0; m < 14; m++){
                            System.out.printf("%d, %d\n", k, m);
                            if (searchingForAttackingPiece){
                                Piece attackingPiece = this.getPiece(k, m);
                                if (attackingPiece != null && !attackingPiece.getColour().equals(losingColour)){
                                    attackingPiece.getMoves(this);
                                    for (int z = 0; z < attackingPiece.getLegalMoves().size(); z++){
                                        int[] move = attackingPiece.getLegalMoves().get(z);
                                        int attackingRow = move[2];
                                        int attackingCol = move[3];
                                        if (attackingRow == row + i && attackingCol == column + j){
                                            int startingRow = move[0];
                                            int startingCol = move[1];
                                            this.board.getTile(startingRow, startingCol).setRed(true);
                                            this.board.updateBoardColours();
                                            searchingForAttackingPiece = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        }

                    }
                } else {
                    System.out.println("skipped");
                }
            }
        }
        System.out.println("finished finding pieces");
        this.board.drawBoard(); //draw the board
        this.drawPieces();
    }

    public boolean checkInvalidMove(int row, int column){      
        int[] currentActive = this.currentActive;
        if (currentActive[0] == -1){
            return false;
        }

        Piece activePiece = this.getPiece(currentActive[0], currentActive[1]);
        if (activePiece != null){
            activePiece.getMoves(this);
            List<int[]> initialLegalMoves = new ArrayList<int[]>();
            List<int[]> initialAttackingMoves = new ArrayList<int[]>();
            List<int[]> finalLegalMoves = new ArrayList<int[]>();
            List<int[]> finalAttackingMoves = new ArrayList<int[]>();

            for (int[] move : activePiece.getLegalMoves()){
                initialLegalMoves.add(move);
            }
  
            for (int[] move : activePiece.getAttackingMoves()){
                initialAttackingMoves.add(move);
            }

            activePiece.cleanMoveSet(this);

            for (int[] move : activePiece.getLegalMoves()){
                finalLegalMoves.add(move);
            }
            for (int[] move : activePiece.getAttackingMoves()){
                finalAttackingMoves.add(move);
            }


            List<int[]> illegalMoves = new ArrayList<>();


            // iterate over all possible moves
            for (int[] initialMove : initialLegalMoves) {
                
                boolean found = false;
                // check if the move is present in the list of legal moves
                for (int[] finalMove : finalLegalMoves) {
                    if (Arrays.equals(initialMove, finalMove)) {
                        found = true;
                        break;
                    }
                }
                // if the move was not found in the list of legal moves, add it to the list of illegal moves
                if (!found) {
                    illegalMoves.add(initialMove);
                }
            }
            for (int[] initialMove : initialAttackingMoves) {
                boolean found = false;
                // check if the move is present in the list of legal moves
                for (int[] finalMove : finalAttackingMoves) {
                    if (Arrays.equals(initialMove, finalMove)) {
                        found = true;
                        break;
                    }
                }
                // if the move was not found in the list of legal moves, add it to the list of illegal moves
                if (!found) {
                    illegalMoves.add(initialMove);
                }
            }
            for (int[] move : illegalMoves){
                if (move[2] == row && move[3] == column){
                    return true;
                }
            }
            return false;
        }
        return false;
        


    }
    /**
     * returns whether position is in check
     * @return if in check positions
    */
    public boolean isInCheck(){
        return this.inCheck;
    }
    /**
     * sets if position is in check
     * @param inCheck whether it is in check
    */
    public void setInCheck(boolean inCheck){
        this.inCheck = inCheck;
    }
    /**
     * gets the player colour
     * @return the player colour
    */
    public String getPlayerColour(){
        return this.playerColour;
    }

    public boolean anyMovePossible(String colour){
        int counter = 0;
        for (int row = 0; row < 14; row++){ //go through every position
            for (int col = 0; col < 14; col++){
                Piece currentPiece = this.getPieces()[row][col];
                if (currentPiece != null && currentPiece.getColour().equals(colour)){
                    currentPiece.clearMoves();
                    currentPiece.getMoves(this);
                    currentPiece.cleanMoveSet(this);
                    counter += currentPiece.getAttackingMoves().size();
                    counter += currentPiece.getLegalMoves().size();
                }
            }
        }
        if (counter != 0){
            return true;
        } else {
            return false;
        }
    }

    public void setKingCheckCoords(int row, int column){
        this.inCheckKingCol = column;
        this.inCheckKingRow = row;
    }

    public int getKingCheckRow(){
        return this.inCheckKingRow;
    }

    public int getKingCheckCol(){
        return this.inCheckKingCol;
    }

    public void makeCastleMove(boolean isLeft, int row){

        Piece toChange = this.Pieces[row][7]; //moving king

        this.setInCheck(false);
        this.board.resetBoardColours(false, true, true, true, true);
        King Kingmoved = (King) toChange;
        Kingmoved.setHasMoved();  

        this.Movement.setIsMoving(true);


        this.Movement.setInitialVectorSize(6);

        if (isLeft){
            toChange.setProjected(row, 5);
        } else {
            toChange.setProjected(row, 9);
        }



        this.Movement.setIsCastling(true);
        if (isLeft){
            Rook toMove = (Rook) this.getPiece(row, 0);
            toMove.setProjected(row, 6);
            toMove.clearMoves();
        } else {
            Rook toMove = (Rook) this.getPiece(row, 13);
            toMove.setProjected(row, 8);
            toMove.clearMoves();
        }

        this.Movement.setIsLeft(isLeft);




        if (toChange.getColour().equals(this.playerColour)){
            this.Timer.incrementPlayerTime();
        } else {
            this.Timer.incrementComputerTime();
        }

        toChange.clearMoves();

        // deal with orange
    }


}