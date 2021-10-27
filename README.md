# Corrupt File Demo

This is a simple app that could be used to show how on OneDrive files could be
corrupted when open via the storage access framework.

First, create a text file with this content on a computer and save it on
OneDrive:

A
B
C
D
E

Next, open the file in this app. Remove the last three lines, and add a new
line:

A
B
1

Save the file. Then open it again, either in the app or on a computer. The
content will now be:

A
B
1
D
E

This issue has been reported:

https://answers.microsoft.com/en-us/msoffice/forum/all/onedrive-for-android-can-corrupt-text-files/b7f46dd1-35c9-4128-a3d7-0a8ad1b9c351
https://docs.microsoft.com/en-us/answers/questions/606349/onedrive-for-android-can-corrupt-text-files.html

Tested on Android 8 and 10 with OneDrive 6.41.
