package ir.mim_app.mim;

import android.graphics.Bitmap;

/**
 * Created by MSF on 4/9/2017.
 */

public class professor {


    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isAliRid() {
        return aliRid;
    }

    public void setAliRid(boolean aliRid) {
        this.aliRid = aliRid;
    }

    String profName;
    String profFamily;
    String profPic;
    float profRate;
    Bitmap bmp;
    String courseID;
    String courseName;
    boolean aliRid = false;

    public professor(String courseID, String courseName, String profID, String profName, String pic,boolean aliRid) {
        this.profName = profName;
        this.profPic = pic;
        this.profRate = 0;
        this.ProfessorsID = profID;
        this.courseID = courseID;
        this.courseName = courseName;
        this.aliRid=aliRid;
    }

    public String getProfessorsID() {
        return ProfessorsID;
    }

    public void setProfessorsID(String professorsID) {
        ProfessorsID = professorsID;
    }

    String ProfessorsID;

    public professor(String profName, String profFamily, String profPic, float profRate, Bitmap bmp, String professorsID) {
        this.profName = profName;
        this.profFamily = profFamily;
        this.profPic = profPic;
        this.profRate = profRate;
        this.bmp = bmp;
        this.ProfessorsID = professorsID;
    }
    public professor(String profName, String profFamily, String profPic, float profRate, String professorsID) {
        this.profName = profName;
        this.profFamily = profFamily;
        this.profPic = profPic;
        this.profRate = profRate;

        this.ProfessorsID = professorsID;
    }

    public professor(String profName, String profFamily, String profPic, float profRate, Bitmap bmp) {
        this.profName = profName;
        this.profFamily = profFamily;
        this.profPic = profPic;
        this.profRate = profRate;
        this.bmp = bmp;
    }

    public professor(String profName, String profFamily, String profPic, float profRate) {
        this.profName = profName;
        this.profFamily = profFamily;
        this.profPic = profPic;
        this.profRate = profRate;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getProfFamily() {
        return profFamily;
    }

    public void setProfFamily(String profFamily) {
        this.profFamily = profFamily;
    }

    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    public String getStringProfRate() {
        String temp = profRate + "";
        return temp;
    }


    public float getProfRate() {
        return profRate;
    }

    public void setProfRate(float profRate) {
        this.profRate = profRate;
    }

    @Override
    public String toString() {
        return "professor{" +
                "profName='" + profName + '\'' +
                ", profFamily='" + profFamily + '\'' +
                ", profPic='" + profPic + '\'' +
                ", profRate=" + profRate +
                ", bmp=" + bmp +
                '}';
    }
}
