import java.util.Scanner;
import java.util.Vector;

public class Menu {
    private Scanner scanner;
    private RecordBase recordBase;
    PlayerAccount playerAccount;
    CardPool cardPool;
    public Menu(){
        scanner = new Scanner(System.in);
        recordBase = new RecordBase();
        cardPool = new CardPool();
        cardPool = recordBase.GetCardPool();
        while (true){
            System.out.println("1.创建新账户");
            System.out.println("2.登录现有的账户");
            System.out.println("3.管理员界面");
            System.out.println("4.退出");
            int choose = scanner.nextInt();
            switch (choose){
                case 1:
                    if(!create()){
                        System.out.println("该账户已存在");
                    }else {
                        System.out.println("创建账户成功");
                        menu2();
                    }
                    break;
                case 2:
                    if(!login()){
                        System.out.println("登陆失败");
                    }else {
                        System.out.println("登陆成功");
                        menu2();
                    }
                    break;
                case 3:
                    menu3();
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    private boolean create(){
        String ID,pwd,Name;
        System.out.println("请输入ID：");
        ID = scanner.next();
        if(recordBase.GetAccountByID(ID) != null) return false;
        System.out.println("请输入用户名：");
        Name = scanner.next();
        System.out.println("请输入密码：");
        pwd = scanner.next();
        playerAccount = new PlayerAccount(ID,Name,pwd);
        recordBase.InsertPlayerAccount(playerAccount);
        return true;
    }

    private boolean login(){
        String ID,pwd;
        System.out.println("请输入ID：");
        ID = scanner.next();
        System.out.println("请输入密码：");
        pwd = scanner.next();
        playerAccount = recordBase.GetPlayerAccount(ID,pwd);
        return playerAccount != null;
    }

    private void menu2(){
        while (true){
            System.out.println("货币：" + playerAccount.getCurrency());
            System.out.println("距离S保底还有：" + playerAccount.getDrawRecord().getSGap());
            System.out.println("距离A保底还有：" + playerAccount.getDrawRecord().getAGap());
            System.out.println("1.充值");
            System.out.println("2.抽1发");
            System.out.println("3.抽10发");
            System.out.println("4.查看抽卡记录");
            System.out.println("5.退出");
            int choose = scanner.nextInt();
            switch (choose){
                case 1:
                    recharge();
                    break;
                case 2:
                    draw();
                    break;
                case 3:
                    draw10();
                    break;
                case 4:
                    showRecord();
                    break;
                case 5:
                    return;
            }

        }
    }

    private void recharge(){
        System.out.println("请输入充值金额：");
        int currency = scanner.nextInt();
        playerAccount.recharge(currency);
        recordBase.UpdatePlayerAccount(playerAccount);
    }

    private void draw(){
        Card card = cardPool.Draw(playerAccount);
        if(card != null){
            System.out.println("抽出了："+ card.getName() + " 等级：" + card.getLevel());
            recordBase.InsertDrawRecord(playerAccount,card);
            recordBase.UpdatePlayerAccount(playerAccount);
        }
        else {
            System.out.println("货币不够");
        }
    }

    private void draw10(){
        int currency = playerAccount.getCurrency();
        if(currency >= cardPool.cost * 10){
            for(int i = 0;i<10;i++){
                draw();
            }
        }else {
            System.out.println("货币不够");
        }
    }

    private void showRecord(){
        Vector<String> record = recordBase.GetDrawRecord(playerAccount);
        for(String s:record){
            String[] ss = s.split(" ");
            String cnum = ss[1];
            Card card = cardPool.getCardByCnum(cnum);
            System.out.println(ss[2] + " : " + card.getName() + " : " + card.getLevel().toString());
        }
    }

    private void menu3(){
        while (true){
            System.out.println("1.创建新卡");
            System.out.println("2.退出");
            int choose = scanner.nextInt();
            switch (choose){
                case 1:
                    createNewCard();
                    break;
                case 2:
                    return;
            }
        }
    }

    private boolean createNewCard(){
        boolean res = false;
        String Cnum,Cname,Slevel;
        System.out.println("输入Cnum");
        Cnum = scanner.next();
        System.out.println(Cnum);
        System.out.println("输入Cname");
        Cname = scanner.next();
        System.out.println(Cname);
        System.out.println("输入Level");
        Slevel = scanner.next();
        System.out.println(Slevel);
        Level level;
        if(Slevel.equals("S")){level = Level.S;}
        else if(Slevel.equals("A")){level = Level.A;}
        else if(Slevel.equals("B")){level = Level.B;}
        else {level = Level.C;}
        Card card = new Card(Cnum,level,Cname);
        cardPool.putCard(card);
        recordBase.InsertCard(card);
        return res;
    }
}
