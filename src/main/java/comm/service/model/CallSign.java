package comm.service.model;

public class CallSign {
    private int createUserId;
    private int defaultLicenseeId;
    private String projectName;
    private String projectType;



    public CallSign(){};

    public CallSign(int createUserId, int defaultLicensee, String projectName, String projectType){
        this.createUserId = createUserId;
        this.defaultLicenseeId = defaultLicensee;
        this.projectName = projectName;
        this.projectType = projectType;
    };


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


