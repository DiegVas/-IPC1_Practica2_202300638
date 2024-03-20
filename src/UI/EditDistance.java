package UI;

import Classes.Data;
import Classes.Destines;

import javax.swing.*;
import java.awt.event.*;

public class EditDistance extends JDialog {
    private JPanel contentPane;

    private Destines destine;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel bottomPanel;
    private JLabel DestinyLabel;
    private JSpinner spinner1;

    public EditDistance(Destines destine) {
        this.destine = destine;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        spinner1.setValue(Integer.parseInt(this.destine.distance));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });


    }

    private void onOK() {
        Data data = new Data();
        int index = data.destinesList.indexOf(destine);
        Destines oldDestine = data.destinesList.get(index);
        data.destinesList.set(index, new Destines(oldDestine.start, oldDestine.end, spinner1.getValue().toString()));
        AppUI.model.setValueAt(spinner1.getValue(), index, 3);

        this.dispose();
        dispose();
    }
}
