package com.example.reportgenerator.service;

import com.example.reportgenerator.model.InputRecord;
import com.example.reportgenerator.model.ReferenceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ReportServiceTests {

    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportService();
    }

    @Test
    public void testGenerateReport() {
        List<InputRecord> inputRecords = List.of(
                createInputRecord("a", "b", "c", new BigDecimal("10"), new BigDecimal("20"), "key1", "key2")
        );
        List<ReferenceRecord> referenceRecords = List.of(
                createReferenceRecord("key1", "ref1", "key2", "ref2", "ref3", new BigDecimal("30"))
        );

        assertDoesNotThrow(() -> reportService.generateReport(inputRecords, referenceRecords));
    }

    private InputRecord createInputRecord(String field1, String field2, String field3, BigDecimal field4, BigDecimal field5, String refkey1, String refkey2) {
        InputRecord record = new InputRecord();
        record.setField1(field1);
        record.setField2(field2);
        record.setField3(field3);
        record.setField4(field4);
        record.setField5(field5);
        record.setRefkey1(refkey1);
        record.setRefkey2(refkey2);
        return record;
    }

    private ReferenceRecord createReferenceRecord(String refkey1, String refdata1, String refkey2, String refdata2, String refdata3, BigDecimal refdata4) {
        ReferenceRecord record = new ReferenceRecord();
        record.setRefkey1(refkey1);
        record.setRefdata1(refdata1);
        record.setRefkey2(refkey2);
        record.setRefdata2(refdata2);
        record.setRefdata3(refdata3);
        record.setRefdata4(refdata4);
        return record;
    }
}