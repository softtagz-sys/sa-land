package be.kdg.land.service;

import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.repository.WeighingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeighingService {

    @Value("${app.weighbridgeName}")
    private String weighBridgeName;

    private WeighingRepository weighingRepository;

    public WeighingService(WeighingRepository weighingRepository) {
        this.weighingRepository = weighingRepository;
    }

    public Weighing GetNewWeighbridgeAssignment(String licensePlate){
        Weighing newWeighing = new Weighing(weighBridgeName, licensePlate);
        newWeighing = weighingRepository.save(newWeighing);

        return newWeighing;
    }

}
