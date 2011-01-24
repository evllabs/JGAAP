#!/bin/bash

# constants

JGAAP_BIN=../bin
EXTERN=../lib/external
CLI_EXTERN_JARS="PDFBox-0.7.3.jar poi-3.5-beta3-20080926.jar poi-contrib-3.5-beta3-20080926.jar poi-jdk14-3.5-beta3-20080926.jar poi-ooxml-3.5-beta3-20080926.jar poi-scratchpad-3.5-beta3-20080926.jar jmathplot.jar jmathio.jar jmatharray.jar stanford-postagger-2008-09-28.jar"


# get cli args
cli_args=""
while [ "$1" != "" ]; do
	cli_args="$cli_args $1"
	shift
done


# setup classpath
cli_classpath=$JGAAP_BIN
for i in $CLI_EXTERN_JARS; do
	cli_classpath="$cli_classpath:$EXTERN/$i"
done


# run it

java -Xmx2048m -cp "$cli_classpath" com.jgaap.backend.CLI $cli_args

