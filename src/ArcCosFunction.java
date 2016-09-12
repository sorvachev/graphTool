/**
 * Created by sakic on 9/5/16.
 */
public class ArcCosFunction  extends Function{
    private final Function target;

    public ArcCosFunction(Function target) {
        this.target = target;
    }
    public String getName()
    {
        return "arcos(" + target.getName() + ")";

    }

    @Override
    double getApproximation(double a) {
        return Math.acos(target.getApproximation(a));
    }

    @Override
    Function getDerive() {
       return  DivideFunction.getInstance(Constant.ONE, Monomial.getInstance(new SinFunction(target), 2));
    }
    @Override
    Function getInstance(Function a, Function b)
    {

    }

}
