package bank.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


public class SignupTwo extends JFrame implements ActionListener {

    JComboBox<String> Religion, Category, Income, Education, Occupation;
    JTextField PanTextField, AadharTextField;
    JRadioButton syes, sno, eyes, eno;
    JButton next;
    String formno;

    SignupTwo(String formno) {
        this.formno = formno;
        setTitle("New Account Application Form - Page 2");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(Color.WHITE);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Page 2: Additional Details");
        heading.setFont(new Font("Raleway", Font.BOLD, 24));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(heading, gbc);
        gbc.gridwidth = 1;

        String[][] labelsAndOptions = {
            {"Religion", "Select","Hindu", "Muslim", "Sikh", "Christian", "Other"},
            {"Category", "General", "OBC", "SC", "ST", "Other"},
            {"Income", "NULL", "<1,50,000", "<2,50,000", "<5,00,000", "Upto 10,00,000"},
            {"Education", "Non-Graduation", "Graduation", "Post-Graduate", "Doctorate", "Others"},
            {"Occupation", "Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"},
        };

        JComboBox[] combos = new JComboBox[5];
        for (int i = 0; i < labelsAndOptions.length; i++) {
            JLabel label = new JLabel(labelsAndOptions[i][0] + " :");
            label.setFont(new Font("Raleway", Font.PLAIN, 18));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            mainPanel.add(label, gbc);

            String[] options = Arrays.copyOfRange(labelsAndOptions[i], 1, labelsAndOptions[i].length);
            combos[i] = new JComboBox<>(options);
            combos[i].setBackground(Color.WHITE);
            gbc.gridx = 1;
            mainPanel.add(combos[i], gbc);
        }

        Religion = combos[0];
        Category = combos[1];
        Income = combos[2];
        Education = combos[3];
        Occupation = combos[4];

        JLabel panLabel = new JLabel("PAN Number:");
        panLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(panLabel, gbc);

        PanTextField = new JTextField();
        gbc.gridx = 1;
        mainPanel.add(PanTextField, gbc);

        JLabel aadharLabel = new JLabel("Aadhar Number:");
        aadharLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(aadharLabel, gbc);

        AadharTextField = new JTextField();
        gbc.gridx = 1;
        mainPanel.add(AadharTextField, gbc);

        JLabel seniorLabel = new JLabel("Senior Citizen:");
        seniorLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(seniorLabel, gbc);

        JPanel seniorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        seniorPanel.setBackground(Color.WHITE);
        syes = new JRadioButton("Yes");
        sno = new JRadioButton("No");
        syes.setBackground(Color.WHITE);
        sno.setBackground(Color.WHITE);
        ButtonGroup seniorGroup = new ButtonGroup();
        seniorGroup.add(syes);
        seniorGroup.add(sno);
        seniorPanel.add(syes);
        seniorPanel.add(sno);
        gbc.gridx = 1;
        mainPanel.add(seniorPanel, gbc);

        JLabel existingLabel = new JLabel("Existing Account:");
        existingLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(existingLabel, gbc);

        JPanel existingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        existingPanel.setBackground(Color.WHITE);
        eyes = new JRadioButton("Yes");
        eno = new JRadioButton("No");
        eyes.setBackground(Color.WHITE);
        eno.setBackground(Color.WHITE);
        ButtonGroup existingGroup = new ButtonGroup();
        existingGroup.add(eyes);
        existingGroup.add(eno);
        existingPanel.add(eyes);
        existingPanel.add(eno);
        gbc.gridx = 1;
        mainPanel.add(existingPanel, gbc);

        next = new JButton("Next");
        next.setFont(new Font("Raleway", Font.BOLD, 16));
        next.setBackground(new Color(0, 7, 8));
        next.setForeground(Color.WHITE);
        next.setFocusPainted(false);
        next.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(next, gbc);

     
        add(mainPanel);
        setSize(700, 700);
        setLocation(0,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String sreligion = (String) Religion.getSelectedItem();
        String scategory = (String) Category.getSelectedItem();
        String sincome = (String) Income.getSelectedItem();
        String seducation = (String) Education.getSelectedItem();
        String soccupation = (String) Occupation.getSelectedItem();

        String seniorcitizen = syes.isSelected() ? "Yes" : "No";
        String existingaccount = eyes.isSelected() ? "Yes" : "No";
        String span = PanTextField.getText();
        String saadhar = AadharTextField.getText();

        try {
            conn c = new conn();
            String query = "INSERT INTO signuptwo VALUES('" + formno + "','" + sreligion + "','" + scategory + "','" + sincome + "','" + seducation + "','" + soccupation + "','" + span + "','" + saadhar + "','" + seniorcitizen + "','" + existingaccount + "')";
            c.s.executeUpdate(query);

            setVisible(false);
            new signupThree(formno).setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new SignupTwo("");
    }
}
