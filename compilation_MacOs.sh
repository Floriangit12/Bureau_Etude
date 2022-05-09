#!/bin/bash

java --version
mkdir 
git clone https://github.com/Floriangit12/Bureau_Etude
mkdir Compilation;
root = pwd
javac -d Compilation --module-path ${root}/javafx-sdk-18.0.1/lib --add-modules javafx.controls,javafx.fxml Sphere3D.java;
cd Compilation/;
java -Djavafx.platform=eglfb -Dprism.verbose=true -Dcom.sun.javafx.experimental.embedded.3d=true  -Dprism.glDepthSize=24 -Dprism.forceGPU=true  --module-path ${root}/javafx-sdk-18.0.1/lib --add-modules javafx.controls,javafx.fxml Sphere3D