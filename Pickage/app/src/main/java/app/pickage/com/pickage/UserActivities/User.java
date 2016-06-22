package app.pickage.com.pickage.UserActivities;

/**
 * Created by User on 02/06/2016.
 */
public class User {

    private int userID;
    private String userEmail;
    private String userPassword;
    private String userPhone;
    private String userName;
    private String userImg;
    private String userCreditCardNumber;
    private String userCreditCardName;
    private String userCreditCardDate;
    private int userCreditCardCSV;
    private String userFeedback;

    public User(String userEmail, String userPassword,String userPhone,String userName,String userImg,
                String userCreditCardNumber,String userCreditCardName,String userCreditCardDate,
                int userCreditCardCSV,String userFeedback) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userName = userName;
        this.userImg = userImg;
        this.userCreditCardNumber = userCreditCardNumber;
        this.userCreditCardName = userCreditCardName;
        this.userCreditCardDate = userCreditCardDate;
        this.userCreditCardCSV = userCreditCardCSV;
        this.userFeedback = userFeedback;
    }

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserCreditCardNumber() {
        return userCreditCardNumber;
    }

    public void setUserCreditCardNumber(String userCreditCardNumber) {
        this.userCreditCardNumber = userCreditCardNumber;
    }

    public String getUserCreditCardName() {
        return userCreditCardName;
    }

    public void setUserCreditCardName(String userCreditCardName) {
        this.userCreditCardName = userCreditCardName;
    }

    public String getUserCreditCardDate() {
        return userCreditCardDate;
    }

    public void setUserCreditCardDate(String userCreditCardDate) {
        this.userCreditCardDate = userCreditCardDate;
    }

    public int getUserCreditCardCSV() {
        return userCreditCardCSV;
    }

    public void setUserCreditCardCSV(int userCreditCardCSV) {
        this.userCreditCardCSV = userCreditCardCSV;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + userName + '\'' +
                ", UserEmail='" + userEmail + '\'' +
                ", UserPassword=" + userPassword +
                ", userID='" + userID + '\'' +
                '}';
    }
}