package co.uk.gumtree.address.domain.repository;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;

import java.util.Optional;

public interface AddressBookRepository {
    void store(Address address);

    AddressBook getAddressBook();

    Optional<Address> findAddressByFirstName(String firstName);

    Optional<Address> findAddressByAgeInAscendingOrder();
}
