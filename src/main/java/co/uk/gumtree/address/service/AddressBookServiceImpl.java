package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
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

}
