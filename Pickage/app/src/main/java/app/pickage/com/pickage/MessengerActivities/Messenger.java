package app.pickage.com.pickage.MessengerActivities;

/**
 * Created by User on 05/06/2016.
 */
public class Messenger {

    private int messengerID;
    private String messengerMail;
    private String messengerPassword;
    private String messengerPhone;
    private String messengerName;
    private String messengerImg;
    private String messengerCarType;

    public Messenger(int messengerID,String messengerMail,String messengerPhone,String messengerName,
                     String messengerImg,String messengerCarType){
        this.messengerID = messengerID;
        this.messengerMail = messengerMail;
        this.messengerPhone = messengerPhone;
        this.messengerName = messengerName;
        this.messengerImg = messengerImg;
        this.messengerCarType = messengerCarType;
    }

    public int getMessengerID() {
        return messengerID;
    }

    public void setMessengerID(int messengerID) {
        this.messengerID = messengerID;
    }

    public String getMessengerMail() {
        return messengerMail;
    }

    public void setMessengerMail(String messengerMail) {
        this.messengerMail = messengerMail;
    }

    public String getMessengerPassword() {
        return messengerPassword;
    }

    public void setMessengerPassword(String messengerPassword) {
        this.messengerPassword = messengerPassword;
    }

    public String getMessengerPhone() {
        return messengerPhone;
    }

    public void setMessengerPhone(String messengerPhone) {
        this.messengerPhone = messengerPhone;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }

    public String getMessengerImg() {
        return messengerImg;
    }

    public void setMessengerImg(String messengerImg) {
        this.messengerImg = messengerImg;
    }

    public String getMessengerCarType() {
        return messengerCarType;
    }

    public void setMessengerCarType(String messengerCarType) {
        this.messengerCarType = messengerCarType;
    }
}
