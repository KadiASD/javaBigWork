public class Card {
    private String Cnum;
    private Level level;
    private String name;

    public Card(String Cnum, Level level,String name){
        this.Cnum = Cnum;
        this.level = level;
        this.name = name;
    }

    public Level getLevel(){return level;}

    public String getName(){return name;}
    public String getCnum(){return Cnum;}
}
