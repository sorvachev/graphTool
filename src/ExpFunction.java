/**
 * Created by sakic on 9/12/16.
 */
public class ExpFunction extends Function
{
    private final Function target;

    private ExpFunction(Function target)
    {
        this.target = target;
    }

    public Function getTarget()
    {
        return target;
    }

    public String getName()
    {
        return "e^(" + target.getName() + ")";
    }

    public Function getRetargeted(Function t)
    {
        return new ExpFunction(t);
    }

   

    public double getApproximation(double val)
    {
        return Math.exp(target.getApproximation(val));
    }

    @Override
    Function getDerive() {
        return null;
    }

    public Function getDerivative(Function t)
    {
        return new ExpFunction(t);
    }

    public static Function getInstance(Function t)
    {
        if (t instanceof LogFunction)
            return ((LogFunction) t).getTarget();
        else
            return new ExpFunction(t);
    }
}