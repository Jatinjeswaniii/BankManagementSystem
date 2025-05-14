package bank.mangement.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.print.*;

public class MiniStatement extends JFrame {

    JLabel mini;
    MiniStatement(String pinnumber) {
        setTitle("Mini Statement");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Bank heading
        JLabel bank = new JLabel("INDIAN BANK");
        bank.setFont(new Font("System", Font.BOLD, 20));
        bank.setBounds(160, 15, 200, 30);
        add(bank);

        // Card number display
        JLabel card = new JLabel("Card Number: ");
        card.setFont(new Font("System", Font.PLAIN, 14));
        card.setBounds(30, 60, 400, 20);
        add(card);

        // Balance display
        JLabel balance = new JLabel("Balance: ");
        balance.setFont(new Font("System", Font.BOLD, 14));
        balance.setBounds(30, 90, 400, 20);
        add(balance);

        // Transaction history area
        mini = new JLabel();
        mini.setVerticalAlignment(SwingConstants.TOP);
        mini.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(mini);
        scrollPane.setBounds(30, 130, 420, 330);  // Reduced height
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Print button (moved up)
        JButton printBtn = new JButton("Print Statement");
        printBtn.setBounds(170, 480, 150, 30); // Button is now visible on laptops
        add(printBtn);

        printBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printStatement();
            }
        });

        // Fetch card number
        try {
            conn conn = new conn();
            ResultSet rs = conn.s.executeQuery("select * from login1 where pin = '" + pinnumber + "'");
            while (rs.next()) {
                String cardNumber = rs.getString("cardnumber");
                card.setText("Card Number: " + cardNumber.substring(0, 4) + "XXXXXXXX" + cardNumber.substring(12));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Fetch transaction history and balance
        try {
            conn conn = new conn();
            int bal = 0;
            StringBuilder statement = new StringBuilder("<html>");

            ResultSet rs = conn.s.executeQuery("select * from bank1 where pin = '" + pinnumber + "'");
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amt = rs.getString("amount");

                statement.append(date).append("&nbsp;&nbsp;&nbsp;")
                        .append(type).append("&nbsp;&nbsp;&nbsp;")
                        .append("Rs. ").append(amt).append("<br><br>");

                if (type.equals("Deposit")) {
                    bal += Integer.parseInt(amt.replace(",", ""));
                } else {
                    bal -= Integer.parseInt(amt.replace(",", ""));
                }
            }

            statement.append("</html>");
            mini.setText(statement.toString());
            balance.setText("Your current account balance is: Rs " + bal);
        } catch (Exception e) {
            System.out.println(e);
        }

        setSize(500, 570); // Smaller frame size for laptop
        setLocation(400, 100);
        setVisible(true);
    }

    // Print functionality
    public void printStatement() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Mini Statement");

        job.setPrintable(new Printable() {
            public int print(Graphics g, PageFormat pf, int pageIndex) {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                mini.printAll(g);
                return Printable.PAGE_EXISTS;
            }
        });

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }
}
