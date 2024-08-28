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

import com.ericsson.oss.apps.bdr.client.BdrClient;
import com.ericsson.oss.apps.bdr.loader.CsvLoader;
import com.ericsson.oss.apps.bdr.loader.FileTracker;
import com.ericsson.oss.apps.model.Neighbour;
import com.ericsson.oss.apps.schema.NeighbourSchema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

@Slf4j
public class NeighbourLoader extends CsvLoader<NeighbourSchema, Neighbour> {
    public static final String PATH_TEMPLATE = "%sNeighbours_Report_%s.csv.gz";
    private  String srcCell = "";
    @Setter
    @Getter
    public String networkType;

    public NeighbourLoader(
            Class<NeighbourSchema> clazz,
            BdrClient bdrClient,
            FileTracker fileTracker,
            Consumer<Neighbour> recordConsumer
    ) {
        super(clazz, PATH_TEMPLATE, bdrClient, fileTracker, recordConsumer);
    }

    @Override
    protected InputStream interceptObjectInputStream(InputStream inputStream) throws IOException {
        return new GZIPInputStream(inputStream);
    }

    @Override
    protected Optional<Neighbour> transformData(final NeighbourSchema neighbourSchema) {
        if(neighbourSchema.getAverageICD()>=0 && neighbourSchema.getDistanceBetweenCellsInMeters() ==0){
            srcCell = neighbourSchema.getNodeCell();
        }
        return Optional.of(new Neighbour(srcCell+"_"+neighbourSchema.getNodeCell()+"_"+neighbourSchema.getHoType()+"_"+networkType,
                neighbourSchema.getNodeCell(),
                srcCell,
                neighbourSchema.getRealLatitude(),
                neighbourSchema.getRealLongitude(),
                neighbourSchema.getPseudoLatitude(),
                neighbourSchema.getPseudoLongitude(),
                neighbourSchema.getAzimuth(),
                neighbourSchema.getAverageICD(),
                neighbourSchema.getDistanceBetweenCellsInMeters(),
                getHoType(neighbourSchema.getHoType()),
                neighbourSchema.getNumberHoToNeighbor(),
                neighbourSchema.getDensityCategory(),
                networkType
                ));
    }
    private String getHoType(String hoType){
        String ho="";
        switch(hoType){
            case "INTRA":
                ho = "HO-Intra";
                break;

            case "ALL":
                ho = "HO-All";
                break;
            default:
                ho = "Geo";
        }
        log.info("Ho type {}", ho);
        return ho;
    }

}


