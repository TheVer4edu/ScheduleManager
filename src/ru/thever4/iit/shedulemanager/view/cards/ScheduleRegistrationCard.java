package ru.thever4.iit.shedulemanager.view.cards;

public class ScheduleRegistrationCard extends RegularCard {

    public ScheduleRegistrationCard() {
        super();
    }

    @Override
    public String getTabTitle() {
        return "Мастер регистрации расписания";
    }

    @Override
    public String getTabImage() {
        return "icons/schedule_registration.png";
    }
}
