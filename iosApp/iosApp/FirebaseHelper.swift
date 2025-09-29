//
// Created by yunseong on 2025. 9. 29..
//

import Foundation
import FirebaseFirestore


@objc(FirebaseHelper)
@objcMembers
public class FirebaseHelper: NSObject {

    // MARK: - 로깅 기능
    @objc(log:)
    public static func log(_ message: String) {
        print("🍎 [SwiftLog] \(message)")
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