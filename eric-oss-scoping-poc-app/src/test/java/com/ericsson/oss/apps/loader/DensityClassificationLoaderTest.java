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

package com.ericsson.oss.apps.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import com.ericsson.oss.apps.schema.*;
import com.ericsson.oss.apps.service.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DensityClassificationLoaderTest extends AbstractConfigLoaderTest {
    private static final String RESOURCE_FILE_NAME = "DensityCategorizationList_Report_1.csv.gz";
    private static final String RESOURCE_FILE_PATH = RESOURCE_FOLDER + RESOURCE_FILE_NAME;
    @Spy
    DataService dataService;
    private DensityClassificationLoader densityClassificationLoader;
    @BeforeEach
    void setup() throws IOException {
        densityClassificationLoader = new DensityClassificationLoader(DensityClassificationSchema.class, bdrClient, fileTracker, dataService::putClassifiedCell);
        InputStream inputStream = new ClassPathResource(RESOURCE_FILE_PATH).getInputStream();
        Mockito.when(bdrClient.getObject(RESOURCE_FILE_PATH)).thenReturn(inputStream);
        Mockito.when(bdrClient.getCheckSum(RESOURCE_FILE_PATH)).thenReturn(Optional.of(E_TAG));
    }

    @Test
    public void loadDensityClassificationEntry() {
        long numRecordsProcessed = densityClassificationLoader.loadData(RESOURCE_FOLDER, "1");
        assertEquals(2, numRecordsProcessed, "Expected number of records not loaded");
        Mockito.verify(dataService, Mockito.times(1)).putClassifiedCell(any());
    }

    @Test
    public void checkSameDensityClassificationEntryLoad(){
        fileTracker.clearTracker();
        this.loadDensityClassificationEntry();
        long numRecordsProcessed = densityClassificationLoader.loadData(RESOURCE_FOLDER, "1");
        assertEquals(0, numRecordsProcessed, "Expected number of records not loaded");
    }

    @Test
    public void transformDataValidInput(){
        this.loadDensityClassificationEntry();
        dataService.isClassified("TESTSITECELL");
    }

}
