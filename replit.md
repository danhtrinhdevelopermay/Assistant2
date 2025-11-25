# AI Assistant Android - Project Documentation

## Overview
Android Kotlin application with AI assistant powered by Gemini API, featuring Siri-like gradient animations and hardware button activation support.

## Project Structure
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **AI Integration:** Google Gemini API (generativeai SDK)
- **Build System:** Gradle with Kotlin DSL
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)

## Recent Changes (2025-11-25)
- Created complete Android project structure
- Implemented Siri-style gradient effects:
  - Rotating gradient border
  - Sweep light effect from bottom to top
  - Pulsing glow animation
  - Animated background
- Integrated Gemini API for AI responses
- Added hardware button activation support (basic intent filters)
- Configured GitHub Actions for APK building (debug only)
- Fixed critical initialization race condition in MainActivity
- Implemented comprehensive error handling (network, quota, timeout, auth)
- Added RECORD_AUDIO permission for future voice features

## Key Features
1. **Gradient Effects** (GradientEffects.kt)
   - SiriGradientBorder: Rotating 360° gradient border
   - SiriSweepEffect: Bottom-to-top sweep animation
   - SiriAnimatedBackground: Dynamic gradient background
   - PulsingGlow: Center pulsing glow effect

2. **AI Integration** (AIViewModel.kt)
   - Gemini 1.5 Flash model
   - State management with Flow
   - Error handling
   - Chat message history

3. **Activation Methods**
   - In-app FAB button
   - Hardware assist button (Home long-press)
   - Voice command intent
   - Assistant app delegation

4. **CI/CD**
   - Automatic APK build on push
   - Manual workflow dispatch
   - Artifact upload
   - GitHub releases

## Configuration Requirements
- **API Key:** Gemini API key required in `local.properties`
- **GitHub Secret:** `GEMINI_API_KEY` for Actions

## Build Instructions
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Architecture
- **MVVM Pattern:** ViewModel manages UI state
- **Compose UI:** Declarative UI with Material Design 3
- **Coroutines:** Async operations with Flow
- **Single Activity:** MainActivity with Compose screens

## Dependencies
- Jetpack Compose BOM 2023.10.01
- Generative AI SDK 0.9.0
- Material Design 3
- Coroutines 1.7.3
- Lifecycle ViewModel

## User Preferences
- Vietnamese language support in strings
- Dark theme by default
- Siri-inspired UI animations

## Notes
- API key is excluded from version control via .gitignore
- BuildConfig used for secure API key access
- Assistant button requires system permission delegation (basic support only)
- Voice input and VoiceInteractionService are future enhancements
- Current version is text-based chat with visual gradient effects

## Known Limitations
- No voice input (text-only for MVP)
- Hardware assistant button activation depends on device manufacturer
- Full VoiceInteractionService not implemented (requires more complex setup)
- No chat persistence (messages cleared on app close)

## Testing Notes
- Requires valid Gemini API key in local.properties
- Test gradient effects in dark environment for best visual experience
- Network connection required for AI responses
- Free API tier: 15 requests/min, 1500 requests/day
