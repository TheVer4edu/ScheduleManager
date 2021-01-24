package ru.thever4.iit.shedulemanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    public Window(String title, int width, int height) {
        super(title);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(width, height));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation((int) (d.getWidth() - width)/2, (int) (d.getHeight() - height)/2);
    }

    public Window(String title, int width, int height, boolean isModal) {
        super(title);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(width, height));
        this.setDefaultCloseOperation(isModal ? WindowConstants.DISPOSE_ON_CLOSE : WindowConstants.EXIT_ON_CLOSE);
        this.setLocation((int) (d.getWidth() - width)/2, (int) (d.getHeight() - height)/2);
    }

    public Window(String title) {
        super(title);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(d);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void display() {
        this.setVisible(true);
    }

    public void addComponents(ArrayList<Component> components) {
        for(Component comp : components)
            this.add(comp);
    }
}
