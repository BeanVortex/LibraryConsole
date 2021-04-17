package Project.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Book;
import Project.Models.Member;

public class BookService {

    private final List<Book> books = new ArrayList<>();

    /** 
     * Create Books
     * Inputs: book name, book author, book id
     */
    public void createBook(Scanner scan) {
        try {
            Book book = new Book();
            System.out.print("Enter book name: ");
            book.setName(scan.nextLine());
            System.out.print("Enter book author: ");
            book.setAuthor(scan.nextLine());
            System.out.print("Enter book id (-1 to exit): ");
            Long id = scan.nextLong();
            // to fill next line buffer
            scan.nextLine();
            if (id == -1)
                return;
            if (doesBookExist(id))
                throw new Exception("Book with id of " + id + " exists.");
            book.setId(id);
            books.add(book);
            System.out.println("Successfully created book with id of " + id + "---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            scan.nextLine();
            createBook(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            createBook(scan);
        }
    }

    /** 
     * @param Long id
     * Checks if a book with given id exists in library
     */
    private boolean doesBookExist(Long id) {
        for (Book book : books) {
            if (book.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * @param List<Book> books
     * Iterates books and prints its data 
     * */
    public void readBooks(List<Book> books) {
        System.out.println("Books data ---------");
        if (books == null) {
            books = this.books;
        }
        // Used Java 8 feature called lambda expression
        books.forEach(book -> {
            System.out.println("Book name: " + book.getName());
            System.out.println("Book author: " + book.getAuthor());
            System.out.println("Book id: " + book.getId());
            System.out.println("Borrowed? " + book.isBorrowed());
            System.out.println();
        });
        System.out.println("---------\n");
    }

    /** 
     * @param Long id
     * Finds book with its id and updates its data with new inputs
     */
    public void updateBook(Scanner scan) {

        try {
            System.out.print("Enter book id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;
            // Used lambda expression to filter and find a book with given id
            List<Book> filteredBooks = books.stream().filter(book -> {
                return book.getId() == id;
            }).collect(Collectors.toList());

            Book book = null;
            // Checks if List of filtered books is not empty
            if (filteredBooks.size() == 0)
                throw new Exception("Book with id of " + id + " does not exists.");
            else
                book = filteredBooks.get(0);

            System.out.print("Enter new name for book: ");
            book.setName(scan.nextLine());
            System.out.print("Enter new author for book: ");
            book.setAuthor(scan.nextLine());

            // finding book index in the list using indexOf() method
            Integer index = books.indexOf(book);
            books.set(index, book);
            System.out.println("Successfully updated book with id of " + id + "---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            scan.nextLine();
            updateBook(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            updateBook(scan);
        }

    }

    /** 
     * @param Long id
     * Takes an id and find book with that id and deletes it
     */
    public void deleteBook(Scanner scan) {
        try {
            System.out.print("Enter book id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;
            // Used lambda expression to filter and find a book with given id
            List<Book> filteredBooks = books.stream().filter(book -> {
                return book.getId() == id;
            }).collect(Collectors.toList());

            Book book = null;
            // Checks if List of filtered books is not empty
            if (filteredBooks.size() == 0)
                throw new Exception("Book with id of " + id + " does not exists.");
            else
                book = filteredBooks.get(0);

            // Removes book. if returns true that means it was successful
            if (books.remove(book))
                System.out.println("Successfully deleted book with id of " + id + "---------\n");
            else
                throw new Exception("Something went wrong.\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            scan.nextLine();
            updateBook(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            deleteBook(scan);
        }
    }

    /** 
     * Search in books with 3 options: 1. Name 2. Author 3. Id
     */
    public void searchInBooks(Scanner scan) {
        try {
            System.out.println("Search by: (enter option)");
            System.out.println("1) Name\n2) Author\n3) Id\n-1) Exit");
            // external private method to avoid extending this method
            List<Book> foundBooks = findBooks(scan);
            if (foundBooks == null)
                return;
            System.out.println("Founded books data: ---------");
            // Printing foundedBooks data with lambda 
            foundBooks.forEach(book -> {
                // Only existing books in the library will show. because borrowed books are not in the library
                if (!book.isBorrowed()) {
                    System.out.println("Book name: " + book.getName());
                    System.out.println("Book author: " + book.getAuthor());
                    System.out.println("Book id: " + book.getId());
                    System.out.println();
                }
            });
            System.out.println("---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for option. Enter integer value. Try again ---------\n");
            scan.nextLine();
            searchInBooks(scan);
        }
    }

    /**
     * @param Scanner scan
     */
    private List<Book> findBooks(Scanner scan) throws InputMismatchException {

        final List<Book> searchBooks = new ArrayList<>();
        // getting user input. if wrong input entered, throws InputMismatchException and handles it in searchInBooks() method
        Integer option = scan.nextInt();
        scan.nextLine();
        switch (option) {
        case 1:
            System.out.print("Enter book name: ");
            String name = scan.nextLine();
            books.forEach(book -> {
                // adds books which contain name or is equal to name
                if (book.getName().equals(name) || book.getName().contains(name)) {
                    searchBooks.add(book);
                }
            });
            break;
        case 2:
            System.out.print("Enter book author: ");
            String author = scan.nextLine();
            books.forEach(book -> {
                // returns books which contain author or is equal to author
                if (book.getAuthor().equals(author) || book.getAuthor().contains(author)) {
                    searchBooks.add(book);
                }
            });
            break;
        case 3:
            System.out.print("Enter book id: ");
            Long id = scan.nextLong();
            scan.nextLine();
            books.forEach(book -> {
                // returns books which id is equal book id
                if (book.getId() == id) {
                    searchBooks.add(book);
                }
            });
            break;

        case -1:
            return null;

        default:
            System.err.println("There is no +" + option + " option. Try entering option again  ---------\n");
            return findBooks(scan);
        }

        return searchBooks;
    }

    /** 
     * @param Member member
     * borrows a book to member and marks book as borrowed
     * so it is not accessible or others
     */
    public void borrowBook(Member member, Scanner scan) {
        try {
            // reads books to show member options
            readBooks(null);
            System.out.print("Which book to borrow? Select by id (-1 to exit): ");
            // if not matchable input entered, throws InputMismatchException and will handle it
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1L)
                return;

            // Filters books and finds books which id is equal to book id and is not borrowed
            List<Book> filteredBooks = books.stream().filter(book -> {
                return (book.getId() == id && !book.getIsBorrowed());
            }).collect(Collectors.toList());

            // Checks if List of filtered books is not empty
            if (filteredBooks.size() == 0) {
                throw new Exception("Book with id of " + id + " does not exists in the library.");
            }
            System.out.print("Borrow it for how many days?: ");
            LocalDateTime date = LocalDateTime.now();
            Integer days = scan.nextInt();
            scan.nextLine();
            date.plusSeconds(days);

            Book book = filteredBooks.get(0);
            // Marks book as borrowed
            book.setBorrowed(true);
            // Borrows book by member
            book.setBorrowedMember(member);
            // borrows book with expiration date
            book.setBorrowExpiration(date);
            // adds member a borrowed book
            member.getBooks().add(book);
            Integer index = books.indexOf(book);
            // Updating the book state
            books.set(index, book);
            System.out.println(
                    "Successfully book with id of " + id + " borrowed by " + member.getName() + " ---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            borrowBook(member, scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            borrowBook(member, scan);
        }

    }

    /** 
    * @param Member member
    * borrows back list of books which member borrowed
    * so borrowed back books are accessible or others
    */
    public void borrowBackBook(Member member, Scanner scan) {

        try {
            // Checks if member has borrowed any book before
            if (member.getBooks() == null || member.getBooks().size() == 0) {
                throw new IndexOutOfBoundsException("This member borrowed nothing!!");
            }
            // Reads members borrowed books 
            readBooks(member.getBooks());

            System.out.print("Which book to borrow back? Select by id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1L)
                return;
            // Finds books if id is equal to book id and if is borrowed
            List<Book> filteredBooks = books.stream().filter(book -> {
                return (book.getId() == id && book.getIsBorrowed());
            }).collect(Collectors.toList());

            // Checks if List of filtered books is not empty
            if (filteredBooks.size() == 0) {
                throw new Exception("Book with id of " + id + " does not exists in the library.");
            }

            Book book = filteredBooks.get(0);

            // Sets borrowed as false so others can borrow
            book.setBorrowed(false);
            // Sets member as null because previous member borrowed back
            book.setBorrowedMember(null);

            Integer index = books.indexOf(book);
            // deletes member a borrowed book
            member.getBooks().remove(book);
            // Updating the book state
            books.set(index, book);
            System.out.println(
                    "Successfully book with id of " + id + " borrowed back by " + member.getName() + " ---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            borrowBook(member, scan);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage() + " returning main menu ---------\n");
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            borrowBook(member, scan);
        }

    }

    public List<Book> getBooks() {
        return this.books;
    }

}