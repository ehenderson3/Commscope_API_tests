package comm.service.model;

public class SegmentModel {

    private double elevation;
    private int latitude;
    private double longitude;
    private String siteName;


    public SegmentModel(){}


    public SegmentModel(double elevationVal ,int latitudeVal, String siteNameVal, double longitudeVal ){
        this.elevation = elevationVal;
        this.latitude = latitudeVal;
        this.siteName = siteNameVal;
        this.longitude = longitudeVal;
    };


    public double getElevation(){
        return elevation;
    }

    public void setElevation(int elevationVal){
        this.elevation = elevationVal;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(int latitudeVal){
        this.latitude = latitudeVal;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitudeVal){
        this.longitude = longitudeVal;
    }

    public String getSiteName(){
        return siteName;
    }

    public void setSiteName(String siteNameVal){
        this.siteName = siteNameVal;
    }



}
