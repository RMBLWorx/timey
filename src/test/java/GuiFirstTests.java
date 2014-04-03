import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite für alle Tests, wobei die GUI-Tests zuerst ausgeführt werden.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@RunWith(Suite.class)
@SuiteClasses({
	GuiTests.class,
	NonGuiTests.class,
})
public class GuiFirstTests {
}
