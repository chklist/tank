package com.mega.tank.pd.chainofresponsibility;

public abstract class Handler {

    /**
     * 直接主管审批处理的请假天数
     */
    int MIN = 1;
    /**
     * 部门经理处理的请假天数
     */
    int MIDDLE = 3;
    /**
     * 总经理处理的请假天数
     */
    int MAX = 30;

    /**
     * 领导名称
     */
    protected String name;

    Handler nextHandler;

    void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(LeaveRequest request);

}
