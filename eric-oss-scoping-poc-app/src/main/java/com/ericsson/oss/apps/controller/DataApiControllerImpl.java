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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.oss.apps.api.controller.*;
import com.ericsson.oss.apps.api.model.controller.*;
import com.ericsson.oss.apps.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DataApiControllerImpl implements DataApi {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataLoaderService dataLoaderService;

    @Override
    public ResponseEntity<List<Cell>> getCells() {
        log.info("Query request received to getCells");
        return new ResponseEntity<>(dataService.getCells(), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Void> getLabelledData() {
        dataLoaderService.loadLabelledData();
        dataLoaderService.loadNeighborData();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<Neighbour>> getNeighbours(String srcCell, String hoType) {
        log.info("Query request received to getNeighbours {},{}", srcCell, hoType);
        return new ResponseEntity<>(dataService.getNeighbours(srcCell,hoType), HttpStatusCode.valueOf(200));
    }

}
