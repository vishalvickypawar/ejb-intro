package com.loonycorn.server;

import javax.ejb.Remote;
import javax.ejb.ScheduleExpression;
import java.util.Date;

@Remote
public interface TimerInterfaceRemote {

    //public void createTimer(long milliseconds);

    //public void createTimer(Date date);

    public void createCalendarTimer(ScheduleExpression schedule);
}
