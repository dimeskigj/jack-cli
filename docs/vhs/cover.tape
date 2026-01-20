Output docs/img/cover.gif

Set Theme "Catppuccin Mocha"
Set Shell "pwsh"
Set FontSize 32
Set Width 1200
Set Height 900
Set Padding 40
Set WindowBar Colorful
Set WindowBarSize 60
Set Margin 40
Set MarginFill "#8839ef"
Set BorderRadius 20

Hide
Type "clear"
Enter
Show

Type "# Jack - The developer's Swiss Army Knife"
Sleep 1s
Enter
Type "jack --help"
Sleep 500ms
Enter
Sleep 2s

Hide
Type "clear"
Enter
Show

Type "# Generate and pipe UUIDs to other tools"
Sleep 500ms
Enter
Type "jack uuid generate --count 5 --type ULID | jack hash -a SHA256"
Sleep 500ms
Enter
Sleep 2s

Hide
Type "clear"
Enter
Show

Type "# Query, format, and minify JSON from APIs"
Sleep 500ms
Enter
Type "curl -s https://dogapi.dog/api/v2/breeds | jack json -q '.data[].attributes.name'"
Sleep 500ms
Enter
Sleep 2s

Hide
Type "clear"
Enter
Show

Type "# Get nested objects from APIs"
Sleep 500ms
Enter
Type "curl -s https://dogapi.dog/api/v2/breeds | jack json -q '.data[0].attributes.life'"
Sleep 500ms
Enter
Sleep 2s

Hide
Type "clear"
Enter
Show

Type "# Decode JWT tokens on the fly"
Sleep 500ms
Enter
Type "jack jwt "
Sleep 200ms
Type@1ms "'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'"
Sleep 500ms
Enter
Sleep 3s

Hide
Type "clear"
Enter
Show

Type "# And much more..."
Sleep 500ms
Enter
Type "jack lorem --count 10"
Sleep 500ms
Enter
Sleep 3s
