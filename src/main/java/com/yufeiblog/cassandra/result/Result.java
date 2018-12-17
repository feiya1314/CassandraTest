package main.java.com.yufeiblog.cassandra.result;

import main.java.com.yufeiblog.cassandra.common.Constant;

public abstract class Result {
    private String returnCode;
    private String returnMsg;
    public boolean isSuccess(){
        if(Constant.SUCCESS.equals(returnCode)){
            return true;
        }
        return false;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
