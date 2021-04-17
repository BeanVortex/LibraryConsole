package Project.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Member;

public class MemberService {

    private final List<Member> members = new ArrayList<>();

    /** 
    * Create Members
    * Inputs: book name, book author, book id
    */
    public void createMember(Scanner scan) {
        try {
            Member member = new Member();

            System.out.print("Enter member name: ");
            member.setName(scan.nextLine());

            System.err.print("Enter member gender: ");
            char gender = scan.nextLine().charAt(0);
            // checks gender exists or not
            switch (gender) {
            case 'm':
            case 'f':
            case 'M':
            case 'F':
                break;
            default:
                throw new Exception("The gender " + gender + " does not exist");

            }
            member.setGender(gender);

            System.out.print("Enter member age: ");
            Integer age = scan.nextInt();
            scan.nextLine();
            member.setAge(age);

            System.out.print("Enter member membership id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;
            // checks if member is already member or not
            if (doesMemberExist(id))
                throw new Exception("member with membership id of " + id + " exists.");

            member.setMembershipId(id);
            // Registers member to the library
            members.add(member);

            System.out.println("Successfully created member with membership id of " + id + "---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for membership or age. Try again ---------\n");
            scan.nextLine();
            createMember(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            createMember(scan);
        }
    }

    /** 
     * @param Long id
     * Checks if a member with given id exists in library
     */
    private boolean doesMemberExist(Long id) {
        for (Member member : members) {
            if (member.getMembershipId() == id)
                return true;
        }
        return false;
    }

    /**
    * @param List<Member> members
    * Iterates members and prints its data 
    * */
    public void readMember(List<Member> members) {
        System.out.println("Members data ---------");
        if (members == null) {
            members = this.members;
        }
        // Used Java 8 feature called lambda expression
        members.forEach(member -> {
            System.out.println("Member name: " + member.getName());
            System.out.println("Member gender: " + member.getGender());
            System.out.println("Member age: " + member.getAge());
            System.out.println("Member membership id: " + member.getMembershipId());
            System.out.println("Borrowed books: ");
            // Checks if member has any book borrowed or not, if yes prints it
            if (member.getBooks().size() == 0)
                System.out.println("Member has no borrowed books!!!");
            else {
                member.getBooks().forEach(book -> {
                    System.out.println("Book name: " + book.getName());
                    System.out.println("Book author: " + book.getAuthor());
                    System.out.println("Book id: " + book.getId());
                });
            }
            System.out.println();
        });
        System.out.println("---------\n");
    }

    /** 
    * @param Long id
    * Finds member with its membership id and updates its data with new inputs
    */
    public void updateMember(Scanner scan) {

        try {
            System.out.print("Enter member membership id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;

            // Used lambda expression to filter and find a book with given id
            List<Member> filteredMembers = members.stream().filter(member -> {
                return member.getMembershipId() == id;
            }).collect(Collectors.toList());

            Member member = null;
            // Checks if List of filtered books is not empty
            if (filteredMembers.size() == 0)
                throw new Exception("Member with membership id of " + id + " does not exists.");
            else
                member = filteredMembers.get(0);

            System.out.print("Enter new name for member: ");
            member.setName(scan.nextLine());
            System.out.print("Enter new age for member: ");
            Integer age = scan.nextInt();
            scan.nextLine();
            member.setAge(age);
            // finding book index in the list using indexOf() method
            Integer index = members.indexOf(member);
            members.set(index, member);
            System.out.println("Successfully updated member with membership id of " + id + "---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for age. Try again ---------\n");
            scan.nextLine();
            updateMember(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            updateMember(scan);
        }

    }

    /** 
    * @param Long id
    * Takes an membership id and find member with that id and deletes it
    */
    public void deleteMember(Scanner scan) {
        try {
            System.out.print("Enter member membership id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;

            // Used lambda expression to filter and find a book with given id
            List<Member> filteredMembers = members.stream().filter(member -> {
                return member.getMembershipId() == id;
            }).collect(Collectors.toList());

            Member member = null;
            // Checks if List of filtered books is not empty
            if (filteredMembers.size() == 0)
                throw new Exception("Book with id of " + id + " does not exists.");
            else
                member = filteredMembers.get(0);

            // Removes member. if returns true that means it was successful
            if (members.remove(member))
                System.out.println("Successfully deleted member with membership id of " + id + "---------\n");
            else
                throw new Exception("Something went wrong.\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for age. Try again ---------\n");
            scan.nextLine();
            updateMember(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            deleteMember(scan);
        }
    }

    /** 
    * Search in members with 3 options: 1. Name 2. Gender Male 3. Gender Female
    */
    public void searchInMembers(Scanner scan) {
        try {
            System.out.println("Search by: (enter option)");
            System.out.println("1) Name\n2) Gender\n-1) Exit");
            // external private method to avoid extending this method
            List<Member> foundMembers = findMembers(scan);
            if (foundMembers == null)
                return;
            System.out.println("Founded members data ---------");
            // Printing foundedBooks data with lambda 
            foundMembers.forEach(member -> {
                System.out.println("Member name: " + member.getName());
                System.out.println("Member gender: " + member.getGender());
                System.out.println("Member age: " + member.getAge());
                System.out.println("Member membership id: " + member.getMembershipId());
                System.out.println("Borrowed books: ");
                // Checks if member has any book borrowed or not, if yes prints it
                if (member.getBooks().size() == 0)
                    System.out.println("Member has no borrowed books!!!");
                else {
                    member.getBooks().forEach(book -> {
                        System.out.println("Book name: " + book.getName());
                        System.out.println("Book author: " + book.getAuthor());
                        System.out.println("Book id: " + book.getId());
                    });
                }
                System.out.println();
            });
            System.out.println("---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for option. Enter integer value. Try again ---------\n");
            scan.nextLine();
            searchInMembers(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            searchInMembers(scan);
        }
    }

    /**
     * @param Scanner scan
     */
    private List<Member> findMembers(Scanner scan) throws InputMismatchException {

        final List<Member> searchMembers = new ArrayList<>();
        // getting user input. if wrong input entered, throws InputMismatchException and handles it in searchInMembers() method
        Integer option = scan.nextInt();
        scan.nextLine();
        switch (option) {
        case 1:
            System.out.print("Enter member's name: ");
            String name = scan.nextLine();
            members.forEach(member -> {
                // adds members which contain name or is equal to name
                if (member.getName().equals(name) || member.getName().contains(name)) {
                    searchMembers.add(member);
                }
            });
            break;
        case 2:
            members.forEach(member -> {
                // adds members which gender is equal to male
                if (member.getGender() == 'm' || member.getGender() == 'M') {
                    searchMembers.add(member);
                }
            });
            break;
        case 3:
            members.forEach(member -> {
                // adds members which gender is equal to female
                if (member.getGender() == 'f' || member.getGender() == 'F') {
                    searchMembers.add(member);
                }
            });
            break;
        case -1:
            return null;
        default:
            System.err.println("There is no +" + option + " option. Try entering option again  ---------\n");
            return findMembers(scan);
        }

        return searchMembers;
    }

    /**
    * Finds members that have borrowed books expired 
    */
    public void showMembersHasExpiredBooks() {
        LocalDateTime date = LocalDateTime.now();
        System.out.println(
                "\nThese members Borrowed books are expired and they should borrow back the books: ---------\n");
        // if a member printed twice or more it means it has more than one book borrowed and expired
        members.forEach(member -> {

            member.getBooks().forEach(book -> {
                if (book.getBorrowExpiration().isBefore(date)) {
                    System.out.println("Member name: " + member.getName());
                    System.out.println("Expired borrowed book: " + book.getName());
                    System.out.println();
                }
            });
        });
        System.out.println("---------\n");
    }

    public List<Member> getMembers() {
        return this.members;
    }

}
