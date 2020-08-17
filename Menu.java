package contacts;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.System.out;
class Menu {
    private static String fileAddress;

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    protected static void mainMenu(PhoneBook phoneBook, String fileAddress) throws IOException {
        boolean IsToRun = true;
        while (IsToRun) {
            Menu.fileAddress = "./" + fileAddress;
            out.print("[menu] Enter action (add, list, search, record, count, exit): > ");
            String action = "";
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (action) {
                case "add":
                    addMenu(phoneBook);
                    break;
                case "search":
                    searchMenu(phoneBook);
                    break;
                case "record":
                    recordMenu(phoneBook);
                    break;
                case "list":
                    listMenu(phoneBook);
                    break;
                case "count":
                    phoneBook.count();
                    break;
                case "exit":
                    phoneBook.exit();
                    IsToRun = false;
                    break;
                case "menu":
                    mainMenu(phoneBook, fileAddress);
                    break;
                default:
                    out.println("Invalid argument!");
                    mainMenu(phoneBook, fileAddress);
            }

        }
}
    private static void addMenu(PhoneBook phoneBook) throws IOException {
        out.print("Enter the type (person, organization): > ");
        String type = null;
        try {
            type = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ("person".equals(type)) {
            var person = new PersonContact();
            out.print("Enter the name: > ");
            String name = null;
            try {
                name = in.readLine();
                person.setName(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print("Enter the surname: > ");
            String surname = null;
            try {
                surname = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            out.print("Enter the Birth date: > ");
            String birthDate = null;
            try {
                birthDate = in.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            person.setBirthDate(birthDate);
            out.print("Enter the gender (M, F): > ");
            String gender = null;
            try {
                gender = in.readLine();
                person.setGender(gender);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print("Enter the number: > ");
            String number = null;
            try {
                number = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            person.setPhoneN(number);


            person.setSurname(surname);
            person.setCreationTime();
            phoneBook.add(person);
            out.println();
            phoneBook.save(fileAddress);

        } else if ("organization".equals(type)) {
            var organization = new OrganizationContact();
            out.print("Enter the organization name: > ");
            String name = null;
            try {
                name = in.readLine();
                organization.setName(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print("Enter the address: > ");
            String address = null;
            try {
                address = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print("Enter the number: > ");
            String number = null;
            try {
                number = in.readLine();
                organization.setPhoneN(number);
            } catch (IOException e) {
                e.printStackTrace();
            }


            organization.setAddress(address);
            organization.setCreationTime();
            phoneBook.add(organization);
            phoneBook.save(fileAddress);
            out.println();
        }

    }
    private static void searchMenu(PhoneBook phoneBook) {
        out.print("Enter search query: > ");
        try {
            String searchItem = in.readLine();
            ArrayList<Contact> result = phoneBook.search(searchItem);
            out.print("[search] Enter action ([number], back, again): > ");
            String action = null;
            action = in.readLine();
            switch (action) {
                case "back":
                    mainMenu(phoneBook, fileAddress);
                    break;
                case "again":
                    searchMenu(phoneBook);
                    break;
                default:
                    try {
                        int index = Integer.parseInt(action);
                        out.println(result.get(index - 1).toString());
                    } catch (NumberFormatException n) {
                        out.println("Invalid number or argument");
                        searchMenu(phoneBook);
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
            searchMenu(phoneBook);
        }
    }
    private static void recordMenu(PhoneBook phoneBook) throws IOException {

        out.print("[record] Enter action (edit, delete, menu): > ");
        String action;
        action = in.readLine();
        switch (action) {
            case "edit":
                if (phoneBook.isEmpty()) {
                    out.println("No records to edit!");
                    out.println();
                    mainMenu(phoneBook, fileAddress);
                }
                phoneBook.list();
                out.print("Select a record: > ");
                int index = 0;
                try {
                    index = Integer.parseInt(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.print("Select a field (name, surname, birth date, gender, number, address): > ");
                String field = null;
                try {
                    field = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.print("Enter " + field + ": > ");
                String fieldValue = null;
                try {
                    fieldValue = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                phoneBook.edit(index, field, fieldValue);
                phoneBook.save(fileAddress);
                out.println();

            case "delete":
                if (phoneBook.isEmpty()) {
                    out.println("No records to remove!");
                    out.println();
                    recordMenu(phoneBook);
                }
                phoneBook.list();
                out.print("Select a record: > ");
                try {
                    index = Integer.parseInt(in.readLine());
                    phoneBook.remove(index);
                    phoneBook.save(fileAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "menu":
                mainMenu(phoneBook, fileAddress);
                break;
            default:
                out.println("Invalid argument!");
                recordMenu(phoneBook);

        }
    }
    private static void listMenu(PhoneBook phoneBook) throws IOException {
        phoneBook.list();
        out.print("[list] Enter action ([number], back): > ");
        String action;
        action = in.readLine();
        if ("back".equals(action)) {
            mainMenu(phoneBook, fileAddress);
        } else {
            try {
                phoneBook.showInfo(Integer.parseInt(action));
            } catch (NumberFormatException nfe) {
                out.println("Invalid entry!");
            }
        }
    }


}
