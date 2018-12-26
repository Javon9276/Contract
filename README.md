# 合同与签名
该功能主要的通过模版（Excel或Word）合同文档，经过数据替换生成新文档后，转换成PDF或png格式进行显示。

# 运行环境
* Tomcat: apache-tomcat-8.5.28
* JDK: jdk1.8.0_144
* 开发软件：IntelliJ IDEA

# 插件
## 前端
* [weui](https://weui.io):是一套同微信原生视觉体验一致的基础样式库，由微信官方设计团队为微信内网页和微信小程序量身设计，令用户的使用感知更加统一。
* [PDF.js](http://mozilla.github.io/pdf.js/):基于Web标准的通用平台，用于解析和呈现PDF。

## 后端
* [Aspose](https://www.aspose.com/):Aspose支持业务中一些最流行的文件格式，包括Microsoft Word文档，Excel电子表格，PowerPoint演示文稿，Outlook电子邮件和存档，Visio图表，项目文件，OneNote文档和Adobe Acrobat PDF文档。还提供OCR，OMR，条形码生成和识别以及图像处理API。
* [Jacob](https://sourceforge.net/projects/jacob-project/):一个JAVA-COM桥，允许您调用COM Automation comp
* [pdfbox](https://pdfbox.apache.org/):操作PDF文件

# 效果
1. 启动首页
![首页](https://github.com/Javon9276/Contract/效果图/1.jpg)

2. 使用PDF.js显示生成的PDF文件
![生成PDF合同](https://github.com/Javon9276/Contract/效果图/2.jpg)

3. PDF合同签名位置
![PDF合同签名位置](https://github.com/Javon9276/Contract/效果图/3.jpg)

4. 合同签名按钮点后提示 
![合同签名提示](https://github.com/Javon9276/Contract/效果图/4.jpg)

5. 签名界面
![合同签名](https://github.com/Javon9276/Contract/效果图/5.jpg)

6. PDF合同签名结果通过PDF.js显示
![PDF合同签名结果](https://github.com/Javon9276/Contract/效果图/6.jpg)

7. 生成PNG合同文件显示
![生成PNG合同](https://github.com/Javon9276/Contract/效果图/7.jpg)

8. PNG合同签名位置
![PNG合同签名位置](https://github.com/Javon9276/Contract/效果图/8.jpg)

9. PNG合同签名结果显示
![PNG合同签名结果](https://github.com/Javon9276/Contract/效果图/9.jpg)

# 问题
问题1: PDF.js 安卓手机异常闪退到桌面
解决：生成PNG图片进行展示，现在还没有找到真正的问题所在，但是估计是因为浏览器内存不足导致闪退。

问题：The document appears to be corrupted and cannot be loaded.
解决：错误意思大概是Word文件损坏异常，但google和百度了一下，说是版本的BUG
