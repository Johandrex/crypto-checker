package com.cryptochecker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelConverter {
    public JPanel panel;

    private final int amountHeaderButtons = 2;
    private final int contentWidth = 250;
    private final int contentHeight = 45;

    private JPanel contentFilling1;
    private JPanel contentFilling2;
    private JPanel contentTop;
    private JPanel contentBottom;
    private JPanel middleBottom;

    private JButton buttonCurrency1;
    private JButton buttonCurrency2;

    private double priceCurrency1;
    private double priceCurrency2;

    private JTextField fieldCurrency1;
    private JTextField fieldCurrency2;

    private String infoCurrency1;
    private String infoCurrency2;

    private JPanel overview;
    private JEditorPane overviewText;

    private JEditorPane textBox1;
    private JEditorPane textBox2;
    private final Font textBoxFont = new Font("Helvetica", Font.PLAIN, 14);

    private DecimalFormat df1 = new DecimalFormat("#.##");
    private DecimalFormat df2 = new DecimalFormat("#.###");
    private DecimalFormat df3 = new DecimalFormat("#.####");
    private DecimalFormat df4 = new DecimalFormat("#.#####");
    private DecimalFormat df5 = new DecimalFormat("#.######");
    private DecimalFormat df6 = new DecimalFormat("#.############");

    public PanelConverter() {
        panel = new JPanel();
        panel.setVisible(false);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // overview data
        overview = new JPanel();
        overview.setLayout(new FlowLayout());
        overview.setBackground(Color.WHITE);
        overview.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        calculateGlobal();
        overview.add(overviewText);

        // overview-content filling
        contentFilling1 = new JPanel();
        contentFilling1.setLayout(new BorderLayout());
        contentFilling1.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.GRAY));

        contentFilling2 = new JPanel();
        contentFilling2.setLayout(new BorderLayout());
        contentFilling2.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.GRAY));

        // CONTENT
        contentTop = new JPanel();
        contentTop.setLayout(new FlowLayout());
        contentTop.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.GRAY));

        contentBottom = new JPanel();
        contentBottom.setLayout(new FlowLayout());
        contentBottom.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.GRAY));

        // CONTENT TOP - LEFT
        JPanel topLeft = new JPanel();
        topLeft.setLayout(new BoxLayout(topLeft, BoxLayout.Y_AXIS));
        topLeft.setPreferredSize(new Dimension(contentWidth, contentHeight));
        topLeft.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JPanel topLeft1 = new JPanel();
        topLeft1.setLayout(new BorderLayout());

        buttonCurrency1 = Main.gui.getButtonTemplate("");
        buttonCurrency1.addActionListener(new buttonCurrencyListener(1));
        buttonCurrency1.setBorder(BorderFactory.createEmptyBorder());

        topLeft1.add(buttonCurrency1);

        JPanel topLeft2 = new JPanel();
        topLeft2.setLayout(new BoxLayout(topLeft2, BoxLayout.Y_AXIS));

        fieldCurrency1 = new JTextField("");
        fieldCurrency1.setHorizontalAlignment(JTextField.CENTER);
        fieldCurrency1.setMinimumSize(new Dimension(contentWidth, contentHeight/2));
        fieldCurrency1.setMaximumSize(new Dimension(contentWidth, contentHeight/2));
        fieldCurrency1.setBorder(BorderFactory.createEmptyBorder());

        topLeft2.add(fieldCurrency1);

        topLeft.add(topLeft1);
        topLeft.add(topLeft2);

        // CONTENT TOP - MIDDLE
        JButton bSwitch = Main.gui.getButtonTemplate("Switch");
        bSwitch.addActionListener(new bSwitchListener());
        bSwitch.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        bSwitch.setPreferredSize(new Dimension(contentWidth/2,contentHeight));

        // CONTENT TOP - RIGHT
        JPanel topRight = new JPanel();
        topRight.setLayout(new BoxLayout(topRight, BoxLayout.Y_AXIS));
        topRight.setPreferredSize(new Dimension(contentWidth, contentHeight));
        topRight.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JPanel topRight1 = new JPanel();
        topRight1.setLayout(new BorderLayout());

        buttonCurrency2 = Main.gui.getButtonTemplate("");
        buttonCurrency2.addActionListener(new buttonCurrencyListener(2));
        buttonCurrency2.setBorder(BorderFactory.createEmptyBorder());

        topRight1.add(buttonCurrency2);

        JPanel topRight2 = new JPanel();
        topRight2.setLayout(new BoxLayout(topRight2, BoxLayout.Y_AXIS));

        fieldCurrency2 = new JTextField("");
        fieldCurrency2.setHorizontalAlignment(JTextField.CENTER);
        fieldCurrency2.setMinimumSize(new Dimension(contentWidth, contentHeight/2));
        fieldCurrency2.setMaximumSize(new Dimension(contentWidth, contentHeight/2));
        fieldCurrency2.setBorder(BorderFactory.createEmptyBorder());
        fieldCurrency2.setBackground(Color.WHITE);
        fieldCurrency2.setEditable(false);

        topRight2.add(fieldCurrency2);

        topRight.add(topRight1);
        topRight.add(topRight2);

        contentTop.add(topLeft);
        contentTop.add(bSwitch);
        contentTop.add(topRight);

        // CONTENT BOTTOM - LEFT
        JPanel leftBottom = new JPanel();
        leftBottom.setLayout(new BoxLayout(leftBottom, BoxLayout.Y_AXIS));
        leftBottom.setPreferredSize(new Dimension(contentWidth, contentHeight*6));
        leftBottom.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        textBox1 = new JEditorPane();
        textBox1.setFont(textBoxFont);
        textBox1.setEditable(false);
        
        leftBottom.add(textBox1);

        // CONTENT BOTTOM - MIDDLE
        middleBottom = new JPanel();
        middleBottom.setLayout(new BorderLayout());
        middleBottom.setPreferredSize(bSwitch.getPreferredSize());

        // CONTENT BOTTOM - RIGHT
        JPanel rightBottom = new JPanel();
        rightBottom.setLayout(new BoxLayout(rightBottom, BoxLayout.Y_AXIS));
        rightBottom.setPreferredSize(new Dimension(contentWidth, contentHeight*6));
        rightBottom.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        textBox2 = new JEditorPane();
        textBox2.setFont(textBoxFont);
        textBox2.setEditable(false);

        rightBottom.add(textBox2);

        contentBottom.add(leftBottom);
        contentBottom.add(middleBottom);
        contentBottom.add(rightBottom);

        // currencyField1
        fieldCurrency1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                Double docText;
                try { 
                    docText = Double.parseDouble(fieldCurrency1.getText());
                } catch(Exception ex) {
                    docText = null;
                }

                if (docText == null) {
                    fieldCurrency2.setText("");
                } else {
                    fieldCurrency2.setText(calculateCurrency(docText));
                }

                serialize();
            }

            public void removeUpdate(DocumentEvent e) {
                Double docText;
                try { 
                    docText = Double.parseDouble(fieldCurrency1.getText());
                } catch(Exception ex) {
                    docText = null;
                }

                if (docText == null) {
                    fieldCurrency2.setText("");
                } else {
                    fieldCurrency2.setText(calculateCurrency(docText));
                }

                serialize();
            }

            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // to change body of generated methods, choose Tools | Templates.
            }
        });

        // TOP HEADER
        JPanel headerTop = new JPanel();
        headerTop.setBackground(Color.GRAY);
        headerTop.setLayout(new BoxLayout(headerTop, BoxLayout.X_AXIS));

        // BUTTONS HEADER
        JPanel headerButtons = new JPanel();
        headerButtons.setLayout(new BoxLayout(headerButtons, BoxLayout.X_AXIS));
        headerButtons.setPreferredSize(new Dimension(Main.panelWidth*amountHeaderButtons, Main.panelHeight-1));

        JButton bRefresh = Main.gui.getButtonTemplate("Refresh");
        bRefresh.addActionListener(new bRefreshListener());
        bRefresh.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.LIGHT_GRAY));
        bRefresh.setMaximumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        JButton bGlobal = Main.gui.getButtonTemplate("Global Data");
        bGlobal.addActionListener(new bGlobalListener());
        bGlobal.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        bGlobal.setMaximumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        headerButtons.add(bRefresh);
        headerButtons.add(bGlobal);

        // HEADER FILLING
        JPanel headerFilling = new JPanel();
        headerFilling.setLayout(new BorderLayout());
        headerFilling.setMaximumSize(new Dimension((int) Main.screenResolution.getWidth(), Main.panelHeight-1));
        headerFilling.setBackground(Color.GRAY);

        themeSwitch(); // select theme color

        headerTop.add(headerButtons);
        headerTop.add(headerFilling);

        panel.add(headerTop);
        panel.add(overview);
        panel.add(contentFilling1);
        panel.add(contentTop);
        panel.add(contentBottom);
        panel.add(contentFilling2);

        deserialize();
        retrieveText(1, infoCurrency1);
        retrieveText(2, infoCurrency2);
    }
    
    public void reCreate() {
        calculateGlobal();

        for (int i = 0; i < Main.gui.webData.coin.size(); ++i) {
            if (Main.gui.webData.coin.get(i).name.equals(buttonCurrency1.getText())) {
                priceCurrency1 = Main.gui.webData.coin.get(i).price;
                retrieveText(1, Main.gui.webData.coin.get(i).getInfo());
                break;
            }
        }
        if (priceCurrency2 != 0) for (int i = 0; i < Main.gui.webData.coin.size(); ++i) {
            if (Main.gui.webData.coin.get(i).name.equals(buttonCurrency2.getText())) {
                priceCurrency2 = Main.gui.webData.coin.get(i).price;
                retrieveText(2, Main.gui.webData.coin.get(i).getInfo());
                break;
            }
        }
        else { // priceCurrency2 is 0, it's value is Main.currency
            buttonCurrency2.setText(Main.currency);
            fieldCurrency1.setText(fieldCurrency1.getText()); // invoke documentlistener on currency change
            retrieveText(2, "");
        }

        fieldCurrency1.setText(fieldCurrency1.getText());
        Main.frame.getContentPane().revalidate();
        Main.frame.getContentPane().repaint();
    }

    private void retrieveText(int box, String info) {
        //Debug.log("Retrieving Box "+box);

        switch (box) {
            case 1:
                infoCurrency1 = info;
                textBox1.setText(infoCurrency1);
                break;
            case 2:
                infoCurrency2 = info;
                textBox2.setText(infoCurrency2);
                break;
            default:
                Debug.log("incorrect case - PanelConverter.java retrieveText()");
                System.exit(0);
                break;
        }
    }

    private String calculateCurrency(double x) {
        Double returnValue = 0.0;
        String returnString = "";

        if (priceCurrency2 == 0.0 && buttonCurrency2.getText().equals(Main.currency)) {
            returnValue = priceCurrency1*x;
        }
        else if (priceCurrency1 == 0 || priceCurrency2 == 0) {
            return String.valueOf(0);
        }
        else {
            returnValue = (priceCurrency1/priceCurrency2)*x;
        }

        if (returnValue > 1) returnString = String.valueOf(df1.format(returnValue));
        else if (returnValue > 0.1) returnString = String.valueOf(df2.format(returnValue));
        else if (returnValue > 0.01) returnString = String.valueOf(df3.format(returnValue));
        else if (returnValue > 0.001) returnString = String.valueOf(df4.format(returnValue));
        else if (returnValue > 0.0001) returnString = String.valueOf(df5.format(returnValue));
        else returnString = String.valueOf(df6.format(returnValue));

        return returnString;
    }

    private void calculateGlobal() {
        double total_market_cap = Main.gui.webData.global_data.total_market_cap;
        double total_24h_volume = Main.gui.webData.global_data.total_24h_volume;
        double bitcoin_percentage = Main.gui.webData.global_data.bitcoin_percentage_of_market_cap;
        
        DecimalFormat htmlFormat = new DecimalFormat("#,###.##");

        String htmlFont = "<font color=\"rgb("+Main.theme.foreground.getRed()+", "+Main.theme.foreground.getGreen()+", "+Main.theme.foreground.getBlue()+")\">";
        String htmlGreen = "<font color=\"rgb("+Main.theme.green.getRed()+", "+Main.theme.green.getGreen()+", "+Main.theme.green.getBlue()+")\">";

        String htmlOverview = htmlFont+"<font size=\"6\"><font face=\"helvetica\">"+"<center>"
        +"{ "+htmlFormat.format(total_market_cap)+" }<br>"
        +"<font size=\"4\"><b>24 Hour Volume: "+htmlGreen+htmlFormat.format(total_24h_volume)+htmlFont+"<br>"
        +"Bitcoin Dominance: "+htmlGreen+bitcoin_percentage+"%"+htmlFont+"</b></center>";

        // set text
        if (overviewText != null) {
            overviewText.setText(htmlOverview);
        } else {
            overviewText = new JEditorPane("text/html", htmlOverview);
            overviewText.setEditable(false);
        }
    }

    public void themeSwitch() {
        overview.setBackground(Main.theme.background);
        overviewText.setBackground(Main.theme.background);
        overviewText.setForeground(Main.theme.foreground);

        fieldCurrency1.setBackground(Main.theme.background);
        fieldCurrency1.setForeground(Main.theme.foreground);
        fieldCurrency2.setBackground(Main.theme.background);
        fieldCurrency2.setForeground(Main.theme.foreground);

        textBox1.setBackground(Main.theme.background);
        textBox1.setForeground(Main.theme.foreground);
        textBox2.setBackground(Main.theme.background);
        textBox2.setForeground(Main.theme.foreground);

        contentFilling1.setBackground(Main.theme.emptyBackground);
        contentFilling2.setBackground(Main.theme.emptyBackground);
        contentTop.setBackground(Main.theme.emptyBackground);
        contentBottom.setBackground(Main.theme.emptyBackground);
        middleBottom.setBackground(Main.theme.emptyBackground);

        calculateGlobal();
    }

    private class bRefreshListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Refresh Clicked");
            new WebData.RefreshCoins();
        }
    }

    private class bGlobalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Global Clicked");
            JOptionPane.showMessageDialog(Main.frame, Main.gui.webData.global_data.toString(), "Global", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private class buttonCurrencyListener implements ActionListener {
        private int nr;

        public buttonCurrencyListener(int nr) {
            this.nr = nr;
        }

        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Convert Currency "+nr+" Clicked");
            int convertSelection;

            if (nr == 2) {
                Object[] convertSelectionOptions = { "Cryptocurrency", "Current currency ("+Main.currency+")"};
                convertSelection = JOptionPane.showOptionDialog(Main.frame, "Select one of the options", "Selection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, convertSelectionOptions, convertSelectionOptions[1]);
                Debug.log("-- menu selection " +convertSelection);
            } else {
                convertSelection = 0;
            }

            switch (convertSelection) {
                case 0:
                    Object[] options = Main.gui.webData.coin.toArray();

                    Object selectedValue = JOptionPane.showInputDialog(Main.frame, "Select cryptocurrency to add", "Add To Portfolio", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (selectedValue == null) {
                        Debug.log("-- cancel");
                        return;
                    } else {
                        Debug.log("-- "+selectedValue);
                    }
    
                    if (nr == 1) {
                        buttonCurrency1.setText(selectedValue.toString());
                        for (int i = 0; i < Main.gui.webData.coin.size(); ++i) {
                            if (Main.gui.webData.coin.get(i).name.equals(selectedValue.toString())) {
                                priceCurrency1 = Main.gui.webData.coin.get(i).price;
                                retrieveText(1, Main.gui.webData.coin.get(i).getInfo());
                                break;
                            }
                        }
                    } else if (nr == 2) {
                        buttonCurrency2.setText(selectedValue.toString());
                        for (int i = 0; i < Main.gui.webData.coin.size(); ++i) {
                            if (Main.gui.webData.coin.get(i).name.equals(selectedValue.toString())) {
                                priceCurrency2 = Main.gui.webData.coin.get(i).price;
                                retrieveText(2, Main.gui.webData.coin.get(i).getInfo());
                                break;
                            }
                        }
                    } else {
                        Debug.log("EXCEPTION: incorrect nr in buttonCurrencyListener");
                        System.exit(0);
                    }
                    break;
                case 1:
                    Debug.log("-- " + Main.currency);
                    priceCurrency2 = 0;
                    buttonCurrency2.setText(Main.currency);
                    retrieveText(2, "");

                    break;
                default:
                    Debug.log("-- cancel");
                    return;
            }

            fieldCurrency1.setText(fieldCurrency1.getText()); // invoke documentlistener on currency change
        }
    }

    private class bSwitchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Switch Clicked");

            if (priceCurrency2 == 0 || priceCurrency1 == 0) {
                JOptionPane.showMessageDialog(Main.frame, "You must select a cryptocurrency for both fields", "Select Cryptocurrencies", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String button1 = buttonCurrency1.getText();
            String button2 = buttonCurrency2.getText();
            String field1 = fieldCurrency1.getText();
            String field2 = fieldCurrency2.getText();
            Double price1 = priceCurrency1;
            Double price2 = priceCurrency2;
            String info1 = infoCurrency1;
            String info2 = infoCurrency2;

            buttonCurrency1.setText(button2);
            buttonCurrency2.setText(button1);
            fieldCurrency1.setText(field2);
            fieldCurrency2.setText(field1);
            priceCurrency1 = price2;
            priceCurrency2 = price1;
            retrieveText(1, info2);
            retrieveText(2, info1);
            
            serialize();
        }
    }

    //@SuppressWarnings("unchecked")
    private void deserialize() {
        try {
            if (!(new File(Main.converterSerLocation).canRead())) {
                Debug.log("ERROR: Couldn't find "+Main.converterSerLocation+".. skipping");
                // default settings if none exists
                priceCurrency1 = Main.gui.webData.coin.get(0).price;
                priceCurrency2 = Main.gui.webData.coin.get(1).price;
                buttonCurrency1.setText(Main.gui.webData.coin.get(0).toString());
                buttonCurrency2.setText(Main.gui.webData.coin.get(1).toString());
                retrieveText(1, Main.gui.webData.coin.get(0).getInfo());
                retrieveText(2, Main.gui.webData.coin.get(1).getInfo());
                return;
            }
            FileInputStream file = new FileInputStream(Main.converterSerLocation);
            BufferedInputStream buffer = new BufferedInputStream(file);
            ObjectInputStream in = new ObjectInputStream(buffer);

            buttonCurrency1.setText((String) in.readObject());
            buttonCurrency2.setText((String) in.readObject());
            fieldCurrency1.setText((String) in.readObject());
            fieldCurrency2.setText((String) in.readObject());
            priceCurrency1 = (Double) in.readObject();
            priceCurrency2 = (Double) in.readObject();
            infoCurrency1 = (String) in.readObject();
            infoCurrency2 = (String) in.readObject();
            in.close();
            Debug.log("Deserialized Converter From " + Main.converterSerLocation);
        } catch(Exception ex) {
            Debug.log("ERROR: PanelConverter.deserialize(), deleting " + Main.converterSerLocation);
            File deleteFile = new File(Main.converterSerLocation);
            deleteFile.delete();
        }
    }

    private void serialize() {
        try {
            if ((priceCurrency1 == 0 || priceCurrency2 == 0) && (!buttonCurrency2.getText().equals(Main.currency))) return;

            FileOutputStream file = new FileOutputStream(Main.converterSerLocation);
            BufferedOutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(buttonCurrency1.getText());
            out.writeObject(buttonCurrency2.getText());
            out.writeObject(fieldCurrency1.getText());
            out.writeObject(fieldCurrency2.getText());
            out.writeObject(priceCurrency1);
            out.writeObject(priceCurrency2);
            out.writeObject(infoCurrency1);
            out.writeObject(infoCurrency2);
            out.close();
            //Debug.log("Serialized Converter To " + Main.converterSerLocation); // too noisy
        } catch(Exception ex) {
            Debug.log("EXCEPTION: PanelConverter.java - serialize()");
            ex.printStackTrace();
        }
    }
}
