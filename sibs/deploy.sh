#!/bin/bash

mvn clean && mvn package && cp ./target/*.jar ../jabc-projekte/
