package bank.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;

public class signupThree extends JFrame implements ActionListener {

    String formno;
    JRadioButton r1, r2, r3, r4;
    JCheckBox c1, c2, c3, c4, c5, c6, c7;
    JButton submit, cancel;

    public signupThree(String formno) {
        this.formno = formno;
        setTitle("Account Details");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel("Page 3: Account Details");
        heading.setFont(new Font("Raleway", Font.BOLD, 24));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(heading);

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(createAccountTypePanel());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createCardPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createPinPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createServicesPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createButtons());

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createAccountTypePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Account Type"));
        panel.setBackground(Color.WHITE);

        r1 = new JRadioButton("Saving Account");
        r2 = new JRadioButton("Current Account");
        r3 = new JRadioButton("Fixed Deposit Account");
        r4 = new JRadioButton("Recurring Deposit Account");

        ButtonGroup group = new ButtonGroup();
        group.add(r1); group.add(r2); group.add(r3); group.add(r4);

        for (JRadioButton rb : new JRadioButton[]{r1, r2, r3, r4}) {
            rb.setFont(new Font("Raleway", Font.PLAIN, 16));
            rb.setBackground(Color.WHITE);
            panel.add(rb);
        }

        return panel;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);

        JPanel labels = new JPanel(new GridLayout(2, 1));
        labels.setBackground(Color.WHITE);
        JLabel card = new JLabel("Card Number");
        card.setFont(new Font("Raleway", Font.BOLD, 18));
        JLabel info = new JLabel("Your 16-digit card number");
        info.setFont(new Font("Raleway", Font.PLAIN, 12));
        labels.add(card);
        labels.add(info);

        JLabel number = new JLabel("XXXX-XXXX-XXXX-4184");
        number.setFont(new Font("Raleway", Font.BOLD, 18));

        panel.add(labels, BorderLayout.WEST);
        panel.add(number, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPinPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);

        JPanel labels = new JPanel(new GridLayout(2, 1));
        labels.setBackground(Color.WHITE);
        JLabel pin = new JLabel("PIN");
        pin.setFont(new Font("Raleway", Font.BOLD, 18));
        JLabel info = new JLabel("Your 4-digit PIN");
        info.setFont(new Font("Raleway", Font.PLAIN, 12));
        labels.add(pin);
        labels.add(info);

        JLabel pnumber = new JLabel("XXXX");
        pnumber.setFont(new Font("Raleway", Font.BOLD, 18));

        panel.add(labels, BorderLayout.WEST);
        panel.add(pnumber, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Services Required"));
        panel.setBackground(Color.WHITE);

        c1 = new JCheckBox("ATM Card");
        c2 = new JCheckBox("Internet Banking");
        c3 = new JCheckBox("Mobile Banking");
        c4 = new JCheckBox("Email and SMS Alerts");
        c5 = new JCheckBox("Cheque Book");
        c6 = new JCheckBox("E-Statement");
        c7 = new JCheckBox("I hereby declare that the above details are correct");

        JCheckBox[] all = {c1, c2, c3, c4, c5, c6, c7};
        for (JCheckBox cb : all) {
            cb.setFont(new Font("Raleway", Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
            panel.add(cb);
        }

        return panel;
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        cancel = new JButton("Cancel");
        submit = new JButton("Submit");

        for (JButton btn : new JButton[]{cancel, submit}) {
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Raleway", Font.BOLD, 14));
            btn.addActionListener(this);
            btn.setFocusPainted(false);
            panel.add(btn);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String accountType = "";
            if (r1.isSelected()) accountType = "Saving Account";
            else if (r2.isSelected()) accountType = "Current Account";
            else if (r3.isSelected()) accountType = "Fixed Deposit Account";
            else if (r4.isSelected()) accountType = "Recurring Deposit Account";

            if (accountType.equals("")) {
                JOptionPane.showMessageDialog(null, "Please select account type.");
                return;
            }

            if (!c7.isSelected()) {
                JOptionPane.showMessageDialog(null, "Please confirm the declaration.");
                return;
            }

            Random random = new Random();
            long suffix = random.nextInt(90000000) + 10000000L;
            long cardnumber = 5040936000000000L + suffix;
            int pinnumber = random.nextInt(9000) + 1000;

            StringBuilder facility = new StringBuilder();
            if (c1.isSelected()) facility.append("ATM Card, ");
            if (c2.isSelected()) facility.append("Internet Banking, ");
            if (c3.isSelected()) facility.append("Mobile Banking, ");
            if (c4.isSelected()) facility.append("Email and SMS Alerts, ");
            if (c5.isSelected()) facility.append("Cheque Book, ");
            if (c6.isSelected()) facility.append("E-Statement, ");

            try {
                conn conn = new conn();
                String q1 = "INSERT INTO signupthree VALUES('" + formno + "','" + accountType + "','" + cardnumber + "','" + pinnumber + "','" + facility.toString() + "')";
                String q2 = "INSERT INTO login1 VALUES('" + formno + "','" + cardnumber + "','" + conn.hashPin(String.valueOf(pinnumber)) + "')";

                conn.s.executeUpdate(q1);
                conn.s.executeUpdate(q2);

                // Send email notification if selected
                if (c4.isSelected()) {
                    sendAccountCreationEmail(cardnumber, pinnumber);
                }

                JOptionPane.showMessageDialog(null, "Account Created Successfully!\nCard Number: " + cardnumber + "\nPIN: " + pinnumber);
                setVisible(false);
                new Login().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }

    private void sendAccountCreationEmail(long cardNumber, int pin) {
        new Thread(() -> {
            try {
                // Get user email from database
                conn c = new conn();
                ResultSet rs = c.s.executeQuery("SELECT email FROM signup WHERE formno = '" + formno + "'");
                if (rs.next()) {
                    String to = rs.getString("email");

                    // Email configuration (replace with your SMTP details)
                    String host = "smtp.gmail.com";
                    String username = "yourbankemail@gmail.com";
                    String password = "yourpassword";

                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    message.setSubject("Your New Bank Account Details");

                    String msg = "Dear Customer,\n\n" +
                                "Your new account has been successfully created with the following details:\n\n" +
                                "Account Number: " + formno + "\n" +
                                "Card Number: " + cardNumber + "\n" +
                                "PIN: " + pin + "\n\n" +
                                "Please keep this information secure. Do not share your PIN with anyone.\n\n" +
                                "Thank you for choosing our bank!\n\n" +
                                "Regards,\nBank Team";

                    message.setText(msg);

                    Transport.send(message);
                }
            } catch (Exception e) {
                System.out.println("Email sending failed: " + e.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        new signupThree("");
    }
}
