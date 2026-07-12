package library.model;

import java.time.LocalDate;

public class Member {

    private int memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate joinDate;

    public Member(String name,String email,String phone){
        this.name=name;
        this.email=email;
        this.phone=phone;
    }

    public Member(int memberId,String name,String email,String phone,LocalDate joinDate){
        this.memberId=memberId;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.joinDate=joinDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString(){
        return "Member{"+
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }

}
