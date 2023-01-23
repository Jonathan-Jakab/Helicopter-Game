package org.csc133.a0;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context){
    }
    
    public void start() {

        if (current != null) {
            current.show();
            return;
        }
        new Game();
    }

    public void stop(){
    }
    
    public void destroy(){
    }
}



class Game extends Form implements Runnable{

    private GameWorld gw;

    public enum State {
        RUNNING,
        PAUSE
    }

    // get display width and height
    //
    final static int DISP_W = Display.getInstance().getDisplayWidth();
    final static int DISP_H = Display.getInstance().getDisplayHeight();

    // get dimensions for horizontal and vertical layout
    //
    public static int getSmallDim() { return Math.min(DISP_W,DISP_H); }
    public static int getLargeDim() { return Math.max(DISP_W,DISP_H); }

    public Game(){

        gw = new GameWorld();

        addKeyListener('Q', (evt) -> gw.quit());
        addKeyListener('R', (evt) -> gw.newGameWorld());
        addKeyListener('d', (evt) -> gw.drink());
        addKeyListener('f', (evt) -> gw.fight());

        addKeyListener(-93, (evt -> gw.playerTurnLeft()));  //KeyCode for left arrow button VK_Left
        addKeyListener(-94, (evt -> gw.playerTurnRight()));  //KeyCode for right arrow button VK_Right
        addKeyListener(-91, (evt -> gw.playerAccelerate())); //KeyCode for up arrow button VK_Up
        addKeyListener(-92, (evt -> gw.playerDecelerate())); //KeyCode for down arrow button VK_Down

        UITimer timer = new UITimer(this);
        timer.schedule(100, true, this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();
    }

    @Override
    public void paint(Graphics g){

        super.paint(g);
        gw.draw(g);
    }

    @Override
    public void run(){

        gw.tick();
        repaint();
    }
}



class GameWorld {

    private CopyOnWriteArrayList<Fire> fires;
    private Helicopter helicopter;
    private Helipad helipad;
    private River river;
    private int NUM_FIRES;

    private boolean gameOver;

    private int fontSizeSmall = 32;
    private int fontSizeMedium = 50;
    private int fontSizeLarge = 60;

    public GameWorld() {

        init();
    }

    public void newGameWorld() {

        init();
    }

    private void init(){

        gameOver = false;

        helicopter = new Helicopter();
        helipad = new Helipad();
        river = new River();

        fires = new CopyOnWriteArrayList<>();

        NUM_FIRES = new Random().nextInt(4-3) + 3;

        for(int i = 0; i < NUM_FIRES; i++){
            fires.add(new Fire());
        }
    }

    //Commands for player
    //
    public void playerAccelerate() {

            if (helicopter.getSpeed() < 10) {
                helicopter.setSpeed(helicopter.getSpeed() + 1);
                System.out.println("Helicopter Speed: " + helicopter.getSpeed());
            }
        else {
            System.out.println("Currently traveling at Max Speed.");
        }
    }

    public void playerDecelerate() {

            if (helicopter.getSpeed() > 0) {
            helicopter.setSpeed(helicopter.getSpeed() - 1);
            System.out.println("Helicopter Speed: " + helicopter.getSpeed());
            }
        else {
            System.out.println("Currently Stopped.");
        }
    }

    public void playerTurnLeft() {

            helicopter.setDirection((helicopter.getDirection() - 1));
    }

    public void playerTurnRight() {

        helicopter.setDirection((helicopter.getDirection() + 1));
    }

    public void fight(){

        Rectangle r1 = helicopter.getBounds();

        for (Fire fire : fires) {

            Rectangle r2 = fire.getBounds();

            if (r1.intersects(r2)) {
                if(fire.getSize() - helicopter.getWater() < 1){
                    helicopter.setWater(0);
                    fires.remove(fires.indexOf(fire));
                    System.out.println("Fire Has Been Put Out.");
                }else {
                    fire.shrink(helicopter.getWater());
                    helicopter.setWater(0);
                    System.out.println("Payload Dropped!");
                }
            }else {
                System.out.println("Cannot fight fire as the Helicopter isn;t over a fire.");
            }
            System.out.println("Helicopter Water: " + helicopter.getWater());
        }
    }

