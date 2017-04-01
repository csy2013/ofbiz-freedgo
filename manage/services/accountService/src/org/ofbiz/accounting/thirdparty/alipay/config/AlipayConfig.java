package org.ofbiz.accounting.thirdparty.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String alipay_public_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPkCGmp1vTlTYa5Ut0923u7vqsEe6j6qaFYlDHzjX2pnEDRkANLWD99QjIhmLdCClS7tYuxrVtfUiA9Ctpti4avM8BFvo7mdm776Qx54kefnAE6JyJgmD/Bgh2oBpzwgm6GbfZleF4iOS+K3feLQdlCQFqLjPuiQPg65hYLRT+lPAgMBAAECgYEA4glRt2Ano/kXy6tlN4I9foIa6/HHS5le2wwImPnBbJIf+J7qzHi6scmpuEugvQ08pPuid+A8VfG8jLXPk6AgHwylRFuU+5uddX3pS7kPRrriT1m0gnz9/Z1L392ZUbrgE9mJ5VldnlOt50pIFGuRi+RIVQFvpZA/vHHm1WFP1lECQQD+cy/HJQMJVfWsykRm9VKiW6E1lqKutHnI7uOQO+9p7vveAn695O8Uzbjol5zOM95gmnX2CefEveahwyIWqDmpAkEA+oZuIa8JeRFSVMwHh8A9dPjWZfarPjNTMFFMYfLNAwWo4y6bZIwW78O3MlyGoqf4ArSN7gAVK7+VFuOLpN8WNwJAE9QADiG5OdoD+gINEITfep1vU5C4fqq7rsfG3e99uaCQ6f0ByWR0qQda+G0QIBV0p8yBkrm04OT3evWMy30yeQJAAYZ1/zCAn0MSvD9twE7UUPT+4SoKStunIwErtOD0jJMpPxDWk1si/Jy1bdIQY8cgBGwy/QL1HgoRj00dWYieKwJAeVzT12xFyxBEJZ8TVx7Ys3PmLAfe8KcwtFV3XP0VbBHoF5pCqe437fnMqA8QRCopPxB485A7CAsUMY2Gxv3ndQ==";
    // 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_wap_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

  public static String private_key = "";
  public static String wap_private_key = "";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网址/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://商户网址/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "alipay.wap.create.direct.pay.by.user";	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
  public static String anti_phishing_key = "";
  // 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
  public static String exter_invoke_ip = "";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

