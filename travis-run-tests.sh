#!/bin/sh

mvn test -B
mvn test -B -P gui-tests
