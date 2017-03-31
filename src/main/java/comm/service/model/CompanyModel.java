package comm.service.model;


/**
 * Created by ehend on 2/2/2017.
 */
public class CompanyModel {
    private String companyName;
    private String contactEmail;
    private String contactName;
    private String licenseeCode;

    public CompanyModel(){};

    public CompanyModel(String companyName, String contactEmail, String licenseeCode, String contactName){
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.licenseeCode = licenseeCode;
        //this.licenseeId = licenseeId;
    };

    public CompanyModel(String companyName, String contactEmail, String licenseeCode){
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.licenseeCode = licenseeCode;
    };





    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getContactName(){
        return contactName;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    public String getContactEmail(){
        return contactEmail;
    }

    public void setContactEmail(String contactEmail){
        this.contactEmail = contactEmail;
    }

    public String getLicenseeCode(){
        return licenseeCode;
    }

    public void setLicenseeCode(String licenseeCode){
        this.licenseeCode = licenseeCode;
    }


}
