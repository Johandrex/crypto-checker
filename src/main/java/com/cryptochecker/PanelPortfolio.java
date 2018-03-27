package com.cryptochecker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class PanelPortfolio {
    public JPanel panel;
    public WebData webData;
    public int nr = Main.gui.webData.portfolio_nr;
    public ArrayList<String> names = Main.gui.webData.portfolio_names;

    private JTable table;
    private TableRenderer renderer;
    private DefaultTableCellRenderer headerRenderer;
    private TableModel model;
    private JScrollPane pane;
    private JScrollPane contentPane;

    private final int rowHeight = 40;
    private final int leftAmountHeaderButtons = 3;
    private final int rightAmountHeaderButtons = 2;
    private final int headerSearchSize = 14;
    private final Dimension searchFieldSize = new Dimension(Main.panelWidth*2, Main.panelHeight-10);

    private JPanel content;
    private JPanel overview;
    private JEditorPane overviewText;

    public PanelPortfolio() {
        panel = new JPanel();
        panel.setVisible(false); // visible to false at startup
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        webData = Main.gui.webData;
        model = new TableModel(); // table model

        // rendering columns+rows
        renderer = new TableRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        JPanel headerTop = new JPanel();
        headerTop.setBackground(Color.GRAY);
        headerTop.setLayout(new BoxLayout(headerTop, BoxLayout.X_AXIS));

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        table = new JTable(model); // create table with data
        table.setDefaultRenderer(Integer.class, renderer);
        table.setDefaultRenderer(Double.class, renderer);
        table.setDefaultRenderer(Short.class, renderer);

        // render header        
        headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(Main.tableHeaderSize);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        // table settings
        table.setRowHeight(rowHeight);
        table.getColumnModel().getColumn(4).setMaxWidth(100); // 1h
        table.getColumnModel().getColumn(5).setMaxWidth(100); // 24h
        table.getColumnModel().getColumn(6).setMaxWidth(100); // 7d

        table.setFont(Main.tableFont);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(Main.tableIntercellSpacing);
        table.setFocusable(false);

        table.setDefaultRenderer(Object.class, renderer);
        table.addMouseListener(new TableMouseListener()); // Mouse Listener

        // LEFT BUTTONS HEADER
        JPanel leftHeaderButtons = new JPanel();
        leftHeaderButtons.setLayout(new BoxLayout(leftHeaderButtons, BoxLayout.X_AXIS));
        leftHeaderButtons.setPreferredSize(new Dimension(Main.panelWidth*leftAmountHeaderButtons, Main.panelHeight-1)); // times button

        JButton bRefresh = Main.gui.getButtonTemplate("Refresh");
        bRefresh.addActionListener(new bRefreshListener());
        bRefresh.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.LIGHT_GRAY));
        bRefresh.setMinimumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        JButton bAddCoin = Main.gui.getButtonTemplate("Add Coin");
        bAddCoin.addActionListener(new bAddCoinListener());
        bAddCoin.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        bAddCoin.setMinimumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        JButton bRemoveCoin = Main.gui.getButtonTemplate("Remove Coin");
        bRemoveCoin.addActionListener(new bRemoveCoinListener());
        bRemoveCoin.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        bRemoveCoin.setMinimumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        leftHeaderButtons.add(bRefresh);
        leftHeaderButtons.add(bAddCoin);
        leftHeaderButtons.add(bRemoveCoin);

        // RIGHT BUTTONS HEADER
        JPanel rightHeaderButtons = new JPanel();
        rightHeaderButtons.setLayout(new BoxLayout(rightHeaderButtons, BoxLayout.X_AXIS));
        rightHeaderButtons.setPreferredSize(new Dimension(Main.panelWidth*rightAmountHeaderButtons, Main.panelHeight-1)); // times button
        
        JButton bSwitchPortfolio = Main.gui.getButtonTemplate("Switch");
        bSwitchPortfolio.addActionListener(new bSwitchPortfolioListener());
        bSwitchPortfolio.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        bSwitchPortfolio.setPreferredSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        JButton bManagePortfolio = Main.gui.getButtonTemplate("Manage");
        bManagePortfolio.addActionListener(new bManagePortfolioListener());
        bManagePortfolio.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        bManagePortfolio.setPreferredSize(new Dimension(Main.panelWidth, Main.panelHeight+1));
        
        rightHeaderButtons.add(bSwitchPortfolio);
        rightHeaderButtons.add(bManagePortfolio);

        // HEADER FILLING
        JPanel headerFilling = new JPanel();
        headerFilling.setLayout(new BorderLayout());
        headerFilling.setMaximumSize(new Dimension((int) Main.screenResolution.getWidth(), Main.panelHeight-1));
        headerFilling.setBackground(Color.GRAY);

        // SEARCH HEADER
        JPanel headerSearch = new JPanel();
        headerSearch.setLayout(new BoxLayout(headerSearch, BoxLayout.X_AXIS));
        headerSearch.setMaximumSize(searchFieldSize);
        headerSearch.setBackground(Color.GRAY);

        JTextField headerSearchField = new JTextField("", headerSearchSize);
        headerSearch.add(headerSearchField);

        // sorting
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>(25);
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        rowSorter.setSortKeys(sortKeys);

        /*
        headerSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                String text = headerSearchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            public void removeUpdate(DocumentEvent e) {
                String text = headerSearchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // to change body of generated methods, choose Tools | Templates.
            }
        });
        */

        // add headers together
        headerTop.add(leftHeaderButtons);
        headerTop.add(headerFilling);
        //headerTop.add(headerSearch); // really needed?
        //headerTop.add(headerFilling2);
        headerTop.add(rightHeaderButtons);        

        // overview data
        overview = new JPanel();
        overview.setLayout(new FlowLayout());
        overview.setMaximumSize(new Dimension(10000, 0));
        calculatePortfolio();
        overview.add(overviewText);

        // scrolling pane, add JScrollPane to JPanel
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        pane = new JScrollPane(table);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(null);
        pane.setViewportBorder(null);
        pane.addMouseWheelListener(new contentPaneScroll());
        pane.getVerticalScrollBar().setUnitIncrement(5);
        pane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

        content.add(overview);
        content.add(pane);

        contentPane = new JScrollPane(content);
        //contentPane.setVerticalScrollBarPolicy(pane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.getVerticalScrollBar().setUnitIncrement(12);

        panel.add(headerTop);
        panel.add(contentPane);

        themeSwitch(); // select theme color
    }

    public class contentPaneScroll implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() < 0) {
                contentPane.getVerticalScrollBar().setValue(-e.getScrollAmount()*12+contentPane.getVerticalScrollBar().getValue());
            } else {
                contentPane.getVerticalScrollBar().setValue(e.getScrollAmount()*12+contentPane.getVerticalScrollBar().getValue());
            }
        }
    }

    public void reCreate() {
        model.list = Main.gui.webData.portfolio.get(nr);
        table.getRowSorter().allRowsChanged();

        calculatePortfolio();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        Main.frame.getContentPane().add(BorderLayout.CENTER, Main.gui.panelPortfolio.panel);
        Main.frame.getContentPane().revalidate();
        Main.frame.getContentPane().repaint();
    }

    public void calculatePortfolio() {
        double value = 0.0;
        double gains = 0.0;
        for (int i = 0; i < Main.gui.webData.portfolio.get(nr).size(); ++i) {
           value += Main.gui.webData.portfolio.get(nr).get(i).portfolio_value;
           gains += Main.gui.webData.portfolio.get(nr).get(i).portfolio_gains;
        }
        
        DecimalFormat overviewFormat = new DecimalFormat("#,###.##");

        NumberFormat decimalFormat = NumberFormat.getPercentInstance();
        decimalFormat.setMinimumFractionDigits(2);

        String htmlFont = "<font color=\"rgb("+Main.theme.foreground.getRed()+", "+Main.theme.foreground.getGreen()+", "+Main.theme.foreground.getBlue()+")\">";
        String htmlGreen = "<font color=\"rgb("+Main.theme.green.getRed()+", "+Main.theme.green.getGreen()+", "+Main.theme.green.getBlue()+")\">";
        String htmlRed = "<font color=\"rgb("+Main.theme.red.getRed()+", "+Main.theme.red.getGreen()+", "+Main.theme.red.getBlue()+")\">";

        String htmlBottom;

        if (gains >= 0) { htmlBottom = htmlGreen; }
        else { htmlBottom = htmlRed; }

        String percentGains;
        if (value == 0) { percentGains = "0.00%"; }
        else { percentGains = decimalFormat.format(gains/(value-gains)); }

        String overviewHTML = htmlFont+"<font size=\"6\"><font face=\"helvetica\">"+"<center>"
        +"{ "+overviewFormat.format(value)+" }<br>"
        +"<font size=\"5\"><b>"+htmlBottom+overviewFormat.format(gains)+" ("+percentGains+")"+"</b></center>";

        // set text
        if (overviewText != null) {
            overviewText.setText(overviewHTML);
        } else {
            overviewText = new JEditorPane("text/html", overviewHTML);
            overviewText.setEditable(false);
        }
    }

    private class TableMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            if (table.getSelectedColumn() < 0 || table.getSelectedRow() < 0) // if nothing is selected or if user right clicks
                return;

                int columnNr = table.convertColumnIndexToModel(table.getSelectedColumn());
                int rowNr = table.convertRowIndexToModel(table.getSelectedRow());
                Debug.log("Table Portfolio " + nr + ", Row " + rowNr + ", Column " + columnNr);
                JOptionPane.showMessageDialog(Main.frame, Main.gui.webData.portfolio.get(nr).get(rowNr).getPortfolio(), Main.gui.webData.portfolio.get(nr).get(rowNr).toString(), JOptionPane.PLAIN_MESSAGE);
                table.clearSelection();
        }

        public void mouseExited(MouseEvent e) { }
        public void mouseEntered(MouseEvent ev) { }
        public void mouseReleased(MouseEvent ev) { }
        public void mouseClicked(MouseEvent ev) { }
        
    }

    private class TableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {"Name", "Coin Value", "Portfolio Value", "Gains / Losses", "1h", "24h", "7d"};
        private ArrayList<WebData.Coin> list = webData.portfolio.get(nr); // PORTFOLIO

        public int getColumnCount() {
            return columnNames.length;
        }
    
        public int getRowCount() {
            return list.size();
        }
    
        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Class<?> getColumnClass(int column) { // for sorting
            switch (column) {
                case 0:
                    return String.class;
                case 1:
                    return Double.class;
                case 2:
                    return Double.class;
                case 3:
                    return Double.class;
                case 4:
                    return Double.class;
                case 5:
                    return Double.class;
                case 6:
                    return Double.class;
                default:
                    return String.class;
            }
        }
        
        public Object getValueAt(int row, int col) {
            Object value = null;
            switch (col) {
                case 0:
                    value = list.get(row).name;
                    break;
                case 1:
                    value = list.get(row).price;
                    break;
                case 2:
                    value = list.get(row).portfolio_value;
                    break;
                case 3:
                    value = list.get(row).portfolio_gains;
                    break;
                case 4:
                    value = list.get(row).percent_change_1h;
                    break;
                case 5:
                    value = list.get(row).percent_change_24h;
                    break;
                case 6:
                    value = list.get(row).percent_change_7d;
                    break;
            }
            return value;
        }
    }

    private class TableRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        private NumberFormat nf = NumberFormat.getCurrencyInstance();
        private DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.setBackground(Main.theme.background);
            super.setForeground(Main.theme.foreground);

            if (column == 1 || column == 2) {
                decimalFormatSymbols.setCurrencySymbol(Main.currencyChar);
                ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
                
                value = nf.format(value).trim();
            }

            if (column == 3) {
                if (0 <= (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.green);
                }
                else if (0 > (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.red);
                }

                value = nf.format(value).trim();
            }

            if (column == 4 || column == 5 || column == 6) {
                if (0 <= (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.green);
                }
                else if (0 > (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.red);
                }

                value = value+"%";
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public void themeSwitch() {
        pane.getViewport().setBackground(Main.theme.emptyBackground);

        overview.setBackground(Main.theme.background);
        overviewText.setBackground(Main.theme.background);
        overviewText.setForeground(Main.theme.foreground);

        table.setSelectionForeground(Main.theme.selection);
        table.setSelectionBackground(Main.theme.background);

        table.setBackground(Main.theme.background);
        table.getTableHeader().setForeground(Main.theme.foreground);
        table.getTableHeader().setBackground(Main.theme.background);
        
        table.getTableHeader().setBackground(Main.theme.background);
        headerRenderer.setForeground(Main.theme.foreground);

        calculatePortfolio();
    }

    private class bRefreshListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Refresh Clicked");
            new WebData.RefreshCoins();
        }
    }

    private class bAddCoinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Add Coin Clicked");

            Object[] options = webData.coin.toArray();

            // SELECTING cryptocurrency
            Object selectedValue = JOptionPane.showInputDialog(Main.frame, "Select cryptocurrency to add", "Add To Portfolio", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (selectedValue == null) {
                Debug.log("-- cancel");
                return;
            } else if (findPortfolioName(selectedValue.toString())) {
                Debug.log("-- "+selectedValue+" already exists, cancelling");
                JOptionPane.showMessageDialog(Main.frame, selectedValue+" already exists!");
                return;
            } else {
                Debug.log("-- "+selectedValue);
            }
            // convert to WebData.Coin
            WebData.Coin shallowValue = (WebData.Coin) selectedValue;
            WebData.Coin value = (WebData.Coin) shallowValue.copy();


            // SELECTING amounts to cryptocurrency
            String input = JOptionPane.showInputDialog(Main.frame, "Amounts of " + value, "Add To Portfolio", JOptionPane.PLAIN_MESSAGE);
            if (input == null) { // pressed "cancel button"
                Debug.log("-- cancel");
                return;
            }

            double amounts;
            try {
                amounts = Double.parseDouble(input);
                value.portfolio_amount = amounts;
                Debug.log("-- "+amounts);
            } catch(Exception ex) {
                Debug.log("-- cancel - incorrect format");
                JOptionPane.showMessageDialog(Main.frame, "Incorrect format!\nYou can only write a number with or without decimal\nexample: 51.2");
                return;
            }

            // SELECTING per price/total price
            Object[] paneSelectionOptions = { "Value per piece", "Total value", "Retrieve current value"};
            int paneSelection = JOptionPane.showOptionDialog(Main.frame, "How do you want to enter the start value?", "Start Value", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, paneSelectionOptions, paneSelectionOptions[1]);
            Debug.log("-- menu selection " +paneSelection);
            switch (paneSelection) {
                case 0:
                    // SELECTING price for cryptocurrency (optional)
                    input = JOptionPane.showInputDialog(Main.frame, "Price per " + value, "Add To Portfolio", JOptionPane.PLAIN_MESSAGE);
                    if (input == null) { // pressed "cancel button"
                        Debug.log("-- cancel");
                        return;
                    }

                    double price;
                    try {
                        price = Double.parseDouble(input);
                        value.portfolio_price_start = price;
                        Debug.log("-- "+price);
                    } catch(Exception ex) {
                        value.portfolio_price_start = value.price;
                        Debug.log("-- incorrect format, getting current price " + value.price);
                    }

                    value.portfolio_value = value.portfolio_price * value.portfolio_amount;
                    value.portfolio_value_start = value.portfolio_price_start * value.portfolio_amount;
                    break;
                case 1:
                    // SELECT total price
                    input = JOptionPane.showInputDialog(Main.frame, "Total value of " + value, "Add To Portfolio", JOptionPane.PLAIN_MESSAGE);
                    if (input == null) { // pressed "cancel button"
                        Debug.log("-- cancel");
                        return;
                    }

                    double valueStart;
                    try {
                        valueStart = Double.parseDouble(input);
                        value.portfolio_value = valueStart;
                        value.portfolio_value_start = value.portfolio_value;

                        value.portfolio_price_start = valueStart/value.portfolio_amount;
                        Debug.log("-- "+value.portfolio_price_start);
                    } catch(Exception ex) {
                        value.portfolio_price_start = value.price;
                        Debug.log("-- incorrect format, getting current price " + value.portfolio_price_start);

                        value.portfolio_value = value.portfolio_price_start * value.portfolio_amount;
                        value.portfolio_value_start = value.portfolio_value;
                    }

                    break;
                case 2:
                    // SELECT get current price
                    value.portfolio_price_start = value.price;
                    Debug.log("-- getting current price " + value.price);
                    value.portfolio_value_start = value.portfolio_price_start * value.portfolio_amount;
                    break;
                default:
                    Debug.log("-- cancel");
                    return;
            }


            value.portfolio_currency = Main.currency;


            // adding object to arraylist
            webData.portfolio.get(nr).add((WebData.Coin) value);
            Debug.log("Portfolio " + nr + " Added " + value);

            // serialize portfolio and re-setup the table
            serializePortfolio();
            refreshPortfolio();
            reCreate();
        }
    }

    private class bRemoveCoinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Remove Coin Clicked");

            try {
                Object[] options = webData.portfolio.get(nr).toArray();
                Object value = JOptionPane.showInputDialog(Main.frame, "Select cryptocurrency to remove", "Remove From Portfolio", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
                for (int i = 0; i < webData.portfolio.get(nr).size(); ++i) {
                    if (webData.portfolio.get(nr).get(i) == value) {
                        webData.portfolio.get(nr).remove(i);
                        Debug.log("Portfolio " + nr + " Removed " + value);
                        break; // can only be one name in each
                    }
                }
    
                serializePortfolio();
                reCreate();
            } catch(Exception ex) {
                Debug.log("Portfolio Nothing Left To Remove");
                JOptionPane.showMessageDialog(Main.frame, "Nothing Left To Remove");
            }
        }
    }

    private class bSwitchPortfolioListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Switch Portfolio Clicked");
            Object[] portfolios = names.toArray();
            Object selectedValue = JOptionPane.showInputDialog(Main.frame, "Select from the list", "Switch To Portfolio", JOptionPane.PLAIN_MESSAGE, null, portfolios, names.get(nr));
            
            if (selectedValue != null) {
                String value = (String) selectedValue;
                Debug.log("Switching To Portfolio " + value);
                for (int i = 0; i < names.size(); ++i) {
                    if (value.equals(names.get(i))) {
                        nr = i;
                    }
                }
                
                serializePortfolio();
                refreshPortfolio();
                reCreate();
            }
        }
    }

    private class bManagePortfolioListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Manage Portfolio Clicked");
            
            Object[] options = {"Rename Current", "Delete Current", "New Portfolio"};

            int selectedValue = JOptionPane.showOptionDialog(Main.frame, "Select action for the current portfolio", "Manage Portfolio", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options);
            switch (selectedValue) {
                case 0:
                    Debug.log("Renaming Portfolio " + nr);
                    String message = JOptionPane.showInputDialog(Main.frame, "Rename current portfolio", names.get(nr));

                    if (message != null) {
                        for (int i = 0; i < names.size(); ++i) {
                            if (names.get(i).equals(message)) {
                                if (i != nr) {
                                    Debug.log("Name already existst, cancelling..");
                                    JOptionPane.showMessageDialog(Main.frame, "Name already exists!");
                                    return;
                                }
                            }
                        }

                        names.set(nr, message);
                        Debug.log("-- renamed to " + message);
                    }
                    break;
                case 1:
                    Debug.log("Deleting Portfolio " + nr);
                    if (webData.portfolio.size() == 1) {
                        Debug.log("-- cancel");
                        JOptionPane.showMessageDialog(Main.frame, "You must have at least one portfolio!");
                        return;
                    }
                    webData.portfolio.remove(nr);
                    names.remove(nr);
                    
                    nr = 0;
                    Debug.log("-- completed");
                    break;
                case 2:
                    Debug.log("New Portfolio");
                    webData.portfolio.add(new ArrayList<WebData.Coin>());
                    names.add(new String("Portfolio " + webData.portfolio.size()));

                    for (int i = 0; i < names.size(); ++i) {
                        if (names.get(names.size()-1).equals(names.get(i))) {
                            if (names.size()-1 != i) names.set(names.size()-1, names.get(names.size()-1)+" ");
                        }
                    }

                    nr = webData.portfolio.size()-1;
                    Debug.log("-- completed");
                    break;
            }

            if (selectedValue == 1 || selectedValue == 2) {
                serializePortfolio();
                refreshPortfolio();
                reCreate();
            }
        }
    }

    public void serializePortfolio() {
        try {
            FileOutputStream file = new FileOutputStream(Main.portfolioSerLocation);
            BufferedOutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(webData.portfolio);
            out.writeObject(names);
            out.writeObject(nr);
            out.close();
            Debug.log("Serialized Portfolio To " + Main.portfolioSerLocation);

        } catch(Exception ex) {
            Debug.log("EXCEPTION: PanelPortfolio.java - serializePortfolio()");
            ex.printStackTrace();
        }
    }

    private boolean findPortfolioName(String name) {
        for (int i = 0; i < webData.portfolio.get(nr).size(); ++i) {
            if (webData.portfolio.get(nr).get(i).name.equals(name)) {
                return true;
            }
        }
        
        return false;
    }

    private WebData.Coin getPortfolioName(String name) {
        for (int i = 0; i < webData.coin.size(); ++i) {
            if (webData.coin.get(i).name.equals(name)) {
                return webData.coin.get(i);
            }
        }

        return webData.getCoin();
    }

    public void refreshPortfolio() {
        for (int i = 0; i < webData.portfolio.get(nr).size(); ++i) {
            WebData.Coin coin = getPortfolioName(webData.portfolio.get(nr).get(i).name);

            if (Main.currency.equals(webData.portfolio.get(nr).get(i).portfolio_currency)) {
                webData.portfolio.get(nr).get(i).portfolio_price = webData.portfolio.get(nr).get(i).portfolio_price_start;
                webData.portfolio.get(nr).get(i).portfolio_value = coin.price * webData.portfolio.get(nr).get(i).portfolio_amount;
                webData.portfolio.get(nr).get(i).portfolio_gains = webData.portfolio.get(nr).get(i).portfolio_value - webData.portfolio.get(nr).get(i).portfolio_value_start;
            } else {
                webData.portfolio.get(nr).get(i).portfolio_price *= (coin.price / webData.portfolio.get(nr).get(i).price); // convert portfolio_start_price to new currency
                webData.portfolio.get(nr).get(i).portfolio_value = coin.price * webData.portfolio.get(nr).get(i).portfolio_amount; // calculate current value of portfolio
                webData.portfolio.get(nr).get(i).portfolio_gains = webData.portfolio.get(nr).get(i).portfolio_value - (webData.portfolio.get(nr).get(i).portfolio_price * webData.portfolio.get(nr).get(i).portfolio_amount); // calculate gains
            }

            webData.portfolio.get(nr).get(i).rank = coin.rank;
            webData.portfolio.get(nr).get(i).price = coin.price;
            webData.portfolio.get(nr).get(i).price_btc = coin.price_btc;
            webData.portfolio.get(nr).get(i)._24h_volume = coin._24h_volume;
            webData.portfolio.get(nr).get(i).market_cap = coin.market_cap;
            webData.portfolio.get(nr).get(i).available_supply = coin.available_supply;
            webData.portfolio.get(nr).get(i).total_supply = coin.total_supply;
            webData.portfolio.get(nr).get(i).max_supply = coin.max_supply;
            webData.portfolio.get(nr).get(i).percent_change_1h = coin.percent_change_1h;
            webData.portfolio.get(nr).get(i).percent_change_24h = coin.percent_change_24h;
            webData.portfolio.get(nr).get(i).percent_change_7d = coin.percent_change_7d;
            webData.portfolio.get(nr).get(i).last_updated = coin.last_updated;
        }
    }
}