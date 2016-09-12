public class addFunction extends Function
{
    public final Function a, b;

    private addFunction(Function a, Function b)
    {
        this.a = a;
        this.b = b;
    }



    String getName() {
        return a.getName() + "+" + b.getName();
    }

    public double getApproximation()
    {
        return a.getApproximation() + b.getApproximation();
    }
    public Function getDerive()
    {
        return getInstance(a.getDerive(), b.getDerive());
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