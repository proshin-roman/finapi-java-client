⚠️ Development and maintenance of this project is over. Thank you all who were using it and even contributing into it! 

Feel free to fork it and continue the project under your name. If you decide to do so - write me an email and I will put a link to your repo here!

---

[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

<a href="https://www.yegor256.com/2018/09/30/award-2019.html">
<img src="https://www.yegor256.com/images/award/2019/winner-proshin-roman.png" height="45" alt='winner'/>
</a>
<br/>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

### Releases

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.proshin/finapi-java-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.proshin/finapi-java-client)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fproshin-roman%2Ffinapi-java-client.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fproshin-roman%2Ffinapi-java-client?ref=badge_shield)
[![](https://jitpack.io/v/proshin-roman/finapi-java-client.svg)](https://jitpack.io/#proshin-roman/finapi-java-client)

### Builds

[![Build Status](https://travis-ci.com/proshin-roman/finapi-java-client.svg?branch=master)](https://travis-ci.com/proshin-roman/finapi-java-client)
[![Build status](https://ci.appveyor.com/api/projects/status/n88wydxq8oa0eou0/branch/master?svg=true)](https://ci.appveyor.com/project/proshin-roman/finapi-java-client/branch/master)

### Code quality

[![codecov](https://codecov.io/gh/proshin-roman/finapi-java-client/branch/master/graph/badge.svg)](https://codecov.io/gh/proshin-roman/finapi-java-client)
[![Coverage Status](https://coveralls.io/repos/github/proshin-roman/finapi-java-client/badge.svg?branch=coverall-integration)](https://coveralls.io/github/proshin-roman/finapi-java-client?branch=master)
[![codebeat badge](https://codebeat.co/badges/907cb4b8-0f65-446a-a874-9ece228ab579)](https://codebeat.co/projects/github-com-proshin-roman-finapi-java-client-master)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=org.proshin%3Afinapi-java-client&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.proshin%3Afinapi-java-client)
[![CodeFactor](https://www.codefactor.io/repository/github/proshin-roman/finapi-java-client/badge)](https://www.codefactor.io/repository/github/proshin-roman/finapi-java-client)
[![Hits-of-Code](https://hitsofcode.com/github/proshin-roman/finapi-java-client)](https://hitsofcode.com/view/github/proshin-roman/finapi-java-client)

### Puzzle Driven Development

[![PDD status](http://www.0pdd.com/svg?name=proshin-roman/finapi-java-client)](http://www.0pdd.com/p?name=proshin-roman/finapi-java-client)

### Community

[![Join the chat at https://gitter.im/finapi-java-client/Lobby](https://badges.gitter.im/finapi-java-client/Lobby.svg)](https://gitter.im/finapi-java-client/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

**finapi-java-client** is a true-OOP Java client for API provided by [finAPI](https://finapi.io).

## Principles

The project is following the next principles (it is a subset of [those ones](https://www.elegantobjects.org/#principles)):
- No null
  ![nullfree status](https://iwillfailyou.com/nullfree/proshin-roman/finapi-java-client)
- No getters and setters
  ![setterfree status](https://iwillfailyou.com/setterfree/proshin-roman/finapi-java-client)
- No mutable objects
  ![allfinal status](https://iwillfailyou.com/allfinal/proshin-roman/finapi-java-client)
- No static methods, not even private ones
  ![staticfree status](https://iwillfailyou.com/staticfree/proshin-roman/finapi-java-client)
  ![allpublic status](https://iwillfailyou.com/allpublic/proshin-roman/finapi-java-client)
- No instanceof, type casting or reflection
- No implementation inheritance
  ![inheritancefree status](https://iwillfailyou.com/inheritancefree/proshin-roman/finapi-java-client)
- No multiple returns
  ![nomultiplereturn status](https://iwillfailyou.com/nomultiplereturn/proshin-roman/finapi-java-client)

All badges are through [iwillfailyou](https://iwillfailyou.com/)

## How to use

Just add the following lines into your `pom.xml`

```!xml
<dependency>
  <groupId>org.proshin</groupId>
  <artifactId>finapi-java-client</artifactId>
  <version>${version}</version>
</dependency>
```

You can find the latest `version` on [`Releases`](https://github.com/proshin-roman/finapi-java-client/releases) page.

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

## How to release

Push a new **annotated** tag. Here is an example of the command:

```
git tag -a v0.1.107 -m "Release v0.1.107" 53901d
```

Versioning is done using [jgitver](https://github.com/jgitver/jgitver) tool, so it will use the new tag as a base of
the version.

## License

Copyright 2018-2019 Roman Proshin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fproshin-roman%2Ffinapi-java-client.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fproshin-roman%2Ffinapi-java-client?ref=badge_large)
