package rmblworx.tools.timey.exception;

/**
 * Wird geworfen um anzuzeigen, dass der an die Methode uebergebene Wert kleiner als der im jeweiligen Kontext
 * erforderliche Mindestwert.
 * 
 * @author mmatthies
 */
public final class ValueMinimumArgumentException extends IllegalArgumentException {
	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = -2178673564031594013L;

	/**
	 * Konstruiert eine {@code ValueMinimumArgumentException} mit vordefinierter Fehlermeldung.
	 */
	public ValueMinimumArgumentException() {
		super("Values less than one are not permitted!");
	}

	/**
	 * Konstruiert eine {@code ValueMinimumArgumentException} mit frei definierbarer Fehlermeldung.
	 * 
	 * @param msg
	 *            die Fehlermeldung
	 */
	public ValueMinimumArgumentException(final String msg) {
		super(msg);
	}
}
