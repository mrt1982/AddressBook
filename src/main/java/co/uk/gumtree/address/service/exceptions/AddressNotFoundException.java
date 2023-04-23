package co.uk.gumtree.address.service.exceptions;

public class AddressNotFoundException extends AddressBookException {
    public AddressNotFoundException(String msg) {
        super(msg);
    }
}