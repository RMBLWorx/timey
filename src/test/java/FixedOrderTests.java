import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Suite für alle Tests, wobei die GUI-Tests zuerst ausgeführt werden. Durch gemischte Ausführung von GUI- und Nicht-GUI-Tests wäre die
 * CPU-Auslastung unnötig hoch, was auch Probleme auf Travis hervorrufen würde.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@RunWith(Suite.class)
@SuiteClasses({
	GuiTests.class,
	NonGuiTests.class,
})
public class FixedOrderTests {
}
