#!/bin/bash

echo "Scale by (0=pixels, 1=factor)?"
read -r MODE

if [[ $MODE -eq 0 ]]; then
  echo "Image size in pixels (x=y)?"
  read -r IMGSIZE
elif [[ $MODE -eq 1 ]]; then
  echo "Scale factor?"
  read -r IMGSCALEFAC
else
  echo "Unknown mode. Exiting."
  exit 100
fi

if [[ $IMGSIZE -gt 4096 ]]; then
  echo "Max image size is 4096*4096. Exiting."
  exit 100
fi

echo "Source dir?"
read -r SRCDIR

if [[ ! -d $SRCDIR ]]; then
  echo "Source dir $SRCDIR doesn't exist! Exiting."
  exit 100
fi

echo "Target dir?"
read -r DSTDIR

if [[ ! -d $DSTDIR ]]; then
  echo "Target dir $DSTDIR doesn't exist! Creating."
  mkdir -p "$DSTDIR"
fi

for FILE in "$SRCDIR"/*; do
  FCLEAN=${FILE##*/}
  FCLEAN=${FCLEAN%.png}
  if [[ $MODE -eq 0 ]]; then
    FNAME="$DSTDIR"/"$FCLEAN"@"$IMGSIZE"x"$IMGSIZE".png
    echo "Processing $FILE. -> $FNAME"
    ffmpeg -i "$FILE" -vf scale="$IMGSIZE":"$IMGSIZE":flags=neighbor "$FNAME"
  elif [[ $MODE -eq 1 ]]; then
    FNAME="$DSTDIR"/"$FCLEAN"@"$IMGSCALEFAC"x.png
    echo "Processing $FILE. -> $FNAME"
    ffmpeg -i "$FILE" -vf scale=iw*"$IMGSCALEFAC":ih*"$IMGSCALEFAC":flags=neighbor "$FNAME"
  fi
done
