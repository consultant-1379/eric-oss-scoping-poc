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
    case "NEIGHBOR_CELL":
      return [
        {
          name: "Warning",
          value: ""
        },
      ];
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

function filterCell(cell) {
    var include = true;
    var iconstr = "antenna";
    var alertType = "NEIGHBOR_CELL";
    if(cell.nodeCell===cell.srcCell){
        iconstr = "icon-manufacturing";
        alertType = cell.densityCategory;
    }else if((args.data.densityType === "Geo+HO All" && cell.hoType === "Geo-Ho-ALL")||(args.data.densityType === "Geo+HO Intra" && cell.hoType === "Geo-Ho-Intra")){
         alertType =  "RURAL";
         iconstr = "square";
        
    }else if(args.data.densityType === "Geo+HO All" && cell.hoType === "Geo"){
         alertType =  "URBAN";
         iconstr = "diamond";
        
    }
    if(include === true){
        nodes.push(
            {
              key: index,
              name: "",
              icon: iconstr,
              y: cell.realLatitude,
              x: cell.realLongitude,
              direction: Number(cell.azimuth),
              alerts:getAlerts(alertType),
            });
    }
    index = index + 1;
}

results.cell_neighbor_data.map((cell) => filterCell(cell));
result = {
  alerts: [{ name: "Warning" },{ name: "Critical" }, { name: "Major" }, { name: "Minor" }, { name: "Maintenance" }],
  nodes,
};