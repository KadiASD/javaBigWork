import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class RecordBase {
    /*
    * 数据库结构：
    * PlayerAccount(ID，Name，pwd，currency)
    * DrawRecord(Dnum,ID,Cnum,Time)
    * Card(Cnum,Cname,Clevel)
    *
    *
    * */

    private Connection conn;
    private PreparedStatement insertPlayerAccount,updatePlayerAccount,insertCard,getCardPool,getPlayerAccount,getAccountByID,insertDrawRecord,getCountDrawRecord,getDrawRecord;
    public RecordBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CardRecord","KadiASD","Bronya818.");
            insertPlayerAccount = conn.prepareStatement("insert into PlayerAccount values(?,?,?,?,?,?);");
            updatePlayerAccount = conn.prepareStatement("update PlayerAccount set currency = ?, Sgap = ?, Agap = ? where ID = ?;");
            insertCard = conn.prepareStatement("insert into Card values(?,?,?);");
            getCardPool = conn.prepareStatement("Select * from Card;");
            getPlayerAccount = conn.prepareStatement("Select * from PlayerAccount where ID = ? and pwd = ?;");
            getAccountByID = conn.prepareStatement("Select * from PlayerAccount where ID = ?;");
            insertDrawRecord = conn.prepareStatement("insert into DrawRecord values(?,?,?,?);");
            getCountDrawRecord = conn.prepareStatement("select count(*) from DrawRecord;");
            getDrawRecord = conn.prepareStatement("select * from DrawRecord where ID = ?;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertPlayerAccount(PlayerAccount playerAccount){
        try{
            insertPlayerAccount.setString(1,playerAccount.getID());
            insertPlayerAccount.setString(2,playerAccount.getName());
            insertPlayerAccount.setString(3,playerAccount.getPwd());
            insertPlayerAccount.setInt(4,playerAccount.getCurrency());
            insertPlayerAccount.setInt(5,playerAccount.getDrawRecord().getSGap());
            insertPlayerAccount.setInt(6,playerAccount.getDrawRecord().getAGap());

            insertPlayerAccount.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UpdatePlayerAccount(PlayerAccount playerAccount){
        try {
            updatePlayerAccount.setInt(1, playerAccount.getCurrency());
            updatePlayerAccount.setInt(2,playerAccount.getDrawRecord().getSGap());
            updatePlayerAccount.setInt(3,playerAccount.getDrawRecord().getAGap());
            updatePlayerAccount.setString(4,playerAccount.getID());

            updatePlayerAccount.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertCard(Card card){
        try {
            insertCard.setString(1,card.getCnum());
            insertCard.setString(2,card.getName());
            insertCard.setString(3,card.getLevel().toString());
            insertCard.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertCardPool(CardPool cardPool){
        for(Card card:cardPool.Spool){
            InsertCard(card);
        }
        for(Card card:cardPool.Apool){
            InsertCard(card);
        }
        for(Card card:cardPool.Bpool){
            InsertCard(card);
        }
        for(Card card:cardPool.Cpool){
            InsertCard(card);
        }
    }

    public CardPool GetCardPool(){
        CardPool cardPool = new CardPool();
        try {
            ResultSet rs =  getCardPool.executeQuery();
            while (rs.next()){
                String Cnum = rs.getString(1);
                String Slevel = rs.getString(3);
                String Name = rs.getString(2);
                Level level;
                if(Slevel.equals("S")){level = Level.S;}
                else if(Slevel.equals("A")){level = Level.A;}
                else if(Slevel.equals("B")){level = Level.B;}
                else {level = Level.C;}
                Card card = new Card(Cnum,level,Name);
                cardPool.putCard(card);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cardPool;
    }

    public PlayerAccount GetPlayerAccount(String ID,String pwd){
        PlayerAccount playerAccount = new PlayerAccount();
        try {
            getPlayerAccount.setString(1,ID);
            getPlayerAccount.setString(2,pwd);
            ResultSet rs = getPlayerAccount.executeQuery();
            if(rs.next()){
                playerAccount = new PlayerAccount(rs.getString(1),rs.getString(2),rs.getString(3));
                playerAccount.recharge(rs.getInt(4));
                playerAccount.getDrawRecord().setSGap(rs.getInt(5));
                playerAccount.getDrawRecord().setAGap(rs.getInt(6));
            }else {return null;}
        }catch (Exception e){
            e.printStackTrace();
        }
        return playerAccount;
    }

    public PlayerAccount GetAccountByID(String ID){
        PlayerAccount playerAccount = new PlayerAccount();
        try {
            getAccountByID.setString(1,ID);
            ResultSet rs = getAccountByID.executeQuery();
            if(rs.next()){
                playerAccount = new PlayerAccount(rs.getString(1),rs.getString(2),rs.getString(3));
            }else {return null;}
        }catch (Exception e){
            e.printStackTrace();
        }
        return playerAccount;
    }

    public void InsertDrawRecord(PlayerAccount playerAccount,Card card){
        try{
        ResultSet rs = getCountDrawRecord.executeQuery();
        rs.next();
        String Dnum = rs.getInt(1) + 1 + "";
        while (Dnum.length() < 6){
            Dnum = "0" + Dnum;
        }
        insertDrawRecord.setString(1,Dnum);
        insertDrawRecord.setString(2,playerAccount.getID());
        insertDrawRecord.setString(3,card.getCnum());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        insertDrawRecord.setString(4, now.format(dateTimeFormatter));
        insertDrawRecord.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Vector<String> GetDrawRecord(PlayerAccount playerAccount){
        Vector<String> record = new Vector<String>();
        try {
            getDrawRecord.setString(1,playerAccount.getID());
            ResultSet rs = getDrawRecord.executeQuery();
            while (rs.next()){
                record.add(rs.getString(1) +" "+ rs.getString(3) + " " + rs.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }






}
