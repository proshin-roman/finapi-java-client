#!/bin/bash
set -ev

if [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    if [ "$TRAVIS_BRANCH" == 'master' ]; then
        echo "Prepare GPG key for the master branch"
        openssl aes-256-cbc -K $encrypted_f06771379276_key -iv $encrypted_f06771379276_iv -in codesigning.asc.enc -out codesigning.asc -d
        gpg --fast-import codesigning.asc
    elif [[ $TRAVIS_BRANCH = "v"* ]]; then
        echo "Prepare GPG key for the release branch"
        openssl aes-256-cbc -K $encrypted_f06771379276_key -iv $encrypted_f06771379276_iv -in codesigning.asc.enc -out codesigning.asc -d
        gpg --fast-import codesigning.asc
    else
        echo "The current branch is neither master nor v* - no GPG is required"
    fi
else
    echo "The current branch looks like a pull request - no GPG is required"
fi
