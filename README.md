Android Icon Chooser plugin for Eclipse
=======================================

Because resources can change between platform versions, you should not reference built-in icons using the Android platform resource IDs
(i.e. menu or Action Bar icons under `android.R.drawable`). If you want to use any icons or other internal drawable resources,
you should store a local copy of those icons or drawables in your application resources, then reference the local copy from your application code.
In that way, you can maintain control over the appearance of your icons, even if the system's copy changes.  

When you use the an Action Bar you must download the <a href="http://developer.android.com/design/downloads/index.html">Action Bar Icon Pack</a>,
find, rename and copy the icons for the diferents densities to your project.  

With this plugin you can easily import the icons from the Action Bar Icon Pack (tested in eclipse Indigo and Juno)

Installation
------------

Copy the <a href="https://www.dropbox.com/s/46hzcfymd7ckkbs/com.rmembrives.androidiconchooser_1.0.0.201304091619.jar">plugin</a>
file into the eclipse *dropin* folder.

How-to:
-------

 * Press right mouse button in an Android Project and select the *Android Icon Chooser* menu option.
 * Be sure you are in the *Package Browser* view
 * Select the theme
 * Mark the icons to add
 * Press de *Add* button

Tested
------
Tested on Windows version of Eclipse Indigo and Eclipse Juno
