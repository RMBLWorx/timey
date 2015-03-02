import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.loadui.testfx.categories.TestFX;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Suite für Tests, bei denen keine GUI sichtbar sein muss.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@RunWith(Categories.class)
@Categories.ExcludeCategory(TestFX.class)
@Suite.SuiteClasses(AllTests.class)
public class NonGuiTests {
}
