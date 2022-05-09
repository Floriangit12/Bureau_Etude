#!/bin/bash

java --version
git clone https://github.com/Floriangit12/Bureau_Etude
cd Bureau_Etude
mkdir Compilation
wget https://download2.gluonhq.com/openjfx/18.0.1/openjfx-18.0.1_linux-x64_bin-sdk.zip
unzip openjfx-18.0.1_osx-x64_bin-sdk.zip
rm openjfx-18.0.1_osx-x64_bin-sdk.zip
mv javafx-sdk-18.0.1 javafx-sdk_Linux-18.0.1
current_work="$(pwd)"
JAVAFX_Dir="$current_work/JAVAFX/javafx-sdk_Linux-18.0.1/lib"
javac -d Compilation --module-path "$JAVAFX_Dir" --add-modules javafx.controls,javafx.fxml Sphere3D.java
cd Compilation;
java -Djavafx.platform=eglfb -Dprism.verbose=true -Dcom.sun.javafx.experimental.embedded.3d=true  -Dprism.glDepthSize=24 -Dprism.forceGPU=true  --module-path "$JAVAFX_Dir" --add-modules javafx.controls,javafx.fxml Sphere3D
