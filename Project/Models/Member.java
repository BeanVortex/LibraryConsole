package Project.Models;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private String name;
    private Integer age;
    private Long membershipId;
    private Character gender;
    private final List<Book> books = new ArrayList<>();

    public Member() {
    }

    public Member(String name, Integer age, Long membershipId, Character gender) {
        this.name = name;
        this.age = age;
        this.membershipId = membershipId;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getMembershipId() {
        return this.membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    public Character getGender() {
        return this.gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public List<Book> getBooks() {
        return this.books;
    }

}
