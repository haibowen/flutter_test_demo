#import "FlutterTestDemoPlugin.h"
#if __has_include(<flutter_test_demo/flutter_test_demo-Swift.h>)
#import <flutter_test_demo/flutter_test_demo-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_test_demo-Swift.h"
#endif

@implementation FlutterTestDemoPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTestDemoPlugin registerWithRegistrar:registrar];
}
@end
