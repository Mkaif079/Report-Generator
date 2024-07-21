package com.example.reportgenerator.service;

import com.example.reportgenerator.model.InputRecord;
import com.example.reportgenerator.model.ReferenceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public void generateReport(List<InputRecord> inputRecords, List<ReferenceRecord> referenceRecords) {
        logger.debug("Starting report generation...");

        Map<String, ReferenceRecord> referenceMap = referenceRecords.stream()
                .collect(Collectors.toMap(r -> r.getRefkey1() + "-" + r.getRefkey2(), r -> r));

        for (InputRecord inputRecord : inputRecords) {
            String key = inputRecord.getRefkey1() + "-" + inputRecord.getRefkey2();
            ReferenceRecord referenceRecord = referenceMap.get(key);

            if (referenceRecord != null) {
                String outfield1 = inputRecord.getField1() + inputRecord.getField2();
                String outfield2 = referenceRecord.getRefdata1();
                String outfield3 = referenceRecord.getRefdata2() + referenceRecord.getRefdata3();
                BigDecimal outfield4 = inputRecord.getField4().multiply(max(inputRecord.getField5(), referenceRecord.getRefdata4()));
                BigDecimal outfield5 = max(inputRecord.getField5(), referenceRecord.getRefdata4());

                // Log or store the output fields as needed
                logger.debug("Generated output: {}, {}, {}, {}, {}", outfield1, outfield2, outfield3, outfield4, outfield5);
            }
        }

        logger.debug("Report generation completed.");
    }

    private BigDecimal max(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0 ? a : b;
    }
}
