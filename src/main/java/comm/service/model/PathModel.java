package comm.service.model;


public class PathModel {
    private int projectId;
    private int bandId;
    private String pathName;

    public PathModel(){}




    public PathModel(int projectIdVal,  int bandIdVal, String pathNameVal){

        this.projectId = projectIdVal;
        this.bandId = bandIdVal;
        this.pathName = pathNameVal;
    }




    public void setCount(int countVal){
        this.projectId = countVal;
    }


    public int getProjectId(){
        return projectId;
    }

    public void setProjectId(int projectIdVal){
        this.projectId = projectIdVal;
    }

    public int getBandId(){
        return bandId;
    }

    public void setBandId(int bandIdVal){
        this.bandId = bandIdVal;
    }


    public String getpathName(){
        return pathName;
    }

    public void setpathName(String pathNameVal){
        this.pathName = pathNameVal;
    }




}

