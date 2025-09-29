//
// Created by yunseong on 2025. 9. 29..
//

import Foundation
import FirebaseFirestore
import FirebaseAuth


@objc(FirebaseHelper)
@objcMembers
public class FirebaseHelper: NSObject {

    // MARK: - 로깅 기능
    @objc(log:)
    public static func log(_ message: String) {
        print(" [SwiftLog] \(message)")
    }

    // MARK: - Firebase Auth 기능들

    /// 현재 로그인된 사용자의 UID를 반환
    @objc(getCurrentUUID)
    public static func getCurrentUUID() -> String? {
        log("swift \(Auth.auth().currentUser?.uid)")
        return Auth.auth().currentUser?.uid
    }

    /// 현재 로그인된 사용자의 이메일을 반환
    @objc(getUserEmail)
    public static func getUserEmail() -> String {
        guard let currentUser = Auth.auth().currentUser,
              let email = currentUser.email
        else {
            log(" No user email available")
            return "unknown"
        }
        log(" Current user email: \(email)")
        return email
    }

    /// 현재 사용자가 로그인되어 있는지 확인
    @objc(isUserSignedIn)
    public static func isUserSignedIn() -> Bool {
        let isSignedIn = Auth.auth().currentUser != nil
        log(" User signed in: \(isSignedIn)")
        return isSignedIn
    }

    /// 현재 사용자의 모든 정보를 딕셔너리로 반환
    @objc(getCurrentUserInfo)
    public static func getCurrentUserInfo() -> [String: Any] {
        guard let currentUser = Auth.auth().currentUser else {
            log(" No user logged in for user info")
            return [:]
        }

        let userInfo: [String: Any] = [
            "uid": currentUser.uid,
            "email": currentUser.email ?? "unknown",
            "displayName": currentUser.displayName ?? "unknown",
            "isEmailVerified": currentUser.isEmailVerified,
            "creationDate": currentUser.metadata.creationDate?.timeIntervalSince1970 ?? 0,
            "lastSignInDate": currentUser.metadata.lastSignInDate?.timeIntervalSince1970 ?? 0
        ]

        log(" Current user info retrieved: \(userInfo)")
        return userInfo
    }

    /// 이메일과 비밀번호로 로그인
    @objc(signInWithEmail:password:completion:)
    public static func signInWithEmail(
        email: String,
        password: String,
        completion: @escaping (Bool, String?) -> Void
    ) {
        log(" Attempting to sign in with email: \(email)")

        Auth.auth().signIn(withEmail: email, password: password) { result, error in
            if let error = error {
                log(" Sign in failed: \(error.localizedDescription)")
                completion(false, error.localizedDescription)
            } else if let user = result?.user {
                log(" Sign in successful for user: \(user.uid)")
                completion(true, nil)
            }
        }
    }

    /// 이메일과 비밀번호로 회원가입
    @objc(createUserWithEmail:password:completion:)
    public static func createUserWithEmail(
        email: String,
        password: String,
        completion: @escaping (Bool, String?) -> Void
    ) {
        log(" Attempting to create user with email: \(email)")

        Auth.auth().createUser(withEmail: email, password: password) { result, error in
            if let error = error {
                log(" User creation failed: \(error.localizedDescription)")
                completion(false, error.localizedDescription)
            } else if let user = result?.user {
                log(" User creation successful for user: \(user.uid)")
                completion(true, nil)
            }
        }
    }

    /// 로그아웃
    @objc(signOut:)
    public static func signOut(completion: @escaping (Bool, String?) -> Void) {
        log(" Attempting to sign out")

        do {
            try Auth.auth().signOut()
            log(" Sign out successful")
            completion(true, nil)
        } catch let error {
            log(" Sign out failed: \(error.localizedDescription)")
            completion(false, error.localizedDescription)
        }
    }

    // MARK: - Firebase Firestore 기능들
    @objc(saveDataWithCollection:data:completion:)
    public static func saveDataWithCollection(
        collection: String,
        data: [String: Any],
        completion: @escaping (Bool, String?) -> Void
    ) {
        let db = Firestore.firestore()

        log("🔥 FirebaseHelper - saving data to \(collection)")

        db.collection(collection).addDocument(data: data) { error in
            if let error = error {
                log("❌ FirebaseHelper - save failed: \(error.localizedDescription)")
                completion(false, error.localizedDescription)
            } else {
                log("✅ FirebaseHelper - save success")
                completion(true, nil)
            }
        }
    }

    @objc(getDataWithCollection:documentId:completion:)
    public static func getDataWithCollection(
        collection: String,
        documentId: String,
        completion: @escaping ([String: Any]?, String?) -> Void
    ) {
        let db = Firestore.firestore()

        log("🔥 FirebaseHelper - getting document \(documentId) from \(collection)")

        db.collection(collection).document(documentId).getDocument { document, error in
            if let error = error {
                log("❌ FirebaseHelper - get failed: \(error.localizedDescription)")
                completion(nil, error.localizedDescription)
            } else if let document = document, document.exists {
                log("✅ FirebaseHelper - document found")
                completion(document.data(), nil)
            } else {
                log("🚫 FirebaseHelper - document not found")
                completion(nil, nil)
            }
        }
    }

    @objc(getDocumentsWithCollection:field:value:completion:)
    public static func getDocumentsWithCollection(
        collection: String,
        field: String,
        value: Any,
        completion: @escaping ([[String: Any]]?, String?) -> Void
    ) {
        let db = Firestore.firestore()

        log("🔥 FirebaseHelper - querying \(collection) where \(field) == \(value)")

        db.collection(collection).whereField(field, isEqualTo: value).getDocuments { snapshot, error in
            if let error = error {
                log("❌ FirebaseHelper - query failed: \(error.localizedDescription)")
                completion(nil, error.localizedDescription)
            } else {
                let documents = snapshot?.documents.compactMap {
                    $0.data()
                } ?? []
                log("✅ FirebaseHelper - found \(documents.count) documents")
                completion(documents, nil)
            }
        }
    }

    // 추가적인 초기화 메서드 (Objective-C 호환성을 위해)
    @objc public override init() {
        super.init()
    }
}
