package co.uk.gumtree.address.domain.repository;

import co.uk.gumtree.address.domain.model.Address;

public interface AddressBookRepository {
    void store(Address address);
}
