package org.acme.vlille.exception;

/**
 * Synchronisation exception.
 * @author Jean-Charles
 */
public class SynchronisationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4223503917552714664L;
	/**
     * Default constructor.
     */
    public SynchronisationException() {
        super();
    }

    /**
     * Message constructor.
     * @param message is the message.
     */
    public SynchronisationException(final String message) {
        super(message);
    }
    /**
     * 
     * @param message
     * @param e
     */
    public SynchronisationException(final String message,Throwable e) {
        super(message,e);
    } 
}
