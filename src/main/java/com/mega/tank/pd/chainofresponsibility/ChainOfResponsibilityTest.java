package com.mega.tank.pd.chainofresponsibility;

import org.junit.Test;

public class ChainOfResponsibilityTest {

    @Test
    public void testMain() {
        LeaveRequest request = new LeaveRequest(1, "小明");

        Handler directLeader = new DirectLeaderLeaveHandler("县令");
        Handler deptManager = new DeptManagerLeaveHandler("知府");
        Handler gManager = new GManagerLeaveHandler("京兆尹");
        directLeader.setNextHandler(deptManager);
        deptManager.setNextHandler(gManager);

        directLeader.handleRequest(request);

    }
}
