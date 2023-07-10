package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;

public class Timer {
    /**
     * time 1 as a string
     */
    private String Time1;
    /**
     * integer of time 1 minutes
     */
    private int Time1mins;
    /**
     * integer of time 1 seconds
     */
    private int Time1secs;
    /**
     * string of time 2
     */
    private String Time2;
    /**
     * integer of time 2 minutes
     */
    private int Time2mins;
    /**
     * integer of time 2 seconds
     */
    private int Time2secs;
    /**
     * FPS timer 1
     */
    private int timer1;
    /**
     * FPS timer 2
     */
    private int timer2;
    /**
     * has the time started
     */
    private boolean Started;
    /**
     * are we counting ontime1
     */
    private boolean onTime1;
    /**
     * the parent app
     */
    private PApplet parent;
    /**
     * timer for the computer move
     */
    private int computerMoveTimer;
    /**
     * player colour for timer 1
     */
    private String timer1Colour;
    /**
     * increment for the player
     */
    private int playerIncrement;
    /**
     * increment for the computer/player 2
     */
    private int computerIncrement;
    /**
     * the players colour
     */
    private String playerColour;

    /**
     * Constructor for the timer class
     * @param secondsTim1 the amount of seconds for the first timer
     * @param secondsTim2 the amount of seconds for the second timer
     * @param parent the parent app
     * @param timer1Colour the timer 1 colour
     * @param playerIncrement the amount of time added after every player mvoe
     * @param computerIncrement the amount of time added after every computer move
     */
    public Timer(int secondsTim1, int secondsTim2, PApplet parent, String timer1Colour, int playerIncrement, int computerIncrement){
        this.Time1mins = secondsTim1/60;
        this.Time1secs = secondsTim1 % 60;
        this.Time2mins = secondsTim2/60;
        this.Time2secs = secondsTim2 % 60;
        this.timer1 = 0;
        this.timer2 = 0;
        this.computerMoveTimer = 0;
        this.adjustTimes();
        this.Started = false;
        this.onTime1 = true;
        this.parent = parent;
        this.timer1Colour = timer1Colour;
        this.playerIncrement = playerIncrement;
        this.computerIncrement = computerIncrement;
        this.playerColour = playerColour;
    }
    /**
     * tick the clocks by 1
     */
    public void tick(){
        if (this.Started){
            if (this.Time1secs >= 60){
                this.Time1secs -= 60;
                this.Time1mins++;
            }
            if (this.Time2secs >= 60){
                this.Time2secs -= 60;
                this.Time2mins++;
            }
            if (this.onTime1){
                this.timer1++;
                if (this.timer1 == 60){
                    this.Time1secs--;
                    if (this.Time1secs == -1){
                        this.Time1mins--;
                        this.Time1secs = 59;
                    }
                    this.timer1 = 0;
                }
            } else {
                this.timer2++;
                if (this.timer2 == 60){
                    this.Time2secs--;
                    if (this.Time2secs == -1){
                        this.Time2mins--;
                        this.Time2secs = 59;
                    }
                    this.timer2 = 0;
                }
            }
            this.adjustTimes();
        }
    }
    /**
     * set the clock as started
     */
    public void setStarted(){
        this.Started = true;
    }
    
    /**
     * set on time 1
     * @param onTime1 whether on time1 or not
     */
    public void setOnTime1(boolean onTime1){
        this.onTime1 = onTime1;
    }

    /**
     * get ont time 1
     * @return if on time 1
     */
    public boolean getOntime1(){
        return this.onTime1;
    }
    /**
     * draws the timer onto the screen
     */
    public void drawTimer(){
        parent.textSize(30);
        parent.fill(255);
        parent.text(this.Time2, 700, 45);
        parent.text(this.Time1, 700, 650);
    }
    /**
     * adjusts the times from integer to string format
     */
    public void adjustTimes(){
        if (this.Time1secs / 10 != 0){
                this.Time1 = this.Time1mins + ":" + this.Time1secs;
        } else {
            this.Time1 = this.Time1mins + ":0" + this.Time1secs;
        }
        if (this.Time2secs / 10 != 0){
            this.Time2 = this.Time2mins + ":" + this.Time2secs;
        } else {
            this.Time2 = this.Time2mins + ":0" + this.Time2secs;
        }
    }
    /**
     * finds whether any of the times are 0
     * @return if any time is zero
     */
    public boolean isAnyTimeZero(){
        if (this.Time1.equals("0:00") || this.Time1.equals("0:00")){
            return true;
        }
        return false;
    }
    /**
     * returns whether time 1 is 0
     * @return if time 1 is 0
     */
    public boolean isTime1Zero(){
        if (this.Time1.equals("0:00")){
            return true;
        } else {
            return false;
        }
    }
    /**
     * returns whether time 2 is zero
     * @return if time 2 is zero
     */
    public boolean isTime2Zero(){
        if (this.Time2.equals("0:00")){
            return true;
        } else {
            return false;
        }
    }
    /**
     * waits for 2 seconds and then makes computer move
     * @param FPS the frames per second
     * @param computer the computer class
     * @return if still on computer move
     */
    public boolean computerTimer(int FPS, Computer computer){
        this.setOnTime1(false);
        this.computerMoveTimer++;
        if (this.computerMoveTimer == FPS * 2 + 1){
            computer.makeMove();
            this.computerMoveTimer = 0;           
            return true;
        }
        return false;
    }
    /**
     * gets the colour of the timer 1
     * @return the colour of whoever is using timer 1
     */
    public String getTimer1Colour(){
        return this.timer1Colour;
    }
    /**
     * increments the player time
     */
    public void incrementPlayerTime(){
        this.Time1secs += this.playerIncrement;
    }
    /**
     * increments the computer time
     */
    public void incrementComputerTime(){
        this.Time2secs += this.computerIncrement;
    }

}