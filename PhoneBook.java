package contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

class PhoneBook implements Serializable {
    private ArrayList<Contact> contacts;
    private static final long serialVersionUID =-5648218740329533806L;


    PhoneBook(String fileAddress) throws IOException {
        load(fileAddress);
        if (contacts.isEmpty()) {
            save(fileAddress);
        }
    }

    private boolean isPerson(Contact contact){
        return contact.getClass() == PersonContact.class;
    }

    void add(Contact contact){
        contacts.add(contact);
        out.println("The record added");
    }
    boolean isEmpty(){
        return contacts.isEmpty();
    }

    int count(){
        if (contacts.isEmpty()){
            out.println("The Phone Book has 0 records!");
            return 0;
        }
        out.printf("The Phone Book has %d records\n", contacts.size());
        return contacts.size();
    }

    void edit(int index, String field, String fieldValue){

        Contact c = contacts.get(index - 1);


        if (isPerson(c)){
            PersonContact p = (PersonContact) c;
            switch (field){
                case "name":
                    p.setName(fieldValue);
                    p.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, p);
                    out.println(p);
                    break;
                case "surname":
                    p.setSurname(fieldValue);
                    p.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, p);
                    out.println(p);
                    break;
                case "number":
                    p.setPhoneN(fieldValue);
                    p.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, p);
                    out.println(p);
                    break;
                case "gender":
                    p.setGender(fieldValue);
                    p.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, p);
                    out.println(p);
                    break;
                case "birth date":
                    p.setBirthDate(fieldValue);
                    p.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, p);
                    out.println(p);
                    break;
                default:
                    out.println("invalid selection!");
            }


        } else {
            OrganizationContact oc = (OrganizationContact) c;

            switch (field){
                case "name":

                    oc.setName(fieldValue);
                    oc.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, oc);
                    out.println(oc);
                    break;
                case "address":
                    oc.setAddress(fieldValue);
                    oc.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, oc);
                    out.println(oc);
                    break;
                case "number":
                    oc.setPhoneN(fieldValue);
                    oc.setLastEdit();
                    out.println("Saved");
                    contacts.set(index - 1, oc);
                    out.println(oc);
                    break;
                default:
                    out.println("invalid selection!");
            }


        }

    }



    void remove(int index){
        contacts.remove(index - 1);
        out.println("The record removed!");


    }

    void list(){
        if (!contacts.isEmpty()){
            contacts.forEach(contact -> {
                out.println((contacts.indexOf(contact) + 1) + ". " + contact.getName());
            });
        } else {
            out.println("The Phone Book has 0 records!");
        }
    }

    void showInfo(int index) {
        if (!contacts.isEmpty() && index <= contacts.size()) {
            Contact c = contacts.get(index - 1);
            if (isPerson(c)) {
                PersonContact pc = (PersonContact) c;
                out.println(pc.toString());
            } else {
                OrganizationContact oc = (OrganizationContact) c;
                out.println(oc.toString());
            }
        } else {
            out.print("Invalid index!");
        }
    }

    void save(String fileAddress) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileAddress);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(contacts);
        oos.close();
    }

    void load(String fileAddress) throws IOException {
        ObjectInputStream objectinputstream = null;
        ArrayList<Contact> savedPhoneBook = null;
        try {
            FileInputStream streamIn = new FileInputStream(fileAddress);
            objectinputstream = new ObjectInputStream(streamIn);
            savedPhoneBook = (ArrayList<Contact>) objectinputstream.readObject();

        } catch (FileNotFoundException f) {
            save(fileAddress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(objectinputstream != null){
                objectinputstream .close();
            }
        }
        if(savedPhoneBook != null) {
            this.contacts = savedPhoneBook;
        } else {
            this.contacts = new ArrayList<>();
        }

    }

    ArrayList<Contact> search(String text){
        Pattern pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
        ArrayList<Contact> results = new ArrayList<>();
        contacts.forEach(contact -> {
            Matcher matcher = pattern.matcher(contact.getName());
            if (matcher.find()) {
                results.add(contact);
            }
        });
        if (results.size() < 2) {
            out.printf("Found %d result:\n", results.size());
        } else {
            out.printf("Found %d results:\n", results.size());
        }
        if (!results.isEmpty()) {
            results.forEach(result -> {
                out.println(results.indexOf(result) + 1 + ". " + result.getName());
            });
        }
        return results;
    }

    void exit(){
        return;
    }
}