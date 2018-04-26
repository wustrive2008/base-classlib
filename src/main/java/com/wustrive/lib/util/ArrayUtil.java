package com.wustrive.lib.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wubaoguo
 * @Description: 数组相关操作
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月11日 下午4:33:41
 * @version: v0.0.1
 */
public class ArrayUtil {

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 新建一个空数组
     *
     * @param componentType 元素类型
     * @param newSize       大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 将新元素添加到已有数组中<br/>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param buffer      已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(newElements)) {
            return buffer;
        }

        T[] t = resize(buffer, buffer.length + newElements.length);
        System.arraycopy(newElements, 0, t, buffer.length, newElements.length);
        return t;
    }


    /**
     * 生成一个新的重新设置大小的数组<br/>
     * 新数组的类型为原数组的类型
     *
     * @param buffer  原数组
     * @param newSize 新的数组大小
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize) {
        return resize(buffer, newSize, buffer.getClass().getComponentType());
    }

    /**
     * 生成一个新的重新设置大小的数组
     *
     * @param buffer        原数组
     * @param newSize       新的数组大小
     * @param componentType 数组元素类型
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
        T[] newArray = newArray(componentType, newSize);
        if (isNotEmpty(buffer)) {
            System.arraycopy(buffer, 0, newArray, 0, Math.min(buffer.length, newSize));
        }
        return newArray;
    }


    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        int length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            length += array.length;
        }
        T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);

        length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            System.arraycopy(array, 0, result, length, array.length);
            length += array.length;
        }
        return result;
    }

    /**
     * 克隆数组
     *
     * @param array 被克隆的数组
     * @return 新数组
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }


    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param excludedEnd 结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd   结束的数字（不包含）
     * @param step          步进
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd, int step) {
        if (includedStart > excludedEnd) {
            int tmp = includedStart;
            includedStart = excludedEnd;
            excludedEnd = tmp;
        }

        if (step <= 0) {
            step = 1;
        }

        int deviation = excludedEnd - includedStart;
        int length = deviation / step;
        if (deviation % step != 0) {
            length += 1;
        }
        int[] range = new int[length];
        for (int i = 0; i < length; i++) {
            range[i] = includedStart;
            includedStart += step;
        }
        return range;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        final Class<?> componetType = array.getClass().getComponentType();
        boolean isPrimitive = false;
        if (null != componetType) {
            isPrimitive = componetType.isPrimitive();
        }
        for (T t : array) {
            if (t == value) {
                return true;
            } else if (false == isPrimitive && null != value && value.equals(t)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param keys   键列表
     * @param values 值列表
     * @return Map
     */
    public static <T, K> Map<T, K> zip(T[] keys, K[] values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final int size = Math.min(keys.length, values.length);
        final Map<T, K> map = new HashMap<T, K>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

}
