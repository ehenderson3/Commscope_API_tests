package comm.service.model;

/**
 * Created by ehend on 2/11/2017.
 */
public class Projects {
    private int createUserId;
    private int defaultLicenseeId;
    private String projectName;
    private String projectType;



    private String defaultLicensee;
    private int fresnelZoneRadius;
    private double kFactor;
    private String minimumClearance;
    private boolean showSiteLocationDetails;
    private double targetAvailability;
    private String unitType;


    public Projects(){};

    public Projects(int createUserId, int defaultLicensee, String projectName, String projectType){
        this.createUserId = createUserId;
        this.defaultLicenseeId = defaultLicensee;
        this.projectName = projectName;
        this.projectType = projectType;
    };

    public Projects(String projectName, int fresnelZoneRadius, double kFactor, int i, boolean showSiteLocationDetails, double targetAvailability, String projectType){
        this.projectName = projectName;
        this.projectType = projectType;
    };


    public Projects(String defaultLicenseeVal, int fresnelZoneRadiusVal, double kFactorVal, String minimumClearanceVal, boolean showSiteLocationDetailsVal,double targetAvailabilityVal, String unitTypeVal ){
        this.defaultLicensee = defaultLicenseeVal;
        this.fresnelZoneRadius = fresnelZoneRadiusVal;
        this.kFactor = kFactorVal;
        this.minimumClearance = minimumClearanceVal;
        this.showSiteLocationDetails = showSiteLocationDetailsVal;
        this.targetAvailability = targetAvailabilityVal;
        this.unitType = unitTypeVal;
    };

    public Projects(int fresnelZoneRadiusVal ){
        this.fresnelZoneRadius = fresnelZoneRadiusVal;
    };

    public Projects(String unitTypeVal ){
        this.unitType = unitTypeVal;
    };

    public String getDefaultLicensee(){
        return defaultLicensee;
    }
    public void setDefaultLicensee(String defaultLicenseeVal){
        this.defaultLicensee = defaultLicenseeVal;
    }

    public int getFresnelZoneRadius(){
        return fresnelZoneRadius;
    }
    public void setFresnelZoneRadius(int fresnelZoneRadiusVal){
        this.fresnelZoneRadius = fresnelZoneRadiusVal;
    }

    public double getKFactor(){
        return kFactor;
    }
    public void setKFactor(double kFactorVal){
        this.kFactor = kFactorVal;
    }

    public String getMinimumClearance(){
        return minimumClearance;
    }
    public void setMinimumClearance(String minimumClearanceVal){
        this.minimumClearance = minimumClearanceVal;
    }

    public boolean getShowSiteLocationDetails(){
        return showSiteLocationDetails;
    }
    public void setShowSiteLocationDetails(boolean showSiteLocationDetailsVal){
        this.showSiteLocationDetails = showSiteLocationDetailsVal;}

    public double getTargetAvailability(){
        return targetAvailability;
    }
    public void setTargetAvailability(double targetAvailabilityVal){
        this.targetAvailability = targetAvailabilityVal;}

    public String getUnitType(){
        return unitType;
    }
    public void setUnitType(String unitTypeVal) {
        this.unitType = unitTypeVal;

    }


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



