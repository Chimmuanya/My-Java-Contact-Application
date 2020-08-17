package contacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.System.out;

class ContactApp{

    ContactApp() throws IOException {

        String fileAddress = "phonebook.db";
        out.println("open " + fileAddress);


        PhoneBook phoneBook = new PhoneBook(fileAddress);
        Menu.mainMenu(phoneBook, fileAddress);

    }


}