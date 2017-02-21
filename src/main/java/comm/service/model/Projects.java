package comm.service.model;

/**
 * Created by ehend on 2/11/2017.
 */
public class Projects {
    private int createUserId;
    private int defaultLicenseeId;
    private String projectName;
    private String projectType;

    public Projects(){};

    public Projects(int createUserId, int defaultLicensee, String projectName, String projectType){
        this.createUserId = createUserId;
        this.defaultLicenseeId = defaultLicensee;
        this.projectName = projectName;
        this.projectType = projectType;
    };

    public Projects(String projectName, String projectType){
        this.projectName = projectName;
        this.projectType = projectType;
    };


    public int getCreateUserId(){
        return createUserId;
    }

    public void setCreateUserId(int createUserId){
        this.createUserId = createUserId;
    }

    public int getDefaultLicenseeId(){
        return defaultLicenseeId;
    }

    public void setDefaultLicenseeId(int defaultLicenseeId){
        this.defaultLicenseeId = defaultLicenseeId;
    }

    public String getProjectName(){
        return projectName;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectType(){
        return projectType;
    }

    public void setProjectType(String projectType){
        this.projectType = projectType;
    }
}



