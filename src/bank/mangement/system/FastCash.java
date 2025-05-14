 package bank.mangement.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
public class FastCash extends JFrame implements ActionListener
    
{
    
    JButton amount1,amount2,amount3,amount4,amount5,amount6,exit;
    String pinnumber;
FastCash(String pinnumber){
    
    this.pinnumber = pinnumber;
    setLayout(null);
    
    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
    Image i2= i1.getImage().getScaledInstance(900, 750, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel image = new JLabel(i3);
    image.setBounds(0,0,900,650);
    add(image);
    
    
    JLabel text= new JLabel (" Select Withdrawl Amaount");
    text.setBounds(235,200,700,35);
    text.setFont(new Font("raleway",Font.BOLD,16)    );
    text.setForeground(Color.WHITE);
    image.add(text);
    
    amount1 = new JButton ("Rs. 100");
    amount1.setBounds(160,300,140,25);
    amount1.addActionListener(this);
    image.add(amount1);


    amount2 = new JButton ("Rs. 500");
    amount2.setBounds(355,300,140,25);
    amount2.addActionListener(this);
    image.add(amount2);


    amount3 = new JButton ("Rs. 1000");
    amount3.setBounds(160,330,140,25);
    amount3.addActionListener(this);
    image.add(amount3);

    amount4 = new JButton ("Rs. 2000");
    amount4.setBounds(355,330,140,25);
    amount4.addActionListener(this);
    image.add(amount4);


    amount5 = new JButton ("Rs. 5000");
    amount5.setBounds(160,360,140,25);
    amount5.addActionListener(this);
    image.add(amount5);


    amount6 = new JButton ("Rs. 10,000");
    amount6.setBounds(355,360,140,25);
    amount6.addActionListener(this);
    image.add(amount6);    

    exit = new JButton ("BACK");
    exit.setBounds(355,390,140,25);
    exit.addActionListener(this);
    image.add(exit);        
    
    setSize(900 ,900);
    setLocation(300,0);
    setUndecorated(true);
    setVisible(true);
    
    
}

  @Override
  public void actionPerformed(ActionEvent ae){
       
       if(ae.getSource()==exit){
          setVisible(false);
                     new Transactions(pinnumber).setVisible(true);

       }else{
String amount = ((JButton)ae.getSource()).getText().substring(4).trim().replace(",", "");
           conn c = new conn();
           try{
               ResultSet rs = c.s.executeQuery("select * from bank1 where pin = '"+pinnumber+"'");
               int balance = 0;
               while(rs.next()){
                   if(rs.getString("type").equals("Deposit")){
                       balance += Integer.parseInt(rs.getString("amount"));
                    }else{
                                              balance -= Integer.parseInt(rs.getString("amount"));

                   }
               
               if(ae.getSource() != exit && balance < Integer.parseInt(amount)){
            JOptionPane.showMessageDialog(null, "Insufficient Balance.");
                return;
               }
               
               }
               Date date = new Date();
               String querry = "insert into bank1 values('"+pinnumber+"','"+date+"','withdrawl','"+amount+"')";
               c.s.executeUpdate(querry);
               JOptionPane.showMessageDialog(null, "Rs "+amount+" Debited Sucessfully");
               
               setVisible(false);
               new Transactions(pinnumber).setVisible(true);
                              
           }catch(Exception e){
               System.out.println(e);
           }
               
       }
  }
    public static void main(String args[]) {
 
        new FastCash("");
        
    }
}
