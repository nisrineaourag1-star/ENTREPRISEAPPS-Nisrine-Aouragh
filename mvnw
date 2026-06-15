#!/bin/sh
# Maven wrapper script
set -e

MAVEN_WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

# Read the distributionUrl from properties
DISTRIBUTION_URL=$(grep '^distributionUrl=' "$MAVEN_WRAPPER_PROPERTIES" | sed 's/distributionUrl=//')

# Determine the Maven home
MAVEN_USER_HOME="${MAVEN_USER_HOME:-${HOME}/.m2}"
MAVEN_WRAPPER_HOME="${MAVEN_USER_HOME}/wrapper/dists/$(basename $DISTRIBUTION_URL .zip)"

if [ ! -d "$MAVEN_WRAPPER_HOME" ]; then
    echo "Downloading Maven from $DISTRIBUTION_URL"
    mkdir -p "$MAVEN_WRAPPER_HOME"
    TEMP_FILE=$(mktemp)
    curl -fsSL "$DISTRIBUTION_URL" -o "$TEMP_FILE"
    unzip -q "$TEMP_FILE" -d "$MAVEN_WRAPPER_HOME"
    rm "$TEMP_FILE"
fi

MAVEN_HOME=$(find "$MAVEN_WRAPPER_HOME" -maxdepth 1 -type d -name 'apache-maven-*' | head -1)
exec "$MAVEN_HOME/bin/mvn" "$@"
