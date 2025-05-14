package bank.mangement.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SignupOne extends JFrame implements ActionListener {
    long random;
    JTextField nameTextField, fnameTextField, dobTextField, emailTextField, addressTextField, cityTextField, stateTextField, pincodeTextField;
    JButton next;
    JRadioButton male, female, other, married, unmarried;
    JDateChooser dateChooser;

    SignupOne() {
             
        Random ran = new Random();
        random = ran.nextInt(9000) + 1000;        
        
        
       JPanel header = new JPanel();
        header.setBackground(new Color(25, 118, 210));
        header.setBounds(0,0, 900, 60);
        header.setLayout(null);
        
        
        JLabel title = new JLabel("APPLICATION FORM NO. " + random);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 500, 40);
        header.add(title);
        add(header);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(0, 50, 900, 600); 
        formPanel.setBackground(Color.WHITE);
        add(formPanel);

        JLabel personaldetails = new JLabel("Page 1: Personal Details");
        personaldetails.setFont(new Font("Segoe UI", Font.BOLD, 22));
        personaldetails.setBounds(290, 10, 400, 30);
        formPanel.add(personaldetails);


        JLabel name = new JLabel("Name:");
        name.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        name.setBounds(100, 70, 100, 30);
        formPanel.add(name);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameTextField.setBounds(300, 70, 400, 30);
        formPanel.add(nameTextField);


        JLabel fName = new JLabel("Father's Name:");
        fName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        fName.setBounds(100, 120, 200, 30);
        formPanel.add(fName);

        fnameTextField = new JTextField();
        fnameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fnameTextField.setBounds(300, 120, 400, 30);
        formPanel.add(fnameTextField);


        JLabel dob = new JLabel("Date Of Birth:");
        dob.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        dob.setBounds(100, 170, 200, 30);
        formPanel.add(dob);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(300, 170, 400, 30);
        formPanel.add(dateChooser);


        JLabel gender = new JLabel("Gender:");
        gender.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gender.setBounds(100, 220, 100, 30);
        formPanel.add(gender);

        male = new JRadioButton("Male");
        male.setBounds(300, 220, 60, 30);
        male.setBackground(Color.WHITE);
        formPanel.add(male);

        female = new JRadioButton("Female");
        female.setBounds(450, 220, 120, 30);
        female.setBackground(Color.WHITE);
        formPanel.add(female);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);


        JLabel email = new JLabel("Email Address:");
        email.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        email.setBounds(100, 270, 200, 30);
        formPanel.add(email);

        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailTextField.setBounds(300, 270, 400, 30);
        formPanel.add(emailTextField);



        JLabel marital = new JLabel("Marital Status:");
        marital.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        marital.setBounds(100, 320, 200, 30);
        formPanel.add(marital);

        married = new JRadioButton("Married");
        married.setBounds(300, 320, 100, 30);
        married.setBackground(Color.WHITE);
        formPanel.add(married);

        unmarried = new JRadioButton("Unmarried");
        unmarried.setBounds(450, 320, 120, 30);
        unmarried.setBackground(Color.WHITE);
        formPanel.add(unmarried);

        other = new JRadioButton("Other");
        other.setBounds(630, 320, 120, 30);
        other.setBackground(Color.WHITE);
        formPanel.add(other);

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(married);
        maritalGroup.add(unmarried);
        maritalGroup.add(other);


        JLabel address = new JLabel("Address:");
        address.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        address.setBounds(100, 370, 200, 30);
        formPanel.add(address);

        addressTextField = new JTextField();
        addressTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addressTextField.setBounds(300, 370, 400, 30);
        formPanel.add(addressTextField);


        JLabel city = new JLabel("City:");
        city.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        city.setBounds(100, 420, 100, 30);
        formPanel.add(city);

        cityTextField = new JTextField();
        cityTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cityTextField.setBounds(300, 420, 400, 30);
        formPanel.add(cityTextField);


        JLabel state = new JLabel("State:");
        state.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        state.setBounds(100, 470, 100, 30);
        formPanel.add(state);

        stateTextField = new JTextField();
        stateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        stateTextField.setBounds(300, 470, 400, 30);
        formPanel.add(stateTextField);


        JLabel pincode = new JLabel("Pincode:");
        pincode.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        pincode.setBounds(100, 520, 200, 30);
        formPanel.add(pincode);

        pincodeTextField = new JTextField();
        pincodeTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pincodeTextField.setBounds(300, 520, 400, 30);
        formPanel.add(pincodeTextField);


        next = new JButton("Next");
        next.setBackground(new Color(33, 150, 243));
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Segoe UI", Font.BOLD, 16));
        next.setBounds(620, 580, 100, 40);
        next.setFocusPainted(false);
        next.addActionListener(this);
        formPanel.add(next);


        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
        setSize(900, 700);
        setLocation(0,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    
    public void actionPerformed(ActionEvent ae) {
        String formno = "" + random;
        String name = nameTextField.getText();
        String fname = fnameTextField.getText();
        String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = null;
        if (male.isSelected()) {
            gender = "male";
        } else if (female.isSelected()) {
            gender = "female";
        }

        String email = emailTextField.getText();
        String marital = null;
        if (married.isSelected()) {
            marital = "married";
        } else if (unmarried.isSelected()) {
            marital = "unmarried";
        } else if (other.isSelected()) {
            marital = "other";
        }

        String address = addressTextField.getText();
        String city = cityTextField.getText();
        String state = stateTextField.getText();
        String pincode = pincodeTextField.getText();

        try {
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Name is required");
            } else if (state.equals("")) {
                JOptionPane.showMessageDialog(null, "State is required");
            } else if (fname.equals("")) {
                JOptionPane.showMessageDialog(null, "Father's name is required");
            } else if (email.equals("") || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
    JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            }else {
                conn c = new conn();
                String query = "INSERT INTO signup VALUES('" + formno + "','" + name + "','" + fname + "','" + dob + "','" + gender + "','" + email + "','" + marital + "','" + address + "','" + city + "','" + pincode + "','" + state + "')";
                c.s.executeUpdate(query);

                setVisible(false);
                new SignupTwo(formno).setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        new SignupOne();
    }
}