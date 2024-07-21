package com.example.reportgenerator.controller;

import com.example.reportgenerator.service.ReportService;
import com.example.reportgenerator.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/generateReport")
    public ResponseEntity<String> generateReport(@RequestParam("inputFile") MultipartFile inputFile,
                                                 @RequestParam("referenceFile") MultipartFile referenceFile) {
        try {
            Path inputPath = Files.createTempFile("input-", ".csv");
            Path referencePath = Files.createTempFile("reference-", ".csv");
            Files.write(inputPath, inputFile.getBytes());
            Files.write(referencePath, referenceFile.getBytes());

            var inputRecords = FileUtil.readInputRecords(inputPath);
            var referenceRecords = FileUtil.readReferenceRecords(referencePath);

            reportService.generateReport(inputRecords, referenceRecords);

            return ResponseEntity.ok("Report generated successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate report");
        }
    }
}
