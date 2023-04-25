package co.uk.gumtree.address.application;

import co.uk.gumtree.address.domain.model.Address;
import co.uk.gumtree.address.domain.model.Gender;
import co.uk.gumtree.address.domain.repository.AddressBookRepository;
import co.uk.gumtree.address.infrastructure.persistance.AddressBookRepositoryInMemoryImpl;
import co.uk.gumtree.address.service.AddressBookService;
import co.uk.gumtree.address.service.AddressBookServiceImpl;
import co.uk.gumtree.address.service.exceptions.AddressNotFoundException;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class AddressBookLauncher {
    public static void main(String[] args) throws IOException, CsvException {
        AddressBookRepository addressBookRepository = new AddressBookRepositoryInMemoryImpl();
        AddressBookService addressBookService = new AddressBookServiceImpl(addressBookRepository);
        CsvReader csvReader = new CsvReader(addressBookService);
        LocalDateTime start = LocalDateTime.now();
        log.info("Start loading addresses. start={}", start);
        csvReader.loadAddressesFromCsvFile("/AddressBook.csv");
        LocalDateTime end = LocalDateTime.now();
        long elapsedSeconds = ChronoUnit.MILLIS.between(start, end);
        log.info("Finish loading addresses. Finish ={} elapsed time in milliseconds={}", end, elapsedSeconds);
        boolean exitProgram = false;
        Scanner console = new Scanner(System.in);
        while (!exitProgram) {
            exitProgram = options(console, addressBookService);
        }

    }

    private static boolean options(Scanner console, AddressBookService addressBookService) {
        System.out.println("#### MENU ####");
        System.out.println("Do you want to exit? please enter 1");
        System.out.println("How many people are in the address book by gender? please enter 2");
        System.out.println("Who is the oldest person in the address book? please enter 3");
        System.out.println("How many days older is Person 1 than Person 2? please enter 4");
        System.out.println("#### MENU ####");
        int optionChoice = console.nextInt();
        if (optionChoice != 1) {
            if (optionChoice == 2) {
                processHowManyPeopleByGender(addressBookService);
            }
            if (optionChoice == 3) {
                processWhoIsTheOldestPerson(addressBookService);
            }
            if (optionChoice == 4) {
                processAgeDifferenceBetweenPerson1AndPerson2(addressBookService);
            }
        }
        return optionChoice == 1;
    }

    private static void processAgeDifferenceBetweenPerson1AndPerson2(AddressBookService addressBookService) {
        System.out.println("#### Option 4 ####");
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter Person 1 first name ");
        String person1FirstName = console.nextLine();
        System.out.println("Person 1 First Name = " + person1FirstName);
        System.out.println("Please enter Person 2 first name ");
        String person2FirstName = console.nextLine();
        System.out.println("Person 2 First Name = " + person2FirstName);
        try{
            Long ageDifferenceByDays = addressBookService.calculatePersonAgeDifference(person1FirstName, person2FirstName, ChronoUnit.DAYS);
            System.out.printf("Person 1 %s is =%s days older than Person 2 = %s%n", person1FirstName, ageDifferenceByDays, person2FirstName);
        } catch (AddressNotFoundException ex) {
            log.warn("Cannot calculate age difference between person1 firstName={} and person2 firstName={}", person1FirstName, person2FirstName);
            System.out.printf("Cannot find Address. Error Message = %s", ex.getMessage());
        }
        System.out.println("#### Option 4 ####");
    }

    private static void processWhoIsTheOldestPerson(AddressBookService addressBookService) {
        Optional<Address> oldestPersonAddress = addressBookService.filterAddressesByOldestPerson();
        System.out.println("#### Option 3 ####");
        System.out.printf("The oldest person is First Name = %s Surname = %s%n", oldestPersonAddress.get().getFirstName(), oldestPersonAddress.get().getSurname());
        System.out.println("#### Option 3 ####");
    }

    private static void processHowManyPeopleByGender(AddressBookService addressBookService) {
        System.out.println("#### Option 2 ####");
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter Gender? Male or Female");
        String genderType = console.nextLine();
        System.out.println("Gender = " + genderType);
        Gender gender = Gender.fromGenderName(genderType);
        Long numberOfAddresses = addressBookService.calculateAddressesByGender(gender);
        System.out.printf("Number of people by gender %s is = %s%n", gender.getGenderName(), numberOfAddresses);
        System.out.println("#### Option 2 ####");
    }
}
