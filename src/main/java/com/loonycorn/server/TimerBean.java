package com.loonycorn.server;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

@Stateless
public class TimerBean
//        implements TimerInterfaceRemote
{

    int counter = 1;
    static final int limit = 3;
/*
    @Resource
    private SessionContext context;

    public void createTimer(long duration) {
        context.getTimerService().createTimer(duration, "Created new programmatic timer");
    }

    public void createTimer(Date date) {
        context.getTimerService().createTimer(date,
                String.format("Scheduled task set to run at ", date));
    }

    public void createCalendarTimer(ScheduleExpression schedule) {
        context.getTimerService().createCalendarTimer(schedule,
                new TimerConfig("Timer scheduled.",false));
    }
*/
    //@Timeout
    @Schedule(minute = "*/1", hour = "*", dayOfMonth = "4th Thu", second = "10, 40",
        persistent = false, info = "Scheduled job")
    public void timeOutHandler(Timer timer) {

        System.out.println("Timer service has begun at: [" + (new Date()).toString() + "]" );
        System.out.println("Timer message : " + timer.getInfo());
        System.out.println("Counter: " + counter);

        counter++;

        if(counter > limit) {
            System.out.println("Canceling the timer service at: [" + (new Date()).toString() + "]" );
            timer.cancel();
        }
    }
}
