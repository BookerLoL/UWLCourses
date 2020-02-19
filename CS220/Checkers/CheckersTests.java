import java.lang.reflect.Modifier;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class CheckersTests.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CheckersTests
{
   CheckerBoard buildInitial()
   {
       return new CheckerBoard();
   }
   
   @Test(timeout=1000) public void testConstructor()
   {
       buildIntial();
   }
}
