package rmblworx.tools.timey.exception;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Wird geworfen um anzuzeigen das eine leere Zeichenkette als Argument an eine Methode uebergeben wurde.
 * @author mmatthies
 */
public final class EmptyArgumentException extends IllegalArgumentException {

	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = 7023419861119994001L;

	/**
	 * Konstruiert eine {@code EmptyArgumentException} mit vordefinierter Fehlermeldung.
	 */
	public EmptyArgumentException() {
		super("Empty strings are not permitted!");
	}

	/**
	 * Konstruiert eine {@code EmptyArgumentException} mit frei definierbarer Fehlermeldung.
	 * 
	 * @param msg
	 *            die Fehlermeldung
	 */
	public EmptyArgumentException(final String msg) {
		super(msg);
	}
}
