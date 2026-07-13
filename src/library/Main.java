package library;

import library.dao.BookDAO;
import library.dao.MemberDAO;
import library.dao.TransactionDAO;
import library.model.Book;
import library.model.Member;
import library.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception{
        BookDAO bookDAO=new BookDAO();
        MemberDAO memberDAO=new MemberDAO();
        TransactionDAO transactionDAO=new TransactionDAO();

        System.out.println("-------------BOOK DAO-----------");
        //CREATE
        Book book = new Book("The Alchemist", "Paulo Coelho", "Fiction", 5, 5);
        int bookId = bookDAO.addBook(book);
        System.out.println("Added book with ID: " + bookId);

        //READ
        Book getBook=bookDAO.getBookById(bookId);
        System.out.println("fetched one book by ID: "+getBook);

        //READ ALL
        System.out.println("all Books");
        bookDAO.getAllBooks().forEach(System.out::println);

        //UPDATE
        getBook.setTotalCopies(10);
        getBook.setAvailableCopies(20);
        bookDAO.updateBook(getBook);
        System.out.println("Updated book: " + bookDAO.getBookById(bookId));

        System.out.println("\n===== MEMBER DAO =====");

        // CREATE
        Member member = new Member("Rishika", "rishika3@email.com", "9876543210");
        int memberId = memberDAO.addMember(member);
        System.out.println("Added member with ID: " + memberId);

        // READ ONE
        Member fetchedMember = memberDAO.getMemberById(memberId);
        System.out.println("Fetched by ID: " + fetchedMember);

        // READ ALL
        System.out.println("All members:");
        memberDAO.getAllMembers().forEach(System.out::println);

        // UPDATE
        fetchedMember.setPhone("9999999999");
        memberDAO.updateMember(fetchedMember);
        System.out.println("Updated member: " + memberDAO.getMemberById(memberId));

        System.out.println("\n===== TRANSACTION DAO =====");

        // CREATE (borrow)
        Transaction t = new Transaction(bookId, memberId, LocalDate.now(), LocalDate.now().plusDays(14));
        transactionDAO.addTransaction(t);
        System.out.println("Transaction added.");

        // READ ALL (to get the generated transaction_id)
        List<Transaction> allTransactions = transactionDAO.getAllTransactions();
        System.out.println("All transactions:");
        allTransactions.forEach(System.out::println);

        // Get the last transaction's ID for update/read-by-id testing
        int transactionId = allTransactions.get(allTransactions.size() - 1).getTransactionId();

        // READ ONE
        System.out.println("Fetched by ID: " + transactionDAO.getTransactionById(transactionId));

        // READ BY MEMBER
        System.out.println("Transactions for member " + memberId + ":");
        transactionDAO.getTransactionsByMember(memberId).forEach(System.out::println);

        // UPDATE (return the book)
        transactionDAO.updateReturn(transactionId, LocalDate.now().plusDays(1), new BigDecimal("0.00"));
        System.out.println("After return: " + transactionDAO.getTransactionById(transactionId));

        // DELETE
        transactionDAO.deleteTransaction(transactionId);
        System.out.println("Transaction deleted. Remaining transactions:");
        transactionDAO.getAllTransactions().forEach(System.out::println);


    }
}
