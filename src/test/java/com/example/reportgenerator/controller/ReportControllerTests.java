package com.example.reportgenerator.controller;

import com.example.reportgenerator.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(ReportController.class)
public class ReportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    public void testGenerateReport() throws Exception {
        doNothing().when(reportService).generateReport(null, null);

        mockMvc.perform(multipart("/generateReport")
                        .file("inputFile", "input.csv".getBytes())
                        .file("referenceFile", "reference.csv".getBytes()))
                .andExpect(status().isOk());
    }
}