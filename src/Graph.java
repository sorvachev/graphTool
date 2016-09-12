/**
 * Created by sakic on 9/12/16.
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Graph {
    private static final Color[] FUNCTION_COLORS = {Color.RED, new Color(0, 127, 0), Color.BLUE};
    private static final int SCALE_INTERVAL = 100;
    private static final int CACHE_SIZE = 100;
    private static final int MAX_CACHE = 750;
    private static final int Q_SIZE = MAX_CACHE / 2;
    private static final int MARK_DIST = 30;

    private static final Color gridline_colour = new Color(180, 180, 180);
    private static final BufferedImage empty_image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    //final
    private Function[] f;

    private int fCachePos;
    private boolean cacheValid = false;
    //final
    private double[][] fCache;

    private boolean hasGridlines = true;
    private boolean showScale = true;
    private double xScale = 1;
    private double yScale = 1;
    private Map<Point, BufferedImage> cache = new TreeMap<Point, BufferedImage>();
    private final Queue<Point> recentPoints = new ArrayDeque<Point>();
}