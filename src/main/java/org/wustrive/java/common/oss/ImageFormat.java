package org.wustrive.java.common.oss;

/**
 *
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/20 16:50
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public enum ImageFormat {
    JPEG, TIFF, PNG, BMP, GIF, ICO, RAW, PSD, UNKNOWN;
    
    public static ImageFormat getImageFormat(String suffix){
        if("JPEG".equalsIgnoreCase(suffix)){
            return JPEG;
        }else if("JPG".equalsIgnoreCase(suffix)){
            return JPEG;
        }else if("BMP".equalsIgnoreCase(suffix)){
            return BMP;
        }else if("GIF".equalsIgnoreCase(suffix)){
            return GIF;
        }else if("PNG".equalsIgnoreCase(suffix)){
            return PNG;
        }else if("TIFF".equalsIgnoreCase(suffix)){
            return TIFF;
        }else if("TFF".equalsIgnoreCase(suffix)){
            return TIFF;
        }else{
            return UNKNOWN;
        }
    }
    
    public static String getDesc(ImageFormat format){
        if(JPEG == format){
            return "JPEG";
        }else if(BMP == format){
            return "BMP";
        }else if(GIF == format){
            return "GIF";
        }else if(PNG == format){
            return "PNG";
        }else if(TIFF == format){
            return "TIFF";
        }else{
            return "UNKNOWN";
        }
    }
    
    public String getDesc() {
        return ImageFormat.getDesc(this);
    }
}
