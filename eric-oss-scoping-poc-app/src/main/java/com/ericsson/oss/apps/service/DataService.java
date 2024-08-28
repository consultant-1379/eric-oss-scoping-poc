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

import java.util.*;
import java.util.stream.Collectors;

import com.ericsson.oss.apps.model.Neighbour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.oss.apps.api.model.controller.*;
import com.ericsson.oss.apps.model.*;
import com.ericsson.oss.apps.repository.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataService {
    @Autowired
    private ClassifiedCellRepo classifiedCellRepo;

    @Autowired
    private NeighbourRepo neighbourRepo;

    private List<ClassifiedCell> classifiedCells = new ArrayList<>();
    private List<Neighbour> neighbourList = new ArrayList<>();

    public boolean putClassifiedCell(ClassifiedCell classifiedCell) {
        return classifiedCells.add(classifiedCell);
    }
    public boolean putNeighbour(Neighbour neighbour){
        return neighbourList.add(neighbour);
    }

    public boolean isClassified(String siteCell){
        boolean isClassified = true;
        if( classifiedCells.stream().filter(c -> c.getNodeCell().equals(siteCell))
                .findFirst().isEmpty()){
            isClassified = false;
        }
        return isClassified;
    }

    public void storeClassifiedCells(){
        classifiedCellRepo.saveAll(classifiedCells);
        classifiedCells.clear();
    }
    public void storeNeighbour(){
        neighbourRepo.saveAll(neighbourList);
        neighbourList.clear();
    }

    public List<Cell> getCells(){
        return classifiedCellRepo.findAll().stream().map(this::convertedToCell).toList();
    }
    public List<com.ericsson.oss.apps.api.model.controller.Neighbour> getNeighbours(String srcCell, String hoTypeFromFrontend){
        List<Neighbour> neighbours = null;
        if(hoTypeFromFrontend.equals("Geo HO All")){
            neighbours = neighbourRepo.findGeoAndALLNeighbours(srcCell);
            findNodeNameInMoreThanOneHoType(neighbours,"Geo-Ho-ALL");
        }else  if(hoTypeFromFrontend.equals("Geo HO Intra")){
            neighbours = neighbourRepo.findGeoAndIntraNeighbours(srcCell);
            findNodeNameInMoreThanOneHoType(neighbours,"Geo-Ho-Intra");
        }else {
            neighbours = neighbourRepo.findNeighbours(srcCell, hoTypeFromFrontend);
        }
        List<com.ericsson.oss.apps.api.model.controller.Neighbour> guiNeighbourList = new ArrayList<>();
        for(Neighbour neighbour: neighbours){
            com.ericsson.oss.apps.api.model.controller.Neighbour neighbour1 = new com.ericsson.oss.apps.api.model.controller.Neighbour();
            neighbour1.setSrcCell(neighbour.getSrcCell());
            neighbour1.setAzimuth(neighbour.getAzimuth());
            neighbour1.setDensityCategory(neighbour.getDensityCategory());
            neighbour1.setAverageICD(neighbour.getAverageICD());
            neighbour1.setHoType(neighbour.getHoType());
            neighbour1.setRealLatitude(neighbour.getRealLatitude());
            neighbour1.setRealLongitude(neighbour.getRealLongitude());
            neighbour1.setNodeCell(neighbour.getNodeCell());
            neighbour1.setDistanceBetweenCells(neighbour.getDistanceBetweenCellsInMeters());
            neighbour1.setNumberHoToNeighbor(neighbour.getNumberHoToNeighbor());
            guiNeighbourList.add(neighbour1);
        }
        return guiNeighbourList;
    }
    private void findNodeNameInMoreThanOneHoType(List<Neighbour> neighbours,String hoType){
        Map<String, Long> counts = neighbours.stream()
                .collect(Collectors.groupingBy( Neighbour::getNodeCell, Collectors.counting()));
        neighbours.forEach(neighbour -> {
            if (counts.get(neighbour.getNodeCell()) > 1 && !neighbour.getNodeCell().equals(neighbour.getSrcCell())) {
                neighbour.setHoType(hoType);
                log.debug("nodeCell: {}" , neighbour.getNodeCell());
            }
        });
        Collections.sort(neighbours, (neighbour1, neighbour2) -> neighbour1.getHoType().compareTo(neighbour2.getHoType()));

    }

    private Cell convertedToCell(ClassifiedCell classifiedCell){
        Cell cell = new Cell();
        cell.setNodeCell(classifiedCell.getNodeCell());
        cell.setNode(classifiedCell.getNode());
        cell.setLatitude(classifiedCell.getPseudoLatitude());
        cell.setLongitude(classifiedCell.getPseudoLongitude());
        cell.setArfcnDL(classifiedCell.getArfcnDL());
        cell.setAzimuth(classifiedCell.getAzimuth());
        cell.setBeamwidth(classifiedCell.getBeamwidth());
        cell.setIndooroutdoor(classifiedCell.getIndooroutdoor());
        cell.setCellrange(classifiedCell.getCellRange());
        cell.setClusterNumber(classifiedCell.getClusterNumber());
        cell.setGeoDensity(classifiedCell.getGeoDensity());
        cell.setGeoDensityCategory(classifiedCell.getGeoDensityCategory());
        cell.setCoSiteId(classifiedCell.getCoSiteId());
        cell.setCoSiteWtihNameId(classifiedCell.getCoSiteWithNameId());
        cell.setCoSectorId(classifiedCell.getCoSectorId());
        cell.setNetworkType(classifiedCell.getNetworkType());
        cell.setHoDensityCategoryAll(classifiedCell.getHoDensityCategoryAll());
        cell.setHoDensityCategoryIntra(classifiedCell.getHoDensityCategoryIntra());
        cell.setGeoAverageInterCellDistanceInCoverageNeighbors(classifiedCell.getGeoAverageInterCellDistanceInCoverageNeighbors());
        cell.setHoAverageCellDistanceAll(classifiedCell.getHoAverageCellDistanceAll());
        cell.setHoAverageCellDistanceIntra(classifiedCell.getHoAverageCellDistanceIntra());
        cell.setGeoAverageInterCellDistNearestNeighbours(classifiedCell.getGeoAverageInterCellDistNearestNeighbours());
        cell.setGeoAllDiff(classifiedCell.getGeoHoAllDiff());
        cell.setGeoIntraDiff(classifiedCell.getGeoHoIntraDiff());
        return cell;
    }

}
