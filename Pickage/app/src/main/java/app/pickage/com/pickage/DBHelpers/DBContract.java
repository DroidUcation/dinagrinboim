package app.pickage.com.pickage.dbhelpers;

import android.provider.BaseColumns;

/**
 * Created by Din&Yeudit on 26/05/2016.
 */
public class DBContract implements BaseColumns {

    public static final String DATABASE_NAME = "pickage.db";
    public static final int DATABASE_VERSION = 1;

    // columns of the package table
    public static final String PACKAGE_TBL = "PackageTBL";
    public static final String PACKAGE_ID = "packageID";
    public static final String ORIGIN_PACKAGE = "userFillOriginPackage";
    public static final String DESTINATION_PACKAGE = "userFillDestinationPackage";
    public static final String PKG_ORIGIN_LONG = "packageOriginLong";
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
    public static final String USER_FEEDBACK = "userFeedback";

    // columns of the messenger table
    public static final String MESSENGER_TBL = "MessengerTBL";
    public static final String MESSENGER_ID = "messengerID";
    public static final String MESSENGER_MAIL = "messengerMail";
    public static final String MESSENGER_PASSWORD = "messengerPasswoed";
    public static final String MESSENGER_PHONE = "messengerPhone";
    public static final String MESSENGER_NAME = "messengerName";
    public static final String MESSENGER_IMG = "messengerImg";
    public static final String MESSENGER_CAR_TYPE = "messengerCarType";
    public static final String MESSENGER_LAT = "messengerLat";
    public static final String MESSENGER_LONG = "messengerLong";

    // FOREIGN KEY OF PACKAGE TABLE
    public static final String P_MESSENGER_ID = "pMessengerID";
    public static final String P_USER_ID = "pUserID";

    public static final String SQL_DROP_DATABASE = "DROP TABLE IF EXISTS ";
}