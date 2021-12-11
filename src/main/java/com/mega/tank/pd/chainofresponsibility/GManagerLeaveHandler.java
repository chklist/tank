package com.mega.tank.pd.chainofresponsibility;

public class GManagerLeaveHandler extends Handler {

    GManagerLeaveHandler(String name) {
        this.name = name;
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() > MIDDLE && request.getLeaveDays() <= MAX) {
            System.out.println("直接主管:" + name + ",已经处理;流程结束。");
            return;
        }
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("审批拒绝");
        }
    }
}
