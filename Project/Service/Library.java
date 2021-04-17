package Project.Service;

public class Library {

    private final MemberService memberService = new MemberService();
    private final BookService bookService = new BookService();


    public MemberService getMemberService() {
        return this.memberService;
    }


    public BookService getBookService() {
        return this.bookService;
    }


}
