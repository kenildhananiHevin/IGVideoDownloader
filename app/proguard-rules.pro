# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

-dontwarn okhttp3.**

-keep class com.cashloan.myapplication.igvideodownloader.model.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.Languages.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.CustomViewPager.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.VisitedVideoPage.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.time_line.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.**{*;}
-keep class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp.**{*;}

-keepclassmembers class com.cashloan.myapplication.igvideodownloader.model.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.Languages.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.CustomViewPager.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.VisitedVideoPage.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.time_line.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.**{*;}
-keepclassmembers class vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp.**{*;}


-keepattributes Signature

# This is also needed for R8 in compat mode since multiple
# optimizations will remove the generic signature such as class
# merging and argument removal. See:
# https://r8.googlesource.com/r8/+/refs/heads/main/compatibility-faq.md#troubleshooting-gson-gson
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Optional. For using GSON @Expose annotation
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
-keepclassmembers class * {
   @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
-keepattributes *Annotation*

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type


# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# R8 full mode strips generic signatures from return types if not kept.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>


