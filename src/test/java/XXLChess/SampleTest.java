package XXLChess;

import processing.core.PApplet;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import javax.lang.model.type.NullType;

public class SampleTest {


    ///   APP TESTING  ///

    // Test when the powerUp is active
    @Test
    public void testPowerUp1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins
        //assertEquals(expected, app.powerUp(500));
    }


    @Test
    public void testpiecsFun5() {
        App app  = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1100); // to give time to initialise stuff before drawing begins

        
        //move the pawn
        MouseEvent e = new MouseEvent(app, 0, 0, 0, 11*48, 11*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000); // to give time to initialise stuff before drawing begins

        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(1100);

        //black moves their castle
        e = new MouseEvent(app, 0, 0, 0, 4*48, 11*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 4*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);

        //pawn blocks
        e = new MouseEvent(app, 0, 0, 0, 2*48, 13*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 2*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);

        //castle takes pawn
        e = new MouseEvent(app, 0, 0, 0, 4*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 4*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 2*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);

        //king wrong move
        e = new MouseEvent(app, 0, 0, 0, 0*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 0*48, 13*48, 1, 0);
        app.mousePressed(e);
        app.delay(3000);

        //pawn wrong move
        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 12*48, 9*48, 1, 0);
        app.mousePressed(e);
        app.delay(3000);


        //get out of check
        e = new MouseEvent(app, 0, 0, 0, 0*48, 12*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 0*48, 11*48, 1, 0);
        app.mousePressed(e);
        app.delay(2000);

        //black castling
        e = new MouseEvent(app, 0, 0, 0, 7*48, 0*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 3*48, 0*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);

        //white pawnmove
        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 11*48, 9*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);

        //bishop checkmate
        e = new MouseEvent(app, 0, 0, 0, 0*48, 7*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 2*48, 9*48, 1, 0);
        app.mousePressed(e);
        app.delay(2000);

    }

    @Test
    public void testComputer() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins
        PApplet applet = new PApplet();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));;
        app.delay(1000);
    }

    @Test
    public void testForfeit() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins
        PApplet applet = new PApplet();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 27));;
        app.delay(1000);
    }

    @Test
    public void testRest() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins
        PApplet applet = new PApplet();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 82));;
        app.delay(1000);
    }

    @Test
    public void Time2Zero() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins

        //move the pawn
        MouseEvent e = new MouseEvent(app, 0, 0, 0, 11*48, 11*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000); // to give time to initialise stuff before drawing begins

        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(180000);
    }

    @Test
    public void testCastling() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins

        //move the pawn
        MouseEvent e = new MouseEvent(app, 0, 0, 0, 11*48, 11*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000); // to give time to initialise stuff before drawing begins

        e = new MouseEvent(app, 0, 0, 0, 11*48, 10*48, 1, 0);
        app.mousePressed(e);
        app.delay(180000);

        //black castling
        e = new MouseEvent(app, 0, 0, 0, 7*48, 0*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);
        e = new MouseEvent(app, 0, 0, 0, 10*48, 0*48, 1, 0);
        app.mousePressed(e);
        app.delay(1000);


    }



}



// gradle run
// gradle test
// gradle jacocoTestReport

