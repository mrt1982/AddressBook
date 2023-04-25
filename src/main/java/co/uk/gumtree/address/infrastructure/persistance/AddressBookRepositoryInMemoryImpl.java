package co.uk.gumtree.address.infrastructure.persistance;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@Slf4j
public class AddressBookRepositoryInMemoryImpl implements AddressBookRepository {
    private final SortedSet<Address> addressBookEntries = new TreeSet<>();

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

    @Override
    public Optional<Address> findAddressByAgeInAscendingOrder() {
        if(!addressBookEntries.isEmpty()){
            return Optional.of(addressBookEntries.first());
        }
        return Optional.empty();
    }
}
