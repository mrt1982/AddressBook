package co.uk.gumtree.address.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class AddressBook {
   private final Set<Address> addressBook;
}
