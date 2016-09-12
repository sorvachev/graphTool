/**
 * Created by sakic on 9/12/16.
 */
public class FunctionException extends Exception{
    public FunctionException(String str) {
        super(str);
    }
    public FunctionException(String str, Throwable error)
    {
        super(str, error);
    }

}
