package rmblworx.tools.timey.exception;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Wird geworfen um anzuzeigen, dass eine Referenz auf {@code null} an eine Methode uebergeben wurde und dies nicht im
 * jeweiligen Kontext zulässig ist.
 * @author mmatthies
 */
public final class NullArgumentException extends IllegalArgumentException {
	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = -194472936956675747L;

	/**
	 * Konstruiert eine {@code NullArgumentException} mit vordefinierter Fehlermeldung.
	 */
	public NullArgumentException() {
		super("References on null are not permitted!");
	}

	/**
	 * Konstruiert eine {@code NullArgumentException} mit frei definierbarer Fehlermeldung.
	 *
	 * @param msg
	 *            die Fehlermeldung
	 */
	public NullArgumentException(final String msg) {
		super(msg);
	}
}
