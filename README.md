[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/proshin-roman/finapi-java-client.svg?branch=master)](https://travis-ci.org/proshin-roman/finapi-java-client)
[![Build status](https://ci.appveyor.com/api/projects/status/n88wydxq8oa0eou0/branch/master?svg=true)](https://ci.appveyor.com/project/proshin-roman/finapi-java-client/branch/master)
[![codecov](https://codecov.io/gh/proshin-roman/finapi-java-client/branch/master/graph/badge.svg)](https://codecov.io/gh/proshin-roman/finapi-java-client)
[![codebeat badge](https://codebeat.co/badges/907cb4b8-0f65-446a-a874-9ece228ab579)](https://codebeat.co/projects/github-com-proshin-roman-finapi-java-client-master)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=org.proshin%3Afinapi-java-client&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.proshin%3Afinapi-java-client)
[![Report](https://inspecode.rocro.com/badges/github.com/proshin-roman/finapi-java-client/report?token=XQDM-T86oDab_Cxht2zu7R2Id6M09Uobn6izEWhA8es&branch=master)](https://inspecode.rocro.com/reports/github.com/proshin-roman/finapi-java-client/branch/master/summary)
[![PDD status](http://www.0pdd.com/svg?name=proshin-roman/finapi-java-client)](http://www.0pdd.com/p?name=proshin-roman/finapi-java-client) [![Join the chat at https://gitter.im/finapi-java-client/Lobby](https://badges.gitter.im/finapi-java-client/Lobby.svg)](https://gitter.im/finapi-java-client/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)


**finapi-java-client** is a true-OOP Java client for API provided by [finAPI](https://finapi.io).

## Principles

The project is following the next principles (it is a subset of [those ones](https://www.elegantobjects.org/#principles)):
- No null
- No getters and setters
- No mutable objects
- No static methods, not even private ones
- No instanceof, type casting or reflection
- No public methods without contract (interface)
- No implementation inheritance

## How to use

[TO BE DONE] The library will be published in Maven Central repository.

## Implementation status

This library is in early alpha version and not all API methods are implemented yet. This is a status of implementation:

- Authorization
    - [x] Get client's token
    - [x] Get user's token using credentials
    - [ ] Get user's token using refresh token
    - [x] Revoke a token
- Mandator administration
    - [ ] Get user list
    - [ ] Delete users
    - [ ] Change client credentials
- Client configuration
    - [x] Get client configuration
    - [ ] Edit client configuration
- Users
    - [ ] Get the authorized user
    - [x] Get a user's verification status
    - [x] Create a new user
    - [x] Request password change
    - [x] Execute password change
    - [ ] Verify a user
    - [ ] Edit the authorized user
    - [x] Delete the authorized user
    - [ ] Delete an unverified user 
- Banks
    - [x] Get a bank
    - [ ] ~~Get a multiple banks~~ (won't be implemented as deprecated)
    - [x] Get and search all banks
- Bank connections
    - [x] Get a bank connection
    - [ ] ~~Get multiple bank connections~~ (won't be implemented as deprecated)
    - [x] Get all bank connections
    - [x] Import a new bank connection
    - [x] Update a bank connection
    - [x] Edit a bank connection
    - [x] Delete a bank connection
    - [x] Delete all bank connections
- Accounts
    - [x] Get an account
    - [ ] ~~Get multiple accounts~~ (won't be implemented as deprecated)
    - [x] Get and search all accounts
    - [x] Get daily balances
    - [x] Edit an account
    - [x] Request SEPA Money Transfer
    - [x] Execute SEPA Money Transfer
    - [x] Request SEPA Direct Debit
    - [x] Execute SEPA Direct Debit
    - [x] Delete an account
    - [x] Delete all accounts
- Transactions
    - [x Get a transaction
    - [ ] ~~Get multiple transactions~~ (won't be implemented as deprecated)
    - [x] Get and search all transactions
    - [x] Split a transaction
    - [x] Restore a transaction
    - [x] Edit a transaction
    - [ ] ~~Edit multiple transactions (DEPRECATED)~~ (won't be implemented as deprecated)
    - [x] Edit multiple transactions
    - [x] Trigger categorization
    - [x] Delete a transaction
    - [x] Delete all transactions
- Securities
    - [x] Get a security
    - [ ] ~~Get multiple securities~~ (won't be implemented as deprecated)
    - [x] Get and search all securities
- Categories
    - [x] Get a category
    - [ ] ~~Get multiple categories~~ (won't be implemented as deprecated)
    - [x] Get and search all categories
    - [x] Get cash flows
    - [x] Create a new category
    - [x] Train categorization 
    - [x] Delete a category
    - [x] Delete all categories
- Labels
    - [x] Get a label
    - [ ] ~~Get multiple labels~~ (won't be implemented as deprecated)
    - [x] Get and search all labels
    - [x] Create a new label
    - [x] Edit a label
    - [x] Delete a label
    - [x] Delete all labels
- Notification rules
    - [x] Get a notification rule
    - [x] Get and search all notification rules
    - [x] Create a new notification rule
    - [x] Delete a notification rule
    - [x] Delete all notification rules
- Web forms
    - [x] Get a web form
- Mocks and tests
    - [ ] Mock batch update
    - [ ] Check categorization

## What about logging?

The library uses [Logback](https://logback.qos.ch) for logging. As well you can enable logging of the [Apache HTTP 
client](https://hc.apache.org) library by specifying command line parameters 
(read [this](https://hc.apache.org/httpcomponents-client-ga/logging.html) for more details): 
```
-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
-Dorg.apache.commons.logging.simplelog.showdatetime=true
-Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG
-Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=ERROR
```

## How to contribute

Just fork the repo and send a pull request.

Make sure your branch builds without any warnings/issues:

```
mvn clean install
```

## License
Copyright 2018 Roman Proshin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
