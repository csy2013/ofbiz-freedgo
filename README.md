 freedgo
==========

<h1>A Distributed Deployable Framework base on Ofbiz.</h1>

<p>[Ofibz](http://ofbiz.apache.org/)是一整套完善的企业级，以java为核心，结合很多开源项目，支持使用脚本来编写业务逻辑的开源项目。它也是一整套完整的企业ERP,电子商务业务框架.</p>
<p>freedgo延续了ofbiz的 component load，Service Engine,Entity Engine等核心开发框架的优点，对实际对代码结构层次做进一步的改良，并结合docker容器，dubbo相关微服务，Jwts做相关的整合
主要包括如下:</p>
<ol>
<li> 增加的BootStrap 的screen 风格。</li>
<li>突出服务化组件与应用相隔离。</li>
<li>后台与前端的结构分离</li>
<li>增加http restful接口调用方式</li>
<li>提供SSO单点登录</li>
</ol>
等等...

![Bootstrap Style1](http://7xqqm0.com1.z0.glb.clouddn.com/images/themes/bootcss/screenshot.jpg)
![BootStrap coloradmin](http://7xqqm0.com1.z0.glb.clouddn.com/images/themes/coloradmin/screenshot.png) 

<h1>系统要求</h1>

所有可以运行 JAVA SDK7+ 的操作系统

<h1>目录结构</h1>

<ol>
<li>framework: 核心框架</li>
<li>front: 提供用户侧：如电商门户，微信，APP接入层</li>
<li>images:存放静态资源，图片文本，脚本，可CDN接入</li>
<li>runtime: 运行日志等</li>
<li>themem: 提供主题风格</li>
</ol>
<h1>数据库确定</h1>
	支持主流的数据库：
	<ol>
    <li>MYSQL</li>
	<li>ORCLE</li>
	<li>SQL SERVER</li>
	<li>SYBASE</li>
	<li>PostgreSQL</li>
	<li>SAP database</li>
	等。
	</ol>
<h1>安装步骤</h1>

以Mysql为例：首先创建localmysql、localmysqltenant、localmysqlolap对应的数据库。
+ 修改entityengin.xml指定的数据库连接
+ 初始化数据库
	导入基础数据，配置数据到数据库中，	
	进入软件根目录，执行命令:
<pre><code>
cd framework
./ant load-extseed	 
</code></pre>
<h1>quick start</h1>

<pre><code>./ant start</code></pre>
打开浏览器执行：
http://youcompuer.com:8080/ofbizsetup,设置对应的运营主体、网站、场所等。
<h1>教程</h1>

<h1>License</h1>
freedgo is released under the MIT license. See LICENSE for details.