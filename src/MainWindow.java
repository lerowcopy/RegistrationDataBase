import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class MainWindow extends JFrame {

    public JTextField first_jtf = new JTextField();
    public JTextField second_jtf = new JTextField();
    public static MainWindow instance;

    public RegistrationWindow window;

    public final DataBase dataBase = new DataBase();

    public JButton login = new JButton("Login");
    public JButton register = new JButton("Registration");

    public MainWindow() throws InterruptedException, SQLException {
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


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    switch (dataBase.login()){
                        case "OK":
                            System.out.println("Вы успешно вошли в систему.");
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
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = new RegistrationWindow();
                window.setVisible(true);
            }
        });

    }


    public static void main(String[] args) throws InterruptedException, SQLException {
        MainWindow wnd = new MainWindow();


        wnd.setVisible(true);
    }
}
