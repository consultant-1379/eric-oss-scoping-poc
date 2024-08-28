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
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Neighbour {

    @Id
    private String id;
    private String nodeCell;
    private String srcCell;
    private double realLatitude;
    private double realLongitude;
    private double pseudoLatitude;
    private double pseudoLongitude;
    private int azimuth;
    private int averageICD;
    private int distanceBetweenCellsInMeters;
    private String hoType;
    private int numberHoToNeighbor;
    private String densityCategory;
    private String networkType;
}
