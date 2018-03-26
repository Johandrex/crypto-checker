package com.cryptochecker;

import java.awt.*; // user interfaces, painting and images. (color, font, borderlayout)
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*; // provides gui related components (frame, jbutton)

public class Main {
    public static Main gui;
    public static JFrame frame;

    public Debug debug;
    public WebData webData;
    public Menu menu;
    public PanelCoin panelCoin;
    public PanelPortfolio panelPortfolio;
    public PanelConverter panelConverter;
    public PanelSettings panelSettings;

    public static String currency = "USD";
    public static String currencyChar = "$";

    public static enum themes { LIGHT, DARK, CUSTOM };
    public static Theme theme;

    public static Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int panelWidth = 110;
    public static final int panelHeight = 30;

    public static final Font tableFont = new Font("Helvetica", Font.PLAIN, 16);
    public static final Dimension tableHeaderSize = new Dimension(25, 25);
    public static final Dimension tableIntercellSpacing = new Dimension(5, 5);

    public static final String folderLocation = System.getProperty("user.home")+"/.crypto-checker/";
    public static final String dataSerLocation = folderLocation+"data.ser";
    public static final String portfolioSerLocation = folderLocation+"portfolio.ser";
    public static final String settingsSerLocation = folderLocation+"settings.ser";
    public static final String converterSerLocation = folderLocation+"converter.ser";
    public static final String imageLocation = folderLocation+"icon.png";
    public static final String logLocation = folderLocation+"log.txt";

    public static void main(String[] args) {
        if (!(new File(folderLocation).exists())) {
            new File(folderLocation).mkdirs();
        }
        
        gui = new Main();

        try {
            gui.setupGUI(); // create gui
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupGUI() throws Exception {
        debug = new Debug(); // log file

        frame = new JFrame("Crypto Checker"); // create frame of program
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null); // screen location to middle
        frame.setResizable(false); // disallow changes to size
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        deserializeSettings();

        getIcon();
        webData = new WebData();

        deserializePortfolio();

        menu = new Menu();
        panelCoin = new PanelCoin();
        panelPortfolio = new PanelPortfolio();
        panelConverter = new PanelConverter();
        panelSettings = new PanelSettings();

        if (!Debug.mode) new WebData.RefreshCoins(); // refresh upon startup unless in debug mode

        // add the panels to the frame
        frame.getContentPane().add(BorderLayout.CENTER, panelCoin.panel);
        frame.getContentPane().add(BorderLayout.WEST, menu.panel);

        Main.frame.getContentPane().revalidate();
        Main.frame.getContentPane().repaint();
    }

    private void deserializeSettings() throws Exception { // restore theme setting from data
        if (!(new File(settingsSerLocation).canRead())) {
            theme = new Theme(themes.LIGHT);
            Debug.log("ERROR: Couldn't find "+settingsSerLocation+".. skipping");
        } else {

            try {
                FileInputStream file = new FileInputStream(settingsSerLocation);
                BufferedInputStream buffer = new BufferedInputStream(file);
                ObjectInputStream in = new ObjectInputStream(buffer);
    
                Debug.mode = (Boolean) in.readObject();
                theme = (Theme) in.readObject();
                currency = (String) in.readObject();
                currencyChar = (String) in.readObject();
                in.close();
                Debug.log("Deserialized Settings From " + settingsSerLocation);

                if (Debug.mode) Debug.frame.setVisible(true); // if debug mode on, show debug frame
            } catch(Exception ex) {
                Debug.log("ERROR: Main.deserializeSettings(), deleting " + settingsSerLocation);
                File deleteFile = new File(settingsSerLocation);
                deleteFile.delete();

                theme = new Theme(themes.LIGHT);
            }
        }

        UIManager.put("OptionPane.background",theme.emptyBackground);
        UIManager.put("Panel.background",theme.emptyBackground);
        UIManager.put("OptionPane.messageForeground",theme.foreground);
    }

    @SuppressWarnings("unchecked") // coin = (ArrayList<Coin> in.readObject()) supressing
    private void deserializePortfolio() throws Exception {
        if (!(new File(portfolioSerLocation).canRead())) {
            webData.portfolio = new ArrayList<ArrayList<WebData.Coin>>();
            webData.portfolio.add(new ArrayList<WebData.Coin>());
            webData.portfolio_names.add(new String("Portfolio 1"));
            Debug.log("ERROR: Couldn't find "+portfolioSerLocation+".. skipping");
        } else {

            try {
                FileInputStream file = new FileInputStream(portfolioSerLocation);
                BufferedInputStream buffer = new BufferedInputStream(file);
                ObjectInputStream in = new ObjectInputStream(buffer);
        
                webData.portfolio = (ArrayList<ArrayList<WebData.Coin>>) in.readObject();
                webData.portfolio_names = (ArrayList<String>) in.readObject();
                webData.portfolio_nr = (Integer) in.readObject();
                in.close();
                Debug.log("Deserialized Portfolio From " + portfolioSerLocation);
            }
            catch(Exception ex) {
                Debug.log("ERROR: Main.deserializePortfolio(), deleting " + portfolioSerLocation);
                File deleteFile = new File(portfolioSerLocation);
                deleteFile.delete();

                webData.portfolio = new ArrayList<ArrayList<WebData.Coin>>();
                webData.portfolio.add(new ArrayList<WebData.Coin>());
                webData.portfolio_names.add(new String("Portfolio 1"));
            }
        }
    }

    private void getIcon() throws Exception { // program icon
        if (!(new File(imageLocation).exists())) {
            try {
                Debug.log("ERROR: Couldn't find "+imageLocation+".. downloading"); // get image from jar
                BufferedImage image = ImageIO.read(getClass().getResource("/icon.png"));

                ImageIO.write(image, "png", new File(imageLocation)); // write the image to location
                Debug.log("-- image downloaded");
            } catch(Exception ex) {
                Debug.log("ERROR: Couldn't find /icon.png in jar");
                return;
            }
        }
        
        if (imageLocation != null) { // do this if the image exists
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(imageLocation));
        }
    }

