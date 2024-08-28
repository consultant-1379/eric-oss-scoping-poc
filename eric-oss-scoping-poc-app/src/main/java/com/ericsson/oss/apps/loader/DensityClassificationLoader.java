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
package com.ericsson.oss.apps.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import com.ericsson.oss.apps.bdr.client.*;
import com.ericsson.oss.apps.bdr.loader.*;
import com.ericsson.oss.apps.model.*;
import com.ericsson.oss.apps.schema.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DensityClassificationLoader extends CsvLoader<DensityClassificationSchema, ClassifiedCell> {

    public static final String PATH_TEMPLATE = "%sDensityCategorizationList_Report_%s.csv.gz";

    public DensityClassificationLoader(
            Class<DensityClassificationSchema> clazz,
            BdrClient bdrClient,
            FileTracker fileTracker,
            Consumer<ClassifiedCell> recordConsumer
    ) {
        super(clazz, PATH_TEMPLATE, bdrClient, fileTracker, recordConsumer);
    }

    @Override
    protected InputStream interceptObjectInputStream(InputStream inputStream) throws IOException {
        return new GZIPInputStream(inputStream);
    }

    @Override
    protected Optional<ClassifiedCell> transformData(final DensityClassificationSchema densityClassificationSchema) {
        if (densityClassificationSchema.getNodeCell() == "") {
            log.warn("no SiteCell in {}", densityClassificationSchema);
            return Optional.empty();
        }
        return Optional.of(new ClassifiedCell( densityClassificationSchema.getNodeCell(),
                densityClassificationSchema.getNode(),
                densityClassificationSchema.getRealLatitude(),
                densityClassificationSchema.getRealLongitude(),
                densityClassificationSchema.getPseudoLatitude(),
                densityClassificationSchema.getPseudoLongitude(),
                densityClassificationSchema.getArfcnDL(),
                densityClassificationSchema.getBeamwidth(),
                densityClassificationSchema.getAzimuth(),
                densityClassificationSchema.getIndooroutdoor(),
                densityClassificationSchema.getDefaultCellRange(),
                densityClassificationSchema.getCellRange(),
                densityClassificationSchema.getClusterNumber(),
                densityClassificationSchema.getGeoDensity(),
                densityClassificationSchema.getGeoDensityCategory(),
                densityClassificationSchema.getCoSiteId(),
                densityClassificationSchema.getCoSiteWithNameId(),
                densityClassificationSchema.getCoSectorId(),
                densityClassificationSchema.getNetworkType(),
                densityClassificationSchema.getHoDensityCategoryAll(),
                densityClassificationSchema.getHoDensityCategoryIntra(),
                densityClassificationSchema.getGeoAverageInterCellDistInCovNeighbours(),
                densityClassificationSchema.getGeoAverageInterCellDistNearestNeighbours(),
                densityClassificationSchema.getHoAverageCellDistanceAll(),
                densityClassificationSchema.getHoAverageCellDistanceIntra(),
                densityClassificationSchema.getHoDensityOrdinalIntra(),
                densityClassificationSchema.getHoDensityOrdinalAll(),
                densityClassificationSchema.getGeoDensity()-densityClassificationSchema.getHoDensityOrdinalAll(),
                densityClassificationSchema.getGeoDensity()-densityClassificationSchema.getHoDensityOrdinalIntra()
        ));
    }
}
