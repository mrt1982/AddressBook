package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceImplSmallTest {

    private AddressBookService testObj;
    @Mock
    private AddressBookRepository addressBookRepositoryMock;

    @BeforeEach
    void setUp() {
        testObj = new AddressBookServiceImpl(addressBookRepositoryMock);
    }

    @Test
    void createAddress_validAddress_callStoreAddressBookRepositorySuccess() {
        Address address = new Address("firstName", "surname", Gender.MALE, LocalDate.of(1982, 10, 21));
        testObj.createAddress(address);
        verify(addressBookRepositoryMock, times(1)).store(address);
    }
}