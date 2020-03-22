#!/bin/bash
set -ev
if [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    if [ "$TRAVIS_BRANCH" == 'master' ]; then
        echo "Run mvn deploy... for the master branch"
        mvn deploy -P sign,build-extras --settings mvnsettings.xml
    elif [[ $TRAVIS_BRANCH = "v"* ]]; then
        echo "Run mvn deploy... for the release branch"
        mvn deploy -P sign,build-extras --settings mvnsettings.xml
    else
        echo "The current branch is neither master nor v* - no deploy"
    fi
else
    echo "The current branch looks like a pull request - no deploy"
fi
