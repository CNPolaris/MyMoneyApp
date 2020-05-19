package data;

import androidx.annotation.NonNull;

import java.util.Date;

public class GetData {
    private String id;
    private String leixing;
    private String xiangmu;
    private float money;
    private String beizhu;
    private Date date;

    public GetData(String id, String leixing, String xiangmu, float money, String beizhu, Date date) {
        this.id = id;
        this.leixing = leixing;
        this.xiangmu = xiangmu;
        this.money = money;
        this.beizhu = beizhu;
        this.date = date;
    }

    public GetData(String id, String leixing, String xiangmu, float money, Date date) {
        this.id = id;
        this.leixing = leixing;
        this.xiangmu = xiangmu;
        this.money = money;
        this.date = date;
    }



    @NonNull
    @Override
    public String toString() {
        //return "流水号:"+id+" "+"类型:"+leixing+" "+"项目:"+xiangmu+" "+"金额:"+money+" "+"日期:"+date;
        return id+"  "+leixing+"  "+xiangmu+"  "+money+"  "+date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
