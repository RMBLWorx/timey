import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Suite für alle Tests ohne spezifische Reihenfolge.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@RunWith(WildcardPatternSuite.class)
@SuiteClasses({
	"**/*Test.class",
	"!rmblworx/tools/timey/gui/FxmlGuiControllerTest.class",
	"!rmblworx/tools/timey/gui/FxmlGuiTest.class",
})
public class AllTests {
}
