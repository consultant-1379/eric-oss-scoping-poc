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

package com.ericsson.oss.apps.repository;

import com.ericsson.oss.apps.model.Neighbour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeighbourRepo extends JpaRepository<Neighbour,String> {


    @Query("SELECT p FROM Neighbour p WHERE p.srcCell = :srcCell AND p.hoType = :hoType")
    List<Neighbour> findNeighbours(
            @Param("srcCell") String srcCell,
            @Param("hoType") String hoType
    );
    @Query("SELECT p FROM Neighbour p WHERE p.srcCell = :srcCell AND p.hoType IN ('HO-All', 'Geo') GROUP BY p.ID, p.hoType")
    List<Neighbour> findGeoAndALLNeighbours(
            @Param("srcCell") String srcCell
    );
    @Query("SELECT p FROM Neighbour p WHERE p.srcCell = :srcCell AND p.hoType IN ('HO-Intra', 'Geo') GROUP BY p.ID, p.hoType")
    List<Neighbour> findGeoAndIntraNeighbours(
            @Param("srcCell") String srcCell
    );
}
