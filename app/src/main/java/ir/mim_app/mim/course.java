package ir.mim_app.mim;

/**
 * Created by MSF on 4/14/2017.
 */

public class course {
    public course(String courseName, String courseID) {
        this.courseName = courseName;
        this.courseID = courseID;
    }

    String courseName;//$row[1]
    String courseID; //$row[0]
    String profName;
    String profID;

    public course(String courseName, String profName, String courseID, String profID) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.profID = profID;
        this.profName = profName;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "course{" +
                "courseName='" + courseName + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }

}
