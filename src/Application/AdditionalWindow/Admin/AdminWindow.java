package Application.AdditionalWindow.Admin;

import Application.AdditionalWindow.Admin.User.UserInformationWindow;
import Application.Application;
import Application.DataBase.DataBase;
import Application.DataBase.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminWindow extends JFrame {
    private final DataBase dataBase = Application.instance.dataBase;
    private final JLabel nameUser = new JLabel("admin");
    private final JLabel usersLabel = new JLabel("User's list:");
    private final JLabel firstNameFilter = new JLabel("Filter. First Name: ");
    private final JLabel secondNameFilter = new JLabel("Filter. Second Name: ");
    private final JButton logOutBtn = new JButton("Log out");
    public JTextField firstNameFilterEntry = new JTextField();
    public JTextField secondNameFilterEntry = new JTextField();
    public JPanel filterPanel = new JPanel();
    public JPanel panelForUsers = new JPanel();
    public JScrollPane scrollPane = new JScrollPane(panelForUsers);
    public List<JButton> usersBtn = new ArrayList<>();

    
    
    public AdminWindow () throws SQLException {

        CreateWindow();

        AddActionForComponent();

    }

    private void AddActionForComponent() {
        logOutBtn.addActionListener(e -> {
            dispose();
            try {
                Application wnd = new Application();
                wnd.setVisible(true);
            } catch (InterruptedException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        firstNameFilterEntry.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !firstNameFilterEntry.getText().isEmpty()){

                    List<String> usersLogin = Person.filter(Person.FIRST_NAME_FILTER, firstNameFilterEntry.getText());
                    assert usersLogin != null;
                    FirstNameFilter(usersLogin, usersBtn, panelForUsers);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER && firstNameFilterEntry.getText().isEmpty()){
                    for (JButton jButton : usersBtn) {
                        panelForUsers.add(jButton);
                    }
                    panelForUsers.updateUI();
                }
            }
        });

        secondNameFilterEntry.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !secondNameFilterEntry.getText().isEmpty()){

                    List<String> usersLogin = Person.filter(Person.SECOND_NAME_FILTER, secondNameFilterEntry.getText());
                    assert usersLogin != null;
                    FirstNameFilter(usersLogin, usersBtn, panelForUsers);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER && secondNameFilterEntry.getText().isEmpty()){
                    for (JButton jButton : usersBtn) {
                        panelForUsers.add(jButton);
                    }
                    panelForUsers.updateUI();
                }
            }
        });
    }

    private void CreateWindow() throws SQLException {
        nameUser.setHorizontalAlignment(SwingConstants.CENTER);

        List<String> usersName = dataBase.getListName();


        filterPanel.setLayout(new GridLayout(2, 1));
        panelForUsers.setLayout(new GridLayout(0, 1));
        panelForUsers.setAutoscrolls(true);

        int currentCountUsers = usersName.size();

        ActionListener btnAL = e -> {
            try {
                String name = ((JButton)e.getSource()).getText();
                Person user = Person.instance.get_by_name(name);
                UserInformationWindow wnd = new UserInformationWindow(user);
                wnd.setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };


        setSize(750, 300);
        setLayout(new GridBagLayout());
        setLocation(560, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Admin panel");

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 25, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(nameUser, gbc);

        gbc = new GridBagConstraints(
                2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 10, 1, 1), 0, 0
        );

        add(logOutBtn, gbc);


        gbc = new GridBagConstraints(
                6, 1, 1, 1, 1, 1,
                GridBagConstraints.PAGE_START, GridBagConstraints.BOTH,
                new Insets(5, 1, 5, 1), 0, 0
        );

        filterPanel.add(firstNameFilter);
        filterPanel.add(firstNameFilterEntry);
        filterPanel.add(secondNameFilter);
        filterPanel.add(secondNameFilterEntry);

        add(filterPanel, gbc);

        gbc = new GridBagConstraints(
                0, 1, 4, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 1, 5, 1), 0, 0
        );

        add(usersLabel, gbc);

        for (int i = 0; i < currentCountUsers; ++i){
            JButton btn = new JButton(usersName.get(i));
            btn.setSize(new Dimension(300, 150));

            btn.addActionListener(btnAL);

            usersBtn.add(btn);

            panelForUsers.add(usersBtn.get(i));

        }

        gbc = new GridBagConstraints(
                0, 2, 4, 5, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(scrollPane, gbc);
    }

    private static void FirstNameFilter(List<String> usersLogin, List<JButton> usersBtn, JPanel panelForUsers) {
        List<Integer> indexFilter = new ArrayList<>();

        assert usersLogin != null;
        for (String s : usersLogin) {
            for (int j = 0; j < usersBtn.size(); ++j) {
                if (Objects.equals(usersBtn.get(j).getText(), s)) {
                    indexFilter.add(j);
                }
            }
        }
        for (JButton btn : usersBtn){
            panelForUsers.remove(btn);
        }

        for (Integer integer : indexFilter) {
            panelForUsers.add(usersBtn.get(integer));
        }

        panelForUsers.updateUI();
    }
}
