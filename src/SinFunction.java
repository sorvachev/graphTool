/**
 * Created by sakic on 9/5/16.
 */
public class SinFunction extends Function {
    private final Function target;

    public SinFunction(Function target) {
        this.target = target;
    }


    public Function getTarget() {
        return target;
    }


    public String getName() {
        return "|" + target.getName() + "|";
    }

    public Function getRetargeted(Function t) {
        return new SinFunction(t);
    }


    public double getApproximation() {
        return Math.sin(target.getApproximation());
    }

    @Override
    Function getDerive() {
        return new CosFunction(target);
    }


}