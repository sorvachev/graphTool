/**
 * Created by sakic on 9/12/16.
 */
public class NegateFunction extends Function {
    final Function a;


    public NegateFunction(Function _a) {
        this.a = _a;
    }

    @Override
    String getName() {
        return "-" + a.getName();
    }

    @Override
    double getApproximation() {
        return a.getApproximation();
    }

    @Override
    Function getDerive() {
        return new NegateFunction(a.getDerive());
    }

    Function getInstance() {
        if (a == Constant.ZERO)
            return a;

        else if (a instanceof Constant)
            return Constant.getInstance(((Constant) a).getValue());
        else
            return new NegateFunction(a);
    }
}
