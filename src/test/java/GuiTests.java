import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.loadui.testfx.categories.TestFX;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Suite für Tests, bei denen eine GUI sichtbar sein muss.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(TestFX.class)
@Suite.SuiteClasses(AllTests.class)
public class GuiTests {
}
