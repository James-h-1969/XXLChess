package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.io.*;
import java.util.*;
import java.lang.Math;

public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;

    public static final int FPS = 60;
	
    public String configPath;

    public Board Board;
    public Pieces Pieces;
    public Timer Timer;
    public Computer Computer;

    public boolean whiteMove;
    public String playerColour;

    public Movement Movement;

    public GameLogic GameLogic;

    public boolean twoPlayerOn;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        //read the json file
        JSONObject conf = loadJSONObject(new File(this.configPath));
        JSONObject timeControls = conf.getJSONObject("time_controls");
        JSONObject playerJSON = timeControls.getJSONObject("player");
        JSONObject computerJSON = timeControls.getJSONObject("cpu");
        
        String levelFile = conf.getString("layout");

        int playerSeconds = playerJSON.getInt("seconds");
        int playerIncrement = playerJSON.getInt("increment");

        int computerSeconds = computerJSON.getInt("seconds");
        int computerIncrement = computerJSON.getInt("increment");

        this.playerColour = conf.getString("player_colour");
        int maxMovementTime = conf.getInt("max_movement_time");
        double pieceMovementSpeed = conf.getDouble("piece_movement_speed");

        this.twoPlayerOn = conf.getBoolean("two_player");


        this.Board = new Board(this); //create the board

        this.Movement = new Movement(maxMovementTime, pieceMovementSpeed);

        this.Timer = new Timer(playerSeconds, computerSeconds, this, this.playerColour, playerIncrement, computerIncrement); //create the timer

        this.Pieces = new Pieces(this, this.Board, this.playerColour, this.Movement, this.Timer); //create the pieces
        this.Pieces.setDefaultPositions(levelFile);

        this.Computer = new Computer(this.Pieces, this.Board, this);
        

        this.whiteMove = true;
        //this.checkMate = false;

        //font setup
        PFont myFont = this.createFont("Ariel", 32);
        this.textFont(myFont);

        this.GameLogic = new GameLogic(this.Pieces, this.Board, this, this.Timer, this.playerColour, this.twoPlayerOn, this.Movement);
        
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(KeyEvent e){
        int keycode = e.getKeyCode();
        //trigger the reset
        if (keycode == 82) { // If the "r" key is pressed and the sketch is paused
            System.out.println("Resetting the board. This might take a second.");
            this.setup();
            System.out.println("Reset complete.");
        }
        // if (key == 'b'){
        //     this.Pieces.printPieces();
        // }
        if (keycode == 27){
            key = 0;
            this.GameLogic.setPlayerForfeit();
            
        }
        if (keycode == 40){
            this.Computer.makeMove();
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
    }


    @Override
    public void mousePressed(MouseEvent e) {  
        if (!this.Movement.getIsMoving()){
            //get the current pixel position of the mouse
            int x = e.getX();
            int y = e.getY();
      
            //convert to the grid position
            int column = x/48;
            int row = y/48;

            if (column < 14 && row < 14){
                this.GameLogic.mousePressedGameLogic(row, column);
            }
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        if (!this.Timer.isAnyTimeZero() && !this.GameLogic.getCheckMate() && !this.GameLogic.getPlayerForfeit() && !this.GameLogic.isInStaleMate()){
            if (!this.GameLogic.getPlayer1Move() && !this.twoPlayerOn){
                this.GameLogic.setPlayer1Move(this.Timer.computerTimer(FPS, this.Computer));
            }
            if (this.GameLogic.isKingFlashing()){
                this.GameLogic.setFlashingKing(this.Board.flashKing(this.Pieces.getKingCheckRow(), this.Pieces.getKingCheckCol()));
            }
            
            Timer.tick();

            Pieces.tickPieces(this.GameLogic);

            background(150, 150, 150); //background

            Board.drawBoard(); //draw the board

            Pieces.drawPieces();

            Timer.drawTimer();

            if (this.GameLogic.isKingFlashing()){
                textSize(15);
                fill(255);
                PFont myFont = this.createFont("Ariel", 16);
                this.textFont(myFont);
                text("    You must \n   defend your \n        king    ", 680, 350);
            }


            if (this.Pieces.isInCheck() && !this.GameLogic.getCheckMate()){
                textSize(15);
                fill(255);
                PFont myFont = this.createFont("Ariel", 20);
                this.textFont(myFont);
                text("    Check!    ", 680, 300);
            }

        } else {
            textSize(15);
            fill(255);
            if (Timer.isAnyTimeZero()){
                if (Timer.isTime1Zero()){
                    text("    You lost on \n         time", 680, 300);
                } else {
                    text("    You won on \n         time", 680, 300);
                }
  
                text("    Press 'r' to \nrestart the game", 680, 400);

            }
            if (this.GameLogic.getCheckMate()){
                if (!this.GameLogic.getShowingCheckMateHelpers()){
                    this.Pieces.showCheckmateHelpers(this.GameLogic.getLosingColour(), this.GameLogic.getLosingKingRow(), this.GameLogic.getLosingKingCol());
                    this.GameLogic.setShowingCheckMateHelpers();
                }
                
                if (this.GameLogic.getLosingColour().equals(this.playerColour)){
                    text("    You lost by \n    Checkmate", 680, 300);
                } else {
                    text("    You won by \n    Checkmate", 680, 300);
                }
                
                text("    Press 'r' to \nrestart the game", 680, 400);
            } else if (this.GameLogic.isInStaleMate()){
                text("   Stalemate \n     - draw ", 680, 300);
                text("    Press 'r' to \nrestart the game", 680, 400);
            }
            if (this.GameLogic.getPlayerForfeit()){
                text("  You resigned ", 680, 300);
                text("    Press 'r' to \nrestart the game", 680, 400);
            }
            
        }
    }



	// Add any additional methods or attributes you want. Please put classes in different files.

    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }



}
