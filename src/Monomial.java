public class Monomial extends Function {
    private final Function target;
    private final double degree;

    private Monomial(Function target, double degree) {
        this.target = target;
        this.degree = degree;
    }


    @Override
    String getName() {
        return null;
    }

    public double getApproximation(double val) {
        return Math.pow(target.getApproximation(val), degree);
    }

    @Override
    Function getDerive() {
        return MultiplyFunction.getInstance(Constant.getInstance(degree), getInstance(target, degree - 1));
    }

    public Function getTarget() {
        return target;
    }

    public double getDegree() {
        return degree;
    }

    public Function reverseDegree() {
        return getInstance(target, -degree);
    }

    public Function getRetargeted(Function t) {
        return new Monomial(t, degree);
    }

    public static Function getInstance(Function t, double _degree) {
        if (t instanceof Monomial) {
            if (_degree == Constant.ZERO.getValue())
                return new Monomial(t, 0);
            else if (_degree == Constant.ONE.getValue())
                return new Monomial(t, 1);
        }
        if (t instanceof Constant) {
            Constant constant = (Constant) t;
            if (constant == Constant.ZERO)
                return constant;
            else
                return Constant.getInstance(Math.pow(constant.getValue(), _degree));
        } else
            return new Monomial(t, _degree);

    }
}
