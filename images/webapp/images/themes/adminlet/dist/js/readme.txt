// ICO公用方法使用手册

1.全选&&反选

需在祖先节点加上 js-checkparent，控制全选的元素加上 js-allcheck，其余的使用 js-checkchild
具体结构如下：
<div class="js-checkparent">
	<input class="js-allcheck" type="checkbox">
	<input class="js-checkchild" type="checkbox">
	<input class="js-checkchild" type="checkbox">
	<input class="js-checkchild" type="checkbox">
	<input class="js-checkchild" type="checkbox">
</div>

2.列表单页显示条数变更请求
3.列表指定页面跳转请求

使用dom2中对应的部分即可，这里不再给出具体案例

4.列表排序请求

需在祖先节点加上 js-sort-list，对应标签上加上 js-sort，并在data-key中赋予对应键值
具体如下：
<div class="js-sort-list">
	<div>
		<span>时间<a class="js-sort fa fa-sort-amount-desc text-muted" data-key="time" href=""></a></span>
		<span>11:22</span>
		<span>11:11</span>
		<span>13:12</span>
	</div>
	<div>
		<span>价格<a class="js-sort fa fa-sort-amount-desc text-muted" data-key="price" href=""></a></span>
		<span>33</span>
		<span>22</span>
		<span>11</span>
	</div>
</div>

// 2015-12-30
// By Duper_wang
// 1.1版新增功能

5.提示弹窗

该弹窗仅用作提示性的信息展示，不开放任何后续功能。

使用方法：tipLayer(msg);
参数：
	msg		String		提示信息的内容

6.确认弹窗

该弹窗用作确认性质的信息展示，如确认付款、确认删除等功能。

使用方法：$.confirmLayer(option);
参数：
	option		object		参数对象
	 msg		String		确认信息的内容
	 confirm	function	确认按钮点击的回调

7.表单验证（1.2版修订）

此次为表单验证的初版，容错性较差，各位在使用的时候注意参数配置和报错

使用方法：$(Element).dpValidate({validate:true}); 或直接在form上加入validateForm类。
该方法对于dom结构有一定的要求，具体情况请各位参考dom1

参数
	参数名				参数类型		参数意义

	console				布尔值			是否开启调试
	validate			布尔值			是否调用校验方法
	clear				布尔值			是否调用清空方法
	callback			function		验证通过回调

dom参数

	参数名				参数意义		允许值

	data-type			验证类型		required(必填项)
										min(字段长度限制最小)
										max(字段长度限制最大)
										range(字段长度限制区间)
										format(格式验证)
										minCheck(选择项限制最小)
										maxCheck(选择项限制最大)
										linkGt(比对要求大于)
										linkLt(比对要求小于)
										linkEq(比对要求等于)

	data-mark			验证标记		必需值
	data-msg			验证提示信息	可选值
	data-number			验证相关数值	type为min、max、minCheck、maxCheck、range(小值在前,分割)必需值
	data-reg			验证正则式		type为format必需值
	data-compare-link	对比元素ID		type为linkGt、linkLt、linkEq必需值
	data-compare-mark	对比元素标记	可选值
	data-relation		验证关联元素ID	多关联情况用,分割

// 2015-1-9
// By Duper_wang
// 1.3版新增功能

8.图片选择

实现图片的三种方式选择及上传

使用方法 $.chooseImage.function();

方法
	方法名					方法作用
	int(option)				初始化控件
	show()					显示控件
	hide()					隐藏控件
	preview(option) 		图片预览
	choose(obj,callback)	提交选择
	getImgData(obj)         获取方法内部参数（该方法预留了修改组件内部参数的接口）

方法参数

1) 初始化 $.chooseImage.int(option)

option本身为object，参数示例如下：

{
	userId: 3446,
	serverChooseNum: 5,
	getServerImgUrl: '',
	submitLocalImgUrl: '',
	submitServerImgUrl: '',
	submitNetworkImgUrl: ''
}

	参数名			参数意义					允许值
userId				预留用户ID接口
serverChooseNum		服务端允许选择图片数量		任意正整数，默认为5
getServerImgUrl		获取图库数据的地址
submitLocalImgUrl	提交本地图片的地址
submitServerImgUrl	提交图库图片的地址
submitNetworkImgUrl	提交网络图片的地址

2) 图片预览 $.chooseImage.preview(option)

该方法不局限于控件本身，可以单独作用于其他地方的图片压缩预览，方法的endMethod回调中，提供了图片压缩后的base64字符串的返回接口
不过由于组件本身对浏览器版本进行的了判断，故方法内部没有处理浏览器兼容问题（兼容至IE10）

	参数名			参数意义					允许值
	max_Width		图片压缩后允许的最大宽度	任意正整数，默认为1200
	max_Height		图片压缩后允许的最大高度	任意正整数，默认为960
	type			图片压缩的方式				目前提供4种方式 height_Auto 根据高度压缩 width_Auto 根据宽度压缩 clip 根据宽高裁剪 none 不压缩（默认）
	imgType			图片的格式					任意的图片MIME，一般由文件中取出，也可以手动定死
	beforeMethod	图片压缩前回调				function()
	endMethod		图片压缩后回调				function()

3) 图片选择提交 $.chooseImage.choose(obj,callback)

obj为getImgData方法取会的内部参数，callback回调提供了上传成功返回的数据接口

由于业务场景可能不同各位需要根据自身需求自己绑定img-submit-btn的点击事件。