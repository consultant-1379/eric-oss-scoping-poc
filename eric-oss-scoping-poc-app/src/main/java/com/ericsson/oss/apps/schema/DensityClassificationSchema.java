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
package com.ericsson.oss.apps.schema;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
@Data
public class DensityClassificationSchema {
    @CsvBindByName
    private String nodeCell;
    @CsvBindByName
    private String node;
    @CsvBindByName
    private double realLatitude;
    @CsvBindByName
    private double realLongitude;
    @CsvBindByName
    private double pseudoLatitude;
    @CsvBindByName
    private double pseudoLongitude;
    @CsvBindByName
    private int arfcnDL;
    @CsvBindByName
    private int beamwidth;
    @CsvBindByName
    private int azimuth;
    @CsvBindByName
    private String indooroutdoor;
    @CsvBindByName
    private int defaultCellRange;
    @CsvBindByName
    private int cellRange;
    @CsvBindByName
    private double beamwidthBufferLeft;
    @CsvBindByName
    private double beamwidthBufferRight;
    @CsvBindByName
    private int clusterNumber;
    @CsvBindByName
    private int geoDensity;
    @CsvBindByName
    private String geoDensityCategory;
    @CsvBindByName
    private String coSiteId;
    @CsvBindByName
    private String coSiteWithNameId;
    @CsvBindByName
    private String coSectorId;
    @CsvBindByName(column = "geoAverageInterCellDistanceAllNeighbors")
    private int geoAverageInterCellDistAllNeighbours;
    @CsvBindByName(column = "geoAverageInterCellDistanceNearestNeighbors")
    private int geoAverageInterCellDistNearestNeighbours;
    @CsvBindByName(column = "geoAverageInterCellDistanceInCoverageNeighbors")
    private int geoAverageInterCellDistInCovNeighbours;
    @CsvBindByName
    private String networkType;
    @CsvBindByName
    private String hoDensityCategoryAll;
    @CsvBindByName
    private String hoDensityCategoryIntra;
    @CsvBindByName
    private int hoAverageCellDistanceAll;
    @CsvBindByName
    private int hoAverageCellDistanceIntra;
    @CsvBindByName
    private int hoDensityOrdinalIntra;
    @CsvBindByName
    private int hoDensityOrdinalAll;
}
