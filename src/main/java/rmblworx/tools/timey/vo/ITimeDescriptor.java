package rmblworx.tools.timey.vo;

public interface ITimeDescriptor {

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie
	 *         gesetzt wurden.
	 */
	Long getMilliSeconds();

	/**
	 * @param currentTimeMillis
	 *            zu setzende Zeit in Millisekunden
	 */
	void setMilliSeconds(Long currentTimeMillis);

}
