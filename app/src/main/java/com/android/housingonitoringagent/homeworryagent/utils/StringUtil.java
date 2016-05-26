package com.android.housingonitoringagent.homeworryagent.utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class StringUtil {
    /**
     *
     * @param str 被计算的字符串
     * @return 返回字符串的长度
     */
    public static int getBytesLength(String str) {
        return str == null ? 0 : str.getBytes().length;
    }

    /**
     * 是否包含表情
     *
     * @param codePoint Unicode code point
     * @return 如果不包含 返回false,包含 则返回true
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || (codePoint >= 0x10000));
    }

    public static boolean hasEmojiCharacter(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source 源字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        StringBuilder buf = null;
        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {// 如果不包含 则将字符append
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return source;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            return buf.toString();
        }
    }

    //截取字符串，中文算2个字符
    public static String cutString(String str, int maxLength) {
        if (maxLength < 1) {
            return "";
        }

        char arr[] = str.toCharArray();
        int length = 0;
        int charLength = 0;
        for(int i=0; i < arr.length; i++) {
            char c = arr[i];
            if((c >= 0x0391 && c <= 0xFFE5)) {// 中文字符
                if (charLength + 2 > maxLength) {
                    break;
                }
                charLength += 2;
            } else {
                charLength += 1;
            }

            length += 1;
            if (charLength == maxLength) {
                break;
            }
        }

        return str.substring(0, length);
    }

    public static CutResult cutStringByTag(String str, String startTag, String endTag) {
        if (str == null || startTag == null || endTag == null) {
            return null;
        }

        // 从字符串创建StringBuilder，这个StringBuilder将被进行裁剪，处理完成后的输出结果即为剪裁后的字符串
        StringBuilder strBuilder = new StringBuilder(str);

        // 字符串长度
        int strLength = str.length();
        // 开始标识符的长度
        int startTagLength = startTag.length();
        // 结束标识符的长度
        int endTagLength = endTag.length();
        // 被截去的长度
        int delLength = 0;

        CutResult result = new CutResult();
        result.srcString = str;
        result.subStrings = new ArrayList<>();

        for (int i = 0; i < strLength; ) {
            int delStart = str.indexOf(startTag, i);
            if (delStart == -1) {
                break;
            }
            int cutStart = delStart + startTagLength;

            int cutEnd = str.indexOf(endTag, cutStart);
            if (cutEnd == -1) {
                break;
            }
            int delEnd = cutEnd + endTagLength;

            strBuilder.delete(delStart - delLength, delEnd - delLength);
            delLength += delEnd - delStart;

            SubString subString = new SubString();
            subString.text = str.substring(cutStart, cutEnd);
            subString.start = delStart;
            subString.end = delEnd;
            result.subStrings.add(subString);

            i = delEnd;
        }

        result.resultString = strBuilder.toString();

        return result;
    }

    public static class CutResult {
        // 元字符串
        public String srcString;
        // 裁剪后的字符串
        public String resultString;
        // 截取的子字符串
        public ArrayList<SubString> subStrings;
    }

    public static class SubString {
        // 截取的子字符串
        public String text;
        // 子字符串在原字符串的起始位置
        public int start;
        // 子字符串在原字符串的结束位置
        public int end;
    }
}
