package org.servalproject.svd;

public class MessageTypes {
	/**
	 * Switch to High Definition
	 */
	public static final byte HD = 0x11;
	
	/**
	 * Switch to Low Definition
	 */
	public static final byte LD = 0x10;
	
	
	/**
	 * Switch to High Framerate
	 */
	public static final byte HF = 0x21;
	
	/**
	 * Switch to Low Framerate
	 */
	public static final byte LF = 0x20;
	
	/**
	 * Start sending stream
	 */
	public static final byte START_STREAM = 0x01;
	
	/**
	 * Stop sending stream
	 */
	public static final byte STOP_STREAM = 0x00;

	/**
	 * Keep packets, and send them later
	 */
	public static final byte KEEP_PACKETS = 0x31;
	
	/**
	 * Release packets
	 */
	public static final byte RELEASE_PACKETS = 0x30;

}
