#!/usr/bin/env bash
mkdir -p bin
javac -d bin -sourcepath src $(find src -name "*.java")
if [ $? -eq 0 ]; then
    echo "================================================="
    echo " Build Successful! Compiled into ./bin           "
    echo " Run './run.sh' to start console route planner   "
    echo " Run './run_gui.sh' to start Java Swing GUI      "
    echo " Run './run_applet.sh' to launch Applet demo     "
    echo "================================================="
else
    echo "Build Failed!"
    exit 1
fi
