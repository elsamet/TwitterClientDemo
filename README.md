

  
 <h1>TwitterClientDemo</h1>
 is a simple twitter client for android using TwitterKit SDK

<h2>Features</h2>

<ul>
<li>Splash Screen </li>
<li>LoginScreen using Twitter Button</li>
<li>Followers screen that shows the current user followers in a recycler view with Pull to Refresh and Infinite Scrolling.</li>
<li>Followers Information Screen  shows basic user information and his/her last 10 Tweets.</li>
</ul>

Followers/FollowerInfo screens works in offline mode using api response caching not a local database like SQLlist or Realm.
Followers Information screen with a sticky header for background image.
Localization for Arabic and English.

Architecture Design
<p>

<b>Goal:</b> CLean Architecure by Uncle Bob

<b>current:</b >Only MVP(Model View Presenter)
</p>


<h3>Third party library used</h3>

<ul>
<li>TwitterSDK</li>
<li>Retrofit</li>
<li>RxJava</li>
<li>Gson</li>
<li>Glide</li>
<li>Butterknife</li>
<li>Parceler</li>
</ul>

Why
<p>
<b>Retrofit:</b> Type-safe HTTP client for Android and Java by Square, Inc. with retrofit we can easly call web apis and with the help of Gson we can parse JSON and capture JSON from the response body, all we need to deal with is just a plain java objects (POJO) files

<b>Glide:</b> again from Square, Inc. It is a powerful image loading lib with great caching techniques

<b>GSON:</b> It is a great seralization and deserialization lib .It is used here for Json parsing

<b>Rxjava:</b> it is a great lib with reactive features for streams in android 

<b>Okhttp:</b> it is a poweful networking lib with great features and it has easy integration with Retrofit lib

<b>Parceler:</b> using Annotations it allows you to convert you POJO to a Parcelable object and using it is much faster than using Serialization as Parcelables does not use reflection.

<b>Butterknife:</b>view injection can never be easer. it reduce the amount of boilerplate code for just initialize views and hooking view event like OnClick.
</p>
