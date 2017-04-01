package org.ofbiz.shipment.thirdparty.kdniao;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by changsy on 16/5/18.
 */
public class KdGoldAPIDemo {

    //µÁ…ÃID
    private String EBusinessID="1237100";
    //µÁ…Ãº”√‹ÀΩ‘ø£¨øÏµ›ƒÒÃ·π©£¨◊¢“‚±£π‹£¨≤ª“™–π¬©
    private String AppKey="518a73d8-1f7f-441a-b644-33e77b49d846";
    //«Î«Ûurl
    private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

    /**
     * Json∑Ω Ω ‘⁄œﬂœ¬µ•
     * @throws Exception
     */
    public String orderOnlineByJson() throws Exception{
        String requestData= "{'LogisticsWeight':2.0,"+
                "'LogisticsVol':2.0,"+
                "'HQPOrderDesc':'æ°øÏ…œ√≈ ’º˛',"+
                "'HQPPayType':1,"+
                "'IsNeedPay':2,"+
                "'Payment':121.0,"+
                "'OrderCode':'test_1234567890',"+
                "'StartDate':'2015-05-13 21:20:53',"+
                "'EndDate':'2015-05-14 21:20:53',"+
                "'ShipperCode':'LB',"+
                "'LogisticCode':'109932607391',"+
                "'ToCompany':'ª™Œ™ø∆ºº',"+
                "'ToName':'’≈»˝',"+
                "'ToAddressArea':'…Ó€⁄ –ƒœ…Ω«¯π√Ì¬∑555∫≈',"+
                "'ToTel':'',"+
                "'ToMobile':'13800000000',"+
                "'OrderType':2,"+
                "'ToPostCode':'518128',"+
                "'ToProvinceID':'π„∂´ °',"+
                "'ToCityID':'…Ó€⁄ –',"+
                "'ToExpAreaID':'ƒœ…Ω«¯',"+
                "'FromCompany':'–°√◊ø∆ºº',"+
                "'FromName':'¿ÓÀƒ',"+
                "'FromAddressArea':'…Ó€⁄ –∏£ÃÔ«¯ª™«ø±±¬∑222∫≈',"+
                "'FromTel':'88888888',"+
                "'FromMobile':'',"+
                "'FromPostCode':'529800',"+
                "'FromProvinceID':'π„∂´ °',"+
                "'FromCityID':'…Ó€⁄ –',"+
                "'FromExpAreaID':'∏£ÃÔ«¯',"+
                "'Cost':21.0,"+
                "'OtherCost':2.0,"+
                "'Commoditys':"+
                "[{"+
                "'Goodsquantity':12,"+
                "'GoodsName':' ÷ª˙∆¡ƒª',"+
                "'GoodsCode':'kjyhu878787',"+
                "'GoodsPrice':121.0,"+
                "}]}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1001");
        String dataSign=encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result=sendPost(ReqURL, params);

        //∏˘æ›π´Àæ“µŒÒ¥¶¿Ì∑µªÿµƒ–≈œ¢......

