package rmblworx.tools.timey.gui;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownTimePartChangeListener implements ChangeListener<String> {

	private static final long MIN_VALUE = 0L;

	protected final StringProperty textProperty;
	protected final long maxValue;

	public CountdownTimePartChangeListener(final StringProperty textProperty, final long maxValue) {
		this.textProperty = textProperty;
		this.maxValue = maxValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
		try {
			final long value = Long.parseLong(newValue);
			if (value < MIN_VALUE) {
				textProperty.setValue(String.format("%02d", MIN_VALUE));
				return;
			}
			if (value > maxValue) {
				textProperty.setValue(String.format("%02d", maxValue));
				return;
			}
		} catch (final NumberFormatException e) {
			textProperty.setValue(oldValue);
			return;
		}

		textProperty.setValue(newValue);
	}

}
