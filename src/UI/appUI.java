package UI;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class appUI extends JFrame {
    private JPanel Manager;
    private JPanel sideTabs;
    private JLabel title;
    private JPanel titleTabs;
    private JPanel tabs;
    private JButton tarvelsButton;
    private JPanel panelLayout;
    private JPanel destinyPanel;
    private JPanel editDestinyPanel;
    private JPanel tripsPanel;
    private JButton editDestinyButton;
    private JButton travelsButton;
    private JPanel Destinytop;
    private JPanel destiny_Flow;
    private JPanel destiny_File;
    private JLabel destiny_Image_File;
    private JLabel DestinyLabel;

    private final CardLayout cardLayout;

    public appUI() {

        cardLayout = (CardLayout) (panelLayout.getLayout());
        panelLayout.setLayout(cardLayout);

        setContentPane(Manager);
        setTitle("Navigo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        UIManager.put("Button.select", new Color(0x436850));
        tarvelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) panelLayout.getLayout()).show(panelLayout, "destiny");
            }
        });
        editDestinyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) panelLayout.getLayout()).show(panelLayout, "editDestiny");
            }
        });
        travelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) panelLayout.getLayout()).show(panelLayout, "trips");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
