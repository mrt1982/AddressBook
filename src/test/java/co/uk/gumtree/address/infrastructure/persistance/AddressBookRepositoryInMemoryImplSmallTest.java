package co.uk.gumtree.address.infrastructure.persistance;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.AddressBook;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class AddressBookRepositoryInMemoryImplSmallTest {
    private AddressBookRepository testObj;

    @BeforeEach
    void setUp() {
        testObj = new AddressBookRepositoryInMemoryImpl();
    }

    @Test
    void store_validAddress_success() {
        Address address = new Address("firstName", "surname", Gender.MALE, LocalDate.of(1982, 10, 21));
        testObj.store(address);
        Optional<Address> actualAddressOpt = testObj.findAddressByFirstName("firstName");
        assertThat(actualAddressOpt.isPresent(), is(equalTo(true)));
        assertThat(actualAddressOpt.get(), is(equalTo(address)));
    }

    @Test
    void store_duplicateAddress_addressIsOnlyAddedOnce() {
        Address address = new Address("firstName", "surname", Gender.MALE, LocalDate.of(1982, 10, 21));
        testObj.store(address);
        testObj.store(address);
        AddressBook actualAddressBook = testObj.getAddressBook();
        assertThat(actualAddressBook.getAddressBook().size(), is(equalTo(1)));
    }

    @Test
    void getAddressBook_addresses_returnNonEmptyAddressBook() {
        Address address1 = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        Address address2 = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1985, 8, 21));
        testObj.store(address1);
        testObj.store(address2);
        AddressBook actualAddressBook = testObj.getAddressBook();
        assertThat(actualAddressBook.getAddressBook().size(), is(equalTo(2)));
    }

    @Test
    void findAddressByFirstName_validAddressByFirstName_returnAddress() {
        Address address1 = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1982, 10, 21));
        testObj.store(address1);
        Optional<Address> actualAddressOpt = testObj.findAddressByFirstName("firstName1");
        assertThat(actualAddressOpt.isPresent(), is(equalTo(true)));
        assertThat(actualAddressOpt.get().getFirstName(), is(equalTo("firstName1")));
    }

    @Test
    void findAddressByFirstName_addressByFirstNameDoesNotExist_returnEmptyAddress() {
        Optional<Address> actualAddressOpt = testObj.findAddressByFirstName("firstName1");
        assertThat(actualAddressOpt.isEmpty(), is(equalTo(true)));
    }

    @Test
    void findAddressByAgeInAscendingOrder_addresses_returnOldestAddressByAge() {
        Address address1 = new Address("firstName1", "surname1", Gender.MALE, LocalDate.of(1987, 10, 21));
        Address address2 = new Address("firstName2", "surname2", Gender.MALE, LocalDate.of(1985, 8, 21));
        testObj.store(address1);
        testObj.store(address2);

        Optional<Address> actualAddressOpt = testObj.findAddressByAgeInAscendingOrder();
        assertThat(actualAddressOpt.isPresent(), is(equalTo(true)));
        assertThat(actualAddressOpt.get().getFirstName(), is(equalTo("firstName2")));
    }

    @Test
    void findAddressByAgeInAscendingOrder_emptyAddresses_returnEmptyAddress() {
        Optional<Address> actualAddressOpt = testObj.findAddressByAgeInAscendingOrder();
        assertThat(actualAddressOpt.isPresent(), is(equalTo(false)));
    }

}