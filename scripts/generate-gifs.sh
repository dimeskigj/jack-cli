#!/bin/bash
# Generate GIFs from VHS tape files

mkdir -p docs/img

for tape in docs/vhs/*.tape; do
    echo "Processing $tape..."
    vhs < "$tape"
done
