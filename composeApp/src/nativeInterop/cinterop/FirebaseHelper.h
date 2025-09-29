#import <Foundation/Foundation.h>

@class Firestore;

@interface FirebaseHelper : NSObject

// MARK: - 로깅 기능
+ (void)log:(NSString *)message;

// MARK: - Firebase Auth 기능들
+ (NSString *_Nullable)getCurrentUUID;

+ (void)signInWithEmail:(NSString *)email password:(NSString *)password completion:(void (^)(BOOL success, NSString *_Nullable error))completion;

+ (void)createUserWithEmail:(NSString *)email password:(NSString *)password completion:(void (^)(BOOL success, NSString *_Nullable error))completion;

+ (void)signOut:(void (^)(BOOL success, NSString *_Nullable error))completion;

// MARK: - Firebase Firestore 기능들
+ (void)saveDataWithCollection:(NSString *)collection data:(NSDictionary *)data completion:(void (^)(BOOL success, NSString *_Nullable error))completion;

+ (void)getDataWithCollection:(NSString *)collection documentId:(NSString *)documentId completion:(void (^)(NSDictionary *_Nullable data, NSString *_Nullable error))completion;

+ (void)getDocumentsWithCollection:(NSString *)collection field:(NSString *)field value:(id)value completion:(void (^)(NSArray *_Nullable documents, NSString *_Nullable error))completion;

// MARK: - 초기화
- (instancetype)init;

@end
