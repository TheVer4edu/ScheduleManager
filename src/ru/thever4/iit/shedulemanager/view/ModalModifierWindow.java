package ru.thever4.iit.shedulemanager.view;

import ru.thever4.iit.shedulemanager.exceptions.NotYetImplementedException;
import ru.thever4.iit.shedulemanager.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public abstract class ModalModifierWindow extends Window implements MouseListener {

    private JButton save, saveAndExit;
    private List<SupportFieldData> data;
    private List<Pair<Integer, String>> result;

    public ModalModifierWindow(String title, int width, int height) {
        super(title, width, height, true);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        saveAndExit = new JButton("Сохранить и закрыть");
        saveAndExit.addMouseListener(this);
        saveAndExit.setBackground(Color.ORANGE);
        save = new JButton("Сохранить");
        save.addMouseListener(this);
        topPanel.add(saveAndExit);
        topPanel.add(save);
        this.add(topPanel, BorderLayout.PAGE_START);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        this.data = getFields();
        if(data == null) throw new NotYetImplementedException("getFields() not yet implemented");
        for (SupportFieldData field : data) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
            JLabel label = new JLabel(field.getLabel(), JLabel.TRAILING);
            panel.add(label);
            JTextField textField = field.getField();
            textField.setColumns(15);
            label.setLabelFor(textField);
            panel.add(textField);
            body.add(panel);
        }
        JScrollPane pane = new JScrollPane(body);
        pane.setBorder(null);
        this.add(pane, BorderLayout.CENTER);
    }

    protected abstract List<SupportFieldData> getFields();

    private List<Pair<Integer, String>> getResult() {
        List<Pair<Integer, String>> result = new ArrayList<>();
        for (SupportFieldData field : this.data)
            result.add(new Pair<>(field.getID(), field.getField().getText()));
        return result;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == save) registerResult();
        if(e.getSource() == saveAndExit ) {
            registerResult();
            this.dispose();
        }
    }

    public void registerResult() {
        this.result = getResult();
        saveAction();
    }

    public String getResult(int id) {
        for(Pair<Integer, String> pair : this.result)
            if(pair.x == id) return pair.y;
        return null;
    }

    protected abstract void saveAction();

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    protected class SupportFieldData {
        private String label;
        private int ID;
        private JTextField field;

        public SupportFieldData(JTextField field, String label, int ID) {
            this.label = label;
            this.ID = ID;
            this.field = field;
        }

        public String getLabel() {
            return label;
        }

        public int getID() {
            return ID;
        }

        public JTextField getField() {
            return field;
        }
    }

}
