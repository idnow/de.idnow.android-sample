## IDnow Android SDK - Sample App

A simple sample app that shows how to use the IDnow Android SDK.

You can choose between Video-Ident and Photo-Ident.

## Installing

You need:
- This project
- Android Studio

## How to build/run Sample App 

[![Watch the video](https://github.com/idnow/de.idnow.android/blob/souheib93-patch-1/docs/screenshot_video3.png)](https://youtu.be/19Im-DODSaw)


## Home Screen - Screenshot

![Sample App](https://github.com/idnow/de.idnow.android/blob/souheib93-patch-1/docs/device-screenshot.png)

## Customizing

The sample provides examples for how to customize the SDK.

### Custom fonts

The sample is prepared for easily setting application-wide custom fonts using the Android [Support Library v26 XML fonts](https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml.html). The sample includes the font `roboto_thinitalic.ttf`. To enable it for the SDK and the rest of the App, 

 * add the support library dependency to the apps in `app/build.gradle`. Then 
 * add / uncomment the following line in the `AppTheme` in `app/src/main/res/values/styles.xml`:
   `<item name="android:fontFamily">@font/roboto_thinitalic</item>`
   
The `AppTheme` theme will be applied to the whole app including the SDK and thus change the font globally. You can use any true type font. 

## Eclipse Sample

You can find the old Eclipse example at https://github.com/idnow/de.idnow.android-sample/tree/eclipse
