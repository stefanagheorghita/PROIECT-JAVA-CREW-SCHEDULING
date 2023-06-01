package example.model.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class PlaneLocation {

    private int planeId;

    private int cityId;

    private DayOfWeek day;

    private LocalTime time;

    public PlaneLocation(int planeId, int cityId, DayOfWeek day, LocalTime time) {
        this.planeId = planeId;
        this.cityId = cityId;
        this.day = day;
        this.time = time;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
