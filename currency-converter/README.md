# Currency Converter Data Source
This data source gets currency symbols from a public API and then allows the user to get a conversion based of an initial value and the symbols selected.

## Building as Eclipse Project
Before building the module for the first time please setup your settings.xml file as described in the root of this repository.

1. On the pom.xml click Run as > Maven build...
2. The goal is install
3. The profile is install-module

## Folder structure
- src - the java source
- web-src - the web source
- web-src/currencyConverter.js - main UI entry point (must have same name as 'name' in pom.xml
- classes - the i18n translations and any files required on the classpath
- api-test - api node.js tests to run against the REST api
- module.properties - module information for Mango load on startup (template to be filled in at build time)
- pom.xml - define dependencies and build executions
- licenseTypes.xml - licensing (not required)
- package.json - define UI dependencies and info
- webpack.config.js - 

## Java package structure
com.infiniteautomation.mango.currencyConverter - the root package with the module element definitions.

com.infiniteautomation.mango.currencyConverter.vo - the configuration classes that are used to store info in the database

com.infiniteautomation.mango.currencyConverter.rt - the runtime classes


