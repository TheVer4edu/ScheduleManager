package ru.thever4.iit.shedulemanager.view.modules;

import ru.thever4.iit.shedulemanager.model.Lecturer;
import ru.thever4.iit.shedulemanager.view.ModalModifierWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LecturersRegisterModule extends RegularModule {

    public LecturersRegisterModule() {
        super();
    }

    @Override
    protected JPanel incarnateWorkspace() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel defaultButtons = this.incarnateDefaultButtons(new DefaultListener());
        panel.add(defaultButtons, BorderLayout.PAGE_START);


        return panel;
    }

    @Override
    public String getTitle() {
        return "Реестр преподавателей";
    }

    private class DefaultListener extends DefaultButtonsPressedActionListener {

        @Override
        protected void onFlushPressed() {

        }

        @Override
        protected void onCreatePressed() {
            ModalModifierWindow modal = new ModalModifierWindow("Добавление элемента", 260, 150) {
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
                    ///TODO add to list
                    new Lecturer(this.getResult(0),
                        this.getResult(1),
                        this.getResult(2));
                    //inflate
                }
            };
            modal.display();
        }

        @Override
        protected void onEditPressed() {

        }

        @Override
        protected void onRemovePressed() {

        }
    }
}
