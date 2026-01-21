Output docs/img/cover.gif

Set Theme "Catppuccin Mocha"
Set Shell "pwsh"
Set FontSize 24
Set Width 1400
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

Type "echo 'JACK - The Developer Swiss Army Knife'"
Sleep 300ms
Enter
Sleep 800ms
Type "jack --help"
Sleep 300ms
Enter
Sleep 2.5s

Hide
Type "clear"
Enter
Show

Type "# Generate UUIDs and pipe to other tools"
Sleep 400ms
Enter
Type "jack uuid generate -c 5 | nl"
Sleep 300ms
Enter
Sleep 1.5s

Hide
Type "clear"
Enter
Show

Type "# Hash strings or piped data"
Sleep 400ms
Enter
Type "echo 'secret-api-key' | jack hash -a SHA256"
Sleep 300ms
Enter
Sleep 1.5s

Hide
Type "clear"
Enter
Show

Type "# Extract nested data from APIs"
Sleep 400ms
Enter
Type "curl -s 'https://dogapi.dog/api/v2/breeds' | jack json -q '.data[0].attributes.description'"
Sleep 300ms
Enter
Sleep 2s

Hide
Type "clear"
Enter
Show

Type "# Decode JWT tokens instantly"
Sleep 400ms
Enter
Type@1ms "jack jwt 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkphY2sgVXNlciIsImFkbWluIjp0cnVlLCJpYXQiOjE3MzczNzUyMDB9.placeholder'"
Sleep 300ms
Enter
Sleep 2.5s

Hide
Type "clear"
Enter
Show

Type "# Unix timestamps for scripts"
Sleep 400ms
Enter
Type 'echo "Build started at: $(jack timestamp --unit MILLISECONDS)"'
Sleep 300ms
Enter
Sleep 1.5s

Hide
Type "clear"
Enter
Show

Type "# Generate QR codes"
Sleep 400ms
Enter
Type "jack qr 'https://github.com/dimeskigj/jack-cli' -o /tmp/repo.png && echo 'QR saved!'"
Sleep 300ms
Enter
Sleep 1.5s

Hide
Type "clear"
Enter
Show

Type "# Generate placeholder text"
Sleep 400ms
Enter
Type "jack lorem -c 20 | fold -w 60 | head -3"
Sleep 300ms
Enter
Sleep 2s
