package ru.thever4.iit.shedulemanager;

import ru.thever4.iit.shedulemanager.view.RootWindow;
import ru.thever4.iit.shedulemanager.model.Lecturer;
import ru.thever4.iit.shedulemanager.model.Lesson;
import ru.thever4.iit.shedulemanager.model.Subject;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        setLookAndFeel();

        Lecturer lecturer = new Lecturer("Скрипов", "Сергей", "Александрович");
        Subject networks = new Subject("Сети и телекоммуникации");
        networks.addLecturer(lecturer);
        Lesson lesson = new Lesson(networks, 1);


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RootWindow w = new RootWindow();
            }
        });
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored) { }
    }
}
