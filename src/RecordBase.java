import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RecordBase {
    /*
    * 数据库结构：
    * PlayerAccount(ID，Name，pwd，currency)
    * DrawRecord(Dnum,ID,Cnum,Time)
    * Card(Cnum,Cname,Clevel)
    *
    *
    *
    *
    * */

    private Connection conn;
    Statement stat;
    PreparedStatement insertPlayerAccount,updatePlayerAccount;
    public RecordBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/","KadiASD","Bronya818.");
            stat = conn.createStatement();
            insertPlayerAccount = conn.prepareStatement("insert into PlayerAccount values(?,?,?,?)");
            updatePlayerAccount = conn.prepareStatement("update PlayerAccount set ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void AddPlayerAccount(PlayerAccount playerAccount){
        try{
            insertPlayerAccount.setString(1,playerAccount.getID());
            insertPlayerAccount.setString(2,playerAccount.getName());
            insertPlayerAccount.setString(3,playerAccount.getPwd());
            insertPlayerAccount.setInt(4,playerAccount.getCurrency());
            insertPlayerAccount.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UpdatePlayerAccount(PlayerAccount playerAccount){

    }




}
