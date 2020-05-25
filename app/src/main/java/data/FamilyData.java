package data;

import java.util.Date;

public class FamilyData {
    private String id;
    private String user;
    private String leixing;
    private String xiangmu;
    private float money;
    private String beizhu;
    private Date date;

    public FamilyData(String id, String user, String leixing, String xiangmu, float money, String beizhu, Date date) {
        this.id = id;
        this.user = user;
        this.leixing = leixing;
        this.xiangmu = xiangmu;
        this.money = money;
        this.beizhu = beizhu;
        this.date = date;
    }

    @Override
    public String toString() {
        return  id +" "+ user +" " + leixing +" "+ xiangmu + " "+ money + " "+ date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
