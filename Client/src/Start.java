import view.EnterDialog;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        EnterDialog dialog = new EnterDialog();
        dialog.setTitle("Авторизация");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
