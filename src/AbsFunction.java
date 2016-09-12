public class AbsFunction extends Function {
    private final Function target;

    public AbsFunction(Function target) {
        this.target = target;
    }


    public Function getTarget() {
        return target;
    }


    public String getName() {
        return "|" + target.getName() + "|";
    }

    public Function getRetargeted(Function t) {
        return new AbsFunction(t);
    }


    public double getApproximation() {
        return Math.abs(target.getApproximation());
    }

    @Override
    Function getDerive() {
        return null;
    }


}