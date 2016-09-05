public class Constant extends Function
{
    public static final Constant E = new Constant(Math.E);
    public static final Constant PI = new Constant(Math.PI);
    public static final Constant ZERO = new Constant(0);
    public static final Constant ONE = new Constant(1);

    private final double value;

    private Constant(double value)
    {
        this.value = value;
    }

    public String getString()
    {
        return Function.formatNumber(value);
    }

    @Override
    String getName() {
        return null;
    }

    public double getApproximation(double val)
    {
        return value;
    }

    public Function getRetargeted(Function target)
    {
        return this;
    }

    public double getValue()
    {
        return value;
    }

    public Function getDerive()
    {
        return ZERO;
    }


    public boolean needsBrackets()
    {
        return false;
    }

    public static Constant getInstance(double value)
    {
        if (value == 0)
            return ZERO;
        if (value == 1)
            return ONE;
        return new Constant(value);
    }
}