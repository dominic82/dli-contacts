#!/bin/bash

mvn clean assembly:assembly && cp ./target/sib-collection-*-jar-with-dependencies.jar ../jabc-projekte/sib-collection.jar
