package com.tumuer.web.servlet;

/**
 * @author tumuer
 * @date 2022/10/4
 * @version v0.1.0
 * @project_name ChatRoom
 * @note 用户登录
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tumuer.pojo.LoginForm;
import com.tumuer.pojo.Response;
import com.tumuer.service.UserService;
import com.tumuer.service.impl.UserServiceImpl;
import com.tumuer.util.RequestBodyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {

    private final UserService userService = new UserServiceImpl(); // 调用 user service层
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        boolean success = false;
        String message = null;
        int code = 0; // 默认成功
        String json = RequestBodyUtils.getJson(request);
        if (json.length() > 0) {
            try {
                LoginForm loginForm = gson.fromJson(json, LoginForm.class);
                String nickName = loginForm.getNickName();
                // 检查用户名是否为null或为空字符串
                if (nickName != null && nickName.length() > 0) {
                    nickName = nickName.trim(); // 删除字符串的头尾空白符
                    int loginStatus = userService.login(nickName);
                    if (loginStatus == 1) {
                        // 用户名不重复,登录成功
                        success = true;
                        // 获取session对象并存储用户数据
                        HttpSession session = request.getSession();
                        session.setAttribute("user",nickName);
                    } else {
                        code = 101;
                        message = "Duplicated user name";
                    }
                } else {
                    code = 100;
                    message = "Invalid user name";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message = "Missing parameter in request";
            code = 99;
        }
        Response loginResponse = new Response(success, message, code);
        String s = gson.toJson(loginResponse);
        response.getWriter().write(s);
    }
}
