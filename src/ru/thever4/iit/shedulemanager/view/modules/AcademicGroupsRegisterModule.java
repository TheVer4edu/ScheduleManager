package ru.thever4.iit.shedulemanager.view.modules;

import javax.swing.*;
import java.awt.*;

public class AcademicGroupsRegisterModule extends RegularModule {

    public AcademicGroupsRegisterModule() {
        super();
    }

    @Override
    protected JPanel incarnateWorkspace() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        return panel;
    }

    @Override
    public String getTitle() {
        return "Реестр учебных групп";
    }
}
