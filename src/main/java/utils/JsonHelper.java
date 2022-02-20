package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class JsonHelper {

    /**
     * Sorter to help deserialization
     */
    public static class Sorter {
        private String[] sorterArray;

        public Sorter(String[] sorterArray) {
            this.sorterArray = sorterArray;
        }

        public String[] getSorterArray() {
            return sorterArray;
        }
    }


    private JsonHelper() {
    }

    public static String chompString(String string) {
        string = string.replaceAll("(\\r\\n|\\n|\\r)", "");
        return StringUtils.deleteWhitespace(string);
    }

    private static <T> T[] convert(Object[] arr, Class<? extends T[]> clazz) {
        return Arrays.copyOf(arr, arr.length, clazz);
    }

    public static <T> Object[] parseObjectToArray(JSONObject jsonObject, Iterable<T> iterable) {
        int size = jsonObject.size();
        Object[] objects = new Object[size];
        int i = 0;
        for (T key : iterable) {
            Object value = jsonObject.get(key);
            objects[i++] = value;
        }
        return objects;
    }

    public static Object[] parseObjectToArray(String string) {
        JSONObject jsonObject = JSON.parseObject(string);
        return parseObjectToArray(jsonObject, jsonObject.keySet());
    }

    public static Object[] parseObjectToArray(String string, Sorter sorter) {
        JSONObject jsonObject = JSON.parseObject(string);
        return parseObjectToArray(jsonObject, Arrays.asList(sorter.getSorterArray()));
    }

    public static <T> T[] parseObjectToArray(String string, Class<? extends T[]> clazz) {
        Object[] objects = parseObjectToArray(string);
        return convert(objects, clazz);
    }

    public static <T> T[] parseObjectToArray(String string, Class<? extends T[]> clazz, Sorter sorter) {
        Object[] objects = parseObjectToArray(string, sorter);
        return convert(objects, clazz);
    }
}
