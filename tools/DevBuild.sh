#!/bin/sh

export SecEncodedKeystore="$(cat ../Test.jks | base64 -w0)"
export SecKeystorePassword="123456"

sh ./tools/Build.sh
