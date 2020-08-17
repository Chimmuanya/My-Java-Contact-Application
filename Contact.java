package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

abstract class Contact implements Serializable {
    protected String phoneN;
    protected LocalDateTime creationTime;
    protected LocalDateTime lastEdit = creationTime;
    private static final long serialVersionUID =-5648218740329533806L;


    public String getPhoneN() {
        return phoneN;
    }

    public void setPhoneN(String phoneN) {
        if (isValidNumber(phoneN)){
            this.phoneN = phoneN;
        } else {
            out.println("Wrong number format!");
            this.phoneN = "[no number]";
        }
    }

    public void setCreationTime() {
        this.creationTime = LocalDateTime.now();
    }

    public String getCreationTime() {
        long seconds = creationTime.getSecond();
        long nano = creationTime.getNano();
        return creationTime.minusNanos(nano).minusSeconds(seconds).toString();
    }

    public void setLastEdit() {
        this.lastEdit = LocalDateTime.now();
    }

    public String getLastEdit() {
        if (lastEdit != null) {
            long seconds = lastEdit.getSecond();
            long nanos = lastEdit.getNano();
            return lastEdit.minusSeconds(seconds).minusNanos(nanos).toString();
        }

        return "";
    }

    public abstract String toString();
    public abstract void setName(String name);
    public abstract String getName();


    private static boolean isValidNumber(String num){
        String regex = "\\+?\\(?[A-Za-z0-9]{1,}\\)?";
        String regexP = "\\+?\\([A-Za-z0-9]{1,}\\)";
        String regex1 = "(\\+?\\([A-Za-z0-9]{1,}\\))([\\s\\-][A-Za-z)-9]{2,})*";
        String regex2 = "(\\+?[A-Za-z)-9]{1,})([\\s\\-]\\([A-Za-z0-9]{2,}\\))([\\s\\-][A-Za-z0-9]{2,})*";
        String regex3 = "(\\+?[A-Za-z0-9]{1,})([\\s\\-][A-Za-z0-9]{2,})*";

        boolean itMatches;
        itMatches = num.matches(regex) || num.matches(regex1) || num.matches(regex2) || num.matches(regexP) || num.matches(regex3);
        return itMatches;
    }


}

class PersonContact extends Contact{
    private String name;
    private String surname;
    private String birthDate;
    private String gender;




    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name + " " + surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setBirthDate(String birthDate) {
        if (isValidBirthDate(birthDate)){
            this.birthDate = birthDate;
        } else {
            out.println("Bad birth date!");
            this.birthDate = "[no data]";
        }

    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setGender(String gender) {
        if (isValidGender(gender)){
            this.gender = gender;
        } else {
            out.println("Bad gender!");
            this.gender = "[no data]";
        }
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n" + "Surname: "+ this.surname + "\n"
                + "Birth date: " + this.birthDate + "\n" + "Gender: " + this.gender + "\n"
                + "Number: " + this.phoneN + "\n" + "Time created: " + this.getCreationTime() + "\n"
                + "Time last edit: " + this.getLastEdit() + "\n";
    }

    private static boolean isValidGender(String gender){
        String regex = "[MF]{1}";
        return gender.matches(regex);
    }

    private static boolean isValidBirthDate(String birthDate){
        String regex = "[1-2][0-9]{3}\\-[0-1]?[0-9]{1}\\-[0-3]?[0-9]";
        boolean matches = false;
        List<Integer> months1 = List.of(1,3,5,7,8,10,12);
        List<Integer> months2 = List.of(4,6,9,11);

        if (birthDate.matches(regex)) {
            String[] date = birthDate.split("\\-");
            int day = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[1]);
            int yr = Integer.parseInt(date[0]);
            if (months1.contains(month) && day <= 31) {
                matches = true;
            } else if (months2.contains(month) && day <= 30) {
                matches = true;
            } else if (month == 2){
                if (yr % 4 == 0 & day <= 29) {
                    matches = true;
                } else if (day <= 28) {
                    matches = true;
                }
            }
            if (day == 0 || month == 0) {
                matches = false;
            }
        }
        return matches;
    }
}

class OrganizationContact extends Contact{
    private String name;
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Organization name: " + this.name + "\n" + "Address: "+ this.address + "\n"
                + "Number: " + this.phoneN + "\n" + "Time created: " + this.getCreationTime() + "\n"
                + "Time last edit: " + this.getLastEdit() + "\n";

    }
}
