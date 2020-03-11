# ZIosProgressBar

Pretty nice `Progress Bar` like the iOS.


![Gif example](art/flow.gif)

## Usage
Here are some usage examples:

Set custom color:
```xml
<zHelper.view.ZIosProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:barColor="@color/colorPrimaryDark"
        />
```

Set custom drawable:
```xml
<zHelper.view.ZIosProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:barDrawable="@drawable/bar_indicator"
        />
```

## Customization in Style

* `zIosProgressBarStyle` reference for customization in style.




## Installation

**You need to compile sdk 27+**

Just add following dependency in your module `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.zabih1420:ZIosProgressBar:1.0'
}
```

Also add on your module `build.gradle` (unless you already have it):

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```



License
-------

    Copyright 2020 Zabih

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
