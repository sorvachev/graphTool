/**
 * Created by sakic on 9/13/16.
 */
import java.awt.Point;


public class CPoint extends Point implements Comparable<Point>
{
    private static final long serialVersionUID = -477940190824906479L;

    public CPoint()
    {
        super();
    }

    public CPoint(int arg0, int arg1)
    {
        super(arg0, arg1);
    }

    public CPoint(Point arg0)
    {
        super(arg0);
    }

    @Override
    public int compareTo(Point o)
    {
        if (x < o.x)
            return -1;
        if (x > o.x)
            return 1;

        if (y < o.y)
            return -1;
        if (y > o.y)
            return 1;

        return 0;
    }

}