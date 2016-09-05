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
        return "arcsin(" + target.getName() + ")";

    }

    @Override
    double getApproximation(double a) {
        return Math.sin(target.getApproximation(a));
    }

    @Override
    Function getDerive() {
        return null;
    }

}
