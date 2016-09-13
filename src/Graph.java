import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.*;

/**
 * Created by sakic on 9/13/16.
 */
public class Graph   {
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
}
