import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTestDemo {
  static const MethodChannel _channel =
      const MethodChannel('flutter_test_demo');

  static Future<String> doRequest(url,params)  async {
    final String result = await _channel.invokeMethod('doRequest', {
      "url": url,
      "param": params,
    });
    return result;
  }
}





