package com.tumuer.pojo;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/24
 * @project_name ChatRoom
 * @note 统一返回接口
 *
 * code   0 => 当前操作成功
 * code  99 => 参数缺失 Missing parameter in request
 * code 100 => 无效的用户名 Invalid User Name
 * code 101 => 用户名重复 Duplicate User Name
 */
public class Response {

    private boolean success; // 当前操作有没有成功

    private String message; // 当前操作出错描述，如果success为true, 这字段可为null

    private int code; // 自定义错误码(不是状态码，而是接口自定义错误码)，操作成功默认为 0

    public Response(boolean success, String message, int code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public Response() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
