package view;

import controller.Controller;

import javax.swing.*;

public class RegistrationUsers extends JFrame{
    private JPanel contentPane;
    private JTextField login_tf;
    private JButton buttonRegistration;
    private JButton buttonCanselRegistration;
    private JPasswordField passwordField;
    private JPasswordField returnPasswordField;

    public RegistrationUsers(){
        setContentPane(contentPane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonRegistration.setActionCommand("registrationUsers");
        buttonRegistration.addActionListener(Controller.getInstance());
        buttonCanselRegistration.setActionCommand("backToAuthorization");
        buttonCanselRegistration.addActionListener(Controller.getInstance());
    }

    public JTextField getLogin_tf() {
        return login_tf;
    }

    public JButton getButtonRegistration() {
        return buttonRegistration;
    }

    public JButton getButtonCanselRegistration() {
        return buttonCanselRegistration;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getReturnPasswordField() {
        return returnPasswordField;
    }
}
