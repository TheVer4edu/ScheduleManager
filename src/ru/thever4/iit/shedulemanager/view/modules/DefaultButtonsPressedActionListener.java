package ru.thever4.iit.shedulemanager.view.modules;

import ru.thever4.iit.shedulemanager.view.DefaultButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class DefaultButtonsPressedActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultButton source = (DefaultButton) e.getSource();
        switch (source.getType()) {
            case FLUSH:
                onFlushPressed();
                break;
            case CREATE:
                onCreatePressed();
                break;
            case EDIT:
                onEditPressed();
                break;
            case REMOVE:
                onRemovePressed();
                break;
            default:
        }
    }

    protected abstract void onFlushPressed();
    protected abstract void onCreatePressed();
    protected abstract void onEditPressed();
    protected abstract void onRemovePressed();
}
