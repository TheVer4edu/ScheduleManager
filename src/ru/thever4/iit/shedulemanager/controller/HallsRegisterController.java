package ru.thever4.iit.shedulemanager.controller;

import jdk.nashorn.internal.scripts.JO;
import ru.thever4.iit.shedulemanager.Main;
import ru.thever4.iit.shedulemanager.data.Database;
import ru.thever4.iit.shedulemanager.util.Pair;
import ru.thever4.iit.shedulemanager.view.ModalModifierWindow;
import ru.thever4.iit.shedulemanager.view.modules.HallsRegisterModule;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallsRegisterController {

    private HallsRegisterModule module;
    private List<String> data;
    private Database database = Main.getDatabase();
    private String tableName = "halls";
    private boolean hasUncommitedChanges;

    public HallsRegisterController(HallsRegisterModule module) {
        //три всадника апокалипсиса этого кода:
        //modal module model
        this.module = module;
        this.data = new ArrayList<>();
        loadDataFromStorage();
    }

    public void loadDataFromStorage() {
        List<String> columns = new ArrayList<>();
        columns.add("name");
        ResultSet set = database.select(tableName, columns);
        try {
            while(true) {
                if(!set.next()) break;
                data.add(set.getString(columns.get(0)));
            }
        set.close();
        } catch (SQLException ignored) {}
        inflate(module.getModel());
    }

    public void inflate(DefaultListModel model) {
        model.clear();
        for (String element: data)
            model.addElement(element);
        module.setUncommitedChanges(hasUncommitedChanges);
    }

    public void flushData() {
        if(!hasUncommitedChanges) {
            JOptionPane.showMessageDialog(null, "Несохранённых изменений нет");
            return;
        }
        database.delete(tableName);
        List<Pair<String, Object>> keyValuePairs = new ArrayList<>();
        for (String hallName: data) {
            keyValuePairs.add(new Pair<>("name", hallName));
            database.insert(tableName, keyValuePairs);
            keyValuePairs.clear();
        }
        this.hasUncommitedChanges = false;
        inflate(module.getModel());
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
                hasUncommitedChanges = true;
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
                hasUncommitedChanges = true;
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
                hasUncommitedChanges = true;
                break;
        }
        inflate(module.getModel());
    }
}