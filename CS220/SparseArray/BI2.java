import java.util.function.BiFunction;
/**
 * Write a description of class bi2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BI2 implements BiFunction<String, Integer, Double>
{
    public Double apply(String s, Integer i)
    {
        return Math.sqrt(i+ s.length());
    }
}
