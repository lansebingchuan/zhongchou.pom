package com.aisouji.pay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091100484722";//我：这里填的沙箱APPID
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDkt8d6Wmo37KkHo1Mbmy0YsifNGghQ37UoF+HGwFkeLlIGrLVKArYOEhI37k+023gJGNa3xyG7ptje6qs49f5HCNZVtS+MXwCAhRvgGenTCdMwauiQ4cFnR1BuloYA9m6h6XjRU+PKmfCObJNmK3u+Cjycw9l7HfuRF4+NjISzgKQBJOwsWBDptjM/4AfNlK5U0cgdPO/Cj+k2Ayx+hGHk7lVy3QYtH4ow1tH71yAFBm0yjJknYnNwiRntBr2dF/xN0TObwOfowp14Tj8cFonoFubaKaNDxGwAEpgtEgwSDLOGBbc+hm3yxAhzR9eyBpKQflDZyt2T6g1Nwn2g5rUZAgMBAAECggEAWAzgQJw6NBlp4UThI8mTjORZKj6RYQEebrby9l7qcBIJBgTLLhjpGXWfieS2zruG9ImZb9q7g9BxwS+6hw08vQAL86bzDSktukyENLwFJner1AH0UoJ2wFnV/xz3rbsxUCffmUagdUjaMRpfjH/E/Z5XQ0qqmRkr1emqHCYymB99tGz0W4BHL/LpSVdOKsu42fXjqJvhnB0BSEKOJs65wIVEnhpH+f82zrh82O7s1h7Ph1DcleGW0jjvyY+sOPpm2buvnBWo6okB3wl6ES+kVe1T8REhJbsnhvaRSBcdJ9pKctvLdJKakkrt/n6rR6nw8ZQnffb1iamRDRL3SddI4QKBgQDyBowh85tudEIUIIbp17FWJVdPHvPhASXTQc1lcS2G8YDOChYwbj/6EuNdE4N34EXHbijqtJ7wHR9061XpEbot18zhvLjhHK5px/obE7Za3a0va59jmS0+bQmAx7lFhRTyoXalOLTnK9UlJhuNs8ltoALDTIC3gPCX81+uSi/X5QKBgQDx7IbiH10gmYOILaYdZCm5qEwWBI+It+P+ZqAXCNNEkJwCZKCz+Yd2KZtEMG5iq0KWz0httIMisOgH+NP6/ly7kQrHnYL+agCNymRS17cgBytgeJMIlOTZF5ckW0wuXINT5/c2YnTv04+G2dwXmfpAXEowUsJZ2zew04Q6s9BtJQKBgDguVucmsmTwbcpYRpkPraWuo1bFe502c/5XHwO2Qvg5JUouwznYzdcR/V9EwVbZY7lIlAzvgpmAWSX93wJsUAiGOJQKB+yRiNcbSa5xY/oxzRrd47DuBLeh/fu74QEHRHaSDoWJ5UxoO2EVOH4rRs3AwANvxc1TDAZpw0MlC3gVAoGAJxDx6xvzduszUU9FzuM41bE2sCuGXiaL264I1g9nxl+vwbcsOEPoOw2W9Bj6ClbrhtzsLkMDFhwJLBhjatmece+HFRr7Z0gI1NzE/Je3C6X8vDRcUftxRRlJH+baU3Y/AtKnmhGmHoBEYhtWLj2AKtp8ZHdIySfcQ2K0rKIS2z0CgYB5DV+OUOE3KLjpvHeL6fHsGBe2AvHJ32yycBZO45V0WDonnKRAcaSy+Y3HM1HFJVrQHAMNkHQg1jOLOMSHuBPaIzouvE8onCC9fePdSgj7rIiqMh7SB0KNSuADLaYIJQKzNJT6G9Xam7+GDeI2gASF87gnjFjncaYBErNiQxFlhw==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。  
    //我：此处填的沙箱环境的支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzF4gDp1U18FtkbwJno7XBuidgRpYgB/7FhAy8fG2MEnVT1bfRpZV1rF/evmhYzWF3CiQMhCRRCpMC2N2KEWbiBrbPLcuaxB/w81iqIBQLcHSL3de8RjhUwX1HrgWhVoWG4gtCxolgLbv+iQum08KWLWlYwCLWHWqxhiJ2DivlZmijW5LWiCmWMk5+MTijl6QVQgteyZMXHg0pZftXiV3f83RfC5HHqkEG4ZHlpngxA38SIqcpGbTbbjb3L738wn0vwOcBeZk+seAgZQUu0CKQ5cmIkDEz4aw+LQnGbk73YQY4SyLTrHuBj+9d4zm7GaLAfZLymq3vvdETwhteYoPbwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://19e2a49248.imwork.net:41298/pay/notify_url.do";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://19e2a49248.imwork.net:41298/pay/returnUrl.do";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "E:\\Eclipsekongjia_zhongchou\\zhifubao\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

