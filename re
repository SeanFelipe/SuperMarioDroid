#!/bin/bash
#adb shell am instrument -w -r   -e debug false -e class 'srg.pmd.MarioSpec01#marioSpec01' srg.pmd.test/android.support.test.runner.AndroidJUnitRunner
#adb shell am instrument -w -r   -e debug false -e class "srg.pmd.MarioSpec01#$1" srg.pmd.test/android.support.test.runner.AndroidJUnitRunner

#adb shell am instrument -w -r   -e debug false -e class srg.pmd.MarioSpec02#$1 srg.pmd.test/android.support.test.runner.AndroidJUnitRunner
#adb shell am instrument -w -r   -e debug false -e class 'srg.pmd.MarioSpec03#checkPositionFail' srg.pmd.test/android.support.test.runner.AndroidJUnitRunner
adb shell am instrument -w -r   -e debug false -e class 'srg.pmd.MarioSpec01#jumpingMario' srg.pmd.test/android.support.test.runner.AndroidJUnitRunner


