package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceImplSmallTest {
    private static final Set<Address> ADDRESSES = createAddresses();

    private AddressBookService testObj;
    @Mock
    private AddressBookRepository addressBookRepositoryMock;

    private static Set<Address> createAddresses() {
        Address addressMale1 = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address addressMale2 = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        Address addressFemale1 = new Address("firstName3", "surname3", Gender.FEMALE, LocalDate.of(1984, 10, 21));
        Address addressMale3 = new Address("firstName4", "surname4", Gender.MALE, LocalDate.of(1985, 10, 21));
        Address addressFemale2 = new Address("firstName5", "surname5", Gender.FEMALE, LocalDate.of(1986, 10, 21));
        return Set.of(addressMale1, addressMale2, addressFemale1, addressMale3, addressFemale2);
    }

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

    @Test
    void calculateAddressesByGender_genderByMale_return3MaleAddresses() {
        AddressBook addressBook = new AddressBook(ADDRESSES);
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(addressBook);
        Long actualNumberOfAddresses = testObj.calculateAddressesByGender(Gender.MALE);
        assertThat(actualNumberOfAddresses, is(equalTo(3L)));
    }

    @Test
    void calculateAddressesByGender_genderByFemale_return2FemaleAddresses() {
        AddressBook addressBook = new AddressBook(ADDRESSES);
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(addressBook);
        Long actualNumberOfAddresses = testObj.calculateAddressesByGender(Gender.FEMALE);
        assertThat(actualNumberOfAddresses, is(equalTo(2L)));
    }
}