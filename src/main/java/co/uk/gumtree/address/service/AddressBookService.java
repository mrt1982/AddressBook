package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.Gender;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

public interface AddressBookService {
    void createAddress(Address address);

    Long calculateAddressesByGender(Gender gender);

    Optional<Address> filterAddressesByOldestPerson();

    Long calculatePersonAgeDifference(String person1FirstName, String person2FirstName, ChronoUnit datePeriodUnit);
}