    public void drink(){

        Rectangle r1 = helicopter.getBounds();
        Rectangle r2 = river.getBounds();

        if (r1.intersects(r2) && helicopter.getSpeed() <3){
            if(helicopter.getWater() < 100){
                helicopter.setWater(helicopter.getWater() + 10);
            }else{
                System.out.println("Cannot pick up more water. Helicopter is full");
            }
            System.out.println("Helicopter Water: " + helicopter.getWater());
        }
    }

     public void draw(Graphics g) {

         //Set The Standard Font In Game
         //
         Font fonts = Font.createTrueTypeFont("Blockletter", "Blockletter.ttf").derive(fontSizeSmall, Font.STYLE_PLAIN);
         Font fontm = Font.createTrueTypeFont("Blockletter", "Blockletter.ttf").derive(fontSizeMedium, Font.STYLE_PLAIN);
         Font fontl = Font.createTrueTypeFont("Blockletter", "Blockletter.ttf").derive(fontSizeLarge, Font.STYLE_PLAIN);
         g.setFont(fonts);

         //clear the screen and setup some basic constants
         //
         helipad.draw(g);
         river.draw(g);

         for (Fire fire : fires) {
             fire.draw(g);
         }

         helicopter.draw(g);
         helicopter.getDetails(g, fonts);

         for (Fire fire : fires) {
             fire.getDetails(g, fonts);
         }

         //End events
         //
         String gameWon = "YOU WON!";
         String gameLost = "GAME OVER!";
         String wonDesc = "You Have Put Out All The Fires :)";
         String lostDesc = "You Ran Out Of Fuel :(";
         String playAgain = "Play Again?";
         String score = "Score: " + helicopter.getFuelCount();
         String someOtherTime = "Some Other Time (Q)";
         String heckYeah = "Heck Yeah! (R)";

         int boxX = Game.DISP_W / 2;
         int boxY = Game.DISP_H / 4;



         if (helicopter.getFuelCount() < 1) {
             helicopter.setFuelCount(0);

             gameOver = true;
                 g.setColor(ColorUtil.GRAY);
                 g.fillRect(Game.DISP_W / 4, Game.DISP_H / 4, Game.DISP_W / 2, Game.DISP_H / 3, (byte) 200);

                 g.setColor(ColorUtil.WHITE);
                 g.setFont(fontl);
                 g.drawString(gameLost, boxX - fontl.stringWidth(gameLost) / 2, boxY + 50);
                 g.setFont(fonts);
                 g.drawString(wonDesc, boxX - fonts.stringWidth(wonDesc) / 2, boxY + 200);
                 g.drawString(playAgain, boxX - fonts.stringWidth(playAgain) / 2, boxY + 225);

                 g.setFont(fontm);
                 g.drawString(someOtherTime, Game.DISP_W / 4 + 50, boxY + 400);
                 g.drawString(heckYeah, 3 * Game.DISP_W / 4 - fontm.stringWidth(heckYeah) - 50, boxY + 400);
         }

         if (fires.isEmpty()) {
             Rectangle r1 = helicopter.getBounds();
             Rectangle r4 = helipad.getBounds();
             if (r1.intersects(r4)) {
                 gameOver = true;
             }

            if(gameOver) {
                g.setColor(ColorUtil.GRAY);
                g.fillRect(Game.DISP_W / 4, Game.DISP_H / 4, Game.DISP_W / 2, Game.DISP_H / 3, (byte) 200);

                g.setColor(ColorUtil.WHITE);
                g.setFont(fontl);
                g.drawString(gameWon, boxX - fontl.stringWidth(gameWon) / 2, boxY + 50);
                g.setFont(fonts);
                g.drawString(wonDesc, boxX - fonts.stringWidth(wonDesc) / 2, boxY + 200);
                g.drawString(playAgain, boxX - fonts.stringWidth(playAgain) / 2, boxY + 225);
                g.drawString(score, boxX - fonts.stringWidth(score) / 2, boxY + 250);

                g.setFont(fontm);
                g.drawString(someOtherTime, Game.DISP_W / 4 + 50, boxY + 400);
                g.drawString(heckYeah, 3 * Game.DISP_W / 4 - fontm.stringWidth(heckYeah) - 50, boxY + 400);
            }
         }
    }

