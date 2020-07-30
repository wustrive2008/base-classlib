package org.wustrive.java.common.util;

import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * Description:地理位置相关工具
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/11/7 13:56
 */
public class AreaUtil {

    /**
     * 地址截取  如 河南省-郑州市-金水区-经三路街道-金城国际 截取为 金水区-经三路街道-金城国际
     *
     * @param address
     * @return
     */
    public static String getSubAddress(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        String[] adds = address.split("-");
        if (adds.length < 3) {
            return address;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 2; i < adds.length; i++) {
            res.append(adds[i]).append("-");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }


    /**
     * 根据经纬度获取城市名称
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static String getCityNameByLatAndLng(String longitude, String latitude) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + "8cfae3716b48f67cc6d304122e113213" + "&location=" + latitude + "," + longitude + "&output=json&pois=1";
        String responseStr = HttpRequest.get(url)
                .accept("application/json") //Sets request header
                .body();
        String city = JSONObject.fromObject(responseStr).getJSONObject("result")
                .getJSONObject("addressComponent").getString("city");
        return city;
    }

    /**
     * 根据经纬度获取城市名称
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static String getDistrictNameByLatAndLng(String longitude, String latitude) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + "8cfae3716b48f67cc6d304122e113213" + "&location=" + latitude + "," + longitude + "&output=json&pois=1";
        String responseStr = HttpRequest.get(url)
                .accept("application/json") //Sets request header
                .body();
        String district = JSONObject.fromObject(responseStr).getJSONObject("result")
                .getJSONObject("addressComponent").getString("district");
        return district;
    }
}
