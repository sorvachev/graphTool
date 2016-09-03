import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sakic on 9/3/16.
 */
abstract public class Function {
    public boolean parity;
    private String name;
    abstract String  getName();
    abstract  Function  getApproximation(Function f);
    public  String getTargetName()
    {
        return "[" + name + "]";
    }
    @Override
    public String toString()
    {
        return "Function name is " + name + "and parity" + String.valueOf(parity);
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof  Function) {
            if (this == o)
                return false;
            else if (this.getClass() != o.getClass())
                return false;
            else
                return this.getTargetName().equals(((Function) o).getTargetName());
        }
        return false;



    }
    public static String truncate(String s, int places)

    {
        if(s.length() == 0) return "";
        int e = Math.max(s.indexOf('e'), s.indexOf('E'));
        if (e >= 0)
            return truncate(s.substring(0, e), Math.min(2, places)) + s.substring(e);

        int dot = s.indexOf('.');
        if (dot < 0)
            return s;

        if (places == 0)
            return s.substring(0, dot);
        else
            return s.substring(0, Math.min(dot + places + 1, s.length()));
    }

    public static Function parse(String s) throws Exception
    {
        return parse(s, null);
    }
   static public Function parse(String s, Map<String, ? extends Function> mp) throws Exception
    {

        return new Function() {
            @Override
            String getName() {
                return null;
            }

            @Override
            Function getApproximation(Function f) {
                return null;
            }
        };


    }
    private static String formatInputLine(String s)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (i > 0 && c == 'x' && Character.isDigit(s.charAt(i-1)))
                sb.append('*');

            if (!Character.isWhitespace(c))
                sb.append(c);
        }
        return sb.toString();
    }

    public static String formatNumber(double value)
    {
        if (value % 1 == 0)
            return String.valueOf((int) value);
        else
            return String.valueOf(value);
    }
    private static int findLowLevel(String s, String keys)
    {
        int depth = 0;

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c == '(')
                depth++;
            else if (c == ')')
                depth--;

            if (depth == 0 && keys.indexOf(s.charAt(i)) >= 0)
                return i;

        }
        return -1;
    }
}
