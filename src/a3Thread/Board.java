package a3Thread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel implements Runnable {

    private Image star;
    private Thread animator;
    private int x, y;

    private final int DELAY = 50;


    public Board() {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/res/star.png"));
        star = ii.getImage();

        x = y = 10;
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void cycle() {

        x += 1;
        y += 1;

        if (y > 240) {
            y = -45;
            x = -45;
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}