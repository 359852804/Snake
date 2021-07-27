package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnakeFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(10,10,910,720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("贪吃蛇大战");
        frame.add(new MPanel());
        frame.setVisible(true);

    }
}