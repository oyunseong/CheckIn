# 📆 출석체크 앱 (Kotlin Multiplatform)

## 개요

**출석체크 앱**은 Kotlin Multiplatform 기반으로 개발된 출석 관리 앱입니다. 
하루에 한 번 버튼을 눌러 출석을 체크하고, 상태에 따라 실시간으로 UI가 변경됩니다.  
사용자는 매일 출석 여부를 확인할 수 있으며, 각 플랫폼(Android/iOS)에서 동일한 UI/UX를 제공합니다.

---

## ✅ 주요 기능

| 기능              | 설명 |
|-------------------|------|
| 실시간 디지털 시계 | 현재 시간을 초 단위로 표시 |
| 출석 상태 표시    | 출석 전/후에 따라 상태 메시지 변경 |
| 출석 버튼         | 출석이 가능한 시간대에만 활성화 |
| 바텀 네비게이션   | 홈, 출석 목록, 설정 탭 제공 |

---

## 💻 기술 스택

| 기술 | 설명 |
|------|------|
| **Kotlin Multiplatform** | Android, iOS 공통 로직 작성 |
| **Jetpack Compose (Multiplatform)** | UI 구현 |
| **Firebase Auth / Firestore** | 사용자 인증 및 출석 데이터 저장 (iOS는 Swift에서 직접 bridging) |
| **Swift + Objective-C Bridge** | iOS에서 Firebase Firestore 연동 |
| **Koin** | DI 구성 |
| **Decompose** | 화면 전환 관리 |
| **Essenty** | MVI 기반 상태 관리 |

---

## 📱 화면 설명

### 1. 출석 전 화면  
- 현재 날짜와 시간 표시  
- `"아직 출석 전입니다."` 상태 메시지 표시  
- 하단 `"출석하기"` 버튼 활성화  


<p align="left">
  <img width="205" alt="Screenshot_20250929_191420" src="https://github.com/user-attachments/assets/90b7250f-d49f-459f-bb9d-8627d360ac6e" />
</p>

---

### 2. 출석 완료 화면  
- `"오늘은 출석 완료! ✅"` 상태 메시지 표시  
- `"출석 완료"` 버튼 비활성화로 전환  
- 하단 네비게이션 바는 그대로 유지  

<p align="left">
  <img width="205" alt="Screenshot_20250929_191504" src="https://github.com/user-attachments/assets/f68920aa-a868-4f50-a9bc-8f094eadde9c" />
</p>

---
