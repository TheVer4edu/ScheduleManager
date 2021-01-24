package ru.thever4.iit.shedulemanager.view.modules;

import javax.swing.*;

public class SubjectsRegisterModule extends RegularModule {

    @Override
    protected JPanel incarnateWorkspace() {
        return new JPanel(); //здесь гуя
    }

    @Override
    public String getTitle() {
        return "Реестр дисциплин"; //здесь название
    }
}
