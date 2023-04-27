import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.

public class CatchWord extends Thread
{
    String target;
    static AtomicBoolean done ; //REMOVE
    static AtomicBoolean pause; //REMOVE

    private static  FallingWord[] words, hungryy; //list of words
    private static int noWords; //how many
    private static Score score; //user score

    CatchWord(String typedWord)
    {
        target=typedWord;
    }

    public static void setWords(FallingWord[] wordList)
    {
        words=wordList;
        hungryy=new FallingWord[wordList.length/2];
        noWords = words.length;
    }
    public static void setScore(Score sharedScore) 
    {
        score=sharedScore;
    }

    public static void setFlags(AtomicBoolean d, AtomicBoolean p)
    {
        done=d;
        pause=p;
    }
    
    @Override
   public void run()
    {
       int i = 0;
       FallingWord closeToPinkZone = null;
       int duplicateCount = 0;
       boolean caught = false;
       while (i < noWords)
       {
           while (pause.get()) {
           };
           
           if (words[i].getWord().equals(target)) 
           {
               caught = true;
               duplicateCount++;
               
               if (duplicateCount > 1) 
               {
                   if (closeToPinkZone.getY() < words[i].getY())
                   {
                       closeToPinkZone = words[i];
                   }

               } 
               else
               {
                   closeToPinkZone = words[i];
               }

           }
           
           i++;
       }
       if (caught) 
       {

           if (closeToPinkZone.matchWord(target)) 
           {
               System.out.println(" score! '" + target); 
               score.caughtWord(target.length());
           }
       }
   }
}