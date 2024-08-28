/*******************************************************************************
 * COPYRIGHT Ericsson 2024
 *
 *
 *
 * The copyright to the computer program(s) herein is the property of
 *
 * Ericsson Inc. The programs may be used and/or copied only with written
 *
 * permission from Ericsson Inc. or in accordance with the terms and
 *
 * conditions stipulated in the agreement/contract under which the
 *
 * program(s) have been supplied.
 ******************************************************************************/

package com.ericsson.oss.apps.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import com.ericsson.oss.apps.bdr.client.*;
import com.ericsson.oss.apps.bdr.loader.*;
import com.ericsson.oss.apps.loader.*;
import com.ericsson.oss.apps.schema.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@SpringBootTest
public class DataLoaderServiceTest {

    static final String RESOURCE_FOLDER = "reports/";
    private static final String LTE_RESOURCE_FILE_NAME = "LTE_DensityCategorizationList_Report_0.csv.gz";
    private static final String LTE_NEAREST_FILE_NAME = "LTE_GeoNearestNeighbours_Report_704211200000.csv.gz";
    private static final String NR_RESOURCE_FILE_NAME = "DensityCategorizationList_Report_0.csv.gz";
    final FileTracker fileTracker = new FileTracker();
    @MockBean
    BdrS3Client bdrClient;
    @Autowired
    DataService dataService;
    @Autowired
    private DataLoaderService dataLoaderService;

    @BeforeEach
    void setup() throws IOException {
        InputStream LTE_inputStream = new ClassPathResource(RESOURCE_FOLDER + LTE_RESOURCE_FILE_NAME).getInputStream();
        InputStream NR_inputStream = new ClassPathResource(RESOURCE_FOLDER + NR_RESOURCE_FILE_NAME).getInputStream();
        InputStream LTE_Nearest_inputStream = new ClassPathResource(RESOURCE_FOLDER + LTE_NEAREST_FILE_NAME).getInputStream();
        Mockito.when(bdrClient.getObject(RESOURCE_FOLDER + LTE_RESOURCE_FILE_NAME)).thenReturn(LTE_inputStream);
        Mockito.when(bdrClient.getObject(RESOURCE_FOLDER + NR_RESOURCE_FILE_NAME)).thenReturn(NR_inputStream);
        Mockito.when(bdrClient.getObject(RESOURCE_FOLDER + LTE_NEAREST_FILE_NAME)).thenReturn(LTE_Nearest_inputStream);
    }

    @Test
    public void ifFileExists(){
        Mockito.when(bdrClient.exists(any())).thenReturn(true);
        assertTrue(dataLoaderService.ifFileExists(DensityClassificationLoader.PATH_TEMPLATE,"1", LTE_RESOURCE_FILE_NAME), "Expected result not received");
    }

    @Test
    public void loadLabelledDataTest(){
        Mockito.when(bdrClient.exists("reports/LTE_DensityCategorizationList_Report_0.csv.gz")).thenReturn(true);
        Mockito.when(bdrClient.exists("reports/DensityCategorizationList_Report_0.csv.gz")).thenReturn(true);
        Mockito.when(bdrClient.exists("reports/LTE_DensityCategorizationList_Report_1.csv.gz")).thenReturn(false);
        dataLoaderService.loadLabelledData();
        dataLoaderService.loadNeighborData();
        verify(bdrClient, times(9)).exists(any());
    }
}
