/**
 * Created by sakic on 9/12/16.
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class UIApplet extends JPanel implements MouseMotionListener, ComponentListener, ActionListener, DocumentListener, MouseWheelListener
{
    private static JTextField fText;
    private static JTextField gText;
    private static JTextField hText;

    private static JTextField xText;
    private static JTextField yText;

    private static JTextField xScaleText;
    private static JTextField yScaleText;

    private static double xScale = 1;
    private static double yScale = 1;

    private static JCheckBox gridlineBox;
    private static JCheckBox scaleBox;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                launchGUI();
            }
        });
    }

    private static void launchGUI()
    {
        JFrame frame = new JFrame("JGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIApplet graphPanel = new UIApplet();
        graphPanel.addMouseMotionListener(graphPanel);
        graphPanel.addMouseWheelListener(graphPanel);

        Graph graph = graphPanel.graph;

        JPanel panel;
        JLabel label;
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        fText = new JTextField(9);
        gText = new JTextField(9);
        hText = new JTextField(9);

        fText.setText("d/dx(x*sin(x))");

        fText.getDocument().addDocumentListener(graphPanel);
        gText.getDocument().addDocumentListener(graphPanel);
        hText.getDocument().addDocumentListener(graphPanel);

        controlPanel.add(Box.createVerticalStrut(3));
        label = new JLabel("Functions");
        panel = new JPanel();
        panel.add(label);
        controlPanel.add(panel);
        controlPanel.add(Box.createVerticalStrut(3));

        panel = new JPanel();
        panel.add(new JLabel("f(x) = "));
        panel.add(fText);
        panel.add(Box.createHorizontalGlue());
        controlPanel.add(panel);

        panel = new JPanel();
        panel.add(new JLabel("g(x) = "));
        panel.add(gText);
        panel.add(Box.createHorizontalGlue());
        controlPanel.add(panel);

        panel = new JPanel();
        panel.add(new JLabel("h(x) = "));
        panel.add(hText);
        panel.add(Box.createHorizontalGlue());
        controlPanel.add(panel);

        controlPanel.add(Box.createVerticalStrut(18));

        JCheckBox box;

        box = new JCheckBox("Gridlines");
        box.setSelected(graph.hasGridlines());
        box.addActionListener(graphPanel);
        panel = new JPanel();
        panel.add(box);
        panel.add(Box.createHorizontalGlue());
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 26));
        controlPanel.add(panel);
        gridlineBox = box;

        box = new JCheckBox("Scale markers");
        box.setSelected(graph.showingScale());
        box.addActionListener(graphPanel);
        panel = new JPanel();
        panel.add(box);
        panel.add(Box.createHorizontalGlue());
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 26));
        controlPanel.add(panel);
        scaleBox = box;

        controlPanel.add(Box.createVerticalStrut(12));

        xText = new JTextField(7);
        yText = new JTextField(7);

        xText.setText("0.0");
        yText.setText("0.0");

        xText.getDocument().addDocumentListener(graphPanel);
        yText.getDocument().addDocumentListener(graphPanel);

        panel = new JPanel();
        panel.add(new JLabel("x: "));
        panel.add(xText);
        controlPanel.add(panel);

        panel = new JPanel();
        panel.add(new JLabel("y: "));
        panel.add(yText);
        controlPanel.add(panel);

        controlPanel.add(Box.createVerticalStrut(15));

        xScaleText = new JTextField(6);
        yScaleText = new JTextField(6);

        xScaleText.setText("1.0");
        yScaleText.setText("1.0");

        xScaleText.getDocument().addDocumentListener(graphPanel);
        yScaleText.getDocument().addDocumentListener(graphPanel);

        panel = new JPanel();
        panel.add(new JLabel("x-scale: "));
        panel.add(xScaleText);
        controlPanel.add(panel);

        panel = new JPanel();
        panel.add(new JLabel("y-scale: "));
        panel.add(yScaleText);
        controlPanel.add(panel);

        controlPanel.setBorder(BorderFactory.createEtchedBorder());
        controlPanel.setMaximumSize(controlPanel.getPreferredSize());
        controlPanel.setOpaque(true);

        Dimension pref;

        pref = controlPanel.getPreferredSize();
        controlPanel.setBounds(40, 40, pref.width, pref.height);

        pref = graphPanel.getPreferredSize();
        graphPanel.setBounds(0, 0, pref.width, pref.height);

        JLayeredPane pane = new JLayeredPane();
        pane.add(controlPanel, new Integer(1));
        pane.add(graphPanel, new Integer(0));
        pane.setPreferredSize(graphPanel.getPreferredSize());
        pane.setOpaque(true);
        pane.addComponentListener(graphPanel);

        graphPanel.updateGraph();

        ArrayList<Image> icons = new ArrayList<Image>();
        InputStream input = null;

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            input = loader.getResourceAsStream("icon64x64.png");
            icons.add(ImageIO.read(input));
            input.close();

            input = loader.getResourceAsStream("icon32x32.png");
            icons.add(ImageIO.read(input));
            input.close();

            input = loader.getResourceAsStream("icon16x16.png");
            icons.add(ImageIO.read(input));
            input.close();
        }
        catch (Exception ex)
        {
            if (input != null)
            {
                try {
                    input.close();
                }
                catch (IOException ex2) {}
            }
        }

        frame.setIconImages(icons);

        frame.setMinimumSize(new Dimension(500, 450));
        frame.setContentPane(pane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Graph graph;

    private Rectangle view = new Rectangle();
    private int mouseX;
    private int mouseY;
    private boolean manualUpdate = false;

    private Timer changeTimer;

    public UIApplet()
    {
        this(new Graph());
    }

    public UIApplet(Graph graph)
    {
        super();
        this.graph = graph;

        changeTimer = new Timer(0, this);
        changeTimer.setInitialDelay(500);
        changeTimer.setRepeats(false);
    }

    @Override
    public void paint(Graphics gbase)
    {
        try {
            Graphics2D g = (Graphics2D) gbase;

            int w = this.getWidth();
            int h = this.getHeight();

            view.width = w;
            view.height = h;
            view.x -= w / 2;
            view.y -= h / 2;

            graph.draw(g, view);

            view.x += w / 2;
            view.y += h / 2;
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(700, 625);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        if (mx != mouseX || my != mouseY)
        {
            view.x -= mx - mouseX;
            view.y -= my - mouseY;

            mouseX = mx;
            mouseY = my;

            manualUpdate = true;
            xText.setText(Function.truncate(String.valueOf(graph.toGraphScaleX(view.x)), 4));
            yText.setText(Function.truncate(String.valueOf(graph.toGraphScaleY(view.y)), 4));
            manualUpdate = false;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }



    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (e.getWheelRotation() > 0)
        {
            xScale *= 2;
            yScale *= 2;
        }
        else
        {
            xScale /= 2;
            yScale /= 2;
        }

        xScaleText.setText(String.valueOf(xScale));
        yScaleText.setText(String.valueOf(yScale));

        updateGraph();
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        Component c = e.getComponent();
        this.setBounds(0, 0, c.getWidth(), c.getHeight());
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        Document doc = e.getDocument();

        if (doc == xText.getDocument() || doc == yText.getDocument())
        {
            if (!manualUpdate)
                updateViewPosition();
        }
        else
            changeTimer.restart();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        changedUpdate(e);
    }

    private void updateViewPosition()
    {
        String xs = xText.getText();
        String ys = yText.getText();

        if (xs.length() == 0)
            xs = "0";
        if (ys.length() == 0)
            ys = "0";

        try {
            double x = Function.parse(xs).getApproximation();
            view.x = graph.toPixelsX(x);
        }
        catch (Exception ex)
        {}

        try {
            double y = Function.parse(ys).getApproximation();
            view.y = graph.toPixelsY(y);
        }
        catch (Exception ex)
        {}

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == changeTimer)
        {
            updateGraph();
        }
        else if (e.getSource() == gridlineBox)
        {
            graph.setGridlines(gridlineBox.isSelected());
            repaint();
        }
        else if (e.getSource() == scaleBox)
        {
            graph.setShowScale(scaleBox.isSelected());
            repaint();
        }
    }

    private void updateGraph()
    {
        ArrayList<Function> arr = new ArrayList<Function>();
        Map<String, Function> map = new TreeMap<String, Function>();

        try {
            Function f = Function.parse(fText.getText(), map);
            arr.add(f);
            map.put("f", f);
        }
        catch (Exception ex)
        {}
        try {
            Function g = Function.parse(gText.getText(), map);
            arr.add(g);
            map.put("g", g);
        }
        catch (Exception ex)
        {}
        try {
            Function h = Function.parse(hText.getText(), map);
            arr.add(h);
            map.put("h", h);
        }
        catch (Exception ex)
        {}

        try {
            double val = Function.parse(xScaleText.getText()).getApproximation();
            if (val > 0)
                xScale = val;
        }
        catch (Exception ex)
        {}

        try {
            double val = Function.parse(yScaleText.getText()).getApproximation();
            if (val > 0)
                yScale = val;
        }
        catch (Exception ex)
        {}

        xScaleText.setText(String.valueOf(xScale));
        yScaleText.setText(String.valueOf(yScale));

        changeTimer.stop();

        graph = new Graph(arr.toArray(new Function[0]));
        graph.setGridlines(gridlineBox.isSelected());
        graph.setShowScale(scaleBox.isSelected());
        graph.setXScale(xScale);
        graph.setYScale(yScale);
        System.out.println(graph);
        repaint();
    }

}