package com.library;

import java.util.Scanner;

public class Program {
    private static int option;

    public static void main(String[] args) {
        Library library = new Library("com.library.Library of Shynnnnnn");
        Scanner sc = new Scanner(System.in);
        do {
            showMenu();
            askOption();
            switch (Program.option) {
                case 1: {
                    library.showLibraryInfo();
                    break;
                }
                case 2: {
                    library.addNewBook();
                    break;
                }
                case 3: {
                    int findOpt;
                    do {
                        System.out.println("\n\nYou can find book by:");
                        System.out.println("1. com.library.Book ID");
                        System.out.println("2. Keyword of book title");
                        System.out.println("3. Back");
                        System.out.print("Enter your option: ");
                        findOpt = sc.nextInt();

                        switch (findOpt) {
                            case 1: {
                                System.out.println("\n\n-----------------------");
                                System.out.print("Enter the book ID: ");
                                int id = sc.nextInt();
                                if (library.findBook(id))
                                    System.out.println("The com.library.Library has this book!");
                                else System.out.println("The com.library.Library doesn't have this book!");
                                break;
                            }
                            case 2: {
                                System.out.println("\n\n--------------------------------");
                                System.out.print("Enter the keyword: ");
                                sc.nextLine();
                                String keyword = sc.nextLine();
                                if (library.findBook(keyword))
                                    System.out.println("The com.library.Library has this book!");
                                else System.out.println("The com.library.Library doesn't have this book!");
                                break;
                            }
                            case 3:
                                break;
                            default:
                                System.out.println("PLEASE choose the option from 1 - 3!");
                        }
                    } while (findOpt != 3);
                    break;
                }
                case 4: {
                    System.out.println("-----------------------");
                    System.out.print("Enter the book ID: ");
                    int id = sc.nextInt();
                    library.borrowBook(id);
                    break;
                }
                case 5: {
                    System.out.println("-----------------------");
                    System.out.print("Enter the book ID: ");
                    int id = sc.nextInt();
                    library.returnBook(id);
                    break;
                }
                case 6: {
                    System.out.println("Thanks for using!");
                    break;
                }
                default:
                    System.out.println("PLEASE enter the option from 1 to 6 !!");
            }
        } while (Program.option != 6);
    }

    public static void showMenu() {
        System.out.println("\n========== Main menu ==========");
        System.out.println("1. Show library information");
        System.out.println("2. Add new book");
        System.out.println("3. Find book");
        System.out.println("4. Borrow a book");
        System.out.println("5. Return a book");
        System.out.println("6. Exit");
    }

    public static void askOption() {
        System.out.println("==================================");
        System.out.print("Enter menu ID (1 - 6): ");
        Scanner sc = new Scanner(System.in);
        Program.option = sc.nextInt();
    }
}
