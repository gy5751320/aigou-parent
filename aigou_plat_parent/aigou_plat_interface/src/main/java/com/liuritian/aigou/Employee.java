package com.liuritian.aigou;

public class Employee {
    private Long id;
    private String usernmae;//登录账户
    private String password;//密码

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", usernmae='" + usernmae + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsernmae() {
        return usernmae;
    }

    public void setUsernmae(String usernmae) {
        this.usernmae = usernmae;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
