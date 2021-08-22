# BuglyUploader
一直没找到Bugly的可视化上传工具（虽然文档里面说有？是我眼瞎还是藏的太深了？），所以写了一个工具自用。
原理很简单，根据工具配置项生成.bat的命令，然后执行.bat调用bugly的jar包。
#### 使用
下载并解压[BuglyUploadTool](https://github.com/fhbianling/BuglyUploader/releases/download/1.0.0/BuglyUploadTool.rar)，运行BuglyUploadTool.exe。如下示例图：
<br/>![image](https://github.com/fhbianling/BuglyUploader/blob/main/readme_sample.png)
<br/>填入各配置项，然后直接点击upload（填好后，以后打开会读取缓存）。会唤起bugly jar包执行的cmd窗口。
<br/>bugly的jar包需要自己去下载并配置（目前bugly的jar包需要jre 1.8，所以下载后注意JAVA_HOME配置）。

