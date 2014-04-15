import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.loadui.testfx.categories.TestFX;

/**
 * Suite für Tests, bei denen eine GUI sichtbar sein muss.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(TestFX.class)
@Suite.SuiteClasses(AllTests.class)
public class GuiTests {
}
