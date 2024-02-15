package Application.AdditionalWindow;

import Application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ClientWindow extends JFrame {

    private JLabel welcomeLabel;

    private JLabel nameUser;
    private JButton logOutBtn;
    private GridBagConstraints gbc;

    public ClientWindow (String name){

        welcomeLabel = new JLabel(String.format("Здравствуйте, %s. Вы успешно вошли в систему", name));
        nameUser = new JLabel(name);
        logOutBtn = new JButton("LogOut");

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameUser.setHorizontalAlignment(SwingConstants.CENTER);


        setSize(400, 200);
        setLayout(new GridBagLayout());
        setLocation(560, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        gbc = new GridBagConstraints(
                0, 0, 2, 1, 25, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(nameUser, gbc);

        gbc = new GridBagConstraints(
                2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(logOutBtn, gbc);

        gbc = new GridBagConstraints(
                0, 1, 3, 1, 1, 7,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(welcomeLabel, gbc);


        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Application wnd = new Application();
                    wnd.setVisible(true);
                    dispose();
                } catch (InterruptedException | SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }
}
