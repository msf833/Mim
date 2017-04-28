package ir.mim_app.mim;

/**
 * Created by MSF on 4/28/2017.
 */

public class event {

    String sendtimeStamp="";
    String mainContent="";
    String ActiveFlag="";
    String seeStatus="";
    String pic="";
    public event(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getSendtimeStamp() {
        return sendtimeStamp;
    }

    public void setSendtimeStamp(String sendtimeStamp) {
        this.sendtimeStamp = sendtimeStamp;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getActiveFlag() {
        return ActiveFlag;
    }

    public void setActiveFlag(String activeFlag) {
        ActiveFlag = activeFlag;
    }

    public String getSeeStatus() {
        return seeStatus;
    }

    public void setSeeStatus(String seeStatus) {
        this.seeStatus = seeStatus;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public event(String sendtimeStamp, String mainContent, String activeFlag, String seeStatus) {
        this.sendtimeStamp = sendtimeStamp;
        this.mainContent = mainContent;
        ActiveFlag = activeFlag;
        this.seeStatus = seeStatus;

    }
    public event(String sendtimeStamp, String mainContent, String activeFlag, String seeStatus, String pic) {
        this.sendtimeStamp = sendtimeStamp;
        this.mainContent = mainContent;
        ActiveFlag = activeFlag;
        this.seeStatus = seeStatus;
        this.pic = pic;
    }
}
