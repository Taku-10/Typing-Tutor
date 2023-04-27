import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
    private AtomicBoolean done ; //REMOVE
    private AtomicBoolean started ; //REMOVE
    private AtomicBoolean won ; //REMOVE

    private FallingWord[] words; 
    private int numberOfWords;
    private final static int boarderWidth = 25; //appearance - border
    protected static Graphics  graphics;
    private static boolean isLocked = false;
    protected static FontMetrics fMetrics = null;

    public GamePanel(FallingWord[] words, int maxY, AtomicBoolean d, AtomicBoolean s, AtomicBoolean w) 
    {
    	
        this.words=words; //shared word list
        numberOfWords = words.length; //only need to do this once
        done=d; //REMOVE
        started=s; //REMOVE
        won=w; //REMOVE
    }

    // No-Argument Constructor
    public GamePanel()
    {

    }

    @Override 
    synchronized public void paintComponent(Graphics g) 
    {
    	int height = getHeight() - boarderWidth * 2;
        int width = getWidth() - boarderWidth * 2;
        g.clearRect(boarderWidth,boarderWidth,width,height);//the active space
        g.setColor(Color.pink); //change colour of pen
        g.fillRect(boarderWidth,height,width,boarderWidth); //draw danger zone

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 26));
       
         if(!isLocked)
         {
             fMetrics  = g.getFontMetrics();
             graphics = g;
             isLocked = true;
         }
         
        //draw the words
        if (!started.get()) {
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("Type all the words before they hit the red zone,press enter after each one.",boarderWidth*2,height/2);

        }

        else if (!done.get()) {
            boolean bool = false;
            
            for (int i = 0; i < numberOfWords; i++) 
            {
            	// Instance of HungryWord class
                if( words[i].getHungry()){
                    g.setColor(Color.green);
                }
                else{
                    g.setColor(Color.black);
                }

                g.drawString(words[i].getWord(),words[i].getX()+boarderWidth,words[i].getY());
        }
            g.setColor(Color.lightGray); 
            g.fillRect(boarderWidth,0,width,boarderWidth);
        }
        else { if (won.get()) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Well done!",width/3,height/2);
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game over!",width/2,height/2);

        }
        }
    }

    public int getValidXpos() {
        int width = getWidth()-boarderWidth*4;
        int x= (int)(Math.random() * width);
        return x;
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            };
        }
    }


}


