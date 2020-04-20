# OkhttpCacheLib

该库可轻松实现不同接口拥有不同的缓存策略，原理可以查看该贴：https://www.jianshu.com/p/dbda0bb8d541
感谢作者大大的分享，作者分析总结得很简单明了，在这个基础上，该库运用了添加请求参数的方式来实现不同接口的缓存策略

## 可以实现的功能：
- 可以实现不同接口拥有不同的缓存策略
- 有网的时候可以读取缓存，并且可以控制缓存的过期时间，这样可以减轻服务器压力
- 无网的时候读取缓存，并且可以控制缓存过期的时间，缓存过期后会返回无网络的错误信息

## 添加该库：
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    implementation 'com.github.davidgerka:OkhttpCache:Tag'
}
```

## 如何使用？请查看app代码



## 混淆配置（retrofit2、okhttp3 自行混淆）
```
-dontwarn com.gerka.okhttpcachelib.**
-keep class com.gerka.okhttpcachelib.** { *; }
```



## License
```
The MIT License (MIT)

Copyright (c) 2020 Gerka

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR

IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```