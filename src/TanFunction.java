/**
 * Created by sakic on 9/5/16.
 */
public class TanFunction extends Function
{
    private final Function target;

    public TanFunction(Function target)
    {
        this.target = target;
    }

    public Function getTarget()
    {
        return target;
    }
    @Override
    public String getName()
    {
        return "tan(" + target.getName() + ")";
    }

    public Function getRetargeted(Function t)
    {
        return new TanFunction(t);
    }



    public double getApproximation(double val)
    {
        return Math.tan(target.getApproximation(val));
    }

    @Override
    Function getDerive() {
        return null;
    }

    public Function getDerivative(Function t)
    {
        return MultiplyFunction.getInstance(Constant.ONE, Monomial.getInstance(new CosFunction(t), 2));
    }
}