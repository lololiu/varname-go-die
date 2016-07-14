# varname-go-die
一个小巧的Android Studio插件，主要有两个功能：
1. 在代码编辑区输入中文时，可以使用该插件联网翻译成英文并替换成指定变量名称格式。
2. 在代码编辑区输入英文时，可以使用该插件转换成指定变量名称格式

### 优点
1. 设置里可以根据自己的代码风格设置生成格式，写变量名更方便
2. 遇到变量名英文不会写不需要打开翻译软件查找，直接在编辑器中即可自动生成

### 效果图
![快速变量格式转换](https://github.com/lololiu/varname-go-die/raw/master/imgs/screenshot.gif)

![变量风格设置](https://github.com/lololiu/varname-go-die/raw/master/imgs/screenshot1.gif)

### 安装
* Android Studio: 打开Settings → Plugins → Browse repositories 查找 varname-go-die进行安装
你也可以
* [下载jar包](https://plugins.jetbrains.com/plugin/8479?pr=) 通过Settings → Plugins → Install plugin from disk进行安装

### 使用
* 在编辑器中输入单词或中文，Ctrl+W选取，再按Shift+H(默认快捷键，可以在设置中修改)即可弹出选择对话框，也可以选择单词或词组后点击主菜单栏Edit → ChangeVar
* 可以在Settings → Other Settings → VarNameGoDie 中设置自己想要列表弹出的变量格式




