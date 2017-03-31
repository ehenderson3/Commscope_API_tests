package comm.service.model;

/**
 * Created by ehend on 2/11/2017.
 */
public class Projects {
    private int createUserId;
    private int defaultCompanyId;
    private String projectName;
    private String projectType;



    private String defaultLicensee;
    private int fresnelZoneRadius;
    private double kFactor;
    private int minimumClearance;
    private boolean showSiteLocationDetails;
    private double targetAvailability;
    private String unitType;


    public Projects(){};

    public Projects(int createUserId, int defaultCompanyId, String projectName, String projectType){
        this.createUserId = createUserId;
        this.defaultCompanyId = defaultCompanyId;
        this.projectName = projectName;
        this.projectType = projectType;
    };

    public Projects(int createUserId, String projectName, String projectType){
        this.createUserId = createUserId;
        this.projectName = projectName;
        this.projectType = projectType;
    };

    public Projects(int defaultCompanyId, double kFactorVal,double targetAvailabilityVal, int fresnelZoneRadiusVal, int  minimumClearanceVal ){
        this.defaultCompanyId = defaultCompanyId;
        this.kFactor = kFactorVal;
        this.targetAvailability = targetAvailabilityVal;
        this.fresnelZoneRadius = fresnelZoneRadiusVal;
        this.minimumClearance = minimumClearanceVal;


    };

    public Projects(int defaultCompanyId, double kFactorVal,double targetAvailabilityVal, int fresnelZoneRadiusVal, int  minimumClearanceVal, boolean showSiteLocationDetailsVal ){
        this.defaultCompanyId = defaultCompanyId;
        this.kFactor = kFactorVal;
        this.targetAvailability = targetAvailabilityVal;
        this.fresnelZoneRadius = fresnelZoneRadiusVal;
        this.minimumClearance = minimumClearanceVal;
        this.showSiteLocationDetails = showSiteLocationDetailsVal;


    };
    public Projects(int defaultCompanyIdVal, int fresnelZoneRadiusVal, int kFactorVal, int minimumClearanceVal, boolean showSiteLocationDetailsVal,double targetAvailabilityVal, String unitTypeVal ){
        this.defaultCompanyId = defaultCompanyIdVal;
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

    public int getDefaultCompanyId(){
        return defaultCompanyId;
    }
    public void setDefaultLicensee(int defaultCompanyIdVal){
        this.defaultCompanyId = defaultCompanyIdVal;
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

    public int getMinimumClearance(){
        return minimumClearance;
    }
    public void setMinimumClearance(int minimumClearanceVal){
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

//    public int getDefaultLicenseeId(){
//        return defaultCompanyId;
//    }
//
//    public void setDefaultLicenseeId(int defaultLicenseeId){
//        this.defaultCompanyId = defaultLicenseeId;
//    }

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



