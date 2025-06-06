# Jack 🛠️ - Jack of all trades CLI for developer tools

Jack is a versatile command-line tool that provides handy developer utilities all in one place. From UUID generation to
QR codes and dummy text, jack aims to be your swiss knife 🗡️ for everyday development tasks.

## Features ✨

- Generate random UUIDs 🆔
- Create lorem ipsum dummy text 📄
- Generate QR codes as PNG images 📱
- (More features coming soon: hashing 🔐, base64 encoding 🧩, time formatting ⏰, calculator ➕➖, color conversions 🎨, JSON
  formatting 📦, file info 🗂️, random passwords 🔑, IP lookup 📍, and more!)

## Installation 🚀

1. Clone the repository:

   ```bash
   git clone https://github.com/dimeskigj/jack-cli.git
   cd jack-cli
   ```

2. Build and install:

   ```bash
   ./gradlew installDist
   ```

3. Add the binaries to your PATH environment variable. The binaries are located at:

   ```
   /jack-cli/build/install/jack/bin
   ```

   For example, on macOS/Linux, you can add this line to your `.bashrc`, `.zshrc`, or equivalent:

   ```bash
   export PATH="$PATH:/path/to/jack-cli/build/install/jack/bin"
   ```

   On Windows, add the directory to your system PATH via Environment Variables settings.

## Contributing 🤝

Contributions, suggestions, and feature requests are welcome! Feel free to open issues or submit pull requests.

## License 📜

MIT License
