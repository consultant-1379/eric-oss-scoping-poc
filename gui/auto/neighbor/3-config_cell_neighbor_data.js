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


var nodeCell = args.data.nodeCell;
var type = args.data.densityType;
let url = "http://eric-oss-scoping-poc:8080/v1/neighbours?srcCell="+nodeCell+"&hoType="+type;
result = {
   "url": url,
    "options": {
        "method": "GET",
        "timeout": 30000,
        "headers": {
            "content-type": "application/json"
        }
    },
    "responseFormat": "json"
}