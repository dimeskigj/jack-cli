# Jack 🛠️

A versatile CLI tool for common developer tasks.

## Usage

### UUID Generation
Generate one or more UUIDs or ULIDs.
```bash
jack uuid
jack uuid --count 5 --type ULID
```

### Lorem Ipsum
Generate placeholder text.
```bash
jack lorem --count 50
```

### QR Codes
Generate a QR code image from text or a URL.
```bash
jack qr "https://github.com" --output github.png
jack qr "Hello World" --foregroundColor FF0000 --backgroundColor FFFFFF
```

### Hashing
Compute hashes for strings or files.
```bash
jack hash "my secret string" --algorithm SHA256
jack hash --file path/to/file.txt --algorithm MD5
```

### Timestamps
Get the current Unix timestamp.
```bash
jack timestamp
jack timestamp --unit MILLISECONDS
```

### JWT Decoding
Decode and pretty print a JWT token.
```bash
jack jwt "your.jwt.token"
jack jwt "your.jwt.token" --secret "your-secret"
```

### JSON Formatting
Verify and pretty print JSON input.
```bash
jack json '{"name":"jack","version":1}'
jack json --file data.json --indent 2
cat data.json | jack json
```

## Installation

### Quick Install (Recommended)

**Linux / macOS:**
```bash
curl -fsSL https://raw.githubusercontent.com/dimeskigj/jack-cli/main/scripts/install.sh | bash
```

**Windows (PowerShell):**
```powershell
iwr https://raw.githubusercontent.com/dimeskigj/jack-cli/main/scripts/install.ps1 -useb | iex
```

### From Source
1. Clone the repository:
   ```bash
   git clone https://github.com/dimeskigj/jack-cli.git
   cd jack-cli
   ```
2. Build and install:
   ```bash
   ./gradlew installDist
   ```
3. Add the binary to your `PATH`. The binary is located at `build/install/jack/bin`.

### Native Image
To build a standalone executable that doesn't require Java installed:
```bash
./gradlew nativeCompile
```
The executable will be located in `build/native/nativeCompile`.

### From Releases
Download the latest archive from the [releases page](https://github.com/dimeskigj/jack-cli/releases), extract it, and add the `bin` folder to your `PATH`.

## Features
- **UUID/ULID**: Bulk generation of unique identifiers.
- **Lorem Ipsum**: Customizable placeholder text.
- **QR Codes**: PNG generation with custom colors.
- **Hashing**: MD5, SHA1, SHA256, SHA512 support.
- **Timestamps**: Seconds or milliseconds.
- **JWT Decoding**: Pretty print header and payload with signature verification.
- **JSON Formatting**: Verify and pretty print JSON with custom indentation.

## License
MIT
