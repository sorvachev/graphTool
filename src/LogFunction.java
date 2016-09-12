/**
 * Created by sakic on 9/12/16.
 */
public class LogFunction extends Function
{
    private final Function target;

    private LogFunction(Function target)
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
        return "ln(" + target.getName() + ")";
    }

    public Function getRetargeted(Function t)
    {
        return new LogFunction(t);
    }



    public double getApproximation(double val)
    {
        return Math.log(target.getApproximation(val));
    }

    @Override
    Function getDerive() {
        return null;
    }

    public Function getDerivative(Function t)
    {
        return Monomial.getInstance(t, -1);
    }

    public static Function getInstance(Function t)
    {
        if (t instanceof ExpFunction)
            return ((ExpFunction) t).getTarget();
        else
            return new LogFunction(t);
    }
}