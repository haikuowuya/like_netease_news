LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := JniTestNDK
LOCAL_SRC_FILES := com_roboo_like_netease_jni_JniClient.c
include $(BUILD_SHARED_LIBRARY)
