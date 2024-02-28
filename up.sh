#!/usr/bin/env bash
cd ./src/main/docker/rinhaquarkus-compose
podman rm --all
podman image rm quay.io/mcgoulart/rinhaquarkus:latest
podman-compose up --force-recreate
cd -