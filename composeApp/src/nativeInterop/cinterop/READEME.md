1. iosApp모듈에 swift 코드 작성 
2. nativeInterop/cinterop/FirebaseHelper.h에 함수 추가
3. 빌드 : ./gradlew :composeApp:cinteropFirebaseHelperIosArm64

if 캐싱 되어있어서 함수가 추가 안된다면,
rm -rf ~/Library/Developer/Xcode/DerivedData
./gradlew :composeApp:clean
./gradlew :composeApp:cinteropFirebaseIosSimulatorArm64 --rerun-tasks