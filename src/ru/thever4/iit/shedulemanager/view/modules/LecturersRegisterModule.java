package ru.thever4.iit.shedulemanager.view.modules;

import ru.thever4.iit.shedulemanager.controller.LecturersRegisterController;
import ru.thever4.iit.shedulemanager.model.Lecturer;
import ru.thever4.iit.shedulemanager.view.ModalModifierWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LecturersRegisterModule extends RegularModule {

    private LecturersRegisterController controller;
    private JList list;
    private DefaultListModel model;

    public LecturersRegisterModule() {
        super();
        this.controller = new LecturersRegisterController(this);
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

        panel.add(this.incarnateDefaultButtons(new DefaultListener()), BorderLayout.PAGE_START);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public String getTitle() {
        return "Реестр преподавателей";
    }

    public DefaultListModel getModel() {
        return this.model;
    }

    private class DefaultListener extends DefaultButtonsPressedActionListener {

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
