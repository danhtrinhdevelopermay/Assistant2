# AI Assistant - Trợ lý AI thông minh cho Android

Ứng dụng trợ lý AI được xây dựng bằng Kotlin và Jetpack Compose, tích hợp Gemini API với hiệu ứng gradient tuyệt đẹp giống Siri.

## ✨ Tính năng

- 🤖 **Trợ lý AI thông minh** - Sử dụng Gemini API để trả lời mọi câu hỏi qua text
- 🎨 **Hiệu ứng gradient đẹp mắt** - Viền gradient xoay quanh màn hình và luồng sáng quét từ dưới lên, tương tự hiệu ứng Siri trên iPhone
- ⌨️ **Kích hoạt linh hoạt** - Mở app trực tiếp hoặc qua assistant intent (hỗ trợ cơ bản cho phím tắt)
- 💬 **Giao diện chat hiện đại** - Thiết kế tối giản, dễ sử dụng với Jetpack Compose
- 🌙 **Dark mode** - Giao diện tối đẹp mắt, dễ nhìn
- ⚡ **Error handling thông minh** - Phát hiện và thông báo rõ ràng các lỗi network, API quota, timeout

### 🔮 Tính năng có thể mở rộng

- 🎤 Voice input với SpeechRecognizer
- 🔊 Text-to-speech cho câu trả lời
- 📱 Full VoiceInteractionService integration cho hardware assistant button
- 💾 Chat history persistence
- 🌐 Multilingual support

## 📱 Screenshots

