#!/bin/bash

set -e

REPO="dimeskigj/jack-cli"
INSTALL_DIR="$HOME/.jack"
BIN_DIR="$INSTALL_DIR/bin"
EXE_PATH="$BIN_DIR/jack"

echo "Installing Jack CLI..."

# 1. Determine latest version
LATEST_TAG=$(curl -s "https://api.github.com/repos/$REPO/releases/latest" | grep '"tag_name":' | sed -E 's/.*"([^"]+)".*/\1/')

if [ -z "$LATEST_TAG" ]; then
    echo "Error: Could not find latest release."
    exit 1
fi

echo "Latest version: $LATEST_TAG"

# 2. Determine OS and Asset Name
OS="$(uname -s)"
case "${OS}" in
    Linux*)     ASSET_NAME="jack-linux-x64";;
    Darwin*)    ASSET_NAME="jack-macos-x64";;
    *)          echo "Unsupported OS: ${OS}"; exit 1;;
esac

# 3. Prepare install directory
mkdir -p "$BIN_DIR"

# 4. Download Native Binary
ASSET_URL="https://github.com/$REPO/releases/download/$LATEST_TAG/$ASSET_NAME"

echo "Downloading $ASSET_URL..."
curl -L -o "$EXE_PATH" "$ASSET_URL"

# 5. Make executable
chmod +x "$EXE_PATH"

# 6. Add to PATH (Shell specific)
SHELL_CONFIG=""
case "$SHELL" in
  */zsh) SHELL_CONFIG="$HOME/.zshrc" ;;
  */bash) SHELL_CONFIG="$HOME/.bashrc" ;;
  *) SHELL_CONFIG="$HOME/.profile" ;;
esac

if ! grep -q "$BIN_DIR" "$SHELL_CONFIG"; then
    echo "" >> "$SHELL_CONFIG"
    echo "# Jack CLI" >> "$SHELL_CONFIG"
    echo "export PATH=\"\$PATH:$BIN_DIR\"" >> "$SHELL_CONFIG"
    echo "Added $BIN_DIR to $SHELL_CONFIG"
else
    echo "Path already configured in $SHELL_CONFIG"
fi

echo "✅ Installation complete! Restart your terminal or run 'source $SHELL_CONFIG' to start using jack."
echo "Try running: jack --version"
