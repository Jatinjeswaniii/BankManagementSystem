
package bank.mangement.system;
import javax.swing.*;
import java.awt.*;
import  java.awt.event.*;
import java.sql.SQLException;

public class PinChange extends JFrame implements ActionListener
{
    JLabel pintext,repintext,text ,image;
    JPasswordField pin,repin;
    JButton change,back;
    
    String pinnumber;
    
PinChange(String pinnumber){
    this.pinnumber = pinnumber;
    
    setLayout(null);
    
    
  ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
    Image i2 = i1.getImage().getScaledInstance(900, 900,Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    image = new JLabel(i3);
    image.setBounds(0, 0, 900, 900);
    add(image);
    
    text = new JLabel ("CHANGE YOUR PIN ");
    text.setForeground(Color.WHITE);
    text.setFont(new Font("system", Font.BOLD,16));
    text.setBounds(250,290,500,30);
    image.add(text);
    
    pintext = new JLabel ("NEW PIN ");
    pintext.setForeground(Color.WHITE);
    pintext.setFont(new Font("system", Font.BOLD,16));
    pintext.setBounds(165,360,180,30);
    image.add(pintext);
    
    pin = new JPasswordField();
    pin.setFont(new Font("raleway",Font.BOLD,25));
    pin.setBounds(330,360,180,25);
    image.add(pin);
    
    repintext = new JLabel ("RE-ENTER YOUR PIN ");
    repintext.setForeground(Color.WHITE);
    repintext.setFont(new Font("system", Font.BOLD,16));
    repintext.setBounds(160,400,180,30);
    image.add(repintext);
    
    repin = new JPasswordField();
    repin.setFont(new Font("raleway",Font.BOLD,25));
    repin.setBounds(330,400,180,25);
    image.add(repin);
    
    
    change = new JButton("Change");
    change.setBounds(355,470,150,30);
    change.addActionListener(this);
    image.add(change);
    
    back = new JButton("Back");
    back.setBounds(355,510,150,30);
    back.addActionListener(this);
    image.add(back);    
    
    setSize(900,900);
    setLocation(300,0);
    setUndecorated(true);
    setVisible(true);
    
}
    @Override
    
    public void actionPerformed(ActionEvent ae){
    
    if(ae.getSource()== change)
 try {
     String npin = pin.getText();
     String rpin = repin.getText();
     
     if(!npin.equals(rpin)){
         JOptionPane.showMessageDialog(null,"entered pin does not match");
         return;
     }
     if(npin.equals("")){
         JOptionPane.showMessageDialog(null,"Please enter pin");
         return;
     }
     if(rpin.equals("")){
         JOptionPane.showMessageDialog(null,"Please re-enter new pin");
         return;
     }
     
     conn conn = new conn() ;
                 String querry1 = "update bank1 set pin = '"+rpin+"' where pin= '"+pinnumber+"'";
                 String querry2 = "update login1 set pin = '"+rpin+"' where pin= '"+pinnumber+"'";
                 String querry3 = "update signupthree set pin = '"+rpin+"' where pin= '"+pinnumber+"'";

                 conn.s.executeUpdate(querry1);
                 conn.s.executeUpdate(querry2);
                 conn.s.executeUpdate(querry3);

         JOptionPane.showMessageDialog(null,"PIN CHANGED SUCCESSFULLY");

                        setVisible(false);
                        new Transactions(pinnumber).setVisible(true);

 }   catch(HeadlessException | SQLException e){
     System.out.println(e);
     
 
}else{
    setVisible(false);
    new Transactions(pinnumber).setVisible(true);
}
}
    public static void main(String args[]) {
        
        new PinChange("").setVisible(true);
        
    }
}
