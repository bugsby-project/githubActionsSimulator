package com.bugsby.githubactions.simulator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
public class LogsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogsController.class);

    @GetMapping("/logs")
    public ResponseEntity<byte[]> downloadLogs() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("logs_1.zip")) {
            if (inputStream == null) {
                LOGGER.warn("Could not retrieve logs_1.zip file");
                return new ResponseEntity<>("".getBytes(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(inputStream.readAllBytes(), HttpStatus.OK);
        }
    }
}
