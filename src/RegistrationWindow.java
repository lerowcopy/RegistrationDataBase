import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistrationWindow extends JFrame {
    public JTextField first_jtx_reg = new JTextField();
    public JTextField second_jtx_reg = new JTextField();
    public JButton register = new JButton("register");
    private DataBase dataBase;
    public static RegistrationWindow instance;

    public RegistrationWindow() {
        instance = this;
        dataBase = MainWindow.instance.dataBase;

        setSize(300, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(1000, 240);


        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(first_jtx_reg, gbc);

        gbc = new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(second_jtx_reg, gbc);

        gbc = new GridBagConstraints(
                0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(register, gbc);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (dataBase.Registration()) {
                        System.out.println("Пользователь успешно зарегистрирован");
                        MainWindow.instance.window.dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

}
