package no.charlie.rsvpapp.domain;

import android.database.Cursor;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Created by charlie midtlyng on 25/02/15.
 */
public class Event implements Comparable<Event>, Serializable {

    public Long id;
    public DateTime startTime;
    public DateTime endTime;
    public DateTime regStart;
    public DateTime regEnd;

    public String creator;
    public String location;
    public String subject;
    public String description;
    public Integer maxNumber;

    public List<Participant> participants;
    public List<History> history;

    @Override
    public String toString() {
        return subject;
    }

    public String day() {

        return startTime != null ? startTime.dayOfWeek().getAsText(new Locale("no")) : "";
    }

    public String startTimeString() {
        return startTime == null ? "" : startTime.toString("yyyy-MM-dd HH:mm");
    }

    public String regStartString() {
        return regStart == null ? "" : regStart.toString("yyyy-MM-dd HH:mm");
    }

    public String remainingSpots() {
        if (maxNumber == null || participants == null) {
            return "";
        }
        Integer remainingSpots = maxNumber - participants.size();
        if (remainingSpots > 0) {
            return remainingSpots + " ledig";
        }
        return "Fullt! (reserveliste)";
    }

    public boolean registrationIsOpen() {
        return regStart.isBeforeNow() && regEnd.isAfterNow();
    }

    @Override
    public int compareTo(Event another) {
        if (startTime == null) {
            return -1;
        } else if (another == null || another.startTime == null) {
            return 1;
        }
        return startTime.isBefore(another.startTime) ? 1 : -1;
    }

    public static Event fromCursor(Cursor cursor) {
        Event event = new Event();
        event.id = cursor.getLong(0);
        event.subject = cursor.getString(1);
        event.startTime = new DateTime(cursor.getLong(2));
        event.regStart = new DateTime(cursor.getLong(3));
        event.regEnd = new DateTime(cursor.getLong(4));
        return event;
    }

}
