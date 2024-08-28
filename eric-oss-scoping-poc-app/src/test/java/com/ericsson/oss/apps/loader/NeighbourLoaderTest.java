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

import com.ericsson.oss.apps.schema.NeighbourSchema;
import com.ericsson.oss.apps.service.DataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NeighbourLoaderTest extends AbstractConfigLoaderTest {
    private static final String LTE_NEAREST_NEIGHBORS_REPORT = "LTE_GeoNearestNeighbours_Report_704211200000.csv.gz";
    private static final String LTE_ALL_NEIGHBORS_REPORT = "LTE_HoAllNeighbours_Report_1704211200000.csv.gz";
    private static final String LTE_INTRA_NEIGHBORS_REPORT = "LTE_HoIntraNeighbours_Report_1704211200000.csv.gz";
    @Spy
    DataService dataService;
    private NeighbourLoader neighbourLoader;
    @Test
    public void loadNeighborEntry()throws IOException {
        loadCSV(LTE_NEAREST_NEIGHBORS_REPORT);
        long numRecordsProcessed = neighbourLoader.loadData(RESOURCE_FOLDER+"LTE_GeoNearest", "704211200000");
        assertEquals(7, numRecordsProcessed, "Expected number of records not loaded");
        Mockito.verify(dataService, Mockito.times(7)).putNeighbour(any());
    }
    @Test
    public void loadAllNeighborEntry()throws IOException {
        loadCSV(LTE_ALL_NEIGHBORS_REPORT);
        long numRecordsProcessed = neighbourLoader.loadData(RESOURCE_FOLDER+"LTE_HoAll", "1704211200000");
        assertEquals(12, numRecordsProcessed, "Expected number of records not loaded");
        Mockito.verify(dataService, Mockito.times(12)).putNeighbour(any());
    }
    @Test
    public void loadIntraNeighborEntry()throws IOException {
        loadCSV(LTE_INTRA_NEIGHBORS_REPORT);
        long numRecordsProcessed = neighbourLoader.loadData(RESOURCE_FOLDER+"LTE_HoIntra", "1704211200000");
        assertEquals(6, numRecordsProcessed, "Expected number of records not loaded");
        Mockito.verify(dataService, Mockito.times(6)).putNeighbour(any());
    }
    @Test
    public void checkSameNeighborEntryLoad()throws IOException{
        fileTracker.clearTracker();
        this.loadNeighborEntry();
        long numRecordsProcessed = neighbourLoader.loadData(RESOURCE_FOLDER+"LTE_GeoNearest", "704211200000");
        assertEquals(0, numRecordsProcessed, "Expected number of records not loaded");
    }

    private void loadCSV(String filePath)throws IOException{
        neighbourLoader = new NeighbourLoader(NeighbourSchema.class,bdrClient,fileTracker, dataService::putNeighbour);
        InputStream inputStream = new ClassPathResource(RESOURCE_FOLDER + filePath).getInputStream();
        Mockito.when(bdrClient.getObject(RESOURCE_FOLDER + filePath)).thenReturn(inputStream);
        Mockito.when(bdrClient.getCheckSum(RESOURCE_FOLDER + filePath)).thenReturn(Optional.of(E_TAG));
    }

}
