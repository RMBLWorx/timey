package rmblworx.tools.timey.vo;

public interface ITimeDescriptor {

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie
	 *         gesetzt wurden.
	 */
	public Long getMilliSeconds();

	/**
	 * @param currentTimeMillis
	 *            zu setzende Zeit in Millisekunden
	 */
	public void setMilliSeconds(Long currentTimeMillis);

}
