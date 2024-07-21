package com.example.reportgenerator.service;

import com.example.reportgenerator.util.FileUtil;
import com.example.reportgenerator.model.InputRecord;
import com.example.reportgenerator.model.ReferenceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ScheduledReportService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledReportService.class);

    @Autowired
    private ReportService reportService;

    @Value("${report.input.directory}")
    private String inputDirectory;

    @Scheduled(cron = "0 0 0 * * ?") // Schedule to run at midnight every day
    public void scheduledReportGeneration() {
        logger.debug("Scheduled report generation started");

        try {
            Files.list(Paths.get(inputDirectory))
                    .filter(Files::isRegularFile)
                    .forEach(this::processFilePair);
        } catch (IOException e) {
            logger.error("Error reading files from directory", e);
        }

        logger.debug("Scheduled report generation completed");
    }

    private void processFilePair(Path inputFilePath) {
        try {
            String inputFileName = inputFilePath.getFileName().toString();
            String referenceFileName = inputFileName.replace("input", "reference");
            Path referenceFilePath = inputFilePath.resolveSibling(referenceFileName);

            if (Files.exists(referenceFilePath)) {
                List<InputRecord> inputRecords = FileUtil.readInputRecords(inputFilePath);
                List<ReferenceRecord> referenceRecords = FileUtil.readReferenceRecords(referenceFilePath);

                reportService.generateReport(inputRecords, referenceRecords);

                logger.debug("Processed files: {} and {}", inputFileName, referenceFileName);
            } else {
                logger.warn("Reference file not found for input file: {}", inputFileName);
            }
        } catch (IOException e) {
            logger.error("Error processing file pair", e);
        }
    }
}
