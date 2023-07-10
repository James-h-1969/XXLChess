// Defines the package name
package XXLChess;

// Import necessary libraries
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;
import java.util.Random;

// Defines a class "Computer"
public class Computer {
    
    /**
     * Pieces on the board
     */
    private Pieces pieces;
    /**
     * The Board for the computer to interact with
     */
    private Board board;
    /**
     * The parent app
     */
    private PApplet parent;
    /**
     * Help for the randomiser
     */
    private int randomiser;
    /**
     * more randomiser help
     */
    private Random random;
    /**
     * counter variable
     */
    private int counter;

    /**
     * Constructer for the computer
     * @param pieces the pieces for the board
     * @param board the board
     * @param parent the parent app
     */
    public Computer(Pieces pieces, Board board, PApplet parent){
        // Initializes instance variables with values passed as arguments
        this.pieces = pieces;
        this.board = board;
        this.parent = parent;
        // Initializes a Random object and a random integer value
        this.random = new Random();
        this.randomiser = 28;

        this.counter = 0;
    }

    /**
     * Makes a random valid legal move for the computer
     */
    public void makeMove(){
        // Boolean variable to control the loop
        boolean findingMove = true;
        // Loop that generates random moves until a valid move is found
        int forevercounter = 0;
        while(findingMove){
            if (forevercounter == 100){
                return;
            }
            forevercounter++;
            // Generates a random integer value
            int randomInt = this.random.nextInt(this.randomiser);
            // Counter variable to keep track of the number of black pieces encountered
            int counter = 0;
            // Nested loop to iterate over all the pieces on the board
            for (int i = 0; i < 14; i++){
                for (int j = 0; j < 14; j++){
                    // Checks whether the piece is black
                    if (this.pieces.getPieces()[i][j] != null && this.pieces.getPieces()[i][j].getColour() == "black"){
                        // Increments the counter
                        counter++;
                        // Checks whether the counter matches the randomly generated integer value
                        if (counter == randomInt){
                            // Generates legal and attacking moves for the piece
                            this.pieces.getPieces()[i][j].getMoves(this.pieces);
                            this.pieces.getPieces()[i][j].cleanMoveSet(this.pieces);

                            this.board.resetBoardColours(true, true, true, true, true);
                            this.board.updateBoardColours();

                            // Checks whether the piece has any attacking moves
                            if (this.pieces.getPieces()[i][j].getAttackingMoves().size() != 0){
                                // Generates a random index value for the attacking moves and selects the move at that index
                                int randomMoveIndex = this.random.nextInt(this.pieces.getPieces()[i][j].getAttackingMoves().size());
                                int[] move = this.pieces.getPieces()[i][j].getAttackingMoves().get(randomMoveIndex);
                                // Swaps the locations of the selected piece and the target piece
                                this.pieces.movePiece(move[0], move[1], move[2], move[3], true);
                                // Sets the boolean variable to exit the loop
                                findingMove = false;
                            } 
                            // Checks whether the piece has any legal moves
                            else if (this.pieces.getPieces()[i][j].getLegalMoves().size() != 0){
                                // Generates a random index value for the legal moves and selects the move at that index
                                int randomMoveIndex = this.random.nextInt(this.pieces.getPieces()[i][j].getLegalMoves().size());
                                int[] move = this.pieces.getPieces()[i][j].getLegalMoves().get(randomMoveIndex);
                                // Swaps the locations of the selected piece and the target piece
                                this.pieces.movePiece(move[0], move[1], move[2], move[3], true);
                                // Sets the boolean variable to exit the loop
                                findingMove = false;
                            }
                        }
                    }
                }
            }
        }
    }
}