    public void tick() {

        // Place game running code here.
        //Here is where the game mechanics and end game checks will be placed
        //
        if(gameOver == false){
        for (Fire fire : fires)
            if ((new Random().nextInt(600)) < 5) {
                fire.grow();
            }

        helicopter.depleteFuel();
        helicopter.update();
        }
    }

    public void quit() {

        Display.getInstance().exitApplication();
    }

}



class Fire{

    private Point location;
    private int size;
    private int fontSize = 10;

    public Fire(){

        size = new Random().nextInt(225 - 175) + 175;
        location = new Point(new Random().nextInt(Game.DISP_W), new Random().nextInt(Game.DISP_H / 4 * 3));

        if(location.getX() + size > Game.DISP_W){
            location.setX(Game.DISP_W - size);
        }
    }
    public Fire(Fire fire){

    }

    public void draw(Graphics g) {

        g.setColor(ColorUtil.MAGENTA);
        g.fillArc(location.getX(), location.getY(), size, size, 0, 360);
            }

    public int getSize() {
        return size;
    }

    public void setSize(int newSize) { size =  newSize; }

    public void grow(){
        location.setX(location.getX() - 2);
        location.setY(location.getY() - 2);

        size = getSize() + 4;
    }

    public void shrink(int newSize){
        location.setX(location.getX() + (newSize/2));
        location.setY(location.getY() + (newSize/2));

        size -= newSize;
    }

    public void getDetails(Graphics g, Font font){

        //Display Fire UI
        //
        String fireSize = "Size: " +  getSize();

        int fontX = location.getX() + (getSize() - font.stringWidth(fireSize)) / 2;
        int fontY = location.getY() + getSize();

        g.setColor(ColorUtil.WHITE);
        g.drawString(fireSize, fontX, fontY);
    }

    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getSize(), getSize());
    }
}



class Helicopter {

    private Point location;

    private int size;
    private int helicopterRadius;
    private int helicopterX;
    private int helicopterY;

    private double angle;
    private int direction;
    private int speed;

    private int waterCount;
    private int fuelCount;

    public Helicopter() {

        direction = 0;
        speed = 0;
        waterCount = 0;
        fuelCount = 25000;
        size = 60;

        //Create Helicopter Dot
        int centerX = Game.DISP_W / 2;
        int centerY = Game.DISP_H / 2;

        helicopterRadius = size / 2;

        helicopterX = centerX - helicopterRadius;
        helicopterY = Game.DISP_H - (150 / 2 * 3 + helicopterRadius);

        location = new Point(helicopterX, helicopterY);
    }

    public void draw(Graphics g) {

        //Draw Helicopter Dot
        //
        g.setColor(ColorUtil.YELLOW);
        g.fillArc(location.getX(), location.getY(), size, size, 0, 360);

        //Draw Helicopter Line
        //
        helicopterX = location.getX();
        helicopterY = location.getY();

        final int TICK_LENGTH = helicopterRadius * 2;

        int hAngle = ((360 / 24 * getDirection()) - 90);  //helicopter.getDirection()
        int hRadius = helicopterRadius + TICK_LENGTH;

        angle = Math.toRadians(hAngle);

        double eX = (helicopterX + helicopterRadius) + hRadius * Math.cos(angle);
        double eY = (helicopterY + helicopterRadius) + hRadius * Math.sin(angle);
        double sX = helicopterX + helicopterRadius;
        double sY = helicopterY + helicopterRadius;

        g.setColor(ColorUtil.YELLOW);
        g.drawLine((int) sX, (int) sY, (int) eX, (int) eY);
    }

