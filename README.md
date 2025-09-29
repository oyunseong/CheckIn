# 📲 KMP 출석체크 앱

이 앱은 Kotlin Multiplatform (KMP)을 기반으로 개발된 **출석 체크 시스템 앱**입니다.  
Android와 iOS 모두에서 동일한 코드베이스로 작동하며, Firebase 인증과 출석 이력을 관리할 수 있는 기능을 제공합니다.

---

## 🧩 주요 기능

- ✅ 로그인 / 회원가입 (Firebase Auth 연동)
- ⏰ 현재 시간 및 날짜 자동 표시
- 📍 **출석 체크** 기능
- 📖 출석 이력 확인
- 🧭 Android / iOS 대응
- 🧪 Compose Multiplatform 기반 UI

---

## 📸 앱 화면 미리보기

### 🔐 로그인 화면

<p align="center">
  <img width="250" alt="Screenshot_20250929_192154" src="https://github.com/user-attachments/assets/9438b72e-8b61-469a-9950-453aec9e2970" />

</p>

---

### ✅ 홈 화면 (출석 전/후)

<summary>출석 전 / 출석 완료 상태</summary>

<p align="center">
  <img width="250" alt="Screenshot_20250929_191420" src="https://github.com/user-attachments/assets/7ea72f36-590b-4c6e-b201-6c451ffd8a84" />

  <img width="250" alt="Screenshot_20250929_191504" src="https://github.com/user-attachments/assets/e814f819-79bf-42ca-adea-4174be4d13fe" />

</p>


---

### 📋 출석 기록 화면

<summary>출석 내역 확인</summary>

<p align="center">
  <img width="250" alt="Screenshot_20250929_192119" src="https://github.com/user-attachments/assets/788ec2e2-75f0-4454-96db-3a7ad5d2a20f" />

</p>


---

## 🛠 기술 스택

| 항목 | 기술 |
|------|------|
| 언어 | Kotlin Multiplatform (KMP) |
| UI | Jetpack Compose Multiplatform |
| 인증 | Firebase Auth |
| 데이터베이스 | Firebase Firestore |
| DI | Koin |
| iOS 연동 | Swift + cinterop |
| 빌드 시스템 | Gradle |
