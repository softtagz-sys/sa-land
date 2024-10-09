package be.kdg.land.service;

import be.kdg.land.LandApplicationConfig;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.repository.WeighingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeighingService {

    @Autowired
    private LandApplicationConfig config;

    @Autowired
    private WeighingRepository weighingRepository;


    public Weighing getNewWeighbridgeAssignment(String licensePlate){
        Weighing newWeighing = new Weighing(config.getWeighbridgeName(), licensePlate);
        newWeighing = weighingRepository.save(newWeighing);

        return newWeighing;
    }

}
