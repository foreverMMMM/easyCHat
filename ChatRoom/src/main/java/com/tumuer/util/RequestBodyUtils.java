package com.tumuer.util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/5
 * @project_name ChatRoom
 * @note
 */
public class RequestBodyUtils {

    public static String getJson(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        String inputStr;
        try {
            ServletInputStream inputStream = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (
                    (inputStr = bufferedReader.readLine()) != null
            ) {
                stringBuilder.append(inputStr.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }
}
