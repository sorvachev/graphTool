/**
 * Created by sakic on 9/12/16.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a function to find its derivative:");

        Function lastDerivative = null;
        while (true)
        {
            System.out.println();
            String line = in.readLine();

            try {
                Function f = line.length() == 0 && lastDerivative != null ? lastDerivative : Function.parse(line);
                System.out.println("d/dx(" + f + ")");
                System.out.println(lastDerivative = f.getDerive());
            }
            catch (Exception ex)
            {
                System.out.println("Could not understand your input: " + ex);
            }
        }
    }
}