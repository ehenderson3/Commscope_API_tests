package comm.service.model;

public class SiteModel {

    private int count;
    private String callSign;
    private String companyCode;
    private int companyId;
    private String companyName;
    private int elevation;
    private int elevationUS;
    private int latitude;
    private int longitude;
    private int siteId;
    private String siteName;

    public SiteModel(int count, String callSign, String companyCode, int companyId, String companyName, int elevation, int elevationUS, int latitude, int longitude, int siteId, String siteName) {
        this.count = count;
        this.callSign = callSign;
        this.companyCode = companyCode;
        this.companyId = companyId;
        this.companyName = companyName;
        this.elevation = elevation;
        this.elevationUS = elevationUS;
        this.latitude = latitude;
        this.longitude = longitude;
        this.siteId = siteId;
        this.siteName = siteName;
    }

    public SiteModel() {
    }

    public SiteModel(int siteId) {
        this.siteId = siteId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getElevationUS() {
        return elevationUS;
    }

    public void setElevationUS(int elevationUS) {
        this.elevationUS = elevationUS;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
};






