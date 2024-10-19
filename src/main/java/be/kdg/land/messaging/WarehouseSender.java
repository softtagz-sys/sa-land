package be.kdg.land.messaging;

import be.kdg.land.messaging.dto.WarehouseStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouseClient", url = "${app.warehouseIpAddress}")
public interface WarehouseSender {

    @GetMapping("/api/warehouses/{warehouseId}")
    WarehouseStatusDto getWarehouseStatus(@PathVariable("warehouseId") String warehouseId);
}