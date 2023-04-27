import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;

public class FallingWord 
{
   
    private String word; // the word
    protected int x; //position - width
    protected int y; // postion - height
    protected int maxY;
    protected int maxX;
     //maximum height
    
    private boolean dropped; //flag for if user does not manage to catch word in time

    private int fallingSpeed; //how fast this word is
    private static int maxWait=1000;
    private static int minWait=100;


    public static WordDictionary dict;
    private boolean hungry;

    protected static List<FallingWord> existingW;

    FallingWord()
    { //constructor with defaults
        word="computer"; // a default - not used
        x=0;
        y=0;
        maxY=300;
        maxX=900;
        dropped=false;
        fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
        hungry=false;

        if(existingW == null) {
            existingW = new ArrayList<>();
        }
        existingW.add(this);
    }



    FallingWord(String text) 
    {
        this();
        this.word=text;
    }


    FallingWord(String text,int x, int maxY) 
    { //most commonly used constructor - sets it all.
        this(text);
        this.x=x; //only need to set x, word is at top of screen at start
        this.maxY=maxY;
    }


    public static void increaseSpeed( ) 
    {
        minWait+=50;
        maxWait+=50;
    }

    public static void resetSpeed( ) 
    {
        maxWait=1000;
        minWait=100;
    }


    // all getters and setters must be synchronized
    public synchronized  void setY(int y) 
    {
        if (y>maxY)
        {
            y=maxY;
            dropped=true; //user did not manage to catch this word
        }
        this.y=y;
    }
    public synchronized  void setX(int x) 
    {
        if (x>maxX) 
        {
            x=maxX;
            dropped=true; //user did not manage to catch this word
        }
        this.x=x;
    }

    public synchronized  String getWord()
    {
        return word;
    }

    public synchronized  int getX() 
    {
        return x;
    }

    public synchronized  int getY()
    {
        return y;
    }

    public synchronized  int getSpeed()
    {
        return fallingSpeed;
    }

    public synchronized void setPos(int x, int y)
    {
        setY(y);
        setX(x);
    }
    public synchronized void resetPos() 
    {
        setY(0);
    }

    public synchronized void resetWord() 
    {

           resetPos();


           dropped = false;

//
        word = dict.getNewWord();
        fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
       //System.out.println(getWord() + " falling speed = " + getSpeed());
    }

    public synchronized boolean matchWord(String typedText) {
        //System.out.println("Matching against: "+text);
        if (typedText.equals(this.word)) {
            resetWord();
            return true;
        }
        else
            return false;
    }


    public synchronized  void drop(int inc) {
        setY(y+inc);
    }



    public synchronized  boolean dropped() {
        return dropped;
    }


    public  synchronized void  dropped(boolean b) {
        dropped=b;
    }
    public void  setHungry(boolean b) {
        hungry=b;
    }
    public boolean getHungry(){
        return hungry;
    }

}
