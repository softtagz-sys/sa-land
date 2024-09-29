package be.kdg.land.domain.passage;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


//@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("UNSCHEDULED_ENTRY")
public class UnscheduledEntry extends Entry {
}
