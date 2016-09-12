/**
 * Created by sakic on 9/5/16.
 */

public class DivideFunction  extends Function {
    public final Function a, b;
    public DivideFunction(Function a, Function b)
    {
        this.a = a;
        this.b = b;
    }
    public String getName()
    {
        if(a instanceof Constant && b instanceof Constant)
            return  a.getName() + "/" + b.getName();
        StringBuilder sa = new StringBuilder(a.getName());
        StringBuilder sb = new StringBuilder(b.getName());
          if(a.getName().startsWith("(") && a.getName().startsWith("(")) {
              int pos = a.getName().lastIndexOf('(');
              String ns = a.getName().substring(pos, a.getName().length());
              int posb = b.getName().lastIndexOf('(');
              String bs = b.getName().substring(pos, b.getName().length());
              return ns + "/" + bs;
          }
        if(a.getName().startsWith("(")) {
            return a.getName() + "/" + b.getTargetName();
        }
        else if(b.getName().startsWith("("))
            return  a.getTargetName() + "/" + b.getName();




        return a.getTargetName() + "/" + b.getTargetName();

    }
    @Override
    public double getApproximation()
    {
        return a.getApproximation() + b.getApproximation();
    }


    @Override
    Function getDerive() {
        return getInstance(a.getDerive(),b.getDerive());
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
