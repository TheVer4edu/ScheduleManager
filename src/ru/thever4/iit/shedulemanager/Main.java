package ru.thever4.iit.shedulemanager;

import ru.thever4.iit.shedulemanager.data.Database;
import ru.thever4.iit.shedulemanager.exceptions.NotYetImplementedException;
import ru.thever4.iit.shedulemanager.view.RootWindow;
import ru.thever4.iit.shedulemanager.view.cards.ConfigsCard;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Properties;

public class Main {

    private static Database database;
    private static Properties properties;

    public static void main(String[] args) {
        setLookAndFeel();
        if(initialize(args) < 0) return;
        /*Lecturer lecturer = new Lecturer("Скрипов", "Сергей", "Александрович");
        Subject networks = new Subject("Сети и телекоммуникации");
        networks.addLecturer(lecturer);
        Lesson lesson = new Lesson(networks, 1);*/
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RootWindow w = new RootWindow();
            }
        });
    }

    private static int initialize(String[] args) {
        try {
            Constants.RUNNING_AT = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(null,
                    "Произошло что-то крайне неадекватное. Я не могу понять откуда меня запускают. Обратитесь к вашему системному администратору или проверьте права доступа к директории, где находится исполняемый файл. (Также проблемой могут стать русские символы в пути к исполняемому файлу",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if(initializeProperties(getConfigPath(args)) < 0) return -2;
        if(initializeDatabase(getDatabasePath(args)) < 0) return -3;
        return 0;
    }

    private static int initializeProperties(String path) {
        Constants.CONFIG_PATH = path != null ? path : Constants.RUNNING_AT + "/Config.properties";
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(Constants.CONFIG_PATH);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Файл с настройками найти не удалось. Обратитесь к вашему системному администратору.\n"+Constants.CONFIG_PATH,
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;
    }

    private static int initializeDatabase(String path) {
        String pathToDatabase = path;
        String pathFromProperties = properties.getProperty("Database");
        if(pathToDatabase == null)
            pathToDatabase = pathFromProperties != null ? pathFromProperties :Constants.RUNNING_AT + "/ScheduleManager.db";
        if(pathToDatabase != null)
            Constants.DATABASE_PATH = pathToDatabase;
        try {
            database = new Database(Constants.DATABASE_PATH);
        } catch (NoSuchFileException e) {
            JOptionPane.showMessageDialog(null,
                    "Невозможно воспользоваться указанной в настройках базой данных. Обратитесь к вашему системному администратору.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return 0;
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored) { }
    }

    private static String getValueFromArgs(String[] args, String key) {
        if(args.length == 0) return null;
        int index = Arrays.asList(args).indexOf(key);
        if(index < args.length && index != -1)
            return args[index + 1];
        return null;
    }

    private static String getDatabasePath(String args[]) {
        String value = getValueFromArgs(args, "-D");
        if(value == null)
            value = getValueFromArgs(args, "--database-path");
        return value;
    }

    private static String getConfigPath(String args[]) {
        String value = getValueFromArgs(args, "-C");
        if(value == null)
            value = getValueFromArgs(args, "--config-path");
        return value;
    }

    public static Database getDatabase() {
        return database;
    }

    public static void closingActions() {
        saveConfig();
    }

    private static void saveConfig() {
        properties.setProperty("Database", Constants.DATABASE_PATH);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Constants.CONFIG_PATH);
            properties.store(fileOutputStream, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Сохранить конфигурации не получилось :(\nЭто не должно повлиять на Ваши данные, но всё-же проверьте конфигурации",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
