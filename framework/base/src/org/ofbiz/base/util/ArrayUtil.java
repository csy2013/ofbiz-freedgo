package org.ofbiz.base.util;

import java.util.*;

/**
 * Created by csy on 2015/3/13.
 */
public class ArrayUtil {

    public static boolean[] append(boolean[] array, boolean value) {
        boolean[] newArray = new boolean[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static byte[] append(byte[] array, byte value) {
        byte[] newArray = new byte[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static double[] append(double[] array, double value) {
        double[] newArray = new double[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static float[] append(float[] array, float value) {
        float[] newArray = new float[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static int[] append(int[] array, int value) {
        int[] newArray = new int[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static long[] append(long[] array, long value) {
        long[] newArray = new long[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static short[] append(short[] array, short value) {
        short[] newArray = new short[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Boolean[] append(Boolean[] array, Boolean value) {
        Boolean[] newArray = new Boolean[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Double[] append(Double[] array, Double value) {
        Double[] newArray = new Double[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Float[] append(Float[] array, Float value) {
        Float[] newArray = new Float[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Integer[] append(Integer[] array, Integer value) {
        Integer[] newArray = new Integer[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Long[] append(Long[] array, Long value) {
        Long[] newArray = new Long[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Object[] append(Object[] array, Object value) {
        Object[] newArray = new Object[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Object[][] append(Object[][] array, Object[] value) {
        Object[][] newArray = new Object[array.length + 1][];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static Short[] append(Short[] array, Short value) {
        Short[] newArray = new Short[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static String[] append(String[] array, String value) {
        String[] newArray = new String[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static String[][] append(String[][] array, String[] value) {
        String[][] newArray = new String[array.length + 1][];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public static boolean[] append(boolean[] array1, boolean[] array2) {
        boolean[] newArray = new boolean[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static byte[] append(byte[] array1, byte[] array2) {
        byte[] newArray = new byte[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static double[] append(double[] array1, double[] array2) {
        double[] newArray = new double[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static float[] append(float[] array1, float[] array2) {
        float[] newArray = new float[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static int[] append(int[] array1, int[] array2) {
        int[] newArray = new int[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static long[] append(long[] array1, long[] array2) {
        long[] newArray = new long[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static short[] append(short[] array1, short[] array2) {
        short[] newArray = new short[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Boolean[] append(Boolean[] array1, Boolean[] array2) {
        Boolean[] newArray = new Boolean[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Double[] append(Double[] array1, Double[] array2) {
        Double[] newArray = new Double[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Float[] append(Float[] array1, Float[] array2) {
        Float[] newArray = new Float[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Integer[] append(Integer[] array1, Integer[] array2) {
        Integer[] newArray = new Integer[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Long[] append(Long[] array1, Long[] array2) {
        Long[] newArray = new Long[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Object[] append(Object[] array1, Object[] array2) {
        Object[] newArray = new Object[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Object[][] append(Object[][] array1, Object[][] array2) {
        Object[][] newArray = new Object[array1.length + array2.length][];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Short[] append(Short[] array1, Short[] array2) {
        Short[] newArray = new Short[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static String[] append(String[] array1, String[] array2) {
        String[] newArray = new String[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static String[][] append(String[][] array1, String[][] array2) {
        String[][] newArray = new String[array1.length + array2.length][];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static void combine(
            Object[] array1, Object[] array2, Object[] combinedArray) {

        System.arraycopy(array1, 0, combinedArray, 0, array1.length);

        System.arraycopy(
                array2, 0, combinedArray, array1.length, array2.length);
    }

    public static boolean contains(boolean[] array, boolean value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(byte[] array, byte value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(char[] array, char value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(double[] array, double value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(int[] array, int value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(long[] array, long value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(short[] array, short value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(Object[] array, Object value) {
        if ((array == null) || (array.length == 0) || (value == null)) {
            return false;
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (value.equals(array[i])) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String[] distinct(String[] array) {
        return distinct(array, null);
    }

    public static String[] distinct(
            String[] array, Comparator<String> comparator) {

        if ((array == null) || (array.length == 0)) {
            return array;
        }

        Set<String> set = null;

        if (comparator == null) {
            set = new TreeSet<String>();
        }
        else {
            set = new TreeSet<String>(comparator);
        }

        for (int i = 0; i < array.length; i++) {
            String s = array[i];

            if (!set.contains(s)) {
                set.add(s);
            }
        }

        return set.toArray(new String[set.size()]);
    }

    public static int getLength(Object[] array) {
        if (array == null) {
            return 0;
        }
        else {
            return array.length;
        }
    }

    public static Object getValue(Object[] array, int pos) {
        if ((array == null) || (array.length <= pos)) {
            return null;
        }
        else {
            return array[pos];
        }
    }

    public static boolean[] remove(boolean[] array, boolean value) {
        List<Boolean> list = new ArrayList<Boolean>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Boolean(array[i]));
            }
        }

        return toArray(list.toArray(new Boolean[list.size()]));
    }

    public static byte[] remove(byte[] array, byte value) {
        List<Byte> list = new ArrayList<Byte>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Byte(array[i]));
            }
        }

        return toArray(list.toArray(new Byte[list.size()]));
    }

    public static char[] remove(char[] array, char value) {
        List<Character> list = new ArrayList<Character>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Character(array[i]));
            }
        }

        return toArray(list.toArray(new Character[list.size()]));
    }

    public static double[] remove(double[] array, double value) {
        List<Double> list = new ArrayList<Double>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Double(array[i]));
            }
        }

        return toArray(list.toArray(new Double[list.size()]));
    }

    public static int[] remove(int[] array, int value) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Integer(array[i]));
            }
        }

        return toArray(list.toArray(new Integer[list.size()]));
    }

    public static long[] remove(long[] array, long value) {
        List<Long> list = new ArrayList<Long>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Long(array[i]));
            }
        }

        return toArray(list.toArray(new Long[list.size()]));
    }

    public static short[] remove(short[] array, short value) {
        List<Short> list = new ArrayList<Short>();

        for (int i = 0; i < array.length; i++) {
            if (value != array[i]) {
                list.add(new Short(array[i]));
            }
        }

        return toArray(list.toArray(new Short[list.size()]));
    }

    public static String[] remove(String[] array, String value) {
        List<String> list = new ArrayList<String>();

        for (String s : array) {
            if (!s.equals(value)) {
                list.add(s);
            }
        }

        return list.toArray(new String[list.size()]);
    }

    public static String[] removeByPrefix(String[] array, String prefix) {
        List<String> list = new ArrayList<String>();

        for (String s : array) {
            if (!s.startsWith(prefix)) {
                list.add(s);
            }
        }

        return list.toArray(new String[list.size()]);
    }

    public static Boolean[] toArray(boolean[] array) {
        Boolean[] newArray = new Boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = Boolean.valueOf(array[i]);
        }

        return newArray;
    }

    public static Byte[] toArray(byte[] array) {
        Byte[] newArray = new Byte[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = Byte.valueOf(array[i]);
        }

        return newArray;
    }

    public static Character[] toArray(char[] array) {
        Character[] newArray = new Character[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = Character.valueOf(array[i]);
        }

        return newArray;
    }

    public static Double[] toArray(double[] array) {
        Double[] newArray = new Double[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Double(array[i]);
        }

        return newArray;
    }

    public static Float[] toArray(float[] array) {
        Float[] newArray = new Float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Float(array[i]);
        }

        return newArray;
    }

    public static Integer[] toArray(int[] array) {
        Integer[] newArray = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Integer(array[i]);
        }

        return newArray;
    }

    public static Long[] toArray(long[] array) {
        Long[] newArray = new Long[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Long(array[i]);
        }

        return newArray;
    }

    public static Short[] toArray(short[] array) {
        Short[] newArray = new Short[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Short(array[i]);
        }

        return newArray;
    }

    public static boolean[] toArray(Boolean[] array) {
        boolean[] newArray = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].booleanValue();
        }

        return newArray;
    }

    public static byte[] toArray(Byte[] array) {
        byte[] newArray = new byte[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].byteValue();
        }

        return newArray;
    }

    public static char[] toArray(Character[] array) {
        char[] newArray = new char[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].charValue();
        }

        return newArray;
    }

    public static double[] toArray(Double[] array) {
        double[] newArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].doubleValue();
        }

        return newArray;
    }

    public static float[] toArray(Float[] array) {
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].floatValue();
        }

        return newArray;
    }

    public static int[] toArray(Integer[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].intValue();
        }

        return newArray;
    }

    public static long[] toArray(Long[] array) {
        long[] newArray = new long[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].longValue();
        }

        return newArray;
    }

    public static short[] toArray(Short[] array) {
        short[] newArray = new short[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].shortValue();
        }

        return newArray;
    }

    public static String[] toStringArray(boolean[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(byte[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(char[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(double[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(float[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(int[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(long[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(short[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }

    public static String[] toStringArray(Object[] array) {
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = String.valueOf(array[i]);
        }

        return newArray;
    }
}
