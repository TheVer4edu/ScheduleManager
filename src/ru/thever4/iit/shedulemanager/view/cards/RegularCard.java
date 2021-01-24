package ru.thever4.iit.shedulemanager.view.cards;

import ru.thever4.iit.shedulemanager.Constants;
import ru.thever4.iit.shedulemanager.view.modules.RegularModule;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public abstract class RegularCard extends JPanel {

    private final List<ModuleOption> options = new ArrayList<>();
    private final JPanel moduleHolder = new JPanel(new CardLayout());
    private JPanel optionsHolder = new JPanel();

    public RegularCard() {
        super();
        experimental();
        this.setLayout(new BorderLayout());
        optionsHolder.setLayout(new BoxLayout(optionsHolder, BoxLayout.Y_AXIS));
        this.add(optionsHolder, BorderLayout.LINE_START);
        this.add(moduleHolder, BorderLayout.CENTER);
    }

    private void experimental() {
        this.optionsHolder.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(10, 10, 10, 10)));
        this.moduleHolder.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void inflate() {
        this.optionsHolder.removeAll();
        for (ModuleOption mo : options) {
            JLabel option = mo.getOption();
            this.optionsHolder.add(option);
        }
    }

    protected void addModuleOption(RegularModule module) {
        this.options.add(new ModuleOption(module));
    }

    public abstract String getTabTitle();

    public abstract String getTabImage();

    private class ModuleOption implements MouseListener {
        private String title;
        private RegularModule module;

        public ModuleOption(RegularModule module) {
            this.module = module;
            this.title = module.getTitle();
            moduleHolder.add(this.module, this.getTitle());
        }

        public JLabel getOption() {
            JLabel label = new JLabel(this.title);
            label.setFont(label.getFont().deriveFont(Constants.OPTIONS_FONT_SIZE));
            label.setBorder(new EmptyBorder(0, 0, (int) Constants.OPTIONS_FONT_SIZE / 2, 0));
            label.addMouseListener(this);
            return label;
        }

        public String getTitle() {
            return title;
        }

        public RegularModule getModule() {
            return module;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CardLayout cl = (CardLayout) moduleHolder.getLayout();
            cl.show(moduleHolder, this.getTitle());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
