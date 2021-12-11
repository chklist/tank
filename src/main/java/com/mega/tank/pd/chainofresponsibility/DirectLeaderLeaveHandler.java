package com.mega.tank.pd.chainofresponsibility;

public class DirectLeaderLeaveHandler extends Handler {

    DirectLeaderLeaveHandler(String name) {
        this.name = name;
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() <= MIN) {
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
