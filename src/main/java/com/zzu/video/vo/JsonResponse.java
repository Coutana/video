package com.zzu.video.vo;

import lombok.Data;

/**
 * 注释
 *
 * @author Coutana
 * @since 2.9.0
 */
@Data
public class JsonResponse<T> {

    private int code;

    private String msg;

    private T data;

    public JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.data = data;
        msg = "success";
        code = 200;
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail() {
        return new JsonResponse<>(1, "error");
    }

    public static JsonResponse<String> fail(int code, String msg) {
        return new JsonResponse<>(code, msg);
    }
}
