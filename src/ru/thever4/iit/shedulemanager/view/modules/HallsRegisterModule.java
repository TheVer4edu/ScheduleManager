package ru.thever4.iit.shedulemanager.view.modules;

import ru.thever4.iit.shedulemanager.controller.HallsRegisterController;

import javax.swing.*;
import java.awt.*;

public class HallsRegisterModule extends RegularModule {

    private HallsRegisterController controller;
    private JList list;
    private DefaultListModel model;


    public HallsRegisterModule() {
        super();
        this.controller = new HallsRegisterController(this);
        controller.inflate(model);
        list.setSelectedIndex(0);
    }

    @Override
    protected JPanel incarnateWorkspace() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        this.list = new JList();
        this.model = new DefaultListModel();
        list.setModel(this.model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        JScrollPane scrollPane = new JScrollPane(this.list);


        panel.add(this.incarnateDefaultButtons(new DefaultButtonListener()), BorderLayout.PAGE_START);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public DefaultListModel getModel() {
        return this.model;
    }

    @Override
    public String getTitle() {
        return "Реестр аудиторий";
    }

    private class DefaultButtonListener extends DefaultButtonsPressedActionListener {

        @Override
        protected void onFlushPressed() {
            controller.flushData();
        }

        @Override
        protected void onCreatePressed() {
            controller.createEntry();
        }

        @Override
        protected void onEditPressed() {
            controller.editEntry(list.getSelectedIndex());
        }

        @Override
        protected void onRemovePressed() {
            controller.removeEntry(list.getSelectedIndex());
        }
    }
}
