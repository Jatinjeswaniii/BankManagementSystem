
package bank.mangement.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;
public class Withdrawl extends JFrame implements ActionListener
{
    
    JTextField amount;
    JButton Withdraw,back;
    String pinnumber;
    JLabel warningLabel;
    
Withdrawl(String pinnumber)
{
    this.pinnumber = pinnumber;
    setLayout(null);
    
    
    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
    Image i2 = i1.getImage().getScaledInstance(900, 900,Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel image = new JLabel(i3);
    image.setBounds(0, 0, 900, 900);
    add(image);
    
    
    
    JLabel text = new JLabel("enter the amount you want to Withdraw");
    text.setForeground(Color.WHITE);
    text.setFont(new Font("raleway",Font.BOLD,16));
    text.setBounds(170, 300, 400, 20);
    image.add(text);
    
    
    
     amount = new JTextField();
    amount.setFont(new Font("raleway" ,Font.BOLD,22));
    amount.setBounds(170,330,320,20);
    image.add(amount);    
    
    warningLabel = new JLabel("");
warningLabel.setForeground(Color.RED);
warningLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
warningLabel.setBounds(170, 360, 320, 20); // Slightly below amount field
image.add(warningLabel);

    
    Withdraw = new JButton("Withdraw");
    Withdraw.setBounds(355,485,150,30);
    Withdraw.addActionListener(this);
    image.add(Withdraw);
    
    back = new JButton("Back");
    back.setBounds(155,485,150,30);
    back.addActionListener(this);
    image.add(back);
    
    setSize(900,900);
    setLocation(300,0);
    setVisible(true);
      
}
   
 
    
    @Override
    
    public void actionPerformed(ActionEvent ae)
{
    if(ae.getSource() == Withdraw){
        
                Date date = new Date();
         String number = amount.getText().trim();

    
          if (number.equals("")) {
            warningLabel.setText("Please enter the amount to Withdraw.");
        } else if (!number.matches("\\d+")) {
            warningLabel.setText("Only numeric digits (0-9) are allowed.");
        } else {
          try {
    warningLabel.setText("");
    conn conn = new conn();

    // 1. Fetch current balance
    String query = "SELECT * FROM bank1 WHERE pin = '"+pinnumber+"'";
     ResultSet rs = conn.s.executeQuery(query);
    int balance = 0;
    while(rs.next()) {
        String type = rs.getString("type");
        int amt = Integer.parseInt(rs.getString("amount"));
        if(type.equals("Deposit")) {
            balance += amt;
        } else if(type.equals("Withdrawl")) {
            balance -= amt;
        }
    }

    int withdrawalAmount = Integer.parseInt(number);

   
    if (withdrawalAmount > balance) {
        warningLabel.setText("Insufficient balance.");
    } else {
     
        String insertQuery = "insert into bank1 values('"+pinnumber+"','"+date+"','Withdrawl', '"+number+"')";
        conn.s.executeUpdate(insertQuery);
        JOptionPane.showMessageDialog(null,"Rs. "+number+" Withdrawn successfully");
        setVisible(false);
        new Transactions(pinnumber).setVisible(true);
    }

} catch(Exception e){
    System.out.println(e);
}

        }
        
    }else if (ae.getSource() == back){
        setVisible(false);
        new Transactions(pinnumber).setVisible(true);
        
    }
}
    
    public static void main(String args[]) {

        new Withdrawl("");



    }
}
