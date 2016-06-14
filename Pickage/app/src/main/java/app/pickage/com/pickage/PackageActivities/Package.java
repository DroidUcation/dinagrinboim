package app.pickage.com.pickage.PackageActivities;

import app.pickage.com.pickage.DBHelpers.DBContract;

/**
 * Created by User on 05/06/2016.
 */
public class Package {

    private int packageID;
    private double pkgOriginLong;
    private double pkgOriginLat;
    private String originPackage;
    private String destinationPackage;
    private int pMessengerID;
    private int pUserID;

    public Package(int packageID,double pkgOriginLong,double pkgOriginLat,String originPackage,
                   String destinationPackage,int pMessengerID,int pUserID){
        this.packageID = packageID;
        this.pkgOriginLong = pkgOriginLong;
        this.pkgOriginLat = pkgOriginLat;
        this.originPackage = originPackage;
        this.destinationPackage = destinationPackage;
        this.pMessengerID = pMessengerID;
        this.pUserID = pUserID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public double getPkgOriginLong() {
        return pkgOriginLong;
    }

    public void setPkgOriginLong(double pkgOriginLong) {
        this.pkgOriginLong = pkgOriginLong;
    }

    public double getPkgOriginLat() {
        return pkgOriginLat;
    }

    public void setPkgOriginLat(double pkgOriginLat) {
        this.pkgOriginLat = pkgOriginLat;
    }

    public String getOriginPackage() {
        return originPackage;
    }

    public void setOriginPackage(String originPackage) {
        this.originPackage = originPackage;
    }

    public String getDestinationPackage() {
        return destinationPackage;
    }

    public void setDestinationPackage(String destinationPackage) {
        this.destinationPackage = destinationPackage;
    }

    public int getpMessengerID() {
        return pMessengerID;
    }

    public void setpMessengerID(int pMessengerID) {
        this.pMessengerID = pMessengerID;
    }

    public int getpUserID() {
        return pUserID;
    }

    public void setpUserID(int pUserID) {
        this.pUserID = pUserID;
    }
}
