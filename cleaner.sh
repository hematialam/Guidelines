#!/bin/bash
# Conservatively clean some of the garbage bullets
# Otherwise they show up as the letter c
sed -r -i "s/(\W)c(\W)/\1•\2/g" ADA_guidelines.xml

# Clean the name of the journal, which displays a dash as the letter d
sed -i 's/Diabetesd2013/Diabetes: 2013/g' ADA_guidelines.xml

# Replace another ridiculous faux bullet
sed -i 's/myBullet/•/g' ADA_guidelines.xml
