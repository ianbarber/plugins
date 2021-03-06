// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.deviceinfo;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** DeviceInfoPlugin */
public class DeviceInfoPlugin implements MethodCallHandler {

  /** Substitute for missing values. */
  private static final String[] EMPTY_STRING_LIST = new String[] {};

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel =
        new MethodChannel(registrar.messenger(), "plugins.flutter.io/device_info");
    channel.setMethodCallHandler(new DeviceInfoPlugin());
  }

  /** Do not allow direct instantiation. */
  private DeviceInfoPlugin() {}

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getAndroidDeviceInfo")) {
      Map<String, Object> build = new HashMap<>();
      build.put("board", Build.BOARD);
      build.put("bootloader", Build.BOOTLOADER);
      build.put("brand", Build.BRAND);
      build.put("device", Build.DEVICE);
      build.put("display", Build.DISPLAY);
      build.put("fingerprint", Build.FINGERPRINT);
      build.put("hardware", Build.HARDWARE);
      build.put("host", Build.HOST);
      build.put("id", Build.ID);
      build.put("manufacturer", Build.MANUFACTURER);
      build.put("model", Build.MODEL);
      build.put("product", Build.PRODUCT);
      if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        build.put("supported32BitAbis", Arrays.asList(Build.SUPPORTED_32_BIT_ABIS));
        build.put("supported64BitAbis", Arrays.asList(Build.SUPPORTED_64_BIT_ABIS));
        build.put("supportedAbis", Arrays.asList(Build.SUPPORTED_ABIS));
      } else {
        build.put("supported32BitAbis", EMPTY_STRING_LIST);
        build.put("supported64BitAbis", EMPTY_STRING_LIST);
        build.put("supportedAbis", EMPTY_STRING_LIST);
      }
      build.put("tags", Build.TAGS);
      build.put("type", Build.TYPE);

      Map<String, Object> version = new HashMap<>();
      if (VERSION.SDK_INT >= VERSION_CODES.M) {
        version.put("baseOS", VERSION.BASE_OS);
        version.put("previewSdkInt", VERSION.PREVIEW_SDK_INT);
        version.put("securityPatch", VERSION.SECURITY_PATCH);
      }
      version.put("codename", VERSION.CODENAME);
      version.put("incremental", VERSION.INCREMENTAL);
      version.put("release", VERSION.RELEASE);
      version.put("sdkInt", VERSION.SDK_INT);
      build.put("version", version);

      result.success(build);
    } else {
      result.notImplemented();
    }
  }
}
