/**
 * Created by sakic on 9/12/16.
 */
public class ArctanFunction extends Function
{
    private final Function target;

    public ArctanFunction(Function target)
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
        return "arctan(" + target.getName() + ")";
    }

    public Function getRetargeted(Function t)
    {
        return new ArctanFunction(t);
    }



    public double getApproximation(double val)
    {
        return Math.atan(target.getApproximation(val));
    }

    @Override
    Function getDerive() {
        return null;
    }

    public Function getDerivative(Function t)
    {
        return DivideFunction.getInstance(Constant.ONE, addFunction.getInstance(Constant.ONE, Monomial.getInstance(target, 2)));
    }
}