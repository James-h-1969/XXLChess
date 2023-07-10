package XXLChess;

public class Tile {
    /**
     * the default colour of the tile
     */
    private String DefaultColour;
    /**
     * the red value of the tile
     */
    private int R;
    /**
     * the green value of the tile
     */
    private int G;
    /**
     * the blue value of the tile
     */
    private int B;
    /**
     * the x coord of the tile
     */
    private int xCoordinate;
    /**
     * the y coord of the tile
     */
    private int yCoordinate;
    /**
     * whether the tile is blue
     */
    private boolean isBlue;
    /**
     * whether the tile is red
     */
    private boolean isRed;
    /**
     * whether the tile is orange
     */
    private boolean isOrange;
    /**
     * whether the tile is darkred
     */
    private boolean isDarkRed;
    /**
     * whether the tile is green
     */
    private boolean isGreen;


    /**
     * the constructor of the tile
     * @param DefaultColour the default colour of the tile
     * @param R the red value
     * @param G the green value
     * @param B the blue value
     * @param xCoordinate the x coord
     * @param yCoordinate the y coord
     */
    public Tile(String DefaultColour, int R, int G, int B, int xCoordinate, int yCoordinate){
        this.DefaultColour = DefaultColour;
        this.R = R;
        this.G = G;
        this.B = B;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.isBlue = false;
        this.isRed = false;
        this.isOrange = false;
        this.isDarkRed = false;
        this.isGreen = false;
    }
    /**
     * change the colour of the tile
     * @param R the red value
     * @param G the green value
     * @param B the blue value
     */
    public void changeColour(int R, int G, int B){
        this.R = R;
        this.G = G;
        this.B = B;
    }
    /**
     * resets the tile to its default colour
     */
    public void resetColour(){
        if (this.DefaultColour.equals("white")){
            this.changeColour(255, 238, 178);
        } else {
            this.changeColour(205, 172, 119);
        }
    }
    /**
     * gets the red value 
     * @return the red value
     */
    public int getR(){
        return this.R;
    }
    /**
     * gets the green value
     * @return the green value
     */
    public int getG(){
        return this.G;
    }
    /**
     * gets the blue value
     * @return the blue value
     */
    public int getB(){
        return this.B;
    }
    /**
     * gets the x coords
     * @return the x coordinates
     */
    public int getXcoords(){
        return this.xCoordinate;
    }
    /**
     * gets the y coords
     * @return the y coordinates
     */
    public int getYcoords(){
        return this.yCoordinate;
    }
    /**
     * get the default colour of the tile
     * @return the default colour of the tile
     */
    public String getDefaultColour(){
        return this.DefaultColour;
    }
    /**
     * get if blue
     * @return if blue
     */

    public boolean isBlue(){
        return this.isBlue;
    }
    /**
     * get if red
     * @return if red
     */

    public boolean isRed(){
        return this.isRed;
    }
    /**
     * get if green
     * @return if green
     */

    public boolean isGreen(){
        return this.isGreen;
    }
    /**
     * set the blue 
     * @param isBlue is it blue
     */

    public void setBlue(boolean isBlue){
        this.isBlue = isBlue;
    }
    /**
     * set the red
     * @param isRed is it red
     */
    public void setRed(boolean isRed){
        this.isRed = isRed;
    }
    /**
     * set the dark red
     * @param isDarkRed is it dark red
     */

    public void setDarkRed(boolean isDarkRed){
        this.isDarkRed = isDarkRed;
    }
    /**
     * set the green
     * @param isGreen is it green
     */

    public void setGreen(boolean isGreen){
        this.isGreen = isGreen;
    }
    /**
     *  is it dark red
     * @return if dark red
     */

    public boolean isDarkRed(){
        return this.isDarkRed;
    }
    /**
     * is it orange
     * @return if orange
     */

    public boolean isOrange(){
        return this.isOrange;
    }
    /**
     * set if it is orange
     * @param isOrange if it is orange
     */

    public void setOrange(boolean isOrange){
        this.isOrange = isOrange;
    }
}
