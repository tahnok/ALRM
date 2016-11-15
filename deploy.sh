#!/bin/bash
set -e

echo "Please enter the keystore password:"
read -s KEYSTORE_PASSWORD
echo

export KEYSTORE_PASSWORD
./gradlew assembleRelease

echo "apk is at app/build/outputs/apk/app-release.apk"
