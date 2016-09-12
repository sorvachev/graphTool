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



    public Function getDerive()
    {
        return MultiplyFunction.getInstance(Constant.ONE, Monomial.getInstance(SubstactFunction.getInstance(Constant.ONE, Monomial.getInstance(target, 2)), 0.5 ));
    }
   

}
