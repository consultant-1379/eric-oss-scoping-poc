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
const nodes = [];
var index = 1;

const getAlerts = (density) => {
  switch (density) {
    case "DENSE_URBAN":
      return [
        {
          name: "Critical",
          value: ""
        },
      ];
    case "URBAN":
      return [
        {
          name: "Maintenance",
          value: ""
        },
      ];
    case "SUBURBAN":
      return [
        {
          name: "Minor",
          value: ""
        },
      ];
    case "RURAL":
        return [
        {
          name: "Major",
          value: ""
        },
      ];
  }
};

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
    if(args.data){
        if(args.data.arfcnDL && include === true) {
            include = compareTo(cell.arfcnDL, args.data.arfcnDL);
        }
        if(args.data.nodeCell){
            include = includes(cell.nodeCell, args.data.nodeCell);
        }
        
        if(args.data.arfcnDL && include === true) {
            include = compareTo(cell.arfcnDL, args.data.arfcnDL);
        }
        if(args.data.indooroutdoor && include === true) {
            include = compareTo(cell.indooroutdoor, args.data.indooroutdoor);
        }
        if(args.data.cellRange && include === true) {
            include = compareTo(cell.cellrange, args.data.cellRange);
        }
        if(args.data.densityCategory && include === true) {
            include = compareTo(cell.geoDensityCategory, args.data.densityCategory);
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
    if(include === true){
        nodes.push(
            {
              key: index,
              name: "",
              icon: "antenna",
              y: cell.latitude,
              x: cell.longitude,
              direction: Number(cell.azimuth),
              alerts:getAlerts(cell.geoDensityCategory),
            });
    }
    index = index + 1;
}

results.cell_density_data.map((cell) => filterCell(cell));
result = {
  alerts: [{ name: "Critical" }, { name: "Major" }, { name: "Minor" }, { name: "Maintenance" }],
  nodes,
};



