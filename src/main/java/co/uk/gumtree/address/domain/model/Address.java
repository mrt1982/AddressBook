package co.uk.gumtree.address.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Address {
    private final String firstName;
    private final String surname;
    private final Gender gender;
    private final LocalDate dob;
}
