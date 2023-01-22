package top.b0x0.jmb.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String message;

    public Result() {
    }

    /********************** 成功模板 **********************/
    public static <T> Result ok() {
        return new Result().setCode(200).setMessage("操作成功");
    }

    public static <T> Result ok(Integer code, String message) {
        return new Result().setCode(code).setMessage(message);
    }

    public static <T> Result<T> ok(T data) {
        return ok().setData(data);
    }

    public static <T> Result<T> ok(T data, Integer code, String message) {
        return new Result().setCode(code).setMessage(message).setData(data);
    }

    /********************** 失败模板 **********************/
    public static Result fail(String message) {
        Result response = new Result();
        response.setCode(400).setMessage(message);
        return response;
    }

    public static Result fail(Integer code, String message) {
        return new Result().setCode(code).setMessage(message);
    }

    public static <T> Result<T> fail(T data, Integer code, String message) {
        return new Result().setCode(code).setMessage(message).setData(data);
    }
}
