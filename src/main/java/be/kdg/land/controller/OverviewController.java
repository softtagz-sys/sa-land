package be.kdg.land.controller;

import be.kdg.land.controller.dto.out.ArrivalOverviewDto;
import be.kdg.land.controller.dto.out.QueueItemDto;
import be.kdg.land.controller.dto.out.TruckPresentDto;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.service.PayloadDeliveryService;
import be.kdg.land.service.TerrainOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OverviewController {

    @Autowired
    private TerrainOverviewService terrainOverviewService;


    @GetMapping("/get-queue")
    public String getQueue(@RequestParam(value = "time", required = false) LocalDateTime time, ModelMap model) {
        model.addAttribute("time", time);
        if (time != null) {
            List<Appointment> queue =  terrainOverviewService.getQueue(time);
            List<QueueItemDto> queueItemDtos = queue.stream().map(q -> new QueueItemDto(
                    q.getSlot(),
                    q.getLicensePlate()
            )).toList();
            model.addAttribute("queue", queueItemDtos);
        }
        return "overview/queue_overview";
    }

    @GetMapping("/get-arrivals")
    public String getArrivals(@RequestParam(value = "slot", required = false) LocalDateTime slot, ModelMap model) {
        model.addAttribute("slot", slot);
        if (slot != null) {
            List<Appointment> arrivals =  terrainOverviewService.getArrivals(slot);
            List<ArrivalOverviewDto> arrivalDtos = arrivals.stream().map(a -> new ArrivalOverviewDto(
                    a.getSlot(),
                    a.getLicensePlate(),
                    a.getEntry() != null
            )).toList();
            model.addAttribute("arrivals", arrivalDtos);
        }
        return "overview/arrival_overview";
    }

    @GetMapping("/get-terrain-status")
    public String getTerrainStatus(ModelMap model) {
        List<PayloadDelivery> deliveriesInProgress = terrainOverviewService.getPayloadDeliveriesInProgress();
        List<TruckPresentDto> trucksPresent = deliveriesInProgress.stream().map(p -> new TruckPresentDto(p.getLicensePlate(), p.getCustomer().getName())).toList();
        model.addAttribute("trucksPresent", trucksPresent);

        return "overview/terrain_status";
    }
}
