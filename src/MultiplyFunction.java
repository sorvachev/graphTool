/**
 * Created by sakic on 9/5/16.
 */
public class MultiplyFunction {
    private final Function a, b;

    MultiplyFunction(Function _a, Function _b) {

        this.a = _a;
        this.b = _b;
    }



    public static Function getInstance(Function a, Function b) {
        if (a == Constant.ZERO && b == Constant.ZERO)
            return Constant.ZERO;
        else if (a == Constant.ONE && b == Constant.ZERO)
            return Constant.ZERO;
        else if (a == Constant.ONE && b == Constant.ONE)
            return Constant.ONE;
        else if (a instanceof Constant) {
            Constant ca = (Constant) a;
            if (b instanceof Constant)
                return Constant.getInstance(((Constant) a).getValue() * ((Constant) b).getValue());
            if (b instanceof addFunction) {
                addFunction f = (addFunction) b;
                return addFunction.getInstance(getInstance(a, f.a), getInstance(a, f.b));
            }

        }

        return null;
    }
}
