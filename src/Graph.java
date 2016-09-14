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

public class Graph
{
    private static final Color[] FUNCTION_COLORS = {Color.RED, new Color(0, 127, 0), Color.BLUE};
    private static final int SCALE_INTERVAL = 100;
    private static final int CACHE_SIZE = 100;
    private static final int MAX_CACHE = 750;
    private static final int Q_SIZE = MAX_CACHE / 2;
    private static final int MARK_DIST = 30;

    private static final Color gridline_colour = new Color(180, 180, 180);
    private static final BufferedImage empty_image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

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

    public Graph(Function... functions)
    {
        f = Arrays.copyOf(functions, functions.length);
        fCache = new double[functions.length][CACHE_SIZE];
    }

    private BufferedImage getImageAt(int x, int y)
    {
        CPoint p = new CPoint(x, y);

        recentPoints.remove(p);
        recentPoints.add(p);
        while (recentPoints.size() > Q_SIZE)
            recentPoints.remove();

        BufferedImage img;

        img = cache.get(p);
        if (img != null)
            return img;
        else
        {
            double a = toGraphScaleY(y * CACHE_SIZE);
            double b = toGraphScaleY((y+1) * CACHE_SIZE);
            double yMin = Math.min(a, b);
            double yMax = Math.max(a, b) + 1;
            boolean canUseEmptyImage = true;

            if ((x >= -1 && x <= 0) || (y >= -1 && y <= 0))
                canUseEmptyImage = false;
            else
            {
                fillCache(x);
                test_loop:
                for (int fi = 0; fi < f.length; fi++)
                {
                    Function func = f[fi];
                    double prevVal = func.getApproximation() + toGraphScaleX(x * CACHE_SIZE - 1);
                    int xMin = x * CACHE_SIZE;
                    int xMax = (x+1) * CACHE_SIZE;
                    for (int i = xMin - 1; i <= xMax + 1; i++)
                    {
                        double val;
                        if (i >= xMin && i < xMax)
                            val = fCache[fi][i - xMin];
                        else
                            val = func.getApproximation() + toGraphScaleX(i);
                        if ((val >= yMin && val <= yMax) || (val < yMin) != (prevVal < yMin))
                        {
                            canUseEmptyImage = false;
                            break test_loop;
                        }
                    }
                }
            }

            if (canUseEmptyImage)
                img = empty_image;
            else
            {
                img = new BufferedImage(CACHE_SIZE, CACHE_SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                drawGraph(g, new Rectangle(x * CACHE_SIZE, y * CACHE_SIZE, CACHE_SIZE, CACHE_SIZE));
            }

            cache.put(p, img);

            if (cache.size() > MAX_CACHE)
            {
                Map<Point, BufferedImage> newCache = new TreeMap<Point, BufferedImage>();
                for (Map.Entry<Point, BufferedImage> e : cache.entrySet())
                {
                    if (recentPoints.contains(e.getKey()))
                        newCache.put(e.getKey(), e.getValue());
                }
                cache = newCache;
            }

            return img;
        }
    }

    private void drawGraph(Graphics2D g, Rectangle rect)
    {
        g.translate(-rect.x, -rect.y);

        int x = rect.x;
        int y = rect.y;
        int w = rect.width;
        int h = rect.height;

        int sx = x - x % SCALE_INTERVAL;
        for (int i = sx; i <= x + w + SCALE_INTERVAL; i += SCALE_INTERVAL)
        {
            if (xScale > 30 && i % 2 != 0 || !showScale)
                continue;
            g.setColor(Color.black);
            String txt = Function.truncate(Function.formatNumber(i / SCALE_INTERVAL * xScale), 2);
            g.drawString(txt, i + 5, -5);
        }

        int sy = y - y % SCALE_INTERVAL;
        for (int i = sy; i <= y + h + SCALE_INTERVAL; i += SCALE_INTERVAL)
        {
            if ((yScale > 30 && i % 2 != 0) || i == 0 || !showScale)
                continue;
            g.setColor(Color.black);
            String txt = Function.truncate(Function.formatNumber(-i / SCALE_INTERVAL * yScale), 2);
            g.drawString(txt, 5, i);
        }

        for (int i = f.length - 1; i >= 0; i--)
        {
            g.setColor(FUNCTION_COLORS[i % FUNCTION_COLORS.length]);
            double prevVal = f[i].getApproximation() + toGraphScaleX(x-1);
            fillCache(x / CACHE_SIZE);
            for (int j = x; j <= x + w + 1; j++)
            {
                double val;

                if (j < x + w)
                    val = fCache[i][j-x];
                else
                    val = f[i].getApproximation() + toGraphScaleX(j);
                if (isValidNumber(val))
                {
                    if (!isValidNumber(prevVal))
                        prevVal = val;
                    g.drawLine(j-1, toPixelsY(prevVal), j, toPixelsY(val));
                }

                prevVal = val;
            }
        }

        g.translate(rect.x, rect.y);
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

    public void draw(Graphics2D g, Rectangle rect)
    {
        int sx = rect.x / CACHE_SIZE - 1;
        int sy = rect.y / CACHE_SIZE - 1;
        int ex = (rect.x + rect.width) / CACHE_SIZE + 1;
        int ey = (rect.y + rect.height) / CACHE_SIZE + 1;

        int tx = -rect.x;
        int ty = -rect.y;

        g.translate(tx, ty);

        g.setColor(Color.white);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        if (hasGridlines)
        {
            g.setColor(gridline_colour);

            int x = rect.x - rect.x % SCALE_INTERVAL;
            int y = rect.y - rect.y % SCALE_INTERVAL;

            for (; x <= rect.x + rect.width; x += SCALE_INTERVAL)
                g.drawLine(x, rect.y, x, rect.y + rect.height);
            for (; y <= rect.y + rect.height; y += SCALE_INTERVAL)
                g.drawLine(rect.x, y, rect.x + rect.width, y);
        }

        for (int i = sx; i <= ex; i++)
        {
            for (int j = sy; j <= ey; j++)
            {
                BufferedImage img = getImageAt(i, j);
                if (img != null)
                    g.drawImage(img, i * CACHE_SIZE, j * CACHE_SIZE, null);
            }
        }

        g.setColor(Color.BLACK);
        g.drawLine(0, rect.y, 0, rect.y + rect.height);
        g.drawLine(rect.x, 0, rect.x + rect.width, 0);

        g.setColor(Color.BLACK);
        if (rect.x > MARK_DIST && showScale)
        {
            int y = rect.y - 20;
            y -= y % SCALE_INTERVAL;

            for (; y <= rect.y + rect.height + 20; y += SCALE_INTERVAL)
                if (y != 0)
                    g.drawString(Function.truncate(Function.formatNumber(toGraphScaleY(y)), 2), rect.x + 5, y - 4);
        }
        if (rect.x + rect.width < MARK_DIST - 20 && showScale)
        {
            int y = rect.y - 20;
            y -= y % SCALE_INTERVAL;

            for (; y <= rect.y + rect.height + 20; y += SCALE_INTERVAL)
            {
                if (y != 0)
                {
                    String txt = Function.truncate(Function.formatNumber(toGraphScaleY(y)), 2);
                    g.drawString(txt, rect.x + rect.width - 5 - g.getFontMetrics().stringWidth(txt), y - 4);
                }
            }
        }

        if (rect.y > MARK_DIST && showScale)
        {
            int x = rect.x - 20 + SCALE_INTERVAL * 3 / 2;
            x -= x % SCALE_INTERVAL;

            for (; x <= rect.x + rect.width - 35; x += SCALE_INTERVAL)
                if (x != 0)
                    g.drawString(Function.truncate(Function.formatNumber(toGraphScaleX(x)), 2), x + 4, rect.y + 14);
        }
        if (rect.y  + rect.height < MARK_DIST - 40 && showScale)
        {
            int x = rect.x - 20 + SCALE_INTERVAL * 3 / 2;
            x -= x % SCALE_INTERVAL;

            for (; x <= rect.x + rect.width - 35; x += SCALE_INTERVAL)
                if (x != 0)
                    g.drawString(Function.truncate(Function.formatNumber(toGraphScaleX(x)), 2), x + 4, rect.y + rect.height - 10);
        }

        g.translate(-tx, -ty);
    }



    public boolean hasGridlines()
    {
        return hasGridlines;
    }

    public void setGridlines(boolean hasGridlines)
    {
        this.hasGridlines = hasGridlines;
    }



    public boolean showingScale()
    {
        return showScale;
    }

    public void setShowScale(boolean showScale)
    {
        if (showScale != this.showScale)
        {
            this.showScale = showScale;
            invalidate();
        }
    }

    public double getXScale()
    {
        return xScale;
    }

    public void setXScale(double xScale)
    {
        if (xScale != this.xScale)
        {
            this.xScale = xScale;
            invalidate();
        }
    }

    public double getYScale()
    {
        return yScale;
    }

    public void setYScale(double yScale)
    {
        if (yScale != this.yScale)
        {
            this.yScale = yScale;
            invalidate();
        }
    }

    private void invalidate()
    {
        cache.clear();
        cacheValid = false;
    }

    @Override
    public String toString()
    {
        if (f.length == 0)
            return "Graph of nothing";

        StringBuilder sb = new StringBuilder("Graph of ");
        for (int i = 0; i < f.length; i++)
        {
            if (i != 0)
                sb.append(", ");
            sb.append("y = ");
            sb.append(f[i]);
        }
        return sb.toString();
    }
}