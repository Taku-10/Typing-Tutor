import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {


    private FallingWord myWord;
    private AtomicBoolean done;
    private AtomicBoolean pause;
    private Score score;
    CountDownLatch startLatch; //so all can start at once
    private FallingWord hungryword;

    WordMover( FallingWord word) {
        myWord = word;

    }

    WordMover( FallingWord word,WordDictionary dict, Score score, CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
        this(word);
        this.startLatch = startLatch;
        this.score=score;
        this.done=d;
        this.pause=p;
    }



    @Override
   public void run() {
       
        try {
            startLatch.await();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //wait for other threads to start
        System.out.println(myWord.getWord() + " started" );
        while (!done.get()) {
            //animate the word
            while (!myWord.dropped() && !done.get()) {
                myWord.drop(10);

                try {
                    sleep(myWord.getSpeed());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                while(pause.get()&&!done.get()) {};
            }
            if (!done.get() && myWord.dropped()) {
                score.missedWord();
                myWord.resetWord();
            }
            myWord.resetWord();
        }
    }
    public FallingWord getFallingWords() {
        return myWord;
    }
    public void setHungry(FallingWord hungry){
        hungryword =hungry;
    }


}
