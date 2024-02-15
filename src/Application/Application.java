package Application;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import Application.AdditionalWindow.Admin.AdminWindow;
import Application.AdditionalWindow.ClientWindow;
import Application.AdditionalWindow.RegistrationWindow;
import Application.DataBase.DataBase;

public class Application extends JFrame {

    public JTextField first_jtf = new JTextField();
    public JTextField second_jtf = new JTextField();
    public static Application instance;

    public RegistrationWindow windowR;

    public final DataBase dataBase = new DataBase();

    public JButton login = new JButton("Login");
    public JButton register = new JButton("Registration");

    public Application() throws InterruptedException, SQLException {
        instance = this;
        setSize(300, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(first_jtf, gbc);
        gbc = new GridBagConstraints(
                0, 1, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(second_jtf, gbc);

        gbc = new GridBagConstraints(
                0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(login, gbc);

        gbc = new GridBagConstraints(
                1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(register, gbc);

        dataBase.connect();


        login.addActionListener(e -> {
            try{
                switch (dataBase.login()){
                    case "admin":
                        AdminWindow wnd = new AdminWindow();
                        wnd.setVisible(true);
                        dispose();
                        break;
                    case "OK":
                        ClientWindow windowClient = new ClientWindow(first_jtf.getText());
                        windowClient.setVisible(true);
                        dispose();
                        break;
                    case "IL":
                        System.out.println("Вы неверно ввели имя пользователя, попробуй ещё раз.");
                        first_jtf.setText("");
                        second_jtf.setText("");
                        break;
                    case "IP":
                        second_jtf.setText("");
                        System.out.println("Вы неверное ввели пароль, попробуй ещё раз.");
                        break;
                }
            }catch (SQLException l){
                System.out.println(l.getMessage());
            }
        });

        register.addActionListener(e -> {
            windowR = new RegistrationWindow();
            windowR.setVisible(true);
        });

    }


    public static void main(String[] args) throws InterruptedException, SQLException {
        Application wnd = new Application();


        wnd.setVisible(true);
    }
}
