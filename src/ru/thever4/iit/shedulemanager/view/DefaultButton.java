package ru.thever4.iit.shedulemanager.view;

import javax.swing.*;

public class DefaultButton extends JButton {

    private DefaultButtonType type;

    public DefaultButton(String text, DefaultButtonType type) {
        super(text);
        this.type = type;
    }

    public DefaultButtonType getType() {
        return type;
    }

    public enum DefaultButtonType {
        FLUSH,
        CREATE,
        EDIT,
        REMOVE
    }
}
