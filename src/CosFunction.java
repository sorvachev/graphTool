/**
 * Created by sakic on 9/5/16.
 */
public class CosFunction extends Function {
    private final Function target;

    public CosFunction(Function target) {
        this.target = target;
    }


    public Function getTarget() {
        return target;
    }


    public String getName() {
        return "|" + target.getName() + "|";
    }

    public Function getRetargeted(Function t) {
        return new CosFunction(t);
    }


    public double getApproximation() {
        return Math.cos(target.getApproximation());
    }

    @Override
    Function getDerive() {
        return new SinFunction(target);
    }


}