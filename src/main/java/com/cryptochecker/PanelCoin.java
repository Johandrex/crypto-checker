package com.cryptochecker;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

public class PanelCoin {
    public JPanel panel;

    private JTable table;
    private TableRenderer renderer;
    private DefaultTableCellRenderer headerRenderer;
    private TableModel model;
    private JScrollPane pane;

    private TableRowSorter<TableModel> rowSorter;
    private JTextField headerSearchField;

    private final int rowHeight = 40;
    private final int amountHeaderButtons = 1;
    private final int rightHeaderFilling = 50;
    private final int headerSearchSize = 15;
    private final Dimension searchFieldSize = new Dimension(Main.panelWidth*2, Main.panelHeight-10);

    public PanelCoin() {
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        model = new TableModel(); // table model

        // rendering columns+rows
        renderer = new TableRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        table = new JTable(model); // create table with data
        table.setDefaultRenderer(Integer.class, renderer);
        table.setDefaultRenderer(Double.class, renderer);
        table.setDefaultRenderer(Short.class, renderer);

        // render header        
        headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(Main.tableHeaderSize);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        // table settings
        table.setRowHeight(rowHeight);
        table.getColumnModel().getColumn(0).setMaxWidth(50); // rank
        table.getColumnModel().getColumn(3).setMaxWidth(100); // 1h
        table.getColumnModel().getColumn(4).setMaxWidth(100); // 24h
        table.getColumnModel().getColumn(5).setMaxWidth(100); // 7d

        table.setFont(Main.tableFont);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(Main.tableIntercellSpacing);
        table.setFocusable(false);

        table.setDefaultRenderer(Object.class, renderer); // add renderer
        table.addMouseListener(new TableMouseListener()); // Mouse Listener
        
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
        bRefresh.setMinimumSize(new Dimension(Main.panelWidth, Main.panelHeight+1));

        headerButtons.add(bRefresh);

        // HEADER FILLING
        JPanel headerFilling1 = new JPanel();
        headerFilling1.setLayout(new BorderLayout());
        headerFilling1.setMaximumSize(new Dimension((int) Main.screenResolution.getWidth(), Main.panelHeight-1));
        headerFilling1.setBackground(Color.GRAY);

        JPanel headerFilling2 = new JPanel();
        headerFilling2.setLayout(new BorderLayout());
        headerFilling2.setMaximumSize(new Dimension(rightHeaderFilling, Main.panelHeight-1));
        headerFilling2.setBackground(Color.GRAY);

        // SEARCH HEADER
        JPanel headerSearch = new JPanel();
        headerSearch.setLayout(new BoxLayout(headerSearch, BoxLayout.X_AXIS));
        headerSearch.setMaximumSize(searchFieldSize);
        headerSearch.setBackground(Color.GRAY);

        headerSearchField = new JTextField("", headerSearchSize);
        headerSearch.add(headerSearchField);

        // sorting
        rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        
        headerSearchField.getDocument().addDocumentListener(new SearchDocumentListener());

        // add headers together
        headerTop.add(headerButtons);
        headerTop.add(headerFilling1);
        headerTop.add(headerSearch);
        headerTop.add(headerFilling2);

        // scrolling pane, add JScrollPane to JPanel
        pane = new JScrollPane(table);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(headerTop);
        panel.add(pane);

        themeSwitch(); // select theme color
    }

    public void reCreate() {
        model.list = Main.gui.webData.coin;

        Main.frame.getContentPane().revalidate();
        Main.frame.getContentPane().repaint();
    }

    private class SearchDocumentListener implements DocumentListener {
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
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class TableMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            if (table.getSelectedColumn() < 0 || table.getSelectedRow() < 0) // if nothing is selected or if user right clicks
                return;

            int columnNr = table.convertColumnIndexToModel(table.getSelectedColumn());
            int rowNr = table.convertRowIndexToModel(table.getSelectedRow());
            Debug.log("Table Coin, Row " + rowNr + ", Column " + columnNr);
            JOptionPane.showMessageDialog(Main.frame, Main.gui.webData.coin.get(rowNr).getInfo(), Main.gui.webData.coin.get(rowNr).toString(), JOptionPane.PLAIN_MESSAGE);
            table.clearSelection();
        }

        public void mouseExited(MouseEvent e) { }
        public void mouseEntered(MouseEvent ev) { }
        public void mouseReleased(MouseEvent ev) { }
        public void mouseClicked(MouseEvent ev) { }
        
    }

    private class TableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {"#", "Name", "Value", "1h", "24h", "7d", "Market Cap"};
        public ArrayList<WebData.Coin> list = Main.gui.webData.coin;

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
                    return Short.class;
                case 1:
                    return String.class;
                case 2:
                    return Double.class;
                case 3:
                    return Double.class;
                case 4:
                    return Double.class;
                case 5:
                    return Double.class;
                case 6:
                    return Integer.class;
                default:
                    return String.class;
            }
        }
        
        public Object getValueAt(int row, int col) {
            Object value = null;
            switch (col) {
                case 0:
                    value = list.get(row).rank;
                    break;
                case 1:
                    value = list.get(row).name;
                    break;
                case 2:
                    value = list.get(row).price;
                    break;
                case 3:
                    value = list.get(row).percent_change_1h;
                    break;
                case 4:
                    value = list.get(row).percent_change_24h;
                    break;
                case 5:
                    value = list.get(row).percent_change_7d;
                    break;
                case 6:
                    value = list.get(row).market_cap;
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

            if (column == 2) {
                decimalFormatSymbols.setCurrencySymbol(Main.currencyChar);
                ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
                value = nf.format(value).trim();
            }

            if (column == 3 || column == 4 || column == 5) {
                if (0 <= (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.green);
                }
                else if (0 > (Double) table.getValueAt(row,column)) {
                    super.setForeground(Main.theme.red);
                }

                value = value+"%";
            }

            if (column == 6) {
                value = NumberFormat.getNumberInstance().format(value);
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public void themeSwitch() {
        pane.getViewport().setBackground(Main.theme.emptyBackground);

        table.setSelectionForeground(Main.theme.selection);
        table.setSelectionBackground(Main.theme.background);
        table.setBackground(Main.theme.background);
        
        table.getTableHeader().setBackground(Main.theme.background);
        headerRenderer.setForeground(Main.theme.foreground);
    }

    private class bRefreshListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Debug.log("Button Refresh Clicked");
            new WebData.RefreshCoins();
        }
    }
}
