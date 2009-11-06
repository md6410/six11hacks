#!/bin/bash
V=`date +%Y-%m-%d`
T=flatcad-$V.tgz
Z=flatcad-$V.zip

cd build
rm -fr flatcad-$V $T $Z
cp -r dist flatcad-$V
tar cfz $T flatcad-$V
zip -r $Z flatcad-$V
scp $T $Z root@flatcad.org:demo

echo "Your distribution is in build/$T and build/$Z"

