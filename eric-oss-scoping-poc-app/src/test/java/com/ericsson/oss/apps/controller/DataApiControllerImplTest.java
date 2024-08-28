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

package com.ericsson.oss.apps.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import com.ericsson.oss.apps.api.model.controller.*;
import com.ericsson.oss.apps.service.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(DataApiControllerImpl.class)
public class DataApiControllerImplTest {

    @MockBean
    private WebClient webClient;
    @MockBean
    DataService dataService;
    @MockBean
    DataLoaderService dataLoaderService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void getCells() throws Exception {
        Cell cell = new Cell("TESTCELL");
        var cellList = List.of(cell);
        org.mockito.Mockito.when(dataService.getCells()).thenReturn(cellList);

        mvc.perform(get("/v1/cells")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        [{"nodeCell":"TESTCELL"}]"""));
        verify(dataService, times(1)).getCells();
    }

    @Test
    public void getLabelledData() throws Exception {
        mvc.perform(get("/v1/loadData")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(dataLoaderService, times(1)).loadLabelledData();
    }

    @Test
    public void getNeighbors() throws Exception {
        com.ericsson.oss.apps.api.model.controller.Neighbour neighbour = new com.ericsson.oss.apps.api.model.controller.Neighbour();
        neighbour.setNodeCell("Test");
        neighbour.setSrcCell("Test");
        neighbour.setNumberHoToNeighbor(1);
        neighbour.setRealLongitude(10.7);
        neighbour.setRealLatitude(11.2);
        neighbour.setHoType("Geo");
        neighbour.setAzimuth(20);
        neighbour.setAverageICD(1);
        neighbour.setDistanceBetweenCells(3);
        neighbour.setDensityCategory("URBAN");
        var neighborList = List.of(neighbour);
        org.mockito.Mockito.when(dataService.getNeighbours("Test","Geo")).thenReturn(neighborList);

        mvc.perform(get("/v1/neighbours?srcCell=Test&hoType=Geo")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(dataService, times(1)).getNeighbours("Test","Geo");
    }


}