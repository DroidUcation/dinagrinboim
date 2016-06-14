package app.pickage.com.pickage.DBHelpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by User on 26/05/2016.
 */
public class DBContract implements BaseColumns {

    public static final String DATABASE_NAME = "pickage.db";
    public static final int DATABASE_VERSION = 1;

    // columns of the package table
    public static final String PACKAGE_TBL = "PackageTBL";
    public static final String PACKAGE_ID = "packageID";
    public static final String ORIGIN_PACKAGE = "userFillOriginPackage";
    public static final String DESTINATION_PACKAGE = "userFillDestinationPackage";
    public static final String PKG_ORIGIN__LONG = "packageOriginLong";
    public static final String PKG_ORIGIN_LAT = "packageOriginLat";



    // columns of the user table
    public static final String USER_TBL = "UserTBL";
    public static final String USER_ID = "userID";
    public static final String USER_MAIL = "userMail";
    public static final String USER_PASSSWORD = "userPassword";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_NAME = "userName";
    public static final String USER_IMG = "userImg";
    public static final String USER_CREDIT_CARD_NUMBER = "userCreditCardNumber";
    public static final String USER_CREDIT_CARD_NAME = "userCreditCardName";
    public static final String USER_CREDIT_CARD_DATE = "userCreditCardDate";
    public static final String USER_CREDIT_CARD_CSV = "userCreditCardCSV";
    public static final String USER_FEEDBACK = "userFidbek";
    //public static final String USER_PACKAGE_ID = PACKAGE_ID;


    // columns of the messenger table
    public static final String MESSENGER_TBL = "MessengerTBL";
    public static final String MESSENGER_ID = "messengerID";
    public static final String MESSENGER_MAIL = "messengerMail";
    public static final String MESSENGER_PASSSWORD = "messengerPasswoed";
    public static final String MESSENGER_PHONE = "messengerPhone";
    public static final String MESSENGER_NAME = "messengerName";
    public static final String MESSENGER_IMG = "messengerImg";
    public static final String MESSENGER_CAR_TYPE = "messengerCarType";
    //public static final String MESSENGER_PACKAGE_ID = PACKAGE_ID;


    // FOREIGN KEY OF PACKAGE TABLE
    public static final String P_MESSENGER_ID = "pMessengerID";
    public static final String P_USER_ID = "pUserID";

//    // FOREIGN KEY OF PACKAGE TABLE
//    public static final String P_MESSENGER_ID = MESSENGER_ID;
//    public static final String P_USER_ID = USER_ID;

    public static final String SQL_DROP_DATABASE = "DROP TABLE IF EXISTS ";
}