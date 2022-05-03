import java.util.function.Function;
/**
 * Write a description of class f1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class F1 implements Function<String, Integer>
{
    public Integer apply(String first)
    {
        return first.length();
    }
}
