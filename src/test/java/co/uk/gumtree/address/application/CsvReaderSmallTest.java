package co.uk.gumtree.address.application;

import co.uk.gumtree.address.application.exceptions.InvalidAddressEntryException;
import co.uk.gumtree.address.service.AddressBookService;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CsvReaderSmallTest {

    @Mock
    private AddressBookService addressBookServiceMock;

    private CsvReader testObj;

    @BeforeEach
    void setUp() {
        testObj = new CsvReader(addressBookServiceMock);
    }

    @Test
    void loadAddressesFromCsvFile_validAddresses_success() throws IOException, CsvException {
        testObj.loadAddressesFromCsvFile("/valid-addresses.csv");
        verify(addressBookServiceMock, times(2)).createAddress(any());
    }

    @Test
    void loadAddressesFromCsvFile_invalidFirstNameAddress_throwInvalidAddressEntryException() throws IOException, CsvException {
        assertThrows(InvalidAddressEntryException.class, () -> testObj.loadAddressesFromCsvFile("/invalid-fullname-address.csv"));
    }

}