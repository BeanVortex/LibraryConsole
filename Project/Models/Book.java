package Project.Models;

import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String name;
    private String author;
    private Boolean isBorrowed = false;
    private LocalDateTime borrowExpiration;
    private Member borrowedMember;

    public Book() {
    }

    public Book(Long id, String name, String author, Boolean isBorrowed, LocalDateTime borrowExpiration, Member borrowedMember) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrowExpiration = borrowExpiration;
        this.borrowedMember = borrowedMember;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isBorrowed() {
        return this.isBorrowed;
    }

    public void setBorrowed(Boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public LocalDateTime getBorrowExpiration() {
        return this.borrowExpiration;
    }

    public void setBorrowExpiration(LocalDateTime borrowExpiration) {
        this.borrowExpiration = borrowExpiration;
    }


    public Boolean isIsBorrowed() {
        return this.isBorrowed;
    }

    public Boolean getIsBorrowed() {
        return this.isBorrowed;
    }

    public void setIsBorrowed(Boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public Member getBorrowedMember() {
        return this.borrowedMember;
    }

    public void setBorrowedMember(Member borrowedMember) {
        this.borrowedMember = borrowedMember;
    }


}
