package ru.thever4.iit.shedulemanager.view.cards;

import ru.thever4.iit.shedulemanager.view.modules.*;

public class AcademicPlanCard extends RegularCard {

    public AcademicPlanCard() {
        super();
        this.addModuleOption(new LecturersRegisterModule());
        this.addModuleOption(new HallsRegisterModule());
        this.addModuleOption(new AcademicGroupsRegisterModule());
        this.addModuleOption(new SubjectsRegisterModule());
        this.inflate();
    }

    @Override
    public String getTabTitle() {
        return "Мастер учебного плана";
    }

    @Override
    public String getTabImage() {
        return "icons/academic_plan.png"; //from https://icon-icons.com/ru/pack/3d-Icons/2782&page=1
    }

}
