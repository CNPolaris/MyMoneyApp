package data;

public class DayData {
    private int day;
    private float money;

    public DayData(int day, float money) {
        this.day = day;
        this.money = money;
    }

    public float getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
