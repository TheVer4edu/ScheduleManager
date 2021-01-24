package ru.thever4.iit.shedulemanager.controller;

import ru.thever4.iit.shedulemanager.view.ModalModifierWindow;
import ru.thever4.iit.shedulemanager.view.modules.HallsRegisterModule;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HallsRegisterController {

    private HallsRegisterModule module;
    private List<String> data;

    public HallsRegisterController(HallsRegisterModule module) {
        //три всадника апокалипсиса этого кода:
        //modal module model
        this.module = module;
        this.data = new ArrayList<>();
        loadDataFromStorage();
    }

    public void loadDataFromStorage() {
        //TODO data must be filled by magic
        data.add("132");
        inflate(module.getModel());
    }

    public void inflate(DefaultListModel model) {
        model.clear();
        for (String element: data)
            model.addElement(element);
    }

    public void flushData() {
        JOptionPane.showMessageDialog(null, "Not implemented yet");
    }

    public void createEntry() {
        ModalModifierWindow modal = new ModalModifierWindow("Создание записи", 260, 120) {
            @Override
            protected List<SupportFieldData> getFields() {
                List<SupportFieldData> data = new ArrayList<>();
                data.add(new SupportFieldData(new JTextField(), "Аудитория: ", 1));
                return data;
            }

            @Override
            protected void saveAction() {
                data.add(this.getResult(1));
                inflate(module.getModel());
            }
        };
        modal.display();
    }

    public void editEntry(int index) {

        ModalModifierWindow modal = new ModalModifierWindow("Изменение записи", 260, 120) {
            @Override
            protected List<SupportFieldData> getFields() {
                List<SupportFieldData> result = new ArrayList<>();
                JTextField field = new JTextField();
                field.setText(String.valueOf(data.get(index)));
                result.add(new SupportFieldData(field, "Аудитория: ", 1));
                return result;
            }

            @Override
            protected void saveAction() {
                data.set(index, this.getResult(1));
                inflate(module.getModel());
            }
        };
        modal.display();
    }

    public void removeEntry(int index) {
        int result = JOptionPane.showConfirmDialog(null,
                "Вы действительно хотите удалить объект " + data.get(index),
                "Удаление объекта",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                data.remove(index);
                break;
        }
        inflate(module.getModel());
    }
}