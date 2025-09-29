// Logger.h

#import <Foundation/Foundation.h>

@class Logger;

@interface Logger : NSObject

+ (void)log:(NSString *_Nonnull)message;

- (instancetype _Nonnull)init;

@end