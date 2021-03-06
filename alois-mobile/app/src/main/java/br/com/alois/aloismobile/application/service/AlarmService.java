package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import br.com.alois.aloismobile.application.api.reminder.ReminderClient;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 4/23/17.
 */
@EBean
public class AlarmService
{
    //=====================================ATTRIBUTES=======================================
    private final Context context;

    private AlarmManager alarmManager;

    //====================================CONSTRUCTORS======================================
    public AlarmService(Context context)
    {
        this.context = context;
        this.alarmManager = AlarmManagerSingleton.getInstance( context );
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Background
    public void scheduleReminder(Reminder reminder)
    {
        Intent alarmIntent = new Intent(this.context, AlarmReceiverService.class);
        alarmIntent.putExtra("reminder", reminder);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, reminder.getId().intValue(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            reminder.setReminderStatus(ReminderStatus.ACTIVE);
            reminderClient.updateStatusReminder(reminder.getId(), ReminderStatus.ACTIVE, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);

            this.alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(), pendingIntent);
            Log.i("ALOIS-REMINDER", "Alois reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
        }
        catch (FeignException e )
        {
            e.printStackTrace();
        }
    }

    public void rescheduleReminder(Reminder reminder)
    {
        Intent alarmIntent = new Intent(this.context, AlarmReceiverService.class);
        alarmIntent.putExtra("reminder", reminder);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, reminder.getId().intValue(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        this.alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(), pendingIntent);
        Log.i("ALOIS-REMINDER", "Alois reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
    }

    public void deleteReminder(Reminder reminder)
    {
            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            this.cancelReminder( reminder );

            try
            {
                reminderClient.deleteReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);
            }
            catch (FeignException e)
            {
                e.printStackTrace();
            }

    }

    public void cancelReminder(Reminder reminder)
    {
        Intent alarmIntent = new Intent(this.context, AlarmReceiverService.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, reminder.getId().intValue(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Log.i("ALOIS-REMINDER", "Alois reminder with id: " + reminder.getId() + " canceled");
    }

    //======================================================================================

}
