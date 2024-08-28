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

function compareTo(value, test) {
    if("" + value === "" + test){
        return true;
    }
    return false;
}

function includes(value, test) {
    value = "" + value;
    test = "" + test;
    
    if( value.toLowerCase().includes(  test.toLowerCase() )){
        return true;
    }
    return false;
}
function diff(densityCategoryDiff, densityOrdinalDiff, density){
    if(densityCategoryDiff === "DenseUrban-Suburban" && densityOrdinalDiff === -2 && density === "SUBURBAN"){
        return true;
    }
    if(densityCategoryDiff === "Urban-Rural" && densityOrdinalDiff === -2 && density === "RURAL"){
        return true;
    }
    if(densityCategoryDiff === "DenseUrban-Rural" &&  densityOrdinalDiff === -3){
        return true;
    }
}

function filterCell(cell) {
    var include = true;
    var distance = cell.geoAverageInterCellDistNearestNeighbours;
    if(args.data){
        if(args.data.arfcnDL && include === true) {            
            include = compareTo(cell.arfcnDL, args.data.arfcnDL);
        } 
        if(args.data.nodeCell){
            include = includes(cell.nodeCell, args.data.nodeCell);
        }
        if(args.data.indooroutdoor && include === true) {
            include = compareTo(cell.indooroutdoor, args.data.indooroutdoor);
        }
        if(args.data.densityCategory && include === true) {
            include = compareTo(cell.geoDensityCategory, args.data.densityCategory);
        }
        if(args.data.densityType === "Geo"){
             distance = cell.geoAverageInterCellDistNearestNeighbours;
        }
        if(args.data.densityType === "HO-All"){
            distance = cell.hoAverageCellDistanceAll;
        }
       if(args.data.densityType === "HO-Intra"){
            distance = cell.hoAverageCellDistanceIntra;
        }
        if(args.data.networkType && include === true) {
            include = compareTo(cell.networkType, args.data.networkType);
        }
        if(args.data.hoDensityCategoryAll && include === true){
            include = compareTo(cell.hoDensityCategoryAll, args.data.hoDensityCategoryAll);
        }
        if(args.data.hoDensityCategoryIntra && include === true){
            include = compareTo(cell.hoDensityCategoryIntra, args.data.hoDensityCategoryIntra);
        }
        if(args.data.geoHoAllDiff && include === true) { 
            if(cell.hoDensityCategoryAll === "NOTASSIGNED"){
                include = false;
            }else{
             include =  diff(args.data.geoHoAllDiff, cell.geoAllDiff, cell.hoDensityCategoryAll);
            }
         }
        if(args.data.geoHoIntraDiff && include === true ) { 
            if(cell.hoDensityCategoryIntra === "NOTASSIGNED"){
                include = false;
            }else{
                include =  diff(args.data.geoHoIntraDiff, cell.geoIntraDiff, cell.hoDensityCategoryIntra);
            }
         }
    }
    
    if(include === true ){
        filtered_cells.push(
            {
               "nodeCell": cell.nodeCell,
               "node": cell.node,
               "latitude": cell.latitude,
               "longitude": cell.longitude,
               "azimuth": cell.azimuth,
               "arfcnDL": cell.arfcnDL,
               "cositename": cell.coSiteWtihNameId,
               "indooroutdoor": cell.indooroutdoor,
               "densityCategory": cell.geoDensityCategory,
               "cositeId": cell.coSiteId,
               "cosectorId": cell.coSectorId,
               "networkType": cell.networkType,
               "hoDensityCategoryAll": cell.hoDensityCategoryAll,
               "hoDensityCategoryIntra": cell.hoDensityCategoryIntra,
               "distanceToNeighbor": distance
            });
    }
}

results.cell_density_data.map((cell) => filterCell(cell));
result = filtered_cells
