package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.Gender;

public interface AddressBookService {
    void createAddress(Address address);

    Long calculateAddressesByGender(Gender gender);
}
