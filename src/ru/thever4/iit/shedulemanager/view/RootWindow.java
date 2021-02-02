package ru.thever4.iit.shedulemanager.view;

import ru.thever4.iit.shedulemanager.Constants;
import ru.thever4.iit.shedulemanager.Main;
import ru.thever4.iit.shedulemanager.view.cards.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RootWindow {
    private Window window;

    public RootWindow() {
        this.window = new Window(Constants.APP_NAME);
        window.add(new TabsController());
        window.display();
        window.addWindowListener(new RootWindowListener());
    }

    private class RootWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            Main.closingActions();
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    private class TabsController extends JTabbedPane {
        private List<RegularCard> cards;

        public TabsController() {
            initCards();
            int i = 0;
            for (RegularCard card : this.cards) {
                this.addTab(card.getTabTitle(), card);
                this.setTabComponentAt(i++, new SingleTab(card.getTabTitle(), card.getTabImage()));
            }
        }

        private void initCards() {
            cards = new ArrayList();
            this.cards.add(new AcademicPlanCard());
            this.cards.add(new ScheduleRegistrationCard());
            this.cards.add(new ConfigsCard());
        }

        private class SingleTab extends JPanel {
            private String title;
            private String icon;

            public SingleTab(String title, String icon) {
                this.title = title;
                this.icon = icon;
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(getImage());
            }

            private JLabel getImage() {
                if(this.icon == null) return new JLabel(this.title);
                URL res = getClass().getResource("../" + this.icon);
                Image pic;
                try {
                    pic = new ImageIcon(res).getImage();
                } catch (Exception e) {
                    return new JLabel(this.title);
                }
                ImageIcon icon = new ImageIcon(pic.getScaledInstance(Constants.TABS_ICON_SIZE_PX, Constants.TABS_ICON_SIZE_PX, Image.SCALE_SMOOTH));
                JLabel label = new JLabel(icon);
                label.setText(this.title);
                label.setFont(label.getFont().deriveFont(Constants.TABS_FONT_SIZE));
                return label;
            }
        }
    }

}

