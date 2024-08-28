/*
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
 */
const filtered_cells = [];


function filterCell(cell) {
    var include = true;

    if(include === true ){
        filtered_cells.push(
            {
                "nodeCell": cell.nodeCell,
                "srcCell": cell.srcCell,
                "latitude": cell.realLatitude,
                "longitude": cell.realLongitude,
                "azimuth": cell.azimuth,
                "averageICD": cell.averageICD,
                "numberHoToNeighbor": cell.numberHoToNeighbor,
                "distanceToNeighbor": cell.distanceBetweenCells,
                "HOType" : cell.hoType
            }
            );    
    }
}

results.cell_neighbor_data.map((cell) => filterCell(cell));
result = filtered_cells
