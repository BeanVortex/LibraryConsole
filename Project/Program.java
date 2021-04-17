package Project;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Member;
import Project.Service.Library;

public class Program {

    public static void main(String[] args) {
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        Library library = new Library();
        do {
            //using flag to control program flow
            flag = menu(scan, library);
        } while (flag);
        scan.close();

    }

    /** 
     * @param Scanner scan 
     * @param Library library
     * Prints main menu
     */
    public static boolean menu(Scanner scan, Library library) {
        try {
            System.out.println("Welcome to the library\n\nMenu:");
            System.out.println("1) Add Book");
            System.out.println("2) Show Books");
            System.out.println("3) Update Book");
            System.out.println("4) Delete Book");
            System.out.println("5) Search in Books\n");

            System.out.println("6) Add Member");
            System.out.println("7) Show Members");
            System.out.println("8) Update Member");
            System.out.println("9) Delete Member");
            System.out.println("10) Search in Members");
            System.out.println("11) Show persons with expired books\n");

            System.out.println("12) Borrow book ");
            System.out.println("13) Borrow back book ");

            System.out.println("14) Exit \n");
            System.out.print("Choose one of the options above : ");

            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {
            case 1:
                library.getBookService().createBook(scan);
                return true;

            case 2:
                library.getBookService().readBooks(null);
                return true;

            case 3:
                library.getBookService().updateBook(scan);
                return true;

            case 4:
                library.getBookService().deleteBook(scan);
                return true;

            case 5:
                library.getBookService().searchInBooks(scan);
                return true;

            case 6:
                library.getMemberService().createMember(scan);
                return true;

            case 7:
                library.getMemberService().readMember(null);
                return true;

            case 8:
                library.getMemberService().updateMember(scan);
                return true;

            case 9:
                library.getMemberService().deleteMember(scan);
                return true;

            case 10:
                library.getMemberService().searchInMembers(scan);
                return true;

            case 11:
                library.getMemberService().showMembersHasExpiredBooks();
                return true;

            case 12:
                initBorrow(library, scan, "borrow");
                return true;

            case 13:
                initBorrow(library, scan, "");
                return true;

            case 14:
                return false;

            default:
                throw new Exception("Enter options as listed");
            }
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value: " + e.getMessage()
                    + " Enter integer value. Try running program again ---------");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong: " + e.getMessage() + " Try running program again ---------");
            return false;
        }
    }

    /**
     * @param Library library
     * @param Scanner scan 
     * @param String borrow
     * Borrow implementation
     */
    private static void initBorrow(Library library, Scanner scan, String borrow) {
        try {

            System.out.print("\nEnter membership id: ");
            Long membershipId = scan.nextLong();
            scan.nextLine();
            List<Member> members = library.getMemberService().getMembers().stream().filter(member -> {
                return member.getMembershipId() == membershipId;
            }).collect(Collectors.toList());

            if (members.size() == 0) {
                throw new Exception("Member with id of " + membershipId + " does not exists in the library.");
            }

            if (borrow.equals("borrow"))
                library.getBookService().borrowBook(members.get(0), scan);
            else
                library.getBookService().borrowBackBook(members.get(0), scan);
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            initBorrow(library, scan, borrow);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            initBorrow(library, scan, borrow);
        }
    }

}
