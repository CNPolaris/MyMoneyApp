package data;

import android.app.Application;

public class Data extends Application {
    private static String username;
    private static String password;
    private static String familyname;
    public Data(){
        username="";
        password="";
        familyname="";
    }
    public Data(String username,String password){
        this.username=username;
        this.password=password;
    }

    public static void setUsername(String username) {
        Data.username = username;
    }

    public static void setPassword(String password) {
        Data.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
