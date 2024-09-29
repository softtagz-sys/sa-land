package be.kdg.land.domain.passage;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("EXIT")
public class Exit extends Passage {
}
