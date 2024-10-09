package be.kdg.land.service;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;


    public Optional<RawMaterial> findRawMaterialByName(String name) {
        return rawMaterialRepository.findByNameIgnoreCase(name);
    }
}
