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

You have two options for installing jack:

1. Build from source:

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

2. Download a release build from GitHub:

   1. Find the relase you want from the [release page](https://github.com/dimeskigj/jack-cli/releases/tag/v0.0.2-beta) and download the .zip/.tar archive.

   2. Extract the archive where you want to install the tool.

   3. Follow the third step as above.


## Contributing 🤝

Contributions, suggestions, and feature requests are welcome! Feel free to open issues or submit pull requests.

## License 📜

MIT License
