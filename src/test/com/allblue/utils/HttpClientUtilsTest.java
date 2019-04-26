package com.allblue.utils;

import com.allblue.model.dto.HttpClientResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/3/15 16:22
 **/
public class HttpClientUtilsTest {

    /**
     * 读入TXT文件
     */
    public static String readFile(String pathname) {
        // pathname绝对路径或相对路径都可以，写入文件时演示相对路径,读取路径的文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            StringBuilder s = new StringBuilder();
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入TXT文件
     */
    public static void writeFile() {
        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

//        String url = "https://board.luckincoffee.com/api/data/realTimeData?cityId=-1&districtId=-1&departmentId=-1&type=1&channel=0";
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Cookie", "lucky-bi-intranet-test01-sid=3b486317-096f-483c-a902-ccb12763cc62");
//        HttpClientResult result = HttpClientUtils.doGet(url, headers, null);
//
//        String response_data = result.getContent();
        String response_data = readFile("C:\\Users\\Xone\\Desktop\\1.txt");
        if (response_data != null && response_data.startsWith("{")) {
            JSONObject data_obj = new JSONObject(response_data);
            JSONArray content = data_obj.getJSONArray("re");
            int count = 0;
            for (int i = 0; i < content.length(); i++) {

                JSONObject content1 = content.getJSONObject(i);
                JSONArray dataList1 = content1.getJSONArray("dataList");

                JSONObject content2 = dataList1.getJSONObject(2);
                JSONArray dataList2 = content2.getJSONArray("dataList");

                JSONObject content3 = dataList2.getJSONObject(5);
                int x = Integer.valueOf(content3.getString("value"));

                System.out.println("第" + (i + 1) + "个值：" + x);
                count += x;
            }
            System.out.println("总值为:" + count);
        } else {
            System.out.println("数据格式不正确！");
        }
    }
}
