package rmblworx.tools.timey.gui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AllowOnlyNumericKeysKeyEventHandler implements EventHandler<KeyEvent> {

	/**
	 * {@inheritDoc}
	 */
	public void handle(final KeyEvent keyEvent) {
		if (!"0123456789".contains(keyEvent.getCharacter())) {
			keyEvent.consume();
		}
	}

}
