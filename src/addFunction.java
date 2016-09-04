public class addFunction extends Function
{
    public final Function a, b;

    private addFunction(Function a, Function b)
    {
        this.a = a;
        this.b = b;
    }


    @Override
    String getName() {
        return null;
    }

    public double getApproximation(double val)
    {
        return a.getApproximation(val) + b.getApproximation(val);
    }



    public static Function getInstance(Function a, Function b)
    {
        if (a == Constant.ZERO)
            return b;
        else if (b == Constant.ZERO)
            return a;
        else if (a instanceof Constant && b instanceof Constant)
            return Constant.getInstance(((Constant) a).getValue() + ((Constant) b).getValue());
        else
            return new addFunction(a, b);
    }
}