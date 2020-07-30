package org.wustrive.java.common.util;

/**
 * Description: 地图相关操作
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/4/26 15:31
 */
public class MapsUtil {
    /**
     * 求地图上两个点的距离 方法1
     *
     * @param long1
     * @param lat1
     * @param long2
     * @param lat2
     * @return 距离 单位 m
     */
    public static double distance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 求地图上两个点的距离 方法2
     *
     * @param long1
     * @param lat1
     * @param long2
     * @param lat2
     * @return 距离 单位 m
     */
    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
        double R = 6378137; // 地球半径
        double x, y, distance;
        x = (longt2 - longt1) * Math.PI * R * Math.cos(((lat1 + lat2) / 2) * Math.PI / 180) / 180;
        y = (lat2 - lat1) * Math.PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }


    public static void main(String[] args) {
        System.out.println(Math.round(getDistance(113.664983, 34.759949, 113.676669, 34.758921)));
    }

}
