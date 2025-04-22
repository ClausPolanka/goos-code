#!/usr/bin/env bash
set -euo pipefail

# 1) Gradle‑Standardstruktur anlegen
mkdir -p src/main/java src/test/java

# 2) Produktions‑Quellen verschieben
find src -type f -name '*.java' \
     ! -path 'src/main/java/*' \
     ! -path 'src/test/java/*' \
     -print0 \
| while IFS= read -r -d '' file; do
    # Pfad relativ zu src/
    rel="${file#src/}"
    target="src/main/java/$rel"
    mkdir -p "$(dirname "$target")"
    mv "$file" "$target"
done

# 3) Leere Ordner im alten src/ aufräumen
find src -type d -empty \
     ! -path 'src/main/java*' \
     ! -path 'src/test/java*' \
     -delete

# 4) Test‑Quellen verschieben
find test -type f -name '*.java' -print0 \
| while IFS= read -r -d '' file; do
    rel="${file#test/}"
    target="src/test/java/$rel"
    mkdir -p "$(dirname "$target")"
    mv "$file" "$target"
done

# 5) Altes test/ komplett entfernen
rm -rf test

echo "✓ Migration abgeschlossen!"
echo "  Produktions‑Code: src/main/java/…"
echo "  Tests:            src/test/java/…"
