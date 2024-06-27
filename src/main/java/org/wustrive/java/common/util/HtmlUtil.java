package org.wustrive.java.common.util;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: html相关操作
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/4/26 15:31
 */
public class HtmlUtil {
    protected static final Log log = LogFactory.get(FileUtil.class);

    // 获取img标签正则  
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    //图片地址的元素前缀 
    private static final String PREFIX = "src=\"";
    // 获取src路径的正则  
    private static final String IMGSRC_REG = PREFIX + "http:\"?(.*?)(\"|>|\\s+)";
    //前缀长度
    private static final Integer PREFIX_LEN = PREFIX.length();

    /**
     * html页面取文本
     *
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_control;
        Matcher m_control;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            String regEx_controlChar = "\\r\\n|\\r|\\n";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_control = Pattern.compile(regEx_controlChar, Pattern.CASE_INSENSITIVE);
            m_control = p_control.matcher(htmlStr);
            htmlStr = m_control.replaceAll(""); // 过滤控制符

            textStr = htmlStr;

        } catch (Exception e) {
            log.error("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    /***
     * 获取ImageUrl地址
     *
     * @param HTML
     * @return
     */
    public static List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /*** 
     * 获取ImageSrc地址 
     *
     * @param listImageUrl
     * @return
     */
    public static List<String> getImageSrc(List<String> listImageUrl) {
        List<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(PREFIX_LEN, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    public static void main(String[] args) {
        /*String text = "fdsafdsf<fdsfads />fdsaf\r\ndsfdfdsf\ndsfdsdfdsafdsfs<input />fdsf\rds";
    	System.out.println(html2Text(text));*/
        String text = "<span style=\"color: rgb(51, 51, 51); font-family: 宋体, Arial; font-size: 14px; line-height: 26px;\"><span style=\"color: rgb(51, 51, 51); font-family: 宋体, Arial; font-size: 14px; line-height: 26px;\"><img src=\"http://d.youth.cn/shrgch/201609/W020160905744705940851.jpg\" title=\"\" alt=\"http://d.youth.cn/shrgch/201609/W020160905744705940851.jpg\"/></span></span>";
        System.err.println(HtmlUtil.getImageUrl(text));
        System.out.println(HtmlUtil.getImageSrc(HtmlUtil.getImageUrl(text)));
    }
}
