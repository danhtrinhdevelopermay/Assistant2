#!/bin/bash
echo "=== Android Project Validation ==="
echo ""

echo "✓ Checking project structure..."
if [ -f "build.gradle.kts" ] && [ -f "settings.gradle.kts" ] && [ -d "app" ]; then
    echo "  Project structure: OK"
else
    echo "  Project structure: MISSING FILES"
    exit 1
fi

echo "✓ Checking Android manifest..."
if [ -f "app/src/main/AndroidManifest.xml" ]; then
    echo "  AndroidManifest.xml: OK"
else
    echo "  AndroidManifest.xml: NOT FOUND"
    exit 1
fi

echo "✓ Checking Kotlin source files..."
SOURCE_COUNT=$(find app/src/main/java -name "*.kt" | wc -l)
echo "  Found $SOURCE_COUNT Kotlin files"

echo "✓ Checking resources..."
if [ -d "app/src/main/res" ]; then
    echo "  Resources directory: OK"
else
    echo "  Resources directory: NOT FOUND"
    exit 1
fi

echo ""
echo "=== Validation Complete ==="
echo "✅ Android project is properly structured"
echo ""
echo "📝 Next Steps:"
echo "  1. Configure GEMINI_API_KEY in local.properties"
echo "  2. Build APK using GitHub Actions"
echo "  3. Download APK from Actions artifacts"
echo ""
