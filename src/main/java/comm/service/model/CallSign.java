package comm.service.model;

public class CallSign {
    private int createUserId;
    private int defaultCompanyId;
    private String projectName;
    private String projectType;



    public CallSign(){};

    public CallSign(int createUserId, int defaultCompanyId, String projectName, String projectType){
        this.createUserId = createUserId;
        this.defaultCompanyId = defaultCompanyId;
        this.projectName = projectName;
        this.projectType = projectType;
    };


    public int getDefaultCompanyId(){
        return defaultCompanyId;
    }

    public void defaultCompanyId(int defaultCompanyId){
        this.defaultCompanyId = defaultCompanyId;
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


