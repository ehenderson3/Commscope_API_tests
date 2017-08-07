package comm.service.model;


import java.util.List;

public class RadioModel {
    private int radioId;
    private List radioModulations;
    private int createUserId;





    public RadioModel(int radioIdVal, List radioModulationsVal, int createUserIdVal){

        this.radioId = radioIdVal;
        this.radioModulations = radioModulationsVal;
        this.createUserId = createUserIdVal;
    }


    public int getRadioId(){
        return radioId;
    }

    public void setRadioId(int radioIdVal){
        this.radioId = radioIdVal;
    }



    public List getRadioModulations(){
        return radioModulations;
    }

    public void setRadioModulations(List radioModulationsVal){
        this.radioModulations = radioModulationsVal;
    }


    public int getCreateUserId(){
        return createUserId;
    }

    public void setCreateUserId(int createUserIdVal){
        this.createUserId = createUserIdVal;
    }


}

