#!/bin/bash

TOP=`dirname $0`
cd $TOP

PLATFORM=macosx
# PLATFORM=linux

java -Djava.library.path=flatcad-native/$PLATFORM -jar flatcad.jar --flatpath=flatcad-examples:fl flatcad-examples/welcome.fl
