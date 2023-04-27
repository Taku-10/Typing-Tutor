import java.awt.*;
import java.util.Random;
import java.awt.geom.Rectangle2D;
import static java.lang.Thread.sleep;

public class HungryWordMover extends FallingWord 
{
	
    HungryWordMover(String txt, int mX)
    {
        super(txt);
        this.x = -200;
        this.y = 200;
        this.maxX = mX;

    }
    
    @Override
    public synchronized  void drop(int inc)
    {
    	
        Intersection intersection = new Intersection();
        FontMetrics metrics = intersection.getFontMetrics();
        
        if (this.getX() == -200) 
        {
            Random random = new Random();
            
            int sleep = random.nextInt(7000);
            
            try
            {
            	
                this.wait(sleep);

            } catch (InterruptedException e)
            
            {
                throw new RuntimeException(e);
            }
            
            Rectangle2D rec = metrics.getStringBounds(this.getWord(), intersection.getGraphics());
            int start = 200 - (int) rec.getWidth();
            setX(x + start);
        } 
        else 
        {
            setX(x + inc);

            Rectangle2D bound = metrics.getStringBounds(this.getWord(), intersection.getGraphics());

            int startX = this.getX();
            int endx = this.getX() + ((int) bound.getWidth()); 
            int starty = this.getY();
            int endy = this.getY() + (int) bound.getHeight();
           
            for (int i = 0; i < existingW.size(); i++) 
            {
                FallingWord word = existingW.get(i);
                
                if (word != this)
                {
                    bound = metrics.getStringBounds(word.getWord(), intersection.getGraphics());
                    int wordStartX = word.getX();
                    int wordEndX = word.getX() + ((int) bound.getWidth());
                    int wordStartY = word.getY();
                    int wordEndY = word.getY() + (int) bound.getHeight();

                    if (wordStartX >= startX && wordStartY >= starty && wordStartX < endx && wordStartY < endy)
                    {
                        word.dropped(true);

                    }
                    
                    else if (wordStartX >= startX && wordEndY >= starty && wordStartX < endx && wordEndY < endy) {
                        word.dropped(true);

                        
                    } else if (wordEndX >= startX && wordStartY >= starty && wordEndX < endx && wordStartY <= endy) {
                        word.dropped(true);

                        
                    } else if (wordEndX >= startX && wordEndY >= starty && wordEndX < endx && wordEndY < endy) {
                        word.dropped(true);

                    }

                }
            }
        }
    }
    
    @Override
    public synchronized void resetPos()
    {
        setX(-200);
    }
}
