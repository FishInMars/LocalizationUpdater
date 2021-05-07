package com.marsfish;

import com.marsfish.GUI.ZhButtonListener;
import com.marsfish.GUI.EnButtonListener;
import com.marsfish.GUI.GUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalizationUpdater {

    private static int start_flag = 0;

    public static void main(String[] args) throws Exception {
        JPanel panel = new JPanel();

        if (start_flag == 0) {
            GUI.main(null);
            start_flag ++;
        }
        else {
            if (ZhButtonListener.zh_cno != null && EnButtonListener.en_us != null) {
                String Output = ZhButtonListener.zh_cno;
                String AddofZH = ZhButtonListener.zh_cn;
                String AddofEN = EnButtonListener.en_us;
                if (AddofEN != null && AddofZH != null) {
                    TranslationUpdate(AddofZH, AddofEN, Output);
                }
            } else if (GUI.en_us1 != null && GUI.zh_cno1 != null) {
                String Output = GUI.zh_cno1;
                String AddofZH = GUI.zh_cn1;
                String AddofEN = GUI.en_us1;
                if (AddofEN != null && AddofZH != null) {
                    TranslationUpdate(AddofZH, AddofEN, Output);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please check your lang file.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void TranslationUpdate(String AddofZH, String AddofEN, String Output) throws Exception{

        JPanel panel = new JPanel();
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String temp1, temp2, str1, dkey, Sdkey, tmpcheck;
        dkey = checkDuplicateKey(AddofEN);

        String InitialEn = initializeJson(AddofEN, dkey);
        String InitialZh = initializeJson(AddofZH, dkey);

        Map<String, Object> zh_map = new LinkedHashMap<>();
        Map<String, Object> en_map = new LinkedHashMap<>();

        zh_map = gson.fromJson(InitialZh, zh_map.getClass());
        en_map = gson.fromJson(InitialEn, en_map.getClass());

        for (Map.Entry<String, Object> entry1 : en_map.entrySet()) {
            for (Map.Entry<String, Object> entry2 : zh_map.entrySet()) {
                temp1 = entry1.getKey();    temp2 = entry2.getKey();
                if(temp1.equals(temp2)) {
                    en_map.put(entry1.getKey(), entry2.getValue());
                }
            }
        }
        str1 = gson.toJson(en_map);
        tmpcheck = "\"" + dkey + ".*?\"";
        Sdkey = "\"" + dkey + "\"";

        BufferedReader br1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str1.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String tmp1, finalOutput;
        while ((tmp1 = br1.readLine()) != null) {
            if(tmp1.contains(dkey) && dkey != "") {
                tmp1 = tmp1.replaceFirst(tmpcheck, Sdkey);
                sb.append(tmp1).append("\n");
                continue;
            }
            sb.append(tmp1).append("\n");
        }

        finalOutput = sb.toString();
        System.out.println(finalOutput);
        outputString(finalOutput, Output, AddofEN);
        JOptionPane.showMessageDialog(panel, "Update finished！", "Finished", JOptionPane.INFORMATION_MESSAGE);

    }

    public static String initializeJson(String filepath, String dkey) throws Exception {
        String jsonStr;
        int count = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8));
//        FileReader fr = new FileReader(filepath);
//        BufferedReader br = new BufferedReader(fr);
        String s_tmp;
        String s_tmp1;
        String s_tmp2 = dkey;
        StringBuffer sb = new StringBuffer();
        while ((s_tmp = br.readLine ()) != null) {
            if(s_tmp.contains(dkey) && dkey != "") {
                s_tmp1 = "" + count;
                s_tmp2 = s_tmp2.concat(s_tmp1);
                s_tmp = s_tmp.replaceFirst(dkey, s_tmp2);
                s_tmp2 = dkey;
//                s_tmp2 = "_comment";
//                s_tmp = s_tmp.replaceFirst("\"_comment.*?\"","\"_comment\"");
                count++;
                sb.append(s_tmp);
            }
            else{
                sb.append(s_tmp);
            }

        }
        br.close();
        jsonStr = sb.toString();
        return jsonStr;
    }

    public static String checkDuplicateKey(String filepath) throws Exception {
        String ans = "", s_tmp, tmp;
        Pattern pattern = Pattern.compile("\".*?\"");
        Map<String, Integer> checkList = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8));
        while ((s_tmp = br.readLine ()) != null) {
            Matcher matcher = pattern.matcher(s_tmp);
            if(matcher.find()) {
                tmp = matcher.group();
                checkList.put(tmp, checkList.getOrDefault(tmp,0)+1);
            }
        }

        for (Map.Entry<String, Integer> entry : checkList.entrySet()) {
            if(entry.getValue() != 1) {
                ans = entry.getKey();
                break;
            }
        }
        ans = ans.replace("\"","");
        return ans;
    }

    public static void outputString(String s,String add1, String add2) throws Exception{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(add1), StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(add2), StandardCharsets.UTF_8));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String oriStr, tmpStr;
        while((oriStr = br.readLine()) != null) {
            if(oriStr.contains("\"") || oriStr.contains("{") || oriStr.contains("}")) {
                tmpStr = br1.readLine();
                bw.write(tmpStr);
                bw.write("\n");
            }
            else {
                bw.write("\n");
            }
        }
        bw.flush();
    }

}