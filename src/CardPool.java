import java.util.Date;
import java.util.*;

public class CardPool {
    Vector<Card> Cpool;
    Vector<Card> Bpool;
    Vector<Card> Apool;
    Vector<Card> Spool;
    Map<String,Card> cardMap;
    final int cost = 280;
    Map<Level,Double> probability;


    public CardPool(){
        Cpool = new Vector<Card>();
        Bpool = new Vector<Card>();
        Apool = new Vector<Card>();
        Spool = new Vector<Card>();
        cardMap = new HashMap<String,Card>();
        probability = new HashMap<Level,Double>();
        probability.put(Level.S,0.006);
        probability.put(Level.A,0.094);
        probability.put(Level.B,0.4);
        probability.put(Level.C,0.5);

    }

    public void putCard(Card card){
        cardMap.put(card.getCnum(),card);
        Level level = card.getLevel();
        switch (level){
            case S:
                Spool.add(card);
                break;
            case A:
                Apool.add(card);
                break;
            case B:
                Bpool.add(card);
                break;
            case C:
                Cpool.add(card);
                break;
        }
    }

    public Card getCardByCnum(String cnum){
        return cardMap.get(cnum);
    }

    public Card Draw(PlayerAccount playerAccount){
        if(!playerAccount.subCurrency(cost)){return null;}
        Card result;
        DrawRecord drawRecord = playerAccount.getDrawRecord();
        int SGap = drawRecord.getSGap();
        int AGap = drawRecord.getAGap();
        Level level;
        Random random = new Random();
        //Date date = new Date();
        //random.setSeed(date.getTime());
        Double p = random.nextDouble();
        System.out.println(p);
        if(SGap == 1)level = Level.S;
        else if(AGap == 1)level = Level.A;
        else {
            if(p <= probability.get(Level.S)) level = Level.S;
            else if (p<=probability.get(Level.S) + probability.get(Level.A)) level = Level.A;
            else if(p<=probability.get(Level.S) + probability.get(Level.A)+probability.get(Level.B)) level = Level.B;
            else level = Level.C;
        }

        Vector<Card> pool;
        switch (level){
            case S:
                pool = Spool;
                break;
            case A:
                pool = Apool;
                break;
            case B:
                pool = Bpool;
                break;
            case C:
                pool = Cpool;
                break;
            default:
                pool = Cpool;
                break;
        }
        result = pool.get(random.nextInt(pool.size()));
        drawRecord.AddRecord(result);
        return result;
    }

}
