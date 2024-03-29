package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile on screen
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768
    final int screenHeight = tileSize * maxScreenRow; // 576
    final int fps = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null) {

            long currentTime = System.nanoTime();
            //System.out.println("game loop is running");

            // 1 UPDATE: update information like character positions
            update();
            // 2 DRAW: draw the screen
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                // sleep only takes milliseconds, so we convert it
                Thread.sleep((long) remainingTime/1000000);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void update() {

        if (keyH.upPressed) {
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed) {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        ((Graphics2D)g).setColor(Color.white);

        ((Graphics2D)g).fillRect(playerX, playerY, tileSize, tileSize);

        ((Graphics2D)g).dispose();
    }
}
