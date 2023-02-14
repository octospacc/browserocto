#!/bin/sh

rm -rf ./build || true
cp -r ./app/src/main ./build
cd ./build
mv ./java ./src
cp -r ../tools/tiny-android-template/* ./
echo "${SecEncodedKeystore}" | base64 --decode > ./Keystore.jks

perl ./link.pl
bash ./make.sh
