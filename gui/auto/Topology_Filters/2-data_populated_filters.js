/*
 * COPYRIGHT Ericsson 2023
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

const density = [];
const cellrange = [];
const arfcndl = [];
const indooroutdoor = [];
const densityHO = [];
const networkType = [];
const hoDensityCategoryAll = [];
const hoDensityCategoryIntra = [];
function containsObject(obj, list) {
    var i;
    for (i = 0; i < list.length; i++) {
        if (list[i].label === obj.label) {
            return true;
        }
    }
    return false;
}

function addElement(a, v) {
    const entry = { label: "" + v }
      if (containsObject(entry, a) === false) {
        a.push(entry)
      }
}

function handleCell(cell) {
    addElement(density, cell.geoDensityCategory);
    addElement(cellrange, cell.cellrange);
    addElement(arfcndl, cell.arfcnDL);
    addElement(indooroutdoor, cell.indooroutdoor);
    addElement(densityHO, cell.densityCategoryHo);
    addElement(networkType, cell.networkType);
    addElement(hoDensityCategoryAll,cell.hoDensityCategoryAll);
    addElement(hoDensityCategoryIntra,cell.hoDensityCategoryIntra);
}

results.cell_density_data.map((cell) => handleCell(cell));
 
result = [
    {
        path: "rows[1].cols[1].sections[0].dropdown.data",
        replace: arfcndl,
    },
    {
        path: "rows[2].cols[1].sections[0].dropdown.data",
        replace: indooroutdoor,
    },
    {
        path: "rows[3].cols[1].sections[0].dropdown.data",
        replace: cellrange,
    },
    {
        path: "rows[4].cols[1].sections[0].dropdown.data",
        replace: density,
    },
    {
        path: "rows[5].cols[1].sections[0].dropdown.data",
        replace: networkType,
    },
     {
        path: "rows[6].cols[1].sections[0].dropdown.data",
        replace: hoDensityCategoryAll,
    },
    {
        path: "rows[7].cols[1].sections[0].dropdown.data",
        replace: hoDensityCategoryIntra,
    }
];
