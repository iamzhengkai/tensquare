package entity;

public class Result<T> {
    private Integer code;
    private Boolean flag;
    private String messsage;
    private T data;

    public Result() {
    }

    public Result(Boolean flag,Integer code,  String messsage) {
        this.code = code;
        this.flag = flag;
        this.messsage = messsage;
    }

    public Result(Boolean flag, Integer code, String messsage, T data) {
        this.code = code;
        this.flag = flag;
        this.messsage = messsage;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", flag=" + flag +
                ", messsage='" + messsage + '\'' +
                ", data=" + data +
                '}';
    }
}
