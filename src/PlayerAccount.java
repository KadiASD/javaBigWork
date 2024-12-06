import java.util.Vector;

public class PlayerAccount {
    private String ID;
    private String Name;
    private String pwd;

    private int currency;

    private DrawRecord drawRecord;

    public PlayerAccount(){}

    public PlayerAccount(String ID,String Name,String pwd){
        this.ID = ID;
        this.Name = Name;
        this.pwd = pwd;
        drawRecord = new DrawRecord();
        currency = 0;
    }

    public void recharge(int currency){
        this.currency += currency;
    }

    public Boolean subCurrency(int num){
        if(num > this.currency) return false;
        this.currency -= num;
        return true;
    }

    public DrawRecord getDrawRecord(){
        return drawRecord;
    }

    public String getID(){return ID;}
    public String getName(){return Name;}
    public String getPwd(){return pwd;}
    public int getCurrency(){return currency;}



}
