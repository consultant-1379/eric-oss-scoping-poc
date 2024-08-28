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

package com.ericsson.oss.apps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClassifiedCell {
    @Id
    private String nodeCell;
    private String node;
    private Double realLatitude;
    private Double realLongitude;
    private Double pseudoLatitude;
    private Double pseudoLongitude;
    private Integer arfcnDL;
    private Integer beamwidth;
    private Integer azimuth;
    private String indooroutdoor;
    private Integer defaultCellRange;
    private Integer cellRange;
    private Integer clusterNumber;
    private Integer geoDensity;
    private String geoDensityCategory;
    private String coSiteId;
    private String coSiteWithNameId;
    private String coSectorId;
    private String networkType;
    private String hoDensityCategoryAll;
    private String hoDensityCategoryIntra;
    private Integer geoAverageInterCellDistanceInCoverageNeighbors;
    private Integer geoAverageInterCellDistNearestNeighbours;
    private Integer hoAverageCellDistanceAll;
    private Integer hoAverageCellDistanceIntra;
    private Integer hoDensityOrdinalIntra;
    private Integer hoDensityOrdinalAll;
    private Integer geoHoAllDiff;
    private Integer geoHoIntraDiff;
}

