package co.uk.gumtree.address.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.SortedSet;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class AddressBook {
   private final SortedSet<Address> addressBook;
}
