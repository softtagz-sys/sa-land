package be.kdg.land.service;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RawMaterialService {


    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public Optional<RawMaterial> findRawMaterialByName(String name) {
        return rawMaterialRepository.findByNameIgnoreCase(name);
    }
}
