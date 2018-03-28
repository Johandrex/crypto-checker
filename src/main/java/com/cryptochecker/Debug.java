package com.cryptochecker;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.io.*;
import javax.swing.*;

public class Debug {
    public static JFrame frame;
    public static boolean mode = false;
    private static JScrollPane contentPane;
    private static PrintWriter printWriter;
    private static JTextArea print = new JTextArea(0, 0);
    private static LocalDateTime time;

    public Debug() throws Exception {
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(Main.logLocation, true))); // second line in filewriter appends the line
        
        print.append("Program started up "+LocalDateTime.now()+"\n");
        printWriter.println("\nProgram started up "+LocalDateTime.now());
        printWriter.close();

        print.setEditable(true);

        contentPane = new JScrollPane(print);
        contentPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame = new JFrame("Debug Log");
        frame.setSize(610, 300);
        frame.setVisible(false);
        frame.add(contentPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Main.gui.panelSettings.debugFunction();
            }
        });
    }

    public static void log(String s) {
        time = LocalDateTime.now();
        String msg = time.getHour()+":"+time.getMinute()+":"+time.getSecond()+": "+s;

        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(Main.logLocation, true)));
            print.append(msg+"\n");
            contentPane.getVerticalScrollBar().setValue(contentPane.getVerticalScrollBar().getMaximum());
            printWriter.println(msg);
            printWriter.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }
}