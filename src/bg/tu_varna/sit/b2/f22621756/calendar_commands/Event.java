package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import java.util.Date;

public class Event {
    private String name;
    private Date startDate;

    public Event(String name, Date startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
