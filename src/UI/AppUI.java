package UI;

import src.Classes.Data;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AppUI extends JFrame {
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
    private JTable destinyTable;
    private JScrollPane scroolPanel;
    private JPanel editPanel;
    private JButton generateButton;
    private JLabel pilotLabel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    public static DefaultTableModel model = new DefaultTableModel();
    private final CardLayout cardLayout;

    public AppUI() {

        cardLayout = (CardLayout) (panelLayout.getLayout());
        panelLayout.setLayout(cardLayout);

        setContentPane(Manager);
        setTitle("Navigo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        UIManager.put("Button.select", new Color(0x436850));

        Object[] headers = {"id", "Inicio", "Final", "Distancia"};
        destinyTable.setModel(model);
        model.setColumnIdentifiers(headers);

        destinyTable.setFont(new Font("Robot", 0, 14));
        destinyTable.setForeground(new Color(0x12372A));
        destinyTable.setRowHeight(25);

        JTableHeader header = destinyTable.getTableHeader();
        header.setBackground(new Color(0x436850));
        header.setForeground(Color.white);
        header.setPreferredSize(new Dimension(100, 30));
        header.setFont(new Font("Roboto", Font.BOLD, 15));


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
        destiny_File.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }

                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("files", "csv");
                chooser.setFileFilter(filter);

                int select = chooser.showDialog(Manager, "Seleccionar CSV");

                if (select == JFileChooser.APPROVE_OPTION) {
                    File fileCSV = chooser.getSelectedFile();
                    Data readCSV = new Data();
                    readCSV.loadDestines(fileCSV.getAbsolutePath());
                }

                super.mouseClicked(e);
            }
        });
        editPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (destinyTable.getSelectedRow() == -1) return;
                EditDistance dialog = new EditDistance(Data.destinesList.get(destinyTable.getSelectedRow()));
                dialog.pack();
                dialog.setVisible(true);
                super.mouseClicked(e);
            }
        });
    }
}
