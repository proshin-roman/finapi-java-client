[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/proshin-roman/finapi-java-client.svg?branch=master)](https://travis-ci.org/proshin-roman/finapi-java-client)
[![Build status](https://ci.appveyor.com/api/projects/status/n88wydxq8oa0eou0/branch/master?svg=true)](https://ci.appveyor.com/project/proshin-roman/finapi-java-client/branch/master)
[![codecov](https://codecov.io/gh/proshin-roman/finapi-java-client/branch/master/graph/badge.svg)](https://codecov.io/gh/proshin-roman/finapi-java-client)


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
    - [x] Get tokens
    - [ ] Revoke a token
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
    - [ ] Request password change
    - [ ] Execute password change
    - [ ] Verify a user
    - [ ] Edit the authorized user
    - [x] Delete the authorized user
    - [ ] Delete an unverified user 
- Banks
    - [ ] Get a bank
    - [ ] ~~Get a multiple banks~~ (won't be implemented as deprecated)
    - [ ] Get and search all banks 
- Bank connections
    - [ ] Get a bank connection
    - [ ] ~~Get multiple bank connections~~ (won't be implemented as deprecated)
    - [ ] Get all bank connections
    - [ ] Import a new bank connection
    - [ ] Update a bank connection
    - [ ] Edit a bank connection
    - [ ] Delete a bank connection
    - [ ] Delete all bank connections
- Accounts
    - [ ] Get an account
    - [ ] ~~Get multiple accounts~~ (won't be implemented as deprecated)
    - [ ] Get and search all accounts
    - [ ] Get daily balances
    - [ ] Edit an account
    - [ ] Request SEPA Money Transfer
    - [ ] Execute SEPA Money Transfer
    - [ ] Request SEPA Direct Debit
    - [ ] Execute SEPA Direct Debit
    - [ ] Delete an account
    - [ ] Delete all accounts
- Transactions
    - [ ] Get a transaction
    - [ ] ~~Get multiple transactions~~ (won't be implemented as deprecated)
    - [ ] Get and search all transactions
    - [ ] Split a transaction
    - [ ] Restore a transaction
    - [ ] Edit a transaction
    - [ ] ~~Edit multiple transactions (DEPRECATED)~~ (won't be implemented as deprecated)
    - [ ] Edit multiple transactions
    - [ ] Trigger categorization
    - [ ] Delete a transaction
    - [ ] Delete all transactions
- Securities
    - [ ] Get a security
    - [ ] ~~Get multiple securities~~ (won't be implemented as deprecated)
    - [ ] Get and search all securities
- Categories
    - [ ] Get a category
    - [ ] ~~Get multiple categories~~ (won't be implemented as deprecated)
    - [ ] Get and search all categories
    - [ ] Get cash flows
    - [ ] Create a new category
    - [ ] Train categorization 
    - [ ] Delete a category
    - [ ] Delete all categories
- Labels
    - [ ] Get a label
    - [ ] ~~Get multiple labels~~ (won't be implemented as deprecated)
    - [ ] Get and search all labels
    - [ ] Create a new label
    - [ ] Edit a label
    - [ ] Delete a label
    - [ ] Delete all labels
- Notification rules
    - [ ] Get a notification rule
    - [ ] Get and search all notification rules
    - [ ] Create a new notification rule
    - [ ] Delete a notification rule
    - [ ] Delete all notification rules
- Web forms
    - [ ] Get a web form
- Mocks and tests
    - [ ] Mock batch update
    - [ ] Check categorization

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
