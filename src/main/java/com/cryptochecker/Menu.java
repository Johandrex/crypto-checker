package com.cryptochecker;

import java.awt.*;
import java.awt.event.*; // events when interacting with components, such as buttons
import javax.swing.*;
import javax.swing.border.Border;

public class Menu {
    public JPanel panel;
    
    private final int topButtons = 3;
    private final int bottomButtons = 2;

    private final Border borderButtonTop = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
    private final Border borderButtonBottom = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);

    public Menu() {
        // buttons top
        JButton bCoin = Main.gui.getButtonTemplate("Coin Data");
        bCoin.addActionListener(new bCoinListener());
        bCoin.setBorder(borderButtonTop);

        JButton bPortfolio = Main.gui.getButtonTemplate("Portfolio");
        bPortfolio.addActionListener(new bPortfolioListener());
        bPortfolio.setBorder(borderButtonTop);

        JButton bConverter = Main.gui.getButtonTemplate("Converter");
        bConverter.addActionListener(new bConverterListener());
        bConverter.setBorder(borderButtonTop);

        // buttons bottom
        JButton bSettings = Main.gui.getButtonTemplate("Settings");
        bSettings.addActionListener(new bSettingsListener());
        bSettings.setBorder(borderButtonBottom);

        JButton bExit = Main.gui.getButtonTemplate("Exit");
        bExit.addActionListener(new bExitListener());
        bExit.setBorder(borderButtonBottom);
        
        // Menu Layout
        panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Button Panel 1
        JPanel button1Panel = new JPanel();
        button1Panel.setLayout(new BoxLayout(button1Panel, BoxLayout.Y_AXIS));
        button1Panel.setPreferredSize(new Dimension(Main.panelWidth,(topButtons*Main.panelHeight))); // buttons * 30 = height for button1Panel
        button1Panel.add(bCoin);
        button1Panel.add(bPortfolio);
        button1Panel.add(bConverter);

        // Button Panel X
        JPanel buttonXPanel = new JPanel();
        buttonXPanel.setLayout(new BorderLayout());
        buttonXPanel.setBackground(Color.GRAY);

        // Button Panel 2
        JPanel button2Panel = new JPanel();
        button2Panel.setLayout(new BoxLayout(button2Panel, BoxLayout.Y_AXIS));
        button2Panel.setPreferredSize(new Dimension(Main.panelWidth,(bottomButtons*Main.panelHeight))); // buttons * 30 = height for button2Panel
        button2Panel.add(bSettings);
        button2Panel.add(bExit);

        panel.add(button1Panel);
        panel.add(buttonXPanel); // Divide panel 1 with panel 2
        panel.add(button2Panel);
    }

    private class bCoinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Page Coin Clicked");

            Main.frame.getContentPane().add(BorderLayout.CENTER, Main.gui.panelCoin.panel);

            Main.gui.panelCoin.panel.setVisible(true);
            Main.gui.panelPortfolio.panel.setVisible(false);
            Main.gui.panelConverter.panel.setVisible(false);
            Main.gui.panelSettings.panel.setVisible(false);
        }
    }

    private class bPortfolioListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Page Portfolio Pressed");

            Main.frame.getContentPane().add(BorderLayout.CENTER, Main.gui.panelPortfolio.panel);

            Main.gui.panelCoin.panel.setVisible(false);
            Main.gui.panelPortfolio.panel.setVisible(true);
            Main.gui.panelConverter.panel.setVisible(false);
            Main.gui.panelSettings.panel.setVisible(false);
        }
    }

    private class bConverterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Page Converter Pressed");

            Main.frame.getContentPane().add(BorderLayout.CENTER, Main.gui.panelConverter.panel);

            Main.gui.panelCoin.panel.setVisible(false);
            Main.gui.panelPortfolio.panel.setVisible(false);
            Main.gui.panelConverter.panel.setVisible(true);
            Main.gui.panelSettings.panel.setVisible(false);
        }
    }

    private class bSettingsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Page Settings Pressed");

            Main.frame.getContentPane().add(BorderLayout.CENTER, Main.gui.panelSettings.panel);

            Main.gui.panelCoin.panel.setVisible(false);
            Main.gui.panelPortfolio.panel.setVisible(false);
            Main.gui.panelConverter.panel.setVisible(false);
            Main.gui.panelSettings.panel.setVisible(true);
        }
    }

    private class bExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Exit Pressed");
            Debug.log("-- Shutting down..");
            Main.frame.setVisible(false);
            System.exit(0);
        }
    }
}