    public static class Theme implements Serializable {
        private static final long serialVersionUID = 1L;
        public themes currentTheme;
        public Color background;
        public Color foreground;
        public Color green;
        public Color red;
        public Color selection;
        public Color emptyBackground;

        private Color defaultCustomBackground = new Color(0, 0, 0); // black 
        private Color defaultCustomForeground = new Color(14, 255, 0); // light green
        private Color defaultCustomGreen = new Color(244, 255, 0); // light yellow
        private Color defaultCustomRed = new Color(255, 0, 0); // light red
        private Color defaultCustomSelection = new Color(128, 128, 128); // gray
        private Color defaultCustomEmptyBackground = new Color(45, 45, 45); // dark gray

        public Color customBackground;
        public Color customForeground;
        public Color customGreen;
        public Color customRed;
        public Color customSelection;
        public Color customEmptyBackground;

        public Theme(themes currentTheme) {
            resetCustom();
            this.currentTheme = currentTheme;
            update();
        }

        public void change(themes currentTheme) {
            this.currentTheme = currentTheme;
            update();
        }

        public void update() {
            switch (currentTheme) {
                case LIGHT:
                    background = Color.WHITE; // white
                    foreground = Color.BLACK; // black
                    green = new Color(0, 128, 0); // dark green
                    red = new Color(220, 20, 60); // dark red
                    selection = Color.GRAY; // gray
                    emptyBackground = new Color(250, 250, 250); // quite white
                    break;
                case DARK:
                    background = new Color(15, 15, 15); // light black
                    foreground = Color.WHITE; // white
                    green = Color.GREEN; // light green
                    red = Color.RED; // dark red
                    selection = Color.GRAY; // gray
                    emptyBackground = new Color(78, 78, 78); // dark gray
                    break;
                case CUSTOM:
                    background = customBackground;
                    foreground = customForeground;
                    green = customGreen;
                    red = customRed;
                    selection = customSelection;
                    emptyBackground = customEmptyBackground;
                    break;
            }
        }

        public void resetCustom() {
            customBackground = defaultCustomBackground;
            customForeground = defaultCustomForeground;
            customGreen = defaultCustomGreen;
            customRed = defaultCustomRed;
            customSelection = defaultCustomSelection;
            customEmptyBackground = defaultCustomEmptyBackground;
        }
    } 

    public JButton getButtonTemplate(String s) {
        JButton template = new JButton(s);
        template.setMinimumSize(new Dimension(panelWidth, panelHeight));
        template.setMaximumSize(new Dimension(panelWidth, panelHeight));
        template.setBackground(Color.GRAY);
        template.setForeground(Color.WHITE);
        template.setFocusable(false);

        return template;
    }
}
