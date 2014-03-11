package rmblworx.tools.timey.gui;

import javafx.util.StringConverter;

/**
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownTimePartConverter extends StringConverter<Number> {

	/**
	 * {@inheritDoc}
	 */
	public String toString(final Number number) {
		return String.format("%02d", number.longValue());
	}

	/**
	 * {@inheritDoc}
	 */
	public Number fromString(final String string) {
		return Long.parseLong(string);
	}

}
