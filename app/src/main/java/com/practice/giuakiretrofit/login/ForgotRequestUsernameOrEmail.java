package com.practice.giuakiretrofit.login;

public class ForgotRequestUsernameOrEmail {

    private String usernameOrEmail;

    public ForgotRequestUsernameOrEmail(String usernameoremail) {
        this.usernameOrEmail = usernameoremail;
    }

    public String getUsernameoremail() {
        return usernameOrEmail;
    }

    public void setUsernameoremail(String usernameoremail) {
        this.usernameOrEmail = usernameoremail;
    }
}
