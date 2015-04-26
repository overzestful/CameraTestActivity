package net.sourceforge.zbar.android.CameraTest;

/**
 * Created by Overzestful-Fhon on 3/4/2015.
 */
public class Book {
    private long id;
    private String name;
    private String staff;
    private String password;
    private String confirm_password;
   // private String type;
    private String hour;
    //private String title;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    //
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
     //
    public String getStaff() {
         return staff;
     }
    public void setStaff(String staff) {
        this.staff = staff;
    }
    //
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    //
    public String getConfirm_password() {
        return confirm_password;
    }
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
    //
    /*public String getType() {
        return type;
    }
    public void setType(String type) {
        this.name = type;
    }*/

    //
    public String getHour() {
        return hour ;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }

    /*public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }*/

    @Override
    public String toString() {
        return name;
    }
}
