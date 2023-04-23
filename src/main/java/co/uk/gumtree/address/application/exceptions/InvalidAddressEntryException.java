package co.uk.gumtree.address.application.exceptions;

public class InvalidAddressEntryException extends RuntimeException {
    public InvalidAddressEntryException(String msg) {
        super(msg);
    }
}
