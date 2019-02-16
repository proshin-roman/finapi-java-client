# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
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

[Unreleased]: https://github.com/proshin-roman/finapi-java-client/compare/v0.1.62...HEAD
[0.1.62]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.62
[0.1.61]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.61
[0.1.60]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.60
[0.1.59]: https://github.com/proshin-roman/finapi-java-client/releases/tag/v0.1.59