        public int getSize(){ return size; }

        public void setSize(int newSize){
        size = newSize;
    }

        public int getDirection () {
            return direction;
        }

        public void setDirection (int d){
        direction = d;
        }

        public int getSpeed () {
            return speed;
        }

        public void setSpeed (int v){
            speed = v;
        }

        public int getWater () {
            return waterCount;
        }

        public void setWater (int water){
            waterCount = water;
        }

        public int getFuelCount() {
            return fuelCount;
        }

        public void setFuelCount (int fuel){
            fuelCount = fuel;
        }

        public void depleteFuel(){
            int depleteFuel = (int)Math.pow(getSpeed(), 2) + 5;
            setFuelCount(getFuelCount() - depleteFuel);
        }

    public void update(){
        location.setX((int) (location.getX() + (Math.cos(angle) * getSpeed())*2));
        location.setY((int) (location.getY() + (Math.sin(angle) * getSpeed())*2));

        if ((location.getX() > (Game.DISP_W - size)) || (location.getY() > (Game.DISP_H - size)) || (location.getX() < 0) || (location.getY() < 0)) {
            setDirection(getDirection() + 180);
        }
    }

    public void getDetails(Graphics g, Font font){
        //Display Helicopter UI
        //
        String score = "Fuel: " + getFuelCount();
        String water = "Water: " + getWater();

        int fontX1 = location.getX() + (helicopterRadius*2 - font.stringWidth(score)) / 2;;
        int fontY1 = location.getY() + helicopterRadius * 3;
        int fontX2 = location.getX() + (helicopterRadius*2 - font.stringWidth(water)) / 2;;
        int fontY2 = location.getY() + helicopterRadius * 4;

        g.drawString(score, fontX1, fontY1);
        g.drawString(water, fontX2, fontY2);
    }

        public Rectangle getBounds() { return new Rectangle(location.getX(), location.getY(), getSize(), getSize()); }

}



    class Helipad {

        private Point location;

        private int centerX;
        private int centerY;

        private int helipadWidth;
        private int helipadRadius;
        private int helipadX;
        private int helipadY;

        public Helipad() {

            centerX = Game.DISP_W / 2;
            centerY = Game.DISP_H / 2;

            helipadWidth = 150;
            helipadRadius = helipadWidth / 2;

            helipadX = centerX - helipadRadius;
            helipadY = (Game.DISP_H - helipadWidth * 2);

            location = new Point(helipadX, helipadY);
        }

        public void draw(Graphics g) {

            //Draw Helipad
            //
            g.setColor(ColorUtil.GRAY);
            g.drawArc(helipadX, helipadY, helipadWidth, helipadWidth, 0, 360);
            g.drawRect(helipadX - 20, helipadY - 20, helipadWidth + 40, helipadWidth + 40, 5);
        }

        public Rectangle getBounds() { return new Rectangle(location.getX(), location.getY(), helipadWidth, helipadWidth); }
    }



    class River {

        private Point location;
        private int size;
        private int riverWidth;

        public River() {

            size = Game.DISP_H;
            riverWidth = Game.DISP_W;

            location = new Point(0, size / 6);
        }

        void draw(Graphics g) {

            //Draw River
            //
            g.setColor(ColorUtil.BLUE);
            g.fillRect(location.getX(), location.getY(), Game.DISP_W, size / 10);
        }

        public Rectangle getBounds() {
            return new Rectangle(location.getX(), location.getY(), riverWidth, size/10);
        }
    }