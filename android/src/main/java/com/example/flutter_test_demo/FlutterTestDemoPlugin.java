package com.example.flutter_test_demo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * FlutterTestDemoPlugin
 */
public class FlutterTestDemoPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

    private static PluginRegistry.Registrar registrar1;
    private Context mContext;
    ActivityPluginBinding activityPluginBinding1;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        // mContext =flutterPluginBinding.getApplicationContext();
//        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_test_demo");
//        channel.setMethodCallHandler(new FlutterTestDemoPlugin());
        onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    public void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {

        this.mContext = applicationContext;
        final MethodChannel channel = new MethodChannel(messenger, "flutter_test_demo");
        channel.setMethodCallHandler(this);


    }


    public static void registerWith(PluginRegistry.Registrar registrar) {
        FlutterTestDemoPlugin.registrar1 = registrar;
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_test_demo");
        channel.setMethodCallHandler(new FlutterTestDemoPlugin());
    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("doRequest")) {

            HashMap param = call.argument("param");
            String url = call.argument("url");
            doRequest(url, param, result);
            Log.e("44444444444444", "zhixngle");
            Toast.makeText(mContext, "测试的 ", Toast.LENGTH_LONG).show();
        } else {
            result.notImplemented();

        }
    }


    void doRequest(String url, HashMap<String, String> param, final Result result) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (String key : param.keySet()) {
            String value = param.get(key);
            urlBuilder.addQueryParameter(key, value);
        }

        //加入自定义通用参数
        urlBuilder.addQueryParameter("ppp", "yyyy");

        String requestUrl = urlBuilder.build().toString();

        final Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {

                if (!response.isSuccessful()) {
                    final String content = "Unexpected code " + response;


                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // Call the desired channel message here.
                            result.error("Error", content, null);
                        }
                    });

//


                } else {
                    final String content = response.body().string();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // Call the desired channel message here.
                            result.success(content);
                        }
                    });


//
//                    activityPluginBinding1.getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });


                }
            }

            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();

            }


        });


    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {


    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }
}
