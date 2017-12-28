#!/usr/bin/env bash

mkdir target
javac src/com/example/* -d target
java -cp target com.example.Main