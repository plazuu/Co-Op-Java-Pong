import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Set; 
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

class GameScreen {

    private GameScreen name;

    GameScreen() {
        this.name = this;
    }

    private JFrame coopFrame; 
    private Font gameFont;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Random random = new Random();

    private Block playing;
    private int playerOneX = 100;
    private int playerOneY = 300;

    private int playerTwoX = 1700;
    private int playerTwoY = 300;

    public void go() {
        coopFrame = new JFrame("PONG -- Co Op -- 1 v 1");
        Screen.setWindow(coopFrame);
        coopFrame.setBackground(Color.black);
        gameFont = Screen.initFont();

        playing = new Block(playerOneX, playerOneY, playerTwoX, playerTwoY, 5);
        coopFrame.getContentPane().add(playing);

        KeyWatcher playingListener = new KeyWatcher(playing); 
        coopFrame.addKeyListener(playingListener);
        name.startGameLoop();
    }


    public void startGameLoop() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                update(); 
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10);
    }
    public void update() {


        if(playing.getBallY() <= playing.getArenaY()) {
            playing.setVertical(1);
        }
        if(playing.getBallY() + playing.getBallHeight() >= playing.getArenaY() + playing.getArenaHeight()) {
            playing.setVertical(0);
        }

        //instead of hard coding put blovk with for 80 
        //and hiehgt for 200 
        if((playing.getBallX() >= playing.playerX  &&
                    playing.getBallX() <= playing.playerX + playing.getPlayerWidth()) &&
                ((playing.getBallY() >= playing.getPlayerY()) &&
                (playing.getBallY() <= playing.getPlayerY() + playing.getPlayerHeight()))) {
            playing.setBallSpeed(playing.getBallSpeed() + 1);
            playing.setHorizontal(1);
        }

        if((playing.getBallX() >= playing.playerTwoX &&
                    playing.getBallX() <= playing.playerTwoX + playing.getPlayerWidth()) &&
                ((playing.getBallY() >= playing.getPlayerTwoY()) &&
                (playing.getBallY() <= playing.getPlayerTwoY() + playing.getPlayerHeight()))) {
            playing.setBallSpeed(playing.getBallSpeed() + 1);
            playing.setHorizontal(0);
        }
       

        //basicall mean player one got a point
        if(playing.getBallX() >= 1920 + (100 * playing.getBallSpeed())) {
            playing.addPlayerPoints();
            playing.setBallX(980);
            playing.setBallY(540);
            playing.setBallSpeed(5);
            playing.setHorizontal(random.nextInt(3));

        }
        //player two got a point
        if(playing.getBallX() <= -60 - (100 * playing.getBallSpeed())) {
            playing.addPlayerTwoPoints();
            playing.setBallX(980);
            playing.setBallY(540);
            playing.setBallSpeed(5);
            playing.setHorizontal(random.nextInt(3));
        }
    
        

        if(playing.getHorizontal() == 1) {
            playing.setBallX(playing.getBallX() + playing.getBallSpeed());
        } else {
            playing.setBallX(playing.getBallX() - playing.getBallSpeed()); 
        }

        if(playing.getVertical() == 1) {
            playing.setBallY(playing.getBallY() + playing.getBallSpeed());
        } else {
            playing.setBallY(playing.getBallY() - playing.getBallSpeed()); 
        }
        playing.updateGame(pressedKeys); 
        coopFrame.repaint();
    }




    class Block extends JPanel{

        private int vertical;
        private int horizontal;

        public int getVertical() {
            return vertical; 
        } 
        public int getHorizontal() {
            return horizontal;
        } 

        public int getPlayerX() {
            return playerX; 
        } 
        public int getPlayerY() {
            return playerY;
        }

        public int getPlayerTwoX() {
            return playerTwoX; 
        } 
        public int getPlayerTwoY() {
            return playerTwoY;
        }

        public int getPlayerPoints(){
            return playerPoints;
        }

        public int getPlayerTwoPoints(){
            return playerTwoPoints;
        }

        public int getPlayerWidth(){
            return playerWidth;
        }

        public int getArenaY() {
            return arenaY;
        }

        public int getArenaHeight() {
            return arenaHeight;
        }
        
        public int getPlayerHeight(){
            return playerHeight;
        }

        public int getBallHeight(){
            return ballHeight;
        }

        public void addPlayerPoints(){
            playerPoints += 1;
        }

        public void addPlayerTwoPoints(){
            playerTwoPoints += 1;
        }

        public void setVertical(int vertical) {
            this.vertical = vertical;
        }
        public void setHorizontal(int horizontal) {
            this.horizontal = horizontal;
        }

        private int ballY = 540;
        private int ballX = 960;

        public int getBallY() {
            return ballY;
        }

        public int getBallX() {
            return ballX;
        }

        public int getBallSpeed() {
            return ballSpeed;
        }

        public void setBallY(int y) {
            this.ballY = y;
        }

        public void setBallX(int x) {
            this.ballX = x;
        }

        public void setBallSpeed(int x) {
            this.ballSpeed = x;
        }

        private int playerY; 
        private int playerX; 
        private int playerTwoY;
        private int playerTwoX;
        private int playerPoints = 0;
        private int playerTwoPoints = 0;
        private int ballSpeed;

        private final int playerWidth = 40;
        private final int playerHeight = 150;

        private final int arenaX = 50;
        private final int arenaY = 100;
        private final int arenaWidth = 1800;
        private final int arenaHeight = 850;

        private final int ballHeight = 20;

        private final int pointBoxWidth = 200;
        private final int pointBoxHeight = 50;

        Block (int playerX, int playerY, int playerTwoX, int playerTwoY, int ballSpeed) {
            this.playerY = playerY;
            this.playerX = playerX;
            this.playerTwoY = playerTwoY; 
            this.playerTwoX = playerTwoX;
            this.ballSpeed = ballSpeed;
            this.vertical = random.nextInt(3); 
            this.horizontal = random.nextInt(3);
        }

        public void paintComponent(Graphics g){
            g.setColor(Color.green);
            g.setFont(gameFont.deriveFont(Font.BOLD, 30));


            g.drawRect(arenaX, arenaY, arenaWidth, arenaHeight);

            g.drawRect(arenaX,30,pointBoxWidth, pointBoxHeight);
            g.drawString("P1", arenaX + pointBoxWidth + 10, 70);
            //
            //so basically do a switch (player point) then case 1 ( draw 1 rect for 1 point)
            //will win after you get three filling it up
            //
            //
            g.setColor(Color.white);

            switch(playerPoints){
                case 0:
                    break;
                case 1:
                    g.fillRect(arenaX + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    break;
                case 2:
                    g.fillRect(arenaX + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    //print two white boxes
                    break;
                case 3:
                    g.fillRect(arenaX + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + 5 + pointBoxWidth / 3 * 2, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.drawString("P1 Wins", arenaX + arenaWidth / 2 , arenaY + arenaHeight / 2);
                   //print 3 white boxes
                    break;
                case 4: 
                    g.fillRect(arenaX + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + 5 + pointBoxWidth / 3 * 2, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.drawString("P1 Wins", arenaX + arenaWidth / 2 , arenaY + arenaHeight / 2);

                    coopFrame.dispose();

                    Screen screen = new Screen();
                    screen.go();
                    break;
            }

            switch(playerTwoPoints){
                case 0:
                    break;
                case 1:
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    break;
                case 2:
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    //print two white boxes
                    break;
                case 3:
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5 + pointBoxWidth / 3 * 2, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.drawString("P2 Wins", arenaX + arenaWidth / 2 , arenaY + arenaHeight / 2);
                   //print 3 white boxes
                    break;
                case 4: 
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5 + pointBoxWidth / 3, 35, pointBoxWidth / 3  - 10, pointBoxHeight - 10);
                    g.fillRect(arenaX + arenaWidth - pointBoxWidth + 5 + pointBoxWidth / 3 * 2, 35, pointBoxWidth / 3 - 10, pointBoxHeight - 10);
                    g.drawString("P2 Wins", arenaX + arenaWidth / 2 , arenaY + arenaHeight / 2);

                    coopFrame.dispose();

                    Screen screen = new Screen();
                    screen.go();
                    break;
            }
            g.setColor(Color.green);
            g.drawRect(arenaX + arenaWidth - pointBoxWidth ,30,pointBoxWidth, pointBoxHeight);
            g.drawString("P2", arenaX + arenaWidth - pointBoxWidth - 120, 70);

            g.fillRect(playerX, playerY, playerWidth, playerHeight);
            g.fillRect(playerTwoX, playerTwoY, playerWidth, playerHeight); 

            g.fillOval(ballX, ballY, ballHeight, ballHeight);
        }

        public void updateGame(Set<Integer> pressedKeys) {

        if(pressedKeys.contains(KeyEvent.VK_W)) {
            if(playerY + playerHeight < arenaY + arenaHeight){
            playerY += 5;
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_S)) {
            if(playerY > arenaY){
            playerY -= 5;
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_UP)) {
            if(playerTwoY + playerHeight < arenaY + arenaHeight){
            playerTwoY += 5;
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_DOWN)) {
            if(playerTwoY > arenaY){
            playerTwoY -= 5;
            }
        }
    }
    }


    class KeyWatcher implements KeyListener{
        private Block player; 

        KeyWatcher (Block player) {
            this.player = player;
        }
        
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_W) { 
                pressedKeys.add(KeyEvent.VK_W);
            } else if (e.getKeyCode() == KeyEvent.VK_S) { 
                pressedKeys.add(KeyEvent.VK_S);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) { 
                pressedKeys.add(KeyEvent.VK_UP);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
                pressedKeys.add(KeyEvent.VK_DOWN);
            }
        }

        public void keyReleased(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_W) { 
                pressedKeys.remove(KeyEvent.VK_W);
            } else if (e.getKeyCode() == KeyEvent.VK_S) { 
                pressedKeys.remove(KeyEvent.VK_S);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) { 
                pressedKeys.remove(KeyEvent.VK_UP);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
                pressedKeys.remove(KeyEvent.VK_DOWN);
            }
        }

        public void keyTyped(KeyEvent e){ }

    }
}
