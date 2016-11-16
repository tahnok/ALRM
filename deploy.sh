#!/bin/bash
set -e

echo "Please enter the keystore password:"
read -s KEYSTORE_PASSWORD
echo

export KEYSTORE_PASSWORD
./gradlew publishApkRelease
