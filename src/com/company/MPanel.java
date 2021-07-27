package com.company;

import javax.imageio.ImageIO;
import javax.lang.model.util.ElementScanner6;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MPanel extends JPanel implements KeyListener, ActionListener {
    private String imagePath = "Image/";
    private ImageIcon title;
    private ImageIcon body;
    private ImageIcon right;
    private ImageIcon left;
    private ImageIcon up;
    private ImageIcon down;
    private ImageIcon food;
    Timer timer;

    boolean isFailed = false;
    int len = 3;
    //分数
    int score = 0;
    int speed;
    //蛇的长度
    int[] snakex = new int[750];//蛇的x坐标
    int[] snakey = new int[750];//蛇的y坐标

    String fx = "R";//定义方向，R,L,U,D
    boolean isStart = false;

    int foodx;
    int foody;
    Random random = new Random();

    public MPanel() {
        loadImage();
        initSnake();
        timer = new Timer(speed, this);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
//        playBGM();
    }

    private void playBGM() {
        try {
            Clip bgm = AudioSystem.getClip();
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Sound/bgm.wav");
            BufferedInputStream buffer = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buffer);
            bgm.open(ais);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);
        title.paintIcon(this, g, 25, 11);
        g.fillRect(25, 75, 850, 600);

        //显示分数
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 25));
        g.drawString("Score: " + score, 700, 50);

        if (fx == "R") {
            right.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fx == "L") {
            left.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fx == "U") {
            up.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fx == "D") {
            down.paintIcon(this, g, snakex[0], snakey[0]);
        }

        if (isStart == false) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press Space to Start Games", 200, 350);

        }
        if (isFailed == true) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Game Over !", 350, 350);
            g.drawString("Your score is " + score, 325, 300);
        }

        //蛇的身体
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }
        food.paintIcon(this, g, foodx, foody);

        timer.setDelay(speed);
    }

    //初始化蛇的身体和头
    public void initSnake() {
        len = 3;
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
        foodx = 25 + 25 * random.nextInt(34);
        foody = 75 + 25 * random.nextInt(24);
        fx = "R";
        score = 0;
        speed = 300;
    }

    private void loadImage() {
        InputStream is;

        try {
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "title.jpg");
            title = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "body.png");
            body = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "right.png");
            right = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "left.png");
            left = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "up.png");
            up = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "down.png");
            down = new ImageIcon(ImageIO.read(is));
            is = this.getClass().getClassLoader().getResourceAsStream(imagePath + "food.png");
            food = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //键盘按下事件
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFailed) {
                isFailed = false;
                initSnake();

            } else {
                isStart = !isStart;
            }
            repaint();
        } else if (keyCode == KeyEvent.VK_UP) {
            if (fx != "D") {
                fx = "U";
            }
            repaint();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (fx != "U") {
                fx = "D";
            }
            repaint();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (fx != "R") {
                fx = "L";
            }
            repaint();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (fx != "L") {
                fx = "R";
            }
            repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isStart & !isFailed) {

        for (int i = len - 1; i > 0; i--) {
            snakex[i] = snakex[i - 1];
            snakey[i] = snakey[i - 1];
        }

        if (fx == "R") {
            snakex[0] = snakex[0] + 25;
            if (snakex[0] > 850) {
                snakex[0] = 25;
            }
        }
            else if (fx == "L") {
                snakex[0] = snakex[0] - 25;
                if (snakex[0] < 25) {
                    snakex[0] = 850;
                }
            } else if (fx == "U") {
                snakey[0] = snakey[0] - 25;
                if (snakey[0] < 75) {
                    snakey[0] = 650;
                }
            } else if (fx == "D") {
                snakey[0] = snakey[0] + 25;
                if (snakey[0] > 650) {
                    snakey[0] = 75;
                }
            }
        //吃到食物
        if (snakex[0] == foodx && snakey[0] == foody) {
//            len++;
            ++len;
            snakey[len-1] = snakey[len-2];
            snakex[len-1] = snakex[len-2];
            score += 10;
            if (speed >= 200 && speed <=300){
                speed -= 10;
            }
            if (speed <=200 && speed >=100 ){
                speed-=5;
            }
            if (speed <=100&&speed>=10){
                speed -=2;
            }

            foodx = 25 + 25 * random.nextInt(34);
            foody = 75 + 25 * random.nextInt(24);
        }

            for (int i = 1; i < len; i++) {
                if (snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
                    isFailed = true;
                }
            }

        repaint();
        }

        timer.start();
    }
}
