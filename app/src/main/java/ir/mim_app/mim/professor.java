package ir.mim_app.mim;

import android.graphics.Bitmap;

/**
 * Created by MSF on 4/9/2017.
 */

public class professor {


    String profName;
    String profFamily;
    String profPic;
    float profRate;
    Bitmap bmp;

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
}
