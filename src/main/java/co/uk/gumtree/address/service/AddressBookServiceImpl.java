package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

}
