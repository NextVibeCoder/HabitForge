package com.example.habitforgeapi.controller;

import com.example.habitforgeapi.config.DevTimeProvider;
import com.example.habitforgeapi.dto.TimeResponseDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api//test/time")
@Profile({"dev", "test"})
public class TestTimeController {

    private final DevTimeProvider devTimeProvider;

    public TestTimeController(DevTimeProvider devTimeProvider) {
        this.devTimeProvider = devTimeProvider;
    }

    @GetMapping
    public ResponseEntity<TimeResponseDTO> getCurrentTime() {
        TimeResponseDTO response = new TimeResponseDTO(
                devTimeProvider.getCurrentDate(),
                devTimeProvider.getCurrentDateTime(),
                devTimeProvider.getOffset()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/advance")
    public ResponseEntity<TimeResponseDTO> advanceTime(@RequestParam int days) {
        devTimeProvider.advanceDays(days);
        return ResponseEntity.ok(new TimeResponseDTO(
                devTimeProvider.getCurrentDate(),
                devTimeProvider.getCurrentDateTime(),
                devTimeProvider.getOffset()
        ));
    }

    @PostMapping("/rewind")
    public ResponseEntity<TimeResponseDTO> rewindTime(@RequestParam int days) {
        devTimeProvider.rewindDays(days);
        return ResponseEntity.ok(new TimeResponseDTO(
                devTimeProvider.getCurrentDate(),
                devTimeProvider.getCurrentDateTime(),
                devTimeProvider.getOffset()
        ));
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetTime() {
        devTimeProvider.resetOffset();
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "Tiempo reseteado al tiempo real del sistema"
        ));
    }
}
