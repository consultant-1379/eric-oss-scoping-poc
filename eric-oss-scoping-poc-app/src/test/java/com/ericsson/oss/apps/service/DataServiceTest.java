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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ericsson.oss.apps.model.*;
import com.ericsson.oss.apps.repository.*;

@SpringBootTest
public class DataServiceTest {

    @Autowired
    ClassifiedCellRepo classifiedCellRepo;
    @Autowired
    NeighbourRepo neighbourRepo;
    @Autowired
    private DataService dataService;

    @Test
    public void putClassifiedCellTest(){
        ClassifiedCell cell = new ClassifiedCell();
        cell.setNodeCell("TestNodeCell");

        dataService.putClassifiedCell(cell);
        assertTrue(dataService.isClassified(cell.getNodeCell()), "ClassifiedCell was not successfully stored");
    }

    @Test
    public void storeNeighborGeoTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCell_NONE");
        neighbour.setNodeCell("TestNodeCell");
        neighbour.setSrcCell("TestNodeCell");
        neighbour.setHoType("Geo");
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(1, dataService.getNeighbours("TestNodeCell","Geo").size());
    }
    @Test
    public void storeNeighborALLTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCell_NONE");
        neighbour.setNodeCell("TestNodeCell");
        neighbour.setSrcCell("TestNodeCell");
        neighbour.setHoType("HO-All");
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(1, dataService.getNeighbours("TestNodeCell","HO-All").size());
    }
    @Test
    public void storeNeighborIntraTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCell_NONE");
        neighbour.setNodeCell("TestNodeCell");
        neighbour.setSrcCell("TestNodeCell");
        neighbour.setHoType("HO-Intra");
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(1, dataService.getNeighbours("TestNodeCell","HO-Intra").size());
    }
    @Test
    public void storeNeighborGeoAllTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCellGeoAll_ALL");
        neighbour.setNodeCell("TestNodeCellGeoAll");
        neighbour.setSrcCell("TestNodeCellGeoAll");
        neighbour.setHoType("HO-All");
        Neighbour neighbour2 = new Neighbour();
        neighbour2.setId("TestNodeCellGeoAll_NONE");
        neighbour2.setNodeCell("TestNodeCellGeoAll");
        neighbour2.setSrcCell("TestNodeCellGeoAll");
        neighbour2.setHoType("Geo");
        dataService.putNeighbour(neighbour2);
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(2, dataService.getNeighbours("TestNodeCellGeoAll","Geo HO All").size());
    }
    @Test
    public void storeNeighborGeoIntraTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCellGeoIntra_Intra");
        neighbour.setNodeCell("TestNodeCellGeoIntra");
        neighbour.setSrcCell("TestNodeCellGeoIntra");
        neighbour.setHoType("HO-Intra");
        Neighbour neighbour2 = new Neighbour();
        neighbour2.setId("TestNodeCellGeo_Geo");
        neighbour2.setNodeCell("TestNodeCellGeo");
        neighbour2.setSrcCell("TestNodeCellGeoIntra");
        neighbour2.setHoType("Geo");
        dataService.putNeighbour(neighbour2);
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(2, dataService.getNeighbours("TestNodeCellGeoIntra","Geo HO Intra").size());
    }
    @Test
    public void storeNeighborUndefinedTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCell_NONE");
        neighbour.setNodeCell("TestNodeCell");
        neighbour.setSrcCell("TestNodeCell");
        neighbour.setHoType("Geo");
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(1, dataService.getNeighbours("TestNodeCell","Geo").size());
    }
    @Test
    public void storeNeighborDefaultTest(){
        Neighbour neighbour = new Neighbour();
        neighbour.setId("TestNodeCell_NONE");
        neighbour.setNodeCell("TestNodeCell");
        neighbour.setSrcCell("TestNodeCell");
        neighbour.setHoType("Geo");
        dataService.putNeighbour(neighbour);
        dataService.storeNeighbour();
        assertEquals(0, dataService.getNeighbours("TestNodeCell","New").size());
    }
    @Test
    public void putClassifiedCellFailTest(){
        ClassifiedCell cell = new ClassifiedCell();
        cell.setNodeCell("TestNodeCell");

        dataService.putClassifiedCell(cell);
        assertFalse(dataService.isClassified("FAILSITECELL"), "ClassifiedCell was successfully stored");
    }

    @Test
    public void isClassifiedfail(){
        assertFalse(dataService.isClassified("FailSiteCell"), "ClassifiedCell found in map");
    }

    @Test
    public void storeClassifiedCellTest(){
        ClassifiedCell cell = new ClassifiedCell();
        cell.setNodeCell("TestNodeCell");

        dataService.putClassifiedCell(cell);
        assertTrue(dataService.isClassified(cell.getNodeCell()), "ClassifiedCell was not successfully stored");

        dataService.storeClassifiedCells();
        assertFalse(dataService.isClassified(cell.getNodeCell()), "ClassifiedCell was not successfully stored");

        assertEquals(1, dataService.getCells().size());
        assertEquals("TestNodeCell", dataService.getCells().get(0).getNodeCell());

    }


}
