#!/bin/bash
# Generate GIFs from VHS tape files

mkdir -p docs/img

echo "Processing docs/vhs/cover.tape..."
vhs < "docs/vhs/cover.tape"

