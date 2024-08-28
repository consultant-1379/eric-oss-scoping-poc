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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ericsson.oss.apps.bdr.client.*;
import com.ericsson.oss.apps.bdr.loader.*;
import com.ericsson.oss.apps.loader.*;
import com.ericsson.oss.apps.schema.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataLoaderService {
    private final BdrS3Client bdrClient;
    private final FileTracker fileTracker;
    private final DataService dataService;

    @Value("${app.data.directory}")
    private String dataDirectory;
    @Value("#{'${app.data.densityFile}'.split(',')}")
    private String[] densityFiles;

    public void loadLabelledData(){
        DensityClassificationLoader densityClassificationLoader =
                new DensityClassificationLoader(DensityClassificationSchema.class, bdrClient, fileTracker, dataService::putClassifiedCell);
        load(densityClassificationLoader, dataDirectory + "BOTH_");
        // LTE Cell Density data loading
        load(densityClassificationLoader, dataDirectory + "LTE_");
        //NR Cell Density data loading
        load(densityClassificationLoader, dataDirectory+ "NR_");

    }
    public void loadNeighborData(){
        NeighbourLoader neighbourLoader = new NeighbourLoader(NeighbourSchema.class,bdrClient,fileTracker, dataService::putNeighbour);
        neighbourLoader.setNetworkType("LTE");
        loadNeighbor(neighbourLoader,dataDirectory+"LTE_HoAll");
        loadNeighbor(neighbourLoader,dataDirectory+"LTE_HoIntra");
        loadNeighbor(neighbourLoader,dataDirectory+"LTE_GeoNearest");
        neighbourLoader.setNetworkType("NR");
        loadNeighbor(neighbourLoader,dataDirectory+"NR_HoAll");
        loadNeighbor(neighbourLoader,dataDirectory+"NR_HoIntra");
        loadNeighbor(neighbourLoader,dataDirectory+"NR_GeoNearest");
    }

    public void load(DensityClassificationLoader densityClassificationLoader, String dataDirectoryPath){
        for(String densityFile: densityFiles) {
            log.info("********* densityFile:{} **********", densityFile);
            if (ifFileExists(DensityClassificationLoader.PATH_TEMPLATE, densityFile, dataDirectoryPath)) {
                densityClassificationLoader.loadData(dataDirectoryPath, densityFile);
            }
        }
        dataService.storeClassifiedCells();
    }
    public void loadNeighbor(NeighbourLoader neighbourLoader, String dataDirectoryPath){
        for(String densityFile: densityFiles){
            log.info("********* densityFile:{} **********", densityFile);
            String  extension = densityFile;
            if (ifFileExists(NeighbourLoader.PATH_TEMPLATE,extension, dataDirectoryPath)){
                neighbourLoader.loadData(dataDirectoryPath, extension);
            }
        }
        dataService.storeNeighbour();
    }
    public boolean ifFileExists(String path,String fileIncrement, String dataDirectoryPath){
        String objectPath = String.format(path, dataDirectoryPath, fileIncrement);
        log.info("*** file: {}  ***", objectPath);
        return bdrClient.exists(objectPath);
    }


}
