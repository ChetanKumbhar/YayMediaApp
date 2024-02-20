
This is a sample Android App that showing Social Media Posts.

## tech stack used 
* language - Kotlin 
* MVVM clean architecture, Navigation
* jetpack Compose for UI, room DB
* firebase authentication.
* Coil, Gson, Kotlinx, hilt, etc

## App in-site

* Firebase Authentication for login
* Posts are handled locally for own account only now.
* Post can be liked and like status saved.
* post can be like by double tab on image
* This App is just to demonstrate that I can build this type of application. 

## Installation
Download:

$ git clone git@github.com:ChetanKumbhar/YayMediaApp.git

Import Project by Android Studio Menu > File > Import Project...

## Running the Sample App on Android Studio

* Click "Sync Project with Gradle Files" and "Rebuild Project" at Android Studio Menu.
* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'


## Extensions
* Posts can be saved on server and handled for all accounts.
    - The architecture implemented does support extension for this implementation
    - we can create Network Repository and use while adding post and fetching posts from network. 
* unit test cases


