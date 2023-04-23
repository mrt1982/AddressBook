package co.uk.gumtree.address.domain.repository;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;

public interface AddressBookRepository {
    void store(Address address);

    AddressBook getAddressBook();
}
