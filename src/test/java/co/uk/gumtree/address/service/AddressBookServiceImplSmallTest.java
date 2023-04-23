package co.uk.gumtree.address.service;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import co.uk.gumtree.address.service.exceptions.AddressNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceImplSmallTest {
    private static final Set<Address> ADDRESSES = createAddresses();

    private static final AddressBook ADDRESS_BOOK = new AddressBook(ADDRESSES);

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
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(ADDRESS_BOOK);
        Long actualNumberOfAddresses = testObj.calculateAddressesByGender(Gender.MALE);
        assertThat(actualNumberOfAddresses, is(equalTo(3L)));
    }

    @Test
    void calculateAddressesByGender_genderByFemale_return2FemaleAddresses() {
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(ADDRESS_BOOK);
        Long actualNumberOfAddresses = testObj.calculateAddressesByGender(Gender.FEMALE);
        assertThat(actualNumberOfAddresses, is(equalTo(2L)));
    }

    @Test
    void calculateAddressesByGender_emptyAddresses_returnZero() {
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(new AddressBook(new HashSet<>()));
        Long actualNumberOfAddresses = testObj.calculateAddressesByGender(Gender.MALE);
        assertThat(actualNumberOfAddresses, is(equalTo(0L)));
    }

    @Test
    void filterAddressByOldestPerson_validAddresses_returnAddressForOldestPerson() {
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(ADDRESS_BOOK);
        Optional<Address> actualAddressOpt = testObj.filterAddressesByOldestPerson();
        assertThat(actualAddressOpt.isPresent(), is(equalTo(true)));
        assertThat(actualAddressOpt.get().getFirstName(), is(equalTo("firstName1")));
        assertThat(actualAddressOpt.get().getSurname(), is(equalTo("surname1")));
    }

    @Test
    void filterAddressByOldestPerson_emptyAddresses_returnEmptyAddress() {
        when(addressBookRepositoryMock.getAddressBook()).thenReturn(new AddressBook(new HashSet<>()));
        Optional<Address> actualAddressOpt = testObj.filterAddressesByOldestPerson();
        assertThat(actualAddressOpt.isPresent(), is(equalTo(false)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsOlderThanPerson2_return365Days() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName1", "firstName2", ChronoUnit.DAYS);
        assertThat(actualDaysBetween, is(equalTo(365L)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsYoungerThanPerson2_returnMinus365Days() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName2", "firstName1", ChronoUnit.DAYS);
        assertThat(actualDaysBetween, is(equalTo(-365L)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsOlderThanPerson2_return12Months() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName1", "firstName2", ChronoUnit.MONTHS);
        assertThat(actualDaysBetween, is(equalTo(12L)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsYoungerThanPerson2_returnMinus12Months() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName2", "firstName1", ChronoUnit.MONTHS);
        assertThat(actualDaysBetween, is(equalTo(-12L)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsOlderThanPerson2_return1Year() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName1", "firstName2", ChronoUnit.YEARS);
        assertThat(actualDaysBetween, is(equalTo(1L)));
    }

    @Test
    void calculatePersonAgeDifference_person1IsYoungerThanPerson2_returnMinus1Year() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address person2Address = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1983, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName2")).thenReturn(Optional.of(person2Address));
        Long actualDaysBetween = testObj.calculatePersonAgeDifference("firstName2", "firstName1", ChronoUnit.YEARS);
        assertThat(actualDaysBetween, is(equalTo(-1L)));
    }

    @Test
    void calculatePersonAgeDifference_person1DoesNotExist_throwAddressNotFoundException() {
        when(addressBookRepositoryMock.findAddressByFirstName("firstName6")).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> testObj.calculatePersonAgeDifference("firstName6", "firstName2", ChronoUnit.DAYS));
    }

    @Test
    void calculatePersonAgeDifference_person2DoesNotExist_throwAddressNotFoundException() {
        Address person1Address = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName1")).thenReturn(Optional.of(person1Address));
        when(addressBookRepositoryMock.findAddressByFirstName("firstName6")).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> testObj.calculatePersonAgeDifference("firstName1", "firstName6", ChronoUnit.DAYS));
    }
}