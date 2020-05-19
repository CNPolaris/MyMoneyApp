package data;

public class ItemData {
    private String type;
    private float money;
    private float percentage;

    public ItemData(String type, float money, float percentage) {
        this.type = type;
        this.money = money;
        this.percentage = percentage;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

}
