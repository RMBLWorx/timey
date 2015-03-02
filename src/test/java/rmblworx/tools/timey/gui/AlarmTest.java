package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test für {@link Alarm}.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class AlarmTest {

	/**
	 * Testet {@link Alarm#getDateTimeInMillis()}.
	 */
	@Test
	public final void testGetDateTimeInMillis() {
		final long now = System.currentTimeMillis();
		assertEquals(now, new Alarm(DateTimeUtil.getLocalDateTimeFromMillis(now), "alarm").getDateTimeInMillis());
	}

}
