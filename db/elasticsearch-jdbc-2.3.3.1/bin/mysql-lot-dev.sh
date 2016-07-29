#!/bin/sh

# DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DIR=./
bin=${DIR}/../bin
lib=${DIR}/../lib

echo '
{
    "type" : "jdbc",
    "jdbc" : {
        "schedule" : "0 0-59 0-23 ? * *",
        "statefile" : "statefile.json",
        "url" : "jdbc:mysql://0.0.0.0:3306/lot_dev",
        "user" : "root",
        "password" : "root",
        "metrics" : { 
	      "lastexecutionstart" : "2016-03-27T06:37:09.165Z",
	      "lastexecutionend" : "2016-03-27T06:37:09.501Z",
	      "counter" : "1",
	      "enabled" : true 
	    },  
        "sql" : [
            {
                "statement" : "select * from securities where updated_at > ?",
                "parameter" : [ "$metrics.lastexecutionstart" ]
            }
        ],
        "elasticsearch" : {
            "host" : "0.0.0.0",
            "port" : 9300
        }
    }
}
' | java \
    -cp "${lib}/*" \
    -Dlog4j.configurationFile=${bin}/log4j2.xml \
    org.xbib.tools.Runner \
    org.xbib.tools.JDBCImporter