![AI Assistant Demo](https://via.placeholder.com/800x400?text=AI+Assistant+with+Gradient+Effects)

## 🛠️ Công nghệ sử dụng

- **Kotlin** - Ngôn ngữ lập trình chính
- **Jetpack Compose** - UI framework hiện đại
- **Gemini API** - AI model từ Google
- **Material Design 3** - Design system
- **Coroutines & Flow** - Xử lý bất đồng bộ
- **ViewModel** - Quản lý state

## 📋 Yêu cầu

- Android Studio Hedgehog (2023.1.1) trở lên
- JDK 17
- Android SDK 24+
- Gemini API key (miễn phí tại [Google AI Studio](https://aistudio.google.com/app/apikey))

## 🚀 Hướng dẫn cài đặt

### 1. Clone repository

```bash
git clone https://github.com/your-username/ai-assistant-android.git
cd ai-assistant-android
```

### 2. Lấy Gemini API Key

1. Truy cập [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Đăng nhập bằng tài khoản Google
3. Click **"Create API Key"**
4. Copy API key

### 3. Cấu hình API Key

Mở file `local.properties` trong thư mục gốc của project và thêm:

```properties
GEMINI_API_KEY=your_api_key_here
```

⚠️ **Lưu ý:** File `local.properties` đã được thêm vào `.gitignore` để bảo mật API key.

### 4. Build và chạy

#### Trên Android Studio:
1. Mở project trong Android Studio
2. Sync Gradle
3. Chọn device/emulator
4. Click **Run** (Shift+F10)

#### Qua command line:
```bash
# Build debug APK
./gradlew assembleDebug

# Install trên device đã kết nối
./gradlew installDebug
```

APK sẽ được tạo tại: `app/build/outputs/apk/debug/app-debug.apk`

## 🎯 Cách sử dụng

### Kích hoạt trợ lý

**Cách 1:** Mở ứng dụng và click nút AI ở góc dưới bên phải

**Cách 2:** Nhấn và giữ nút Home trên điện thoại (hoặc nút Assistant nếu có)

**Cách 3:** Sử dụng lệnh voice assistant của hệ thống

### Đặt làm trợ lý mặc định

1. Vào **Settings** → **Apps** → **Default apps**
2. Chọn **Digital assistant app** (hoặc **Assist app**)
3. Chọn **AI Assistant**

Giờ bạn có thể kích hoạt trợ lý bằng cách:
- Nhấn và giữ nút Home
- Vuốt từ góc dưới màn hình (trên một số thiết bị)
- Sử dụng phím tắt được cấu hình trên điện thoại

**⚠️ Lưu ý về kích hoạt phím cứng:**
- Tính năng kích hoạt bằng phím tắt phần cứng phụ thuộc vào nhà sản xuất điện thoại
- Một số thiết bị có thể yêu cầu cấu hình thêm VoiceInteractionService
- Hiện tại app hỗ trợ intent filters cơ bản cho ACTION_ASSIST và ACTION_VOICE_COMMAND
- Để có trải nghiệm tốt nhất, khuyến nghị sử dụng nút FAB trong app

### Tương tác với trợ lý

1. Click nút AI (FAB) ở góc dưới để kích hoạt gradient effects
2. Nhập câu hỏi vào ô text field (hiện chỉ hỗ trợ text input)
3. Click nút gửi
4. Đợi AI trả lời (có hiệu ứng "thinking")
5. Xem kết quả trong khung chat

**💡 Tip:** Để tận hưởng hiệu ứng gradient đẹp mắt nhất, sử dụng trong môi trường tối!

## 🔧 Build APK trên GitHub Actions

Project này đã được cấu hình sẵn GitHub Actions để tự động build APK.

### Cấu hình GitHub Secrets

1. Vào repository trên GitHub
2. **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Thêm secret:
   - Name: `GEMINI_API_KEY`
   - Value: API key của bạn

### Trigger build

**Tự động:** Push code lên branch `main`, `master`, hoặc `develop`

**Thủ công:**
1. Vào tab **Actions**
2. Chọn workflow **Build Android APK**
3. Click **Run workflow**

⚠️ **Lưu ý:** Workflow chỉ build debug APK. Để build release APK có thể cài lên Google Play, bạn cần:
1. Tạo signing keystore
2. Cấu hình signing config trong `app/build.gradle.kts`
3. Thêm signing secrets vào GitHub Actions

### Download APK

Sau khi workflow chạy xong:
1. Vào tab **Actions**
2. Click vào workflow run
3. Scroll xuống phần **Artifacts**
4. Download APK

## 🎨 Hiệu ứng Gradient

Ứng dụng có 4 loại hiệu ứng gradient:

1. **SiriGradientBorder** - Viền gradient xoay 360° quanh màn hình
2. **SiriSweepEffect** - Luồng sáng gradient quét từ dưới lên khi kích hoạt
3. **SiriAnimatedBackground** - Nền gradient động, thay đổi màu sắc
4. **PulsingGlow** - Ánh sáng nhấp nháy ở trung tâm màn hình

Tất cả hiệu ứng được tạo bằng Canvas API và Jetpack Compose Animation.

## 📂 Cấu trúc project

```
app/
├── src/main/
│   ├── java/com/aiassistant/
│   │   ├── MainActivity.kt           # Activity chính
│   │   ├── AIViewModel.kt            # ViewModel quản lý state
│   │   ├── GradientEffects.kt        # Các hiệu ứng gradient
│   │   └── AssistButtonReceiver.kt   # Receiver cho phím tắt
│   ├── res/
│   │   ├── drawable/                 # Icons
│   │   ├── values/                   # Strings, colors, themes
│   │   └── xml/                      # Backup rules
│   └── AndroidManifest.xml
├── build.gradle.kts                  # Module-level Gradle
└── proguard-rules.pro

.github/
└── workflows/
    └── build-apk.yml                 # GitHub Actions workflow

build.gradle.kts                      # Project-level Gradle
settings.gradle.kts
local.properties                      # API keys (gitignored)
```

## 🔐 Bảo mật

- ✅ API key được lưu trong `local.properties` (không commit lên Git)
- ✅ GitHub Actions sử dụng Secrets để lưu API key
- ✅ BuildConfig được dùng để truy cập API key trong code
- ✅ Không hardcode API key trong source code

## 🐛 Xử lý lỗi thường gặp

### Lỗi: "Model chưa được khởi tạo"
**Nguyên nhân:** Chưa cấu hình API key hoặc API key sai  
**Giải pháp:** Kiểm tra lại `local.properties` và đảm bảo API key đúng

### Lỗi: "Không thể kết nối với AI" / "API đã đạt giới hạn"
**Nguyên nhân:** Không có kết nối Internet, API limit, hoặc quota hết  
**Giải pháp:** 
- Kiểm tra kết nối mạng
- Kiểm tra quota API tại [Google AI Studio](https://aistudio.google.com/)
- API miễn phí có giới hạn 15 requests/phút, 1500 requests/ngày
- Nâng cấp lên paid tier nếu cần quota cao hơn

### Gradle sync failed
**Nguyên nhân:** Thiếu dependencies hoặc version không tương thích  
**Giải pháp:** 
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

## 📝 License

MIT License - Tự do sử dụng và chỉnh sửa

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Hãy tạo Pull Request hoặc báo cáo Issues.

## 📧 Liên hệ

Nếu có thắc mắc, vui lòng tạo Issue trên GitHub.

---

**Made with ❤️ using Kotlin & Jetpack Compose**
