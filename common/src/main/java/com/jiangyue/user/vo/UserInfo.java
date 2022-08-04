package com.jiangyue.user.vo;

/**
 * @author liuyongkang
 * @date Create in 2022/7/6 14:55
 */
public class UserInfo {

    private Long id;

    private String name;

    private Integer age;

    private String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
