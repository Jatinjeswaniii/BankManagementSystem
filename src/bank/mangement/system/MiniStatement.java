package bank.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MiniStatement extends JFrame {
    
    JLabel mini, card, balance;
    private String pinnumber;
    
    MiniStatement(String pinnumber) {
        this.pinnumber = pinnumber;
        setTitle("Mini Statement");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        // Bank heading
        JLabel bank = new JLabel("INDIAN BANK");
        bank.setFont(new Font("System", Font.BOLD, 20));
        bank.setBounds(160, 15, 200, 30);
        add(bank);
        
        // Card number display
        card = new JLabel("Card Number: ");
        card.setFont(new Font("System", Font.PLAIN, 14));
        card.setBounds(30, 60, 400, 20);
        add(card);
        
        // Balance display
        balance = new JLabel("Balance: ");
        balance.setFont(new Font("System", Font.BOLD, 14));
        balance.setBounds(30, 90, 400, 20);
        add(balance);
        
        // Transaction history area
        mini = new JLabel();
        mini.setVerticalAlignment(SwingConstants.TOP);
        mini.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(mini);
        scrollPane.setBounds(30, 130, 420, 330);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
        
        // Filter dropdown
        String[] filters = {"All Transactions", "Deposits", "Withdrawals"};
        JComboBox<String> filterCombo = new JComboBox<>(filters);
        filterCombo.setBounds(30, 480, 200, 25);
        filterCombo.addActionListener(e -> loadTransactions(pinnumber, (String)filterCombo.getSelectedItem()));
        add(filterCombo);
        
        // Print button
        JButton printBtn = new JButton("Print Statement");
        printBtn.setBounds(250, 480, 150, 25);
        printBtn.addActionListener(e -> printStatement());
        add(printBtn);
        
        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(410, 480, 80, 25);
        backBtn.addActionListener(e -> {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        });
        add(backBtn);
        
        // Load initial transactions
        loadTransactions(pinnumber, "All Transactions");
        
        setSize(500, 550);
        setLocation(400, 100);
        setVisible(true);
    }
    
    private void loadTransactions(String pinnumber, String filterType) {
        try {
            conn conn = new conn();
            int bal = 0;
            StringBuilder statement = new StringBuilder("<html>");
            
            ArrayList<Transaction> transactions = new ArrayList<>();
            
            ResultSet rs = conn.s.executeQuery("SELECT * FROM bank1 WHERE pin = '" + pinnumber + "'");
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");
                
                if (filterType.equals("All Transactions") || 
                    (filterType.equals("Deposits") && type.equalsIgnoreCase("Deposit")) ||
                    (filterType.equals("Withdrawals") && type.equalsIgnoreCase("Withdrawl"))) {
                    
                    transactions.add(new Transaction(date, type, amount));
                }
            }
            
            // Sort by date (newest first)
            Collections.sort(transactions, (t1, t2) -> t2.date.compareTo(t1.date));
            
            // Add to statement
            for (Transaction t : transactions) {
                statement.append(t.date).append("&nbsp;&nbsp;&nbsp;")
                        .append(t.type).append("&nbsp;&nbsp;&nbsp;")
                        .append("Rs. ").append(t.amount).append("<br><br>");
                
                if (t.type.equals("Deposit")) {
                    bal += Integer.parseInt(t.amount.replace(",", ""));
                } else {
                    bal -= Integer.parseInt(t.amount.replace(",", ""));
                }
            }
            
            statement.append("</html>");
            mini.setText(statement.toString());
            balance.setText("Current Balance: Rs " + bal);
            
            // Fetch and display card number
            rs = conn.s.executeQuery("SELECT cardnumber FROM login1 WHERE pin = '" + conn.hashPin(pinnumber) + "'");
            if (rs.next()) {
                String cardNumber = rs.getString("cardnumber");
                card.setText("Card: " + cardNumber.substring(0, 4) + "XXXXXXXX" + cardNumber.substring(12));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void printStatement() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Mini Statement");
        
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            // Print header
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("INDIAN BANK - Mini Statement", 100, 20);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(card.getText(), 100, 40);
            g2d.drawString(balance.getText(), 100, 60);
            
            // Print transactions
            String[] lines = mini.getText().replace("<html>", "").replace("</html>", "").split("<br><br>");
            int y = 80;
            for (String line : lines) {
                g2d.drawString(line.replace("&nbsp;&nbsp;&nbsp;", "   "), 100, y);
                y += 20;
                if (y > pageFormat.getImageableHeight()) break;
            }
            
            return Printable.PAGE_EXISTS;
        });
        
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, "Printing failed: " + e.getMessage());
            }
        }
    }
    
    private class Transaction {
        String date;
        String type;
        String amount;
        
        public Transaction(String date, String type, String amount) {
            this.date = date;
            this.type = type;
            this.amount = amount;
        }
    }
    
    public static void main(String[] args) {
        new MiniStatement("");
    }
}