package bank.mangement.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame implements ActionListener {
    
    JButton login, signup, clear;
    JTextField cardTextField;
    JPasswordField pinTextField;
    private Map<String, Integer> failedAttempts = new HashMap<>();
    
    Login() {
        setTitle("AUTOMATIC TELLER MACHINE");
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70, 10, 100, 100);
        add(label);
        
        JLabel text = new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward", Font.BOLD, 38));
        text.setBounds(200, 40, 600, 40);
        add(text);
        
        JLabel cardno = new JLabel("CARD NO.:");
        cardno.setFont(new Font("Raleway", Font.BOLD, 28));
        cardno.setBounds(120, 150, 400, 30);
        add(cardno);
        
        cardTextField = new JTextField();
        cardTextField.setBounds(300, 150, 230, 30);
        cardTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        add(cardTextField);
        
        JLabel pin = new JLabel("PIN:");
        pin.setFont(new Font("Raleway", Font.BOLD, 28));
        pin.setBounds(120, 220, 250, 40);
        add(pin);
        
        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 220, 230, 30);
        pinTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        add(pinTextField);
        
        login = new JButton("SIGN IN");
        login.setBounds(300, 300, 100, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        clear = new JButton("CLEAR");
        clear.setBounds(430, 300, 100, 30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        signup = new JButton("SIGN UP");
        signup.setBounds(300, 350, 230, 30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setVisible(true);
        setLocation(400, 200);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            pinTextField.setText("");
            cardTextField.setText("");
        } else if (ae.getSource() == login) {
            String cardnumber = cardTextField.getText();
            String pinnumber = new String(pinTextField.getPassword());
            
            // Check for account lock
            if (failedAttempts.containsKey(cardnumber)) {
                if (failedAttempts.get(cardnumber) >= 3) {
                    JOptionPane.showMessageDialog(null, "Account locked. Too many failed attempts. Please contact support.");
                    return;
                }
            }
            
            conn conn = new conn();
            String hashedPin = conn.hashPin(pinnumber);
            String query = "SELECT * FROM login1 WHERE cardnumber = '" + cardnumber + "' AND pin = '" + hashedPin + "'";
            
            try {
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    failedAttempts.remove(cardnumber); // Reset on successful login
                    setVisible(false);
                    new Transactions(pinnumber).setVisible(true);
                } else {
                    // Track failed attempts
                    int attempts = failedAttempts.getOrDefault(cardnumber, 0) + 1;
                    failedAttempts.put(cardnumber, attempts);
                    
                    if (attempts >= 3) {
                        JOptionPane.showMessageDialog(null, "Account locked. Too many failed attempts.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN. Attempts left: " + (3 - attempts));
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new SignupOne().setVisible(true);
        }
    }
    
    public static void main(String args[]) {
        new Login();
    }
}