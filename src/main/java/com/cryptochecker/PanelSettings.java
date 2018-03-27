package com.cryptochecker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class PanelSettings {
    public JPanel panel;

    private JButton bTheme;
    private JButton bCurrency;
    private JButton bDebug;

    private JFrame colorFrame;
    private JColorChooser colorChooser;
    private colorChangeListener changeListener;
    private JButton bColor4;
    private JButton bColor5;
    private JButton bColor6;
    private JButton bColor7;
    private JButton bColor8;
    private JButton bColor9;

    private JPanel topFilling;
    private JPanel bottomFilling;
    private JPanel middle;
    private JPanel middleContent;
    private JPanel content_1;
    private JPanel content1Filling;
    private JPanel content_2;
    private JPanel content2Filling;
    private JPanel content_3;

    private JTextField header1;
    private JTextField content1field;
    private JTextField content2field;
    private JTextField content3field;
    private JTextField header2;
    private JTextField content4field;
    private JTextField content5field;
    private JTextField content6field;
    private JTextField content7field;
    private JTextField content8field;
    private JTextField content9field;

    public PanelSettings() {
        panel = new JPanel();
        panel.setVisible(false);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // color chooser
        colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(new JPanel());

        // create color chooser frame
        colorFrame = new JFrame("Color Chooser");
        colorFrame.add(colorChooser);
        colorFrame.setVisible(false);
        colorFrame.setSize(650, 250);
        colorFrame.setLocationRelativeTo(null);

        topFilling = new JPanel();
        topFilling.setLayout(new BorderLayout());

        bottomFilling = new JPanel();
        bottomFilling.setLayout(new BorderLayout());

        middle = new JPanel();
        middle.setLayout(new FlowLayout());

        middleContent = new JPanel();
        middleContent.setLayout(new BoxLayout(middleContent, BoxLayout.Y_AXIS));

        content_1 = new JPanel();
        content_1.setLayout(new BoxLayout(content_1, BoxLayout.Y_AXIS));
        content_1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        content1Filling = new JPanel();
        content1Filling.setPreferredSize(new Dimension(0, 25));

        content_2 = new JPanel();
        content_2.setLayout(new BoxLayout(content_2, BoxLayout.Y_AXIS));
        content_2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        content2Filling = new JPanel();
        content2Filling.setPreferredSize(new Dimension(0, 25));

        content_3 = new JPanel();
        content_3.setLayout(new BoxLayout(content_3, BoxLayout.Y_AXIS));
        content_3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        // content header
        header1 = getFieldTemplate("Settings");
        header1.setHorizontalAlignment(JTextField.CENTER);
        header1.setFont(new Font("Helvetica", Font.BOLD, 15));
        header1.setPreferredSize(new Dimension(350, 30));
        header1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        content_1.add(header1);

        // content 1 - theme
        JPanel content1 = getPanelTemplate();
        
        content1field = getFieldTemplate("Theme");

        JPanel content1button = getButtonPanelTemplate();
        if (Main.theme.currentTheme == Main.themes.DARK) {
            bTheme = getButtonTemplate("Dark");
        } else if (Main.theme.currentTheme == Main.themes.LIGHT) {
            bTheme = getButtonTemplate("Light");
        } else {
            bTheme = getButtonTemplate("Custom");
        }
        bTheme.addActionListener(new bThemeListener());
        content1button.add(bTheme);

        content1.add(content1field);
        content1.add(content1button);
        content_1.add(content1);
        
        // content 2 - currency
        JPanel content2 = getPanelTemplate();
        
        content2field = getFieldTemplate("Currency");

        JPanel content2button = getButtonPanelTemplate();
        bCurrency = getButtonTemplate(Main.currency);
        bCurrency.addActionListener(new bCurrencyListener());
        content2button.add(bCurrency);

        content2.add(content2field);
        content2.add(content2button);
        content_1.add(content2);

        // content 3 - debug mode
        JPanel content3 = getPanelTemplate();
        
        content3field = getFieldTemplate("Debug Mode");

        JPanel content3button = getButtonPanelTemplate();
        if (Debug.mode) {
            bDebug = getButtonTemplate("On");
        } else {
            bDebug = getButtonTemplate("Off");
        }
        bDebug.addActionListener(new bDebugListener());
        content3button.add(bDebug);

        content3.add(content3field);
        content3.add(content3button);
        content_1.add(content3);

                // custom theme header
                header2 = getFieldTemplate("Custom Theme");
                header2.setHorizontalAlignment(JTextField.CENTER);
                header2.setFont(new Font("Helvetica", Font.BOLD, 15));
                header2.setPreferredSize(new Dimension(350, 30));
                header2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
                content_2.add(header2);

                // content 4 - color background
                JPanel content4 = getPanelTemplate();
        
                content4field = getFieldTemplate("Background");
        
                JPanel content4button = getButtonPanelTemplate();
                bColor4 = getButtonTemplate("");
                bColor4.setBackground(Main.theme.customBackground);
                bColor4.addActionListener(new colorListener(4));
                content4button.add(bColor4);
        
                content4.add(content4field);
                content4.add(content4button);
                content_2.add(content4);
                
                // content 5 - color foreground
                JPanel content5 = getPanelTemplate();
                
                content5field = getFieldTemplate("Font");
        
                JPanel content5button = getButtonPanelTemplate();
                bColor5 = getButtonTemplate("");
                bColor5.setBackground(Main.theme.customForeground);
                bColor5.addActionListener(new colorListener(5));
                content5button.add(bColor5);
        
                content5.add(content5field);
                content5.add(content5button);
                content_2.add(content5);
        
                // content 6 - color green
                JPanel content6 = getPanelTemplate();
                
                content6field = getFieldTemplate("Positive Font");
        
                JPanel content6button = getButtonPanelTemplate();
                bColor6 = getButtonTemplate("");
                bColor6.setBackground(Main.theme.customGreen);
                bColor6.addActionListener(new colorListener(6));
                content6button.add(bColor6);
        
                content6.add(content6field);
                content6.add(content6button);
                content_2.add(content6);

                // content 7 - color red
                JPanel content7 = getPanelTemplate();
        
                content7field = getFieldTemplate("Negative Font");
        
                JPanel content7button = getButtonPanelTemplate();
                bColor7 = getButtonTemplate("");
                bColor7.setBackground(Main.theme.customRed);
                bColor7.addActionListener(new colorListener(7));
                content7button.add(bColor7);
        
                content7.add(content7field);
                content7.add(content7button);
                content_2.add(content7);
                
                // content 8 - color selection
                JPanel content8 = getPanelTemplate();
                
                content8field = getFieldTemplate("Pressing Coin Font");
        
                JPanel content8button = getButtonPanelTemplate();
                bColor8 = getButtonTemplate("");
                bColor8.setBackground(Main.theme.customSelection);
                bColor8.addActionListener(new colorListener(8));
                content8button.add(bColor8);
        
                content8.add(content8field);
                content8.add(content8button);
                content_2.add(content8);
        
                // content 9 - color pane
                JPanel content9 = getPanelTemplate();
                
                content9field = getFieldTemplate("Empty Background");
        
                JPanel content9button = getButtonPanelTemplate();
                bColor9 = getButtonTemplate("");
                bColor9.setBackground(Main.theme.customEmptyBackground);
                bColor9.addActionListener(new colorListener(9));
                content9button.add(bColor9);
        
                content9.add(content9field);
                content9.add(content9button);
                content_2.add(content9);

                // content X1 - view logs
                JPanel content10 = getPanelTemplate();

                JPanel content10button = getButtonPanelTemplate();
                content10button.setLayout(new BorderLayout());
                JButton bColorApply = getButtonTemplate("Apply & Select");
                bColorApply.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
                bColorApply.addActionListener(new bColorApplyListener());
                content10button.add(bColorApply);

                content10.add(content10button);
                content_2.add(content10);

        // content X1 - view logs
        JPanel contentX1 = getPanelTemplate();

        JPanel contentX1button = getButtonPanelTemplate();
        contentX1button.setLayout(new BorderLayout());
        JButton bView = getButtonTemplate("View Logs");
        bView.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
        bView.addActionListener(new bViewListener());
        contentX1button.add(bView);

        contentX1.add(contentX1button);
        content_3.add(contentX1);

        // content X2 - reset settings
        JPanel contentX2 = getPanelTemplate();

        JPanel contentX2button = getButtonPanelTemplate();
        contentX2button.setLayout(new BorderLayout());
        JButton bReset = getButtonTemplate("Reset Settings");
        bReset.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        bReset.addActionListener(new bResetListener());
        contentX2button.add(bReset);

        contentX2.add(contentX2button);
        content_3.add(contentX2);

        // content X3 - delete
        JPanel contentX3 = getPanelTemplate();

        JPanel contentX3button = getButtonPanelTemplate();
        contentX3button.setLayout(new BorderLayout());
        JButton bDelete = getButtonTemplate("Delete Data");
        bDelete.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        bDelete.addActionListener(new bDeleteListener());
        contentX3button.add(bDelete);

        contentX3.add(contentX3button);
        content_3.add(contentX3);

        // finale
        middleContent.add(content_1);
        middleContent.add(content1Filling);
        middleContent.add(content_2);
        middleContent.add(content2Filling);
        middleContent.add(content_3);

        middle.add(middleContent);

        panel.add(topFilling);
        panel.add(middle);
        panel.add(bottomFilling);

        themeSwitch();
    }

    private JButton getButtonTemplate(String s) {
        JButton template = new JButton(s);
        template.setBackground(Color.GRAY);
        template.setForeground(Color.WHITE);
        template.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        template.setMinimumSize(new Dimension(75, 30));
        template.setMaximumSize(new Dimension(75, 30));
        template.setFocusable(false);
        
        return template;
    }

    private JPanel getButtonPanelTemplate() {
        JPanel template = new JPanel();
        template.setLayout(new BoxLayout(template, BoxLayout.Y_AXIS));
        template.setPreferredSize(new Dimension(75, 30));
        
        return template;
    }

    private JTextField getFieldTemplate(String s) {
        JTextField template = new JTextField(s);
        template.setEditable(false);
        template.setBackground(Color.WHITE);
        template.setHorizontalAlignment(JTextField.LEFT);
        template.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        template.setFont(new Font("Helvetica", Font.PLAIN, 14));
        
        return template;
    }

    private JPanel getPanelTemplate() {
        JPanel template = new JPanel();
        template.setLayout(new BoxLayout(template, BoxLayout.X_AXIS));
        template.setPreferredSize(new Dimension(350, 30));
        template.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
        template.setBackground(Color.GRAY);

        return template;
    }

    public void themeSwitch() {
        topFilling.setBackground(Main.theme.emptyBackground);
        bottomFilling.setBackground(Main.theme.emptyBackground);
        middle.setBackground(Main.theme.emptyBackground);
        content1Filling.setBackground(Main.theme.emptyBackground);
        content2Filling.setBackground(Main.theme.emptyBackground);

        header1.setBackground(Main.theme.background);
        header1.setForeground(Main.theme.foreground);
        content1field.setBackground(Main.theme.background);
        content1field.setForeground(Main.theme.foreground);
        content2field.setBackground(Main.theme.background);
        content2field.setForeground(Main.theme.foreground);
        content3field.setBackground(Main.theme.background);
        content3field.setForeground(Main.theme.foreground);
        header2.setBackground(Main.theme.background);
        header2.setForeground(Main.theme.foreground);
        content4field.setBackground(Main.theme.background);
        content4field.setForeground(Main.theme.foreground);
        content5field.setBackground(Main.theme.background);
        content5field.setForeground(Main.theme.foreground);
        content6field.setBackground(Main.theme.background);
        content6field.setForeground(Main.theme.foreground);
        content7field.setBackground(Main.theme.background);
        content7field.setForeground(Main.theme.foreground);
        content8field.setBackground(Main.theme.background);
        content8field.setForeground(Main.theme.foreground);
        content9field.setBackground(Main.theme.background);
        content9field.setForeground(Main.theme.foreground);
    }

    private void serialize() {
        try { // save settings to data
            FileOutputStream file = new FileOutputStream(Main.settingsSerLocation);
            BufferedOutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(Debug.mode);
            out.writeObject(Main.theme);
            out.writeObject(Main.currency);
            out.writeObject(Main.currencyChar);
            out.close();
            Debug.log("Serialized Settings To " + Main.settingsSerLocation);
        } catch(Exception ex) {
            Debug.log("EXCEPTION: PanelSettings.java - serialize()");
            ex.printStackTrace();
        }
    }

    private class bThemeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Theme Clicked");

            switch (Main.theme.currentTheme) {
                case LIGHT: // light -> dark
                    Debug.log("-- Switching to dark mode");
                    bTheme.setText("Dark");
                    Main.theme.change(Main.themes.DARK);
                    break;
                case DARK: // dark -> custom
                    Debug.log("-- Switching to custom mode");
                    bTheme.setText("Custom");
                    Main.theme.change(Main.themes.CUSTOM);
                    break;
                case CUSTOM: // custom -> light
                    Debug.log("-- Switching to light mode");
                    bTheme.setText("Light");
                    Main.theme.change(Main.themes.LIGHT);
                    break;
            }

            UIManager.put("OptionPane.background",Main.theme.emptyBackground);
            UIManager.put("Panel.background",Main.theme.emptyBackground);
            UIManager.put("OptionPane.messageForeground",Main.theme.foreground);

            Main.gui.panelSettings.themeSwitch(); // finish page you are on first
            Main.gui.panelCoin.themeSwitch();
            Main.gui.panelPortfolio.themeSwitch();
            Main.gui.panelConverter.themeSwitch();

            serialize();
        }
    }

    private class bCurrencyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Currency Pressed");
            Debug.log("-- Changing currency");

            String options[] = { "USD", "EUR", "GBP", "SEK", "AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "HKD", "HUF", "IDR", "ILS", "INR", "KRW", "JPY", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SGD", "THB", "TRY", "TWD", "ZAR" };
            String selectedValue = (String) JOptionPane.showInputDialog(Main.frame, "Select currency to use", "Currency", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (selectedValue == null) {
                Debug.log("-- cancel");
                return;
            } else {
                Debug.log("-- "+selectedValue);
            }

            bCurrency.setText(selectedValue);

            if (selectedValue == "USD") {
                Main.currencyChar = "$";
            } else if (selectedValue == "EUR") {
                Main.currencyChar = "€";
            } else if (selectedValue == "GBP") {
                Main.currencyChar = "£";
            } else {
                Main.currencyChar = "";
            }

            Main.currency = selectedValue;

            new WebData.RefreshCoins(); // refresh the coins

            serialize();
        }
    }

    public void debugFunction() {
        Debug.log("Button Debug Pressed");
        if (Debug.mode) {
            Debug.mode = false;
            Debug.frame.setVisible(false);
            Debug.log("-- Disabling Debug Mode");
            bDebug.setText("Off");
        } else {
            Debug.mode = true;
            Debug.frame.setVisible(true);
            Debug.log("-- Enabling Debug Mode");
            bDebug.setText("On");
        }

        serialize();
    }

    public class bDebugListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            debugFunction();
        }
    }

    private class bViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button View Logs Pressed");

            try {
                Desktop.getDesktop().open(new File(Main.logLocation));
            } catch(Exception ex) {
                Debug.log("EXCEPTION: PanelSettings.java - bViewListener.actionPerformed()");
                ex.printStackTrace();
            }
        }
    }

    private class bResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Reset Settings Pressed");

            // currency
            Main.currency = "USD";
            Main.currencyChar = "$";
            bCurrency.setText(Main.currency);
            new WebData.RefreshCoins(); // refresh the coins

            // debug mode
            Debug.mode = false;
            Debug.frame.setVisible(false);
            bDebug.setText("Off");

            // light mode
            bTheme.setText("Light");
            Main.theme.change(Main.themes.LIGHT);

            UIManager.put("OptionPane.background",Main.theme.emptyBackground);
            UIManager.put("Panel.background",Main.theme.emptyBackground);
            UIManager.put("OptionPane.messageForeground",Main.theme.foreground);

            Main.theme.resetCustom(); // reset custom

            bColor4.setBackground(Main.theme.customBackground);
            bColor5.setBackground(Main.theme.customForeground);
            bColor6.setBackground(Main.theme.customGreen);
            bColor7.setBackground(Main.theme.customRed);
            bColor8.setBackground(Main.theme.customSelection);
            bColor9.setBackground(Main.theme.customEmptyBackground);

            Main.gui.panelSettings.themeSwitch(); // finish page you are on first
            Main.gui.panelCoin.themeSwitch();
            Main.gui.panelPortfolio.themeSwitch();
            Main.gui.panelConverter.themeSwitch();

            serialize();
        }
    }

    private class bDeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Delete Data Pressed");
            ArrayList<JCheckBox> deleteList = new ArrayList<JCheckBox>();
            deleteList.add(new JCheckBox("Delete Coin Data"));
            deleteList.add(new JCheckBox("Delete Portfolio Data"));
            deleteList.add(new JCheckBox("Delete Settings Data"));
            deleteList.add(new JCheckBox("Delete Converter Data"));
            deleteList.add(new JCheckBox("Delete Logs"));

            Object[] list = (Object[]) deleteList.toArray(new Object[deleteList.size()]);
            JOptionPane.showMessageDialog(Main.frame, list, "Select Data", JOptionPane.PLAIN_MESSAGE);

            JCheckBox cb1 = (JCheckBox) list[0];
            JCheckBox cb2 = (JCheckBox) list[1];
            JCheckBox cb3 = (JCheckBox) list[2];
            JCheckBox cb4 = (JCheckBox) list[3];
            JCheckBox cb5 = (JCheckBox) list[4];

            if (!cb1.isSelected() && !cb2.isSelected() && !cb3.isSelected() && !cb4.isSelected() && !cb5.isSelected()) { // nothing was selected, return
                Debug.log("-- nothing was selected, cancelling");
                return;
            }

            File deleteFile;

            if (cb1.isSelected()) { // delete coin data
                Debug.log("-- deleting coin data");
                deleteFile = new File(Main.dataSerLocation);
                deleteFile.delete();
            }

            if (cb2.isSelected()) { // delete portfolio data
                Debug.log("-- deleting portfolio data");
                deleteFile = new File(Main.portfolioSerLocation);
                deleteFile.delete();
            }

            if (cb3.isSelected()) { // delete settings data
                Debug.log("-- deleting settings data");
                deleteFile = new File(Main.settingsSerLocation);
                deleteFile.delete();
            }

            if (cb4.isSelected()) { // delete converter data
                Debug.log("-- deleting converter data");
                deleteFile = new File(Main.converterSerLocation);
                deleteFile.delete();
            }

            if (cb5.isSelected()) { // delete logs
                Debug.log("-- deleting logs");
                deleteFile = new File(Main.logLocation);
                deleteFile.delete();
            }

            if (cb1.isSelected() || cb2.isSelected() || cb3.isSelected() || cb4.isSelected()) {
                JOptionPane.showMessageDialog(Main.frame, "You must restart the application!", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class colorListener implements ActionListener {
        private int number;

        public colorListener(int number) {
            this.number = number;
        }

        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Color " + number + " Pressed");

            colorChooser.getSelectionModel().removeChangeListener(changeListener);

            changeListener = new colorChangeListener(number);
            colorChooser.getSelectionModel().addChangeListener(changeListener);
            colorFrame.setVisible(true);

            switch (number) {
                case 4:
                    colorChooser.setColor(bColor4.getBackground());
                    colorFrame.setTitle("Color Chooser - Background");
                    break;
                case 5:
                    colorChooser.setColor(bColor5.getBackground());
                    colorFrame.setTitle("Color Chooser - Font");
                    break;
                case 6:
                    colorChooser.setColor(bColor6.getBackground());
                    colorFrame.setTitle("Color Chooser - Positive Font");
                    break;
                case 7:
                    colorChooser.setColor(bColor7.getBackground());
                    colorFrame.setTitle("Color Chooser - Negative Font");
                    break;
                case 8:
                    colorChooser.setColor(bColor8.getBackground());
                    colorFrame.setTitle("Color Chooser - Pressing Coin Font");
                    break;
                case 9:
                    colorChooser.setColor(bColor9.getBackground());
                    colorFrame.setTitle("Color Chooser - Empty Background");
                    break;
            }
        }
    }

    private class colorChangeListener implements ChangeListener {
        private int number;

        public void stateChanged(ChangeEvent e) {
            Color newColor = colorChooser.getColor();
            
            switch (number) {
                case 4:
                    bColor4.setBackground(newColor);
                    break;
                case 5:
                    bColor5.setBackground(newColor);
                    break;
                case 6:
                    bColor6.setBackground(newColor);
                    break;
                case 7:
                    bColor7.setBackground(newColor);
                    break;
                case 8:
                    bColor8.setBackground(newColor);
                    break;
                case 9:
                    bColor9.setBackground(newColor);
                    break;
            }
        }

        public colorChangeListener(int number) {
            this.number = number;
        }
    }

    private class bColorApplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Color Apply Pressed");

            Main.theme.customBackground = bColor4.getBackground();
            Main.theme.customForeground = bColor5.getBackground();
            Main.theme.customGreen = bColor6.getBackground();
            Main.theme.customRed = bColor7.getBackground();
            Main.theme.customSelection = bColor8.getBackground();
            Main.theme.customEmptyBackground = bColor9.getBackground();

            Debug.log("-- Switching to custom mode");
            bTheme.setText("Custom");
            Main.theme.change(Main.themes.CUSTOM);

            UIManager.put("OptionPane.background",Main.theme.emptyBackground);
            UIManager.put("Panel.background",Main.theme.emptyBackground);
            UIManager.put("OptionPane.messageForeground",Main.theme.foreground);

            Main.gui.panelSettings.themeSwitch(); // finish page you are on first
            Main.gui.panelCoin.themeSwitch();
            Main.gui.panelPortfolio.themeSwitch();
            Main.gui.panelConverter.themeSwitch();

            serialize();
        }
    }
}
