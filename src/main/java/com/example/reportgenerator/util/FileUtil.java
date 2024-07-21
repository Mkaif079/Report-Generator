package com.example.reportgenerator.util;

import com.example.reportgenerator.model.InputRecord;
import com.example.reportgenerator.model.ReferenceRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<InputRecord> readInputRecords(Path path) throws IOException {
        List<InputRecord> records = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                InputRecord record = new InputRecord();
                record.setField1(csvRecord.get(0));
                record.setField2(csvRecord.get(1));
                record.setField3(csvRecord.get(2));
                record.setField4(new BigDecimal(csvRecord.get(3)));
                record.setField5(new BigDecimal(csvRecord.get(4)));
                record.setRefkey1(csvRecord.get(5));
                record.setRefkey2(csvRecord.get(6));
                records.add(record);
            }
        }
        return records;
    }

    public static List<ReferenceRecord> readReferenceRecords(Path path) throws IOException {
        List<ReferenceRecord> records = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                ReferenceRecord record = new ReferenceRecord();
                record.setRefkey1(csvRecord.get(0));
                record.setRefdata1(csvRecord.get(1));
                record.setRefkey2(csvRecord.get(2));
                record.setRefdata2(csvRecord.get(3));
                record.setRefdata3(csvRecord.get(4));
                record.setRefdata4(new BigDecimal(csvRecord.get(5)));
                records.add(record);
            }
        }
        return records;
    }
}
