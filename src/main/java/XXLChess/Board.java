package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;

import java.lang.Thread;

public class Board {
    /**
     * Holds the parent App
     */
    PApplet parent;
    /**
     * List of all the tiles
     */
    Tile[][] Tiles;
    /**
     * Row of the green active tile
     */
    int CurrentGreenRow;
    /**
     * Column of the green active tile
     */
    int CurrentGreenColumn;
    /**
     * counter for the fps for flashing
     */
    public int counter;

    /**
     * Constructor for the board, takes in the app as an input
     * @param parent the parent app
     */
    public Board(PApplet parent){
        this.CurrentGreenRow = -1;
        this.CurrentGreenColumn = -1;
        this.parent = parent;
        this.Tiles = new Tile[14][14];
        boolean WhiteColour = true;
        int xVal = 0;
        int yVal = 0;
        for (int i = 0; i < 14; i++){
            for (int j = 0; j< 14; j++){
                if (WhiteColour){
                    this.Tiles[i][j] = new Tile("white", 255, 238, 178, xVal, yVal);
                } else {
                    this.Tiles[i][j] = new Tile("black", 205, 172, 119, xVal, yVal);    
                }
                xVal += 48;
                WhiteColour = !WhiteColour;
            }
            xVal = 0;
            yVal += 48;
            WhiteColour = !WhiteColour;
        }
        this.counter = 0;
    }

    /**
     * Draw the board to the screen
     */
    public void drawBoard(){
        for (int i = 0; i < 14; i++){
            for (int j = 0; j< 14; j++){
                Tile currentTile = this.Tiles[i][j];
                this.parent.noStroke();
                this.parent.fill(currentTile.getR(), currentTile.getG(), currentTile.getB()); // sets the fill color to red
                this.parent.rect(currentTile.getXcoords(), currentTile.getYcoords(), 48, 48); // draws a rectangle with top-left corner at (50, 50) and width=height=100
            }
        }
    }

    /**
     * Gets a single tile based off a row and column
     * @param row the row of the tile
     * @param column the column of the tile
     * @return the tile
     */
    public Tile getTile(int row, int column){
        return this.Tiles[row][column];
    }

    /**
     * Clears the green tile
     */
    public void clearGreen(){
        this.getTile(CurrentGreenRow, CurrentGreenColumn).setGreen(false);  
    }


    /**
     * Sets the tile at the specified row and column as the green one
     * @param row the row of the new green
     * @param column the column of the new green
     */
    public void setGreen(int row, int column){ 
        if (this.CurrentGreenRow != -1){ //this does a check to reset the old active tile
            this.clearGreen();  
        } 
        
        Tile currentTile = this.getTile(row, column);

        //changes the tile to the required colour (lighter for the white and darker for black)
  
        currentTile.setGreen(true);

        //sets the current Green coords
        this.CurrentGreenRow = row;
        this.CurrentGreenColumn = column;
    }

    /**
     * Resets all the board colours if they have changed
     * @param resetDarkRed does dark red need to be reset
     * @param resetRed does red need to be reset
     * @param resetGreen does green need to be reset
     * @param resetBlue does blue need to be reset
     * @param resetOrange does orange need to be reset
     */
    public void resetBoardColours(boolean resetDarkRed, boolean resetRed, boolean resetGreen, boolean resetBlue, boolean resetOrange){
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 14; j++){
                Tile checkTile = this.Tiles[i][j];
                if (resetDarkRed && checkTile.isDarkRed()){
                    checkTile.setDarkRed(false);
                }
                if (resetRed && checkTile.isRed()){
                    checkTile.setRed(false);
                }
                if (resetGreen && checkTile.isGreen()){
                    checkTile.setGreen(false);
                }
                if (resetBlue && checkTile.isBlue()){
                    checkTile.setBlue(false);
                }
                if (resetOrange && checkTile.isOrange()){
                    checkTile.setOrange(false);
                }
                if (checkTile.getDefaultColour() == "white"){
                    checkTile.changeColour(255, 238, 178);
                } else {
                    checkTile.changeColour(205, 172, 119);
                }
            }
        }
    }

    /**
     * 
     * Updates the new board colours of the tiles
     */
    public void updateBoardColours(){
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 14; j++){
                Tile checkTile = this.Tiles[i][j];
                if (checkTile.isDarkRed()){
                    //update to make tile darkred
                    checkTile.changeColour(122, 0, 0);

                } else if (checkTile.isGreen()){
                    //update to make tile green
                    checkTile.changeColour(105, 138, 76);
                
                } else if (checkTile.isRed()){
                    //update to make tile red
                    checkTile.changeColour(255, 164, 102);

                } else if (checkTile.isBlue()){
                    //update to make tile red
                    if (checkTile.getDefaultColour().equals("white")){
                        checkTile.changeColour(196, 224, 232);
                    } else {
                        checkTile.changeColour(170, 210, 221);
                    }
                } else if (checkTile.isOrange()){
                    //update to make tile orange
                    if (checkTile.getDefaultColour().equals("white")){
                        checkTile.changeColour(205, 210, 106);
                    } else {
                        checkTile.changeColour(170, 162, 58);
                    }
                } 
            }
        }
    }

    public boolean flashKing(int row, int col){
        this.counter++;
        if (this.counter % 30 == 0){
            Tile flashTile = this.getTile(row, col);
            if (flashTile.getR() == 255){
                flashTile.changeColour(120, 0, 0);
            } else {
                flashTile.changeColour(255, 255, 255);
            }
            if (this.counter == 120){
                this.counter = 0;
                return false;
            }
        }
        return true;
    }
    


}