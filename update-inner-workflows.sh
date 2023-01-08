#! /usr/bin/env bash
# exit when any command fails
set -e

echo "Hijacking giter8.test"
cat << EOF > project/giter8.test
> githubWorkflowGenerate
$ exec cp .github/workflows/ci.yml $(pwd)/src/main/g8/.github/workflows/ci.yml
EOF

echo "Running hijacked giter8.test to copy new workflow"
sbt g8Test

echo "Restoring original giter8.test"
git restore project/giter8.test

echo "Running original giter8.test"
sbt g8Test

echo "inner github workflows have been regenerated"
git status
