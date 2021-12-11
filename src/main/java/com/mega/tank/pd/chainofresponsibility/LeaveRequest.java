package com.mega.tank.pd.chainofresponsibility;

class LeaveRequest {
    /**
     * 天数
     */
    private int leaveDays;

    /**
     * 姓名
     */
    private String name;

    LeaveRequest(int leaveDays, String name) {
        this.leaveDays = leaveDays;
        this.name = name;
    }

    int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LeaveRequest{" +
                "leaveDays=" + leaveDays +
                ", name='" + name + '\'' +
                '}';
    }
}
