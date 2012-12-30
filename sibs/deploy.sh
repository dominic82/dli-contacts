#!/bin/bash

mvn clean package && cp ./target/sib-collection-*-jar-with-dependencies.jar ../jabc-projekte/sib-collection.jar
