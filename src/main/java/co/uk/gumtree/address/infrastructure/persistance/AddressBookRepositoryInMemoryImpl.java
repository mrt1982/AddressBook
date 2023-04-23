package co.uk.gumtree.address.infrastructure.persistance;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class AddressBookRepositoryInMemoryImpl implements AddressBookRepository {
    private final Set<Address> addressBookEntries = new HashSet<>();

    @Override
    public void store(Address address) {
        addressBookEntries.add(address);
    }

    @Override
    public AddressBook getAddressBook() {
        return new AddressBook(addressBookEntries);
    }

    @Override
    public Optional<Address> findAddressByFirstName(String firstName) {
        return addressBookEntries.stream()
                .filter(a -> a.getFirstName().equals(firstName))
                .findFirst();
    }
}
