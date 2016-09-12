/**
 * Created by sakic on 9/12/16.
 */
public class SubstactFunction  extends Function {
    final public  Function a;
    final public Function b;

    public SubstactFunction(Function a, Function b) {
        this.a = a;
        this.b = b;
    }

    @Override
    String getName() {
        return a.getName() + "-" + b.getName();
    }


    public double getApproximation()
    {
        return a.getApproximation() - b.getApproximation();
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
            return Constant.getInstance(((Constant) a).getValue() - ((Constant) b).getValue());
        else
            return new SubstactFunction(a, b);
    }
}