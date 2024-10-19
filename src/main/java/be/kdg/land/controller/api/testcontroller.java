package be.kdg.land.controller.api;

import be.kdg.land.messaging.dto.WarehouseStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
public class testcontroller {

    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseStatusDto> getWarehouseStatus(@PathVariable String warehouseId) {

        return ResponseEntity.ok(new WarehouseStatusDto(warehouseId, true));
    }
}
