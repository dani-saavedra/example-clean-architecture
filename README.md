# example-clean-architecture
Clean architecture example, using Spring framework with N-layer architecture.

This architect aims to show a vision of N-layer architecture, using clean architecture concepts.
Apply concepts from SOLID, Interface Adapters.

## Setup

Requires:
* Java >= 1.8.x
* Docker

## How to Run
The first thing is to run the file makeFile "make mysql.run",  this will generate a docker container a mysql image

## Embedded MySql

The embedded MySql is used only when running the tests. This one is only started in the Gradle test task.

