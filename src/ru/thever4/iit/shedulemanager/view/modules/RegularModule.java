package ru.thever4.iit.shedulemanager.view.modules;

import ru.thever4.iit.shedulemanager.exceptions.NotYetImplementedException;
import ru.thever4.iit.shedulemanager.view.DefaultButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class RegularModule extends JPanel {

    private JLabel moduleLabel;

    public RegularModule() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String title = this.getTitle();
        if(title == null) throw new NotYetImplementedException("getTitle() method is not implemented");
        moduleLabel = new JLabel(title);
        moduleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        moduleLabel.setFont(moduleLabel.getFont().deriveFont(24f));
        this.add(moduleLabel);
        JPanel workspace = incarnateWorkspace();
        if(workspace == null) throw new NotYetImplementedException("incarnateWorkspace() method is not implemented");
        workspace.setAlignmentX(Component.LEFT_ALIGNMENT);
        workspace.setAlignmentY(Component.TOP_ALIGNMENT);
        this.add(workspace);
    }

    protected abstract JPanel incarnateWorkspace();

    protected JPanel incarnateDefaultButtons(DefaultButtonsPressedActionListener listener) {
        FlowLayout layout = new FlowLayout();
        JPanel panel = new JPanel(layout);
        layout.setAlignment(FlowLayout.LEADING);
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));

        DefaultButton flushButton = new DefaultButton("Записать", DefaultButton.DefaultButtonType.FLUSH);
        flushButton.setBackground(Color.ORANGE);
        DefaultButton createButton = new DefaultButton("Создать", DefaultButton.DefaultButtonType.CREATE);
        DefaultButton editButton = new DefaultButton("Редактировать", DefaultButton.DefaultButtonType.EDIT);
        DefaultButton removeButton = new DefaultButton("Удалить", DefaultButton.DefaultButtonType.REMOVE);

        flushButton.setAlignmentX(LEFT_ALIGNMENT);
        createButton.setAlignmentX(LEFT_ALIGNMENT);
        editButton.setAlignmentX(LEFT_ALIGNMENT);
        removeButton.setAlignmentX(LEFT_ALIGNMENT);

        flushButton.addActionListener(listener);
        createButton.addActionListener(listener);
        editButton.addActionListener(listener);
        removeButton.addActionListener(listener);

        panel.add(flushButton);
        panel.add(createButton);
        panel.add(editButton);
        panel.add(removeButton);

        return panel;
    }

    public void setUncommitedChanges(boolean flag) {
        this.moduleLabel.setText(flag ? this.getTitle() + "*" : this.getTitle());
    }

    public abstract String getTitle();
}
