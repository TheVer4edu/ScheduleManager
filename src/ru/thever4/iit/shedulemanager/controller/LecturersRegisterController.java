package ru.thever4.iit.shedulemanager.controller;

import ru.thever4.iit.shedulemanager.Main;
import ru.thever4.iit.shedulemanager.data.Database;
import ru.thever4.iit.shedulemanager.model.Lecturer;
import ru.thever4.iit.shedulemanager.util.Pair;
import ru.thever4.iit.shedulemanager.view.ModalModifierWindow;
import ru.thever4.iit.shedulemanager.view.modules.LecturersRegisterModule;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LecturersRegisterController {

    private LecturersRegisterModule module;
    private List<Lecturer> data;
    private Database database = Main.getDatabase();
    private String tableName = "lecturers";
    private boolean hasUncommitedChanges;

    public LecturersRegisterController(LecturersRegisterModule module) {
        this.module = module;
        this.data = new ArrayList<>();
        loadDataFromStorage();
    }

    private void loadDataFromStorage() {
        List<String> columns = new ArrayList<>();
        columns.add("lastName");
        columns.add("firstName");
        columns.add("secondName");
        ResultSet set = database.select(tableName, columns);
        try {
            while(true) {
                if(!set.next()) break;
                data.add(new Lecturer(set.getString(columns.get(0)), set.getString(columns.get(1)), set.getString(columns.get(2))));
            }
            set.close();
        } catch (SQLException ignored) {}
        inflate(module.getModel());
    }

    public void inflate(DefaultListModel model) {
        model.clear();
        for (Lecturer element: data)
            model.addElement(element.toString());
        module.setUncommitedChanges(hasUncommitedChanges);
    }

    public void flushData() {
        if(!hasUncommitedChanges) {
            JOptionPane.showMessageDialog(null, "Несохранённых изменений нет");
            return;
        }
        database.delete(tableName);
        List<Pair<String, Object>> keyValuePairs = new ArrayList<>();
        for (Lecturer lecturer : data) {
            keyValuePairs.add(new Pair<>("lastName", lecturer.getLastName()));
            keyValuePairs.add(new Pair<>("firstName", lecturer.getFirstName()));
            keyValuePairs.add(new Pair<>("secondName", lecturer.getSecondName()));
            database.insert(tableName, keyValuePairs);
            keyValuePairs.clear();
        }
        this.hasUncommitedChanges = false;
        inflate(module.getModel());
    }

    public void createEntry() {
        ModalModifierWindow modal = new ModalModifierWindow("Добавление элемента", 260, 160) {
            @Override
            protected List<SupportFieldData> getFields() {
                List<SupportFieldData> result = new ArrayList<>();
                result.add(new SupportFieldData(new JTextField(), "Фамилия: ", 0));
                result.add(new SupportFieldData(new JTextField(), "Имя: ", 1));
                result.add(new SupportFieldData(new JTextField(), "Отчество: ", 2));
                return result;
            }

            @Override
            protected void saveAction() {
                Lecturer lecturer =
                new Lecturer(this.getResult(0),
                        this.getResult(1),
                        this.getResult(2));
                data.add(lecturer);
                hasUncommitedChanges = true;
                inflate(module.getModel());
            }
        };
        modal.display();
    }

    public void editEntry(int selectedIndex) {
        ModalModifierWindow modal = new ModalModifierWindow("Добавление элемента", 260, 160) {
            @Override
            protected List<SupportFieldData> getFields() {
                List<SupportFieldData> result = new ArrayList<>();
                result.add(new SupportFieldData(new JTextField(data.get(selectedIndex).getLastName()), "Фамилия: ", 0));
                result.add(new SupportFieldData(new JTextField(data.get(selectedIndex).getFirstName()), "Имя: ", 1));
                result.add(new SupportFieldData(new JTextField(data.get(selectedIndex).getSecondName()), "Отчество: ", 2));
                return result;
            }

            @Override
            protected void saveAction() {
                Lecturer lecturer =
                        new Lecturer(this.getResult(0),
                                this.getResult(1),
                                this.getResult(2));
                data.set(selectedIndex, lecturer);
                hasUncommitedChanges = true;
                inflate(module.getModel());
            }
        };
        modal.display();
    }

    public void removeEntry(int selectedIndex) {
        int result = JOptionPane.showConfirmDialog(null,
                "Вы действительно хотите удалить объект " + data.get(selectedIndex),
                "Удаление объекта",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                data.remove(selectedIndex);
                hasUncommitedChanges = true;
                break;
        }
        inflate(module.getModel());
    }
}
