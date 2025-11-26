#!/usr/bin/env bash
# exit when any command fails
set -e

echo "Syncing Scala versions from build.sbt to default.properties"
SCALA2_VERSION=$(grep 'scala-library' build.sbt | grep -o '"2.13.[0-9]\+"' | tr -d '"')
SCALA3_VERSION=$(grep 'scala3-library_3' build.sbt | grep -o '"3.[0-9]\+.[0-9]\+"' | tr -d '"')

if [ -z "$SCALA2_VERSION" ] || [ -z "$SCALA3_VERSION" ]; then
  echo "Error: Could not extract Scala versions from build.sbt"
  exit 1
fi
echo "Found Scala 2 version: $SCALA2_VERSION"
echo "Found Scala 3 version: $SCALA3_VERSION"

# Update default.properties
sed -i.bak "s/scala_version = [0-9.]*/scala_version = $SCALA2_VERSION/" src/main/g8/default.properties
sed -i.bak "s/other_scala_version = [0-9.]*/other_scala_version = $SCALA3_VERSION/" src/main/g8/default.properties
rm -f src/main/g8/default.properties.bak

echo "Updated src/main/g8/default.properties"

