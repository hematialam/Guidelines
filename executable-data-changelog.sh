#!/bin/bash
# Conservatively clean some of the garbage bullets
# Otherwise they show up as the letter c
sed -r -i "s/(\W)c(\W)/\1•\2/g" ADA_guidelines.xml
sed -i -r -e 's/^c(\W)/•\1/g' ADA_guidelines_sentences

# Clean the name of the journal, which displays a dash as the letter d
sed -i 's/Diabetesd2013/Diabetes: 2013/g' ADA_guidelines.xml

# Replace another ridiculous faux bullet
sed -i 's/myBullet/•/g' ADA_guidelines.xml

# At this point it's not XML and we may still want XML
cp ADA_guidelines.xml ADA_guidelines_sentences

# Convert the <s> tags into lines
sed -i -r -e 's|<s i="[0-9]+">||g' ADA_guidelines_sentences
sed -i -r -e 's|</s>|\n|g' ADA_guidelines_sentences

# Remove the two line header and footer
tail -n +2 ADA_guidelines_sentences | head -n -2 >trimmed_ADA
rm ADA_guidelines_sentences
mv trimmed_ADA ADA_guidelines_sentences
