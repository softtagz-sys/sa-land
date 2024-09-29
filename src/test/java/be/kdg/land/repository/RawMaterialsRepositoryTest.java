package be.kdg.land.repository;

import be.kdg.land.domain.RawMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RawMaterialsRepositoryTest {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Test
    void canInsertRawMaterial() {
        RawMaterial rawMaterial = new RawMaterial("Cola", "Dit is een cola");
        RawMaterial savedRawMaterial = rawMaterialRepository.save(rawMaterial);
        assertNotNull(savedRawMaterial);
    }

    @Test
    void canFindRawMaterialByNameCaseInsensitive() {
        Optional<RawMaterial> optionalMaterial = this.rawMaterialRepository.findByNameIgnoreCase("Gips");
        assertThat(optionalMaterial).isPresent();
    }

    @Test
    void canFindRawMaterialById() {
        Optional<RawMaterial> optionalMaterial = this.rawMaterialRepository.findByRawMaterialId(UUID.fromString("59c1088e-df7d-4a6e-be9d-352d1d50ec44"));
        assertThat(optionalMaterial).isPresent();
    }




}