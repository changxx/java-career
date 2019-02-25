package com.changxx.practice.localcache;

public class UserInfo {
    private String userId;
    private String userName;
    private String cardNo;

    public UserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("userId： " + userId + " finalize。");
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}