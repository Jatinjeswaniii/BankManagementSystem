package bank.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Transactions extends JFrame implements ActionListener {
    
    JButton deposit, withdrawl, fastcash, ministatement, pinchange, balanceenquiry, exit;
    String pinnumber;
    private Timer inactivityTimer;
    
    Transactions(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 750, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 650);
        add(image);
        
        JLabel text = new JLabel("Please Select Your Transaction");
        text.setBounds(235, 200, 700, 35);
        text.setFont(new Font("Raleway", Font.BOLD, 16));
        text.setForeground(Color.WHITE);
        image.add(text);
        
        deposit = new JButton("Deposit");
        deposit.setBounds(160, 300, 140, 25);
        deposit.addActionListener(this);
        image.add(deposit);
        
        withdrawl = new JButton("Cash Withdrawl");
        withdrawl.setBounds(355, 300, 140, 25);
        withdrawl.addActionListener(this);
        image.add(withdrawl);
        
        fastcash = new JButton("Fast Cash");
        fastcash.setBounds(160, 330, 140, 25);
        fastcash.addActionListener(this);
        image.add(fastcash);
        
        ministatement = new JButton("Mini Statement");
        ministatement.setBounds(355, 330, 140, 25);
        ministatement.addActionListener(this);
        image.add(ministatement);
        
        pinchange = new JButton("Pin Change");
        pinchange.setBounds(160, 360, 140, 25);
        pinchange.addActionListener(this);
        image.add(pinchange);
        
        balanceenquiry = new JButton("Balance Enquiry");
        balanceenquiry.setBounds(355, 360, 140, 25);
        balanceenquiry.addActionListener(this);
        image.add(balanceenquiry);
        
        exit = new JButton("Exit");
        exit.setBounds(355, 390, 140, 25);
        exit.addActionListener(this);
        image.add(exit);
        
        // Add mouse and key listeners for activity detection
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                resetInactivityTimer();
            }
        });
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                resetInactivityTimer();
            }
        });
        
        // Start inactivity timer
        startInactivityTimer();
        
        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    private void startInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.cancel();
        }
        
        inactivityTimer = new Timer();
        inactivityTimer.schedule(new TimerTask() {
            public void run() {
                JOptionPane.showMessageDialog(null, "Session timed out due to inactivity");
                System.exit(0);
            }
        }, 5 * 60 * 1000); // 5 minutes timeout
    }
    
    private void resetInactivityTimer() {
        startInactivityTimer();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        resetInactivityTimer(); // Reset timer on any button click
        
        if (ae.getSource() == exit) {
            System.exit(0);
        } else if (ae.getSource() == deposit) {
            setVisible(false);
            new Deposit(pinnumber).setVisible(true);
        } else if (ae.getSource() == withdrawl) {
            setVisible(false);
            new Withdrawl(pinnumber).setVisible(true);
        } else if (ae.getSource() == fastcash) {
            setVisible(false);
            new FastCash(pinnumber).setVisible(true);
        } else if (ae.getSource() == pinchange) {
            setVisible(false);
            new PinChange(pinnumber).setVisible(true);
        } else if (ae.getSource() == balanceenquiry) {
            setVisible(false);
            new BalanceEnquiry(pinnumber).setVisible(true);
        } else if (ae.getSource() == ministatement) {
            new MiniStatement(pinnumber).setVisible(true);
        }
    }
    
    public static void main(String args[]) {
        new Transactions("");
    }
}