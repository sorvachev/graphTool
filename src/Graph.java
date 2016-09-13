import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.*;

/**
 * Created by sakic on 9/13/16.
 */
public class Graph {
    private static final Color[] FUNCTION_COLORS = {Color.RED, new Color(0, 127, 0), Color.BLUE};
    private static final int SCALE_INTERVAL = 100;
    private static final int CACHE_SIZE = 100;
    private static final int MAX_CACHE = 750;
    private static final int Q_SIZE = MAX_CACHE / 2;
    private static final int MARK_DIST = 30;
    private final Function[] f;

    private int fCachePos;
    private boolean cacheValid = false;
    private final double[][] fCache;

    private boolean hasGridlines = true;
    private boolean showScale = true;
    private double xScale = 1;
    private double yScale = 1;
    private Map<Point, BufferedImage> cache = new TreeMap<Point, BufferedImage>();
    private final Queue<Point> recentPoints = new ArrayDeque<Point>();

    void setGridlines(boolean isSelected) {
    }

    ;

    void setShowScale(boolean isSelected) {
    }

    ;

    void setXScale(int xScale) {
    }

    ;

    void setYScale(int scale) {
    }

    ;

    public Graph(Function... functions) {
        f = Arrays.copyOf(functions, functions.length);
        fCache = new double[functions.length][CACHE_SIZE];
    }

    private BufferedImage getImageAt(int x, int y) {
        CPoint p = new CPoint(x, y);

        recentPoints.remove(p);
        recentPoints.add(p);
        while (recentPoints.size() > Q_SIZE)
            recentPoints.remove();

        BufferedImage img;

        img = cache.get(p);
        if (img != null)
            return img;
        else {
            double a = toGraphScaleY(y * CACHE_SIZE);
            double b = toGraphScaleY((y + 1) * CACHE_SIZE);
            double yMin = Math.min(a, b);
            double yMax = Math.max(a, b) + 1;
            boolean canUseEmptyImage = true;

            if ((x >= -1 && x <= 0) || (y >= -1 && y <= 0))
                canUseEmptyImage = false;
            else {
                fillCache(x);
                test_loop:
                for (int fi = 0; fi < f.length; fi++) {
                    Function func = f[fi];
                    double prevVal = func.getApproximation() + toGraphScaleX(x * CACHE_SIZE - 1);
                    int xMin = x * CACHE_SIZE;
                    int xMax = (x + 1) * CACHE_SIZE;
                    for (int i = xMin - 1; i <= xMax + 1; i++) {
                        double val;
                        if (i >= xMin && i < xMax)
                            val = fCache[fi][i - xMin];
                        else
                            val = func.getApproximation() + toGraphScaleX(i);
                        if ((val >= yMin && val <= yMax) || (val < yMin) != (prevVal < yMin)) {
                            canUseEmptyImage = false;
                            break test_loop;
                        }
                    }
                }
            }

        }
return new BufferedImage();
    }
    private static boolean isValidNumber(double x)
    {
        return !(Double.isNaN(x) || Double.isInfinite(x));
    }

    public int toPixelsX(double x)
    {
        return (int) (x * SCALE_INTERVAL / xScale);
    }

    public int toPixelsY(double x)
    {
        return -(int) (x * SCALE_INTERVAL / yScale);
    }

    public double toGraphScaleX(int x)
    {
        double s = x;
        s /= SCALE_INTERVAL;
        s *= xScale;
        return s;
    }

    public double toGraphScaleY(int x)
    {
        double s = x;
        s /= SCALE_INTERVAL;
        s *= yScale;
        return -s;
    }

    private void fillCache(int pos)
    {
        if (fCachePos == pos && cacheValid)
            return;

        fCachePos = pos;
        cacheValid = true;
        for (int fi = 0; fi < f.length; fi++)
        {
            for (int i = 0; i < CACHE_SIZE; i++)
            {
                fCache[fi][i] = f[fi].getApproximation() + toGraphScaleX(pos * CACHE_SIZE + i);
            }
        }
    }
}
