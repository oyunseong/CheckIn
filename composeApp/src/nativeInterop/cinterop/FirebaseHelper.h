#import <Foundation/Foundation.h>

@class Firestore;

@interface FirebaseHelper : NSObject

+ (void)log:(NSString *)message;
+ (void)saveDataWithCollection:(NSString *)collection data:(NSDictionary *)data completion:(void (^)(BOOL success, NSString *error))completion;
+ (void)getDataWithCollection:(NSString *)collection documentId:(NSString *)documentId completion:(void (^)(NSDictionary *data, NSString *error))completion;
+ (void)getDocumentsWithCollection:(NSString *)collection field:(NSString *)field value:(id)value completion:(void (^)(NSArray *documents, NSString *error))completion;
- (instancetype)init;

@end
