package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import co.uk.gumtree.address.service.exceptions.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository addressBookRepository;

    @Override
    public void createAddress(Address address) {
        addressBookRepository.store(address);
    }

    @Override
    public Long calculateAddressesByGender(Gender gender) {
        AddressBook addressBook = addressBookRepository.getAddressBook();
        return addressBook.getAddressBook().stream()
                .filter(a -> a.getGender().equals(gender))
                .count();
    }

    @Override
    public Optional<Address> filterAddressesByOldestPerson() {
        return addressBookRepository.findAddressByAgeInAscendingOrder();
    }

    @Override
    public Long calculatePersonAgeDifference(String person1FirstName, String person2FirstName, ChronoUnit datePeriodUnit) {
        Address person1Address = getAddressByFirstName(person1FirstName);
        Address person2Address = getAddressByFirstName(person2FirstName);
        return datePeriodUnit.between(person1Address.getDob(), person2Address.getDob());
    }

    private Address getAddressByFirstName(String personFirstName) {
        Optional<Address> addressOpt = addressBookRepository.findAddressByFirstName(personFirstName);
        if (addressOpt.isPresent()) {
            return addressOpt.get();
        }
        log.warn("Address Not Found by firstName={}", personFirstName);
        throw new AddressNotFoundException("Address Not Found by firstName=" + personFirstName);
    }

}