        return result;
    }

    /**
     * XML∑Ω Ω ‘⁄œﬂœ¬µ•
     * @throws Exception
     */
    public String orderOnlineByXml() throws Exception{
        String requestData= "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+
                "<Content>"+
                "<LogisticsWeight>1.5</LogisticsWeight>"+
                "<OrderCode>test_123456789</OrderCode>"+
                "<LogisticsVol>0.5</LogisticsVol>"+
                "<HQPOrderDesc>≤‚ ‘‘⁄œﬂœ¬µ•Ω”ø⁄ 20150510</HQPOrderDesc>"+
                "<HQPPayType>1</HQPPayType>"+
                "<IsNeedPay>1</IsNeedPay>"+
                "<Payment>1000</Payment>"+
                "<OrderType>1</OrderType>"+
                "<StartDate>2015-05-10 19:36:50</StartDate>"+
                "<EndDate>2015-05-11 19:36:50</EndDate>"+
                "<ShipperCode>LB</ShipperCode>"+
                "<LogisticCode></LogisticCode>"+
                "<ToName>’≈»˝</ToName>"+
                "<ToAddressArea>…Ó€⁄ –ƒœ…Ω«¯ƒœ–¬¬∑2055∫≈</ToAddressArea>"+
                "<ToTel></ToTel>"+
                "<ToMobile>13800000000</ToMobile>"+
                "<ToPostCode></ToPostCode>"+
                "<ToProvinceID>π„∂´ °</ToProvinceID>"+
                "<ToCityID>…Ó€⁄ –</ToCityID>"+
                "<ToExpAreaID>ƒœ…Ω«¯</ToExpAreaID>"+
                "<FromCompany>øÏµ›ƒÒø∆ºº</FromCompany>"+
                "<FromName>¿ÓÀƒ</FromName>"+
                "<FromAddressArea>…Ó€⁄ –∏£ÃÔ«¯ª™«ø±±¬∑211∫≈</FromAddressArea>"+
                "<FromTel></FromTel>"+
                "<FromMobile>13888888888</FromMobile>"+
                "<FromPostCode></FromPostCode>"+
                "<FromProvinceID>π„∂´ °</FromProvinceID>"+
                "<FromCityID>…Ó€⁄ –</FromCityID>"+
                "<FromExpAreaID>∏£ÃÔ«¯</FromExpAreaID>"+
                "<Cost>12</Cost>"+
                "<OtherCost>1</OtherCost>"+
                "<Commoditys>"+
                "<Commodity>"+
                "<GoodsName>ª›∆’œ‘ æ∆˜</GoodsName>"+
                "<GoodsCode>ABCD_123456789</GoodsCode>"+
                "<Goodsquantity>2</Goodsquantity>"+
                "<GoodsPrice>850</GoodsPrice>"+
                "</Commodity>"+
                "<Commodity>"+
                "<GoodsName>…Ò÷›± º«±æ</GoodsName>"+
                "<GoodsCode>QWERT_456456</GoodsCode>"+
                "<Goodsquantity>2</Goodsquantity>"+
                "<GoodsPrice>4200</GoodsPrice>"+
                "</Commodity>"+
                "</Commoditys>"+
                "</Content>";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1001");
        String dataSign=encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "1");

        String result=sendPost(ReqURL, params);

        //∏˘æ›π´Àæ“µŒÒ¥¶¿Ì∑µªÿµƒ–≈œ¢......

        return result;
    }

    /**
     * MD5º”√‹
     * @param str ƒ⁄»›
     * @param charset ±‡¬Î∑Ω Ω
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val < 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64±‡¬Î
     * @param str ƒ⁄»›
     * @param charset ±‡¬Î∑Ω Ω
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * µÁ…ÃSign«©√˚…˙≥…
     * @param content ƒ⁄»›
     * @param keyValue Appkey
     * @param charset ±‡¬Î∑Ω Ω
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign«©√˚
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws Exception
    {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * œÚ÷∏∂® URL ∑¢ÀÕPOST∑Ω∑®µƒ«Î«Û
     * @param url ∑¢ÀÕ«Î«Ûµƒ URL
     * @param params «Î«Ûµƒ≤Œ ˝ºØ∫œ
     * @return ‘∂≥Ã◊ ‘¥µƒœÏ”¶Ω·π˚
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // ∑¢ÀÕPOST«Î«Û±ÿ–Î…Ë÷√»Áœ¬¡Ω––
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST∑Ω∑®
            conn.setRequestMethod("POST");
            // …Ë÷√Õ®”√µƒ«Î«Û Ù–‘
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // ªÒ»°URLConnection∂‘œÛ∂‘”¶µƒ ‰≥ˆ¡˜
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // ∑¢ÀÕ«Î«Û≤Œ ˝
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush ‰≥ˆ¡˜µƒª∫≥Â
            out.flush();
            // ∂®“ÂBufferedReader ‰»Î¡˜¿¥∂¡»°URLµƒœÏ”¶
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // π”√finallyøÈ¿¥πÿ±’ ‰≥ˆ¡˜°¢ ‰»Î¡˜
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
