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
public class NeighbourSchema {
    @CsvBindByName
    private String nodeCell;
    @CsvBindByName
    private double realLatitude;
    @CsvBindByName
    private double realLongitude;
    @CsvBindByName
    private double pseudoLatitude;
    @CsvBindByName
    private double pseudoLongitude;
    @CsvBindByName
    private int azimuth;
    @CsvBindByName
    private int averageICD;
    @CsvBindByName
    private int distanceBetweenCellsInMeters;
    @CsvBindByName
    private String hoType;
    @CsvBindByName
    private int numberHoToNeighbor;
    @CsvBindByName
    private String densityCategory;
}
