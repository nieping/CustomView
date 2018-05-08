package com.example.quickindex;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Administrator on 2018/5/2.
 */

public class PinYinUtils {
    /**
     * 得到汉字的拼音
     * @param hanzi
     * @return
     */
    public static String getPinYin(String hanzi){
        String pinyin= "";
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //由于不能直接对多个汉字转换，只能对单个汉字转换
        char[] arr = hanzi.toCharArray();
        for (int i = 0; i < arr.length;i++){
            if (Character.isWhitespace(arr[i]))continue;//如果是空格，则不处理，进行下一次遍历
            if (arr[i] > 127){ //汉字是2个字节存储，肯定大于127，所以大于127就可以当汉字转换
                try {
                    //由于多音字的存在，
                    String[] pinyingArr= PinyinHelper.toHanyuPinyinStringArray(arr[i],format);
                    if (pinyingArr != null){
                        pinyin += pinyingArr[0];
                    }else {
                        pinyin += arr[i];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    pinyin += arr[i];
                }
            }
        }
        return pinyin;
    }
}
