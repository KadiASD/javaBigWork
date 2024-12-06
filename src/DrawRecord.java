import java.util.Vector;

public class DrawRecord {
    private Vector<Card> drawRecord;
    private int SGap;
    private int AGap;
    private final int _SGap = 90;
    private final int _AGap = 10;

    public DrawRecord(){
        SGap = _SGap;
        AGap = _AGap;
        drawRecord = new Vector<Card>();
    }

    public void AddRecord(Card card){
        drawRecord.add(card);
        Level level = card.getLevel();
        if(level == Level.S){SGap = _SGap;AGap = _AGap;}
        else if(level == Level.A){AGap=_AGap;SGap-=1;}
        else{
            AGap -= 1;
            SGap -= 1;
        }
    }

    public Vector<Card> getRecord(){
        return (Vector<Card>) this.drawRecord.clone();
    }

    public void printRecord(){
        int i = 0;
        for (Card card:drawRecord){
            System.out.println(i + card.getName() + "-----" + card.getLevel().toString());
            i++;
        }
    }

    public int getSGap(){return SGap;}
    public int getAGap(){return AGap;}

    public void setSGap(int SGap){this.SGap = SGap;}

    public void setAGap(int AGap) {this.AGap = AGap;}
}
