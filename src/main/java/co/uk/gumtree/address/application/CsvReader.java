package co.uk.gumtree.address.application;

import co.uk.gumtree.address.application.exceptions.InvalidAddressEntryException;
import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.service.AddressBookService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CsvReader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
    private final AddressBookService addressBookService;

    public void loadAddressesFromCsvFile(String fileName) throws IOException, CsvException {
        InputStream inputStream = AddressBookLauncher.class.getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader);
            CSVReader csvReader = csvReaderBuilder.build();
            List<String[]> lines = csvReader.readAll();
            for (String[] line : lines) {
                String[] fullName = extractToFullName(line[0]);
                String firstName = fullName[0];
                String surname = fullName[1];
                Gender gender = Gender.fromGenderName(line[1].strip());
                LocalDate dob = LocalDate.parse(line[2].strip(), DATE_FORMATTER);
                Address address = new Address(firstName, surname, gender, dob);
                addressBookService.createAddress(address);
            }
        }
    }

    private String[] extractToFullName(String fullName) {
        String[] fullNameExtraction = fullName.split(" ");
        if (fullNameExtraction.length == 2) {
            return fullNameExtraction;
        }
        log.error("Invalid Address Entry. Full Name entry={}", fullName);
        throw new InvalidAddressEntryException("Invalid firstname and surname entry");
    }
}
