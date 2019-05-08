# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.70] - 2019-05-08
### Added
- [#191](https://github.com/proshin-roman/finapi-java-client/issues/191) - The library now supports finAPI v1.70.0
- [#34](https://github.com/proshin-roman/finapi-java-client/issues/34), 
[#41](https://github.com/proshin-roman/finapi-java-client/issues/41), 
[#42](https://github.com/proshin-roman/finapi-java-client/issues/42), 
[#47](https://github.com/proshin-roman/finapi-java-client/issues/47), 
[#74](https://github.com/proshin-roman/finapi-java-client/issues/74),
[#81](https://github.com/proshin-roman/finapi-java-client/issues/81),
[#109](https://github.com/proshin-roman/finapi-java-client/issues/109),
[#143](https://github.com/proshin-roman/finapi-java-client/issues/143) - Improved test coverage

### Changed
- [#195](https://github.com/proshin-roman/finapi-java-client/issues/195) - README.md has been updated

## [0.1.69] - 2019-04-22
### Added
- [#178](https://github.com/proshin-roman/finapi-java-client/issues/178) - The library now supports finAPI v1.69.0
- [#22](https://github.com/proshin-roman/finapi-java-client/issues/22), 
[#23](https://github.com/proshin-roman/finapi-java-client/issues/23), 
[#24](https://github.com/proshin-roman/finapi-java-client/issues/24), 
[#176](https://github.com/proshin-roman/finapi-java-client/issues/176), 
[#177](https://github.com/proshin-roman/finapi-java-client/issues/177) - Improved test coverage

### Changed
- [#182](https://github.com/proshin-roman/finapi-java-client/issues/182) - Some library methods use now LocalDate 
instead of OffsetDateTime for input parameters (where it's possible for the REST API)

### Fixed
- [#174](https://github.com/proshin-roman/finapi-java-client/issues/174) - Improved performance of the unit tests

## [0.1.68] - 2019-04-21
### Added
- [#164](https://github.com/proshin-roman/finapi-java-client/issues/164) - The library now supports finAPI v1.68.0
- [#63](https://github.com/proshin-roman/finapi-java-client/issues/63), 
[#21](https://github.com/proshin-roman/finapi-java-client/issues/21) - Improved test coverage

## [0.1.67] - 2019-03-21
### Added
- [#155](https://github.com/proshin-roman/finapi-java-client/issues/155) - The library now supports finAPI v1.67.0

### Changed
- [#19](https://github.com/proshin-roman/finapi-java-client/issues/19) - All "Users" endpoints now covered by tests

## [0.1.66] - 2019-02-27
### Added
- [#149](https://github.com/proshin-roman/finapi-java-client/issues/149) - The library now supports finAPI v1.66.0

## [0.1.65] - 2019-02-16
### Added
- [#145](https://github.com/proshin-roman/finapi-java-client/issues/145) - The library now supports finAPI v1.65.0

## [0.1.64] - 2019-02-16
### Added
- [#130](https://github.com/proshin-roman/finapi-java-client/issues/130) - The library now supports finAPI v1.64.0

### Fixed
- [#138](https://github.com/proshin-roman/finapi-java-client/issues/138), 
[#139](https://github.com/proshin-roman/finapi-java-client/issues/139) - Improved test coverage

## [0.1.63] - 2019-02-16
### Added
- [#128](https://github.com/proshin-roman/finapi-java-client/issues/128) - The library now supports finAPI v1.63.0

## [0.1.62] - 2019-02-16
### Added
- [#96](https://github.com/proshin-roman/finapi-java-client/issues/96) - The library now supports finAPI v1.62.0

## [0.1.61] - 2019-02-16
### Added
- [#95](https://github.com/proshin-roman/finapi-java-client/issues/95) - The library now supports finAPI v1.61.0

### Fixed
- [#115](https://github.com/proshin-roman/finapi-java-client/issues/115) - 
Method "Bank connections / Get all bank connections" now parses a response from the API correctly
- [#122](https://github.com/proshin-roman/finapi-java-client/issues/122) - 
Method "Bank connections / Get all bank connections" now uses the correct path for communicating to the API
- [#46](https://github.com/proshin-roman/finapi-java-client/issues/46) - Improved test coverage

## [0.1.60] - 2019-01-23
### Added
- [#94](https://github.com/proshin-roman/finapi-java-client/issues/94) - The library now supports finAPI v1.60.0
- [#104](https://github.com/proshin-roman/finapi-java-client/issues/104) - The repo now contains a history of changes

### Fixed
- [#100](https://github.com/proshin-roman/finapi-java-client/issues/100) - Parameter `userId` is not sent for a method 
"Users / Get a user's verification status"
- [#36](https://github.com/proshin-roman/finapi-java-client/issues/36) - Method `next` of `IterableJsonArray` doesn't 
throw `NoSuchElementException`
- [#43](https://github.com/proshin-roman/finapi-java-client/issues/43), 
[#69](https://github.com/proshin-roman/finapi-java-client/issues/69) - Improved test coverage
- [#98](https://github.com/proshin-roman/finapi-java-client/issues/98) - Maven release plugin doesn't work due to 
dependencies issue

## [0.1.59] - 2019-01-08
### Added
- [#73](https://github.com/proshin-roman/finapi-java-client/issues/73) - The library now supports finAPI v1.59.0

[Unreleased]: https://github.com/proshin-roman/finapi-java-client/compare/v0.1.70...HEAD
[0.1.70]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.70
[0.1.69]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.69
[0.1.68]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.68
[0.1.67]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.67
[0.1.66]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.66
[0.1.65]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.65
[0.1.64]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.64
[0.1.63]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.63
[0.1.62]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.62
[0.1.61]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.61
[0.1.60]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.60
[0.1.59]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.59
