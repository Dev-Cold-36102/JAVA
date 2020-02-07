package TestExam;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    Scanner scanner = new Scanner(System.in);
    private LinkedList<Student> listStudentObject;

    public Controller(LinkedList<Student> listStudentObject) {
        this.listStudentObject = listStudentObject;
    }

    public void showMenu() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-30s%-30s%-30s\n", "1.search info", "2.show by score limit", "3.show Nguyen", "4.show incorrect id");
        System.out.printf("%-20s%-30s%-30s%-30s\n", "5.sort by score", "6.write 5. to sapxep.txt", "7.sort by name and surname", "8.write object 7. to sapxephoten.txt");
        System.out.printf("%-20s%-30s%-30s%-30s\n", "9.add student", "10.change info ", "11.delete student", "12.sort by full name");
        System.out.println("13.display all student");
        System.out.print("what do you want?(1-13) ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                searchStudentInfo();
                break;
            }
            case 2: {
                displayByLimitScore();
                break;
            }
            case 3: {
                displayNguyen();
                break;
            }
            case 4: {
                displayIncorrectid();
                break;
            }
            case 5: {
                sortByScore();
                break;
            }
            case 6: {
                exportScoreSortedFile();
                break;
            }
            case 7: {
                sortByName();
                break;
            }
            case 8: {
                try {
                    exportNameSortedFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 9: {
                addStudent();
                break;
            }
            case 10: {
                changeStudentInfo();
                break;
            }
            case 11: {
                deleteStudent();
                break;
            }
            case 12: {
                sortByfullName();
                break;
            }
            case 13: {
                displayStudent();
                break;
            }

        }

    }

    public void displayStudent() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            System.out.printf("%-5s", i + 1);
            this.listStudentObject.get(i).displayStudent();
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        showMenu();
    }

    public void sortByScore() {
        Collections.sort(this.listStudentObject, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                return (-student1.getScore() + student2.getScore());
            }
        });
        displayStudent();
        showMenu();
    }

    public void displayByLimitScore() {
        scanner.nextLine();
        System.out.print("enter the limit score: ");
        int limitScore = scanner.nextInt();
        System.out.printf("\n%-15s%-30s%-35s%-10s\n", "MA HV", "HO TEN", "EMAIL", "SCORE");
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            if (this.listStudentObject.get(i).getScore() > limitScore) {
                this.listStudentObject.get(i).displayStudent();
            }
        }
        showMenu();
    }

    public void exportScoreSortedFile() {
        sortByScore();
        File fileSortedStudent;
        do {
            System.out.print("enter the fullName of file: ");
            String FILEfullName = scanner.nextLine() + ".txt";
            fileSortedStudent = new File(FILEfullName);
            if (!fileSortedStudent.exists()) {
                try {
                    fileSortedStudent.createNewFile();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.out.print("that file is existed! ");
        } while (fileSortedStudent.exists());


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileSortedStudent))) {
            String listSorted = "";
            for (int i = 0; i < this.listStudentObject.size(); i++) {
                listSorted += this.listStudentObject.get(i).getid() + ";" + this.listStudentObject.get(i).getfullName() + ";" + this.listStudentObject.get(i).getEmail() + ";" + this.listStudentObject.get(i).getScore() + "\n";
            }
            bw.write(listSorted);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        showMenu();
    }

    public void sortByfullName() {
        Collections.sort(this.listStudentObject, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                return (student1.getfullName().compareTo(student2.getfullName()));
            }
        });
        showMenu();
    }

    public void sortByName() {

        for (int i = 0; i < this.listStudentObject.size(); i++) {
            String[] elementOfFullName = this.listStudentObject.get(i).getfullName().split("\\s");
            String reverseName = elementOfFullName[elementOfFullName.length - 1];

            for (int j = 0; j < elementOfFullName.length - 1; j++) {
                reverseName += " " + elementOfFullName[j];
            }
            this.listStudentObject.get(i).setFullName(reverseName);
        }
        sortByfullName();
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            String[] elementOfReverseName = this.listStudentObject.get(i).getfullName().split("\\s");
            String realFullName = "";
            for (int j = 1; j < elementOfReverseName.length; j++) {
                realFullName += elementOfReverseName[j] + " ";
            }
            realFullName += elementOfReverseName[0];
            this.listStudentObject.get(i).setFullName(realFullName);
        }
//        displayStudent();
        showMenu();
    }

    public void displayIncorrectid() {
        String regexid = "^[A-Z]{4}\\d{6}$";
        Pattern patternid = Pattern.compile(regexid);
        Matcher matcherid;
        LinkedList<Student> incorrectidList = new LinkedList<>();
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            matcherid = patternid.matcher(this.listStudentObject.get(i).getid());
            if (!matcherid.matches()) {
                incorrectidList.add(this.listStudentObject.get(i));
            }
        }
        if (incorrectidList.size() == 0) {
            System.out.println("ALL are correct!");
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            for (int i = 0; i < incorrectidList.size(); i++) {
                System.out.printf("%-5s", i + 1);
                incorrectidList.get(i).displayStudent();
            }
        }
        showMenu();
    }

    public void displayNguyen() {
        String regexNguyen = "^(Nguyen)(.*?)$";
        Pattern patternNguyen = Pattern.compile(regexNguyen);
        Matcher matcherNguyen;
        LinkedList<Student> listNguyen = new LinkedList<>();
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            matcherNguyen = patternNguyen.matcher(this.listStudentObject.get(i).getfullName());
            if (matcherNguyen.matches()) {
                listNguyen.add(this.listStudentObject.get(i));
            }
        }
        if (listNguyen.size() == 0) {
            System.out.println("No Result!");
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            for (int i = 0; i < listNguyen.size(); i++) {
                System.out.printf("%-5s", i + 1);
                listNguyen.get(i).displayStudent();
            }
        }
        showMenu();
    }

    public void exportNameSortedFile() throws IOException {
        sortByName();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("sapxephoten.txt"));
            Iterator<Student> iterator = this.listStudentObject.iterator();
            while (iterator.hasNext()) {
                oos.writeObject(iterator.next());
            }
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos.close();
        }
        showMenu();
    }

    public void addStudent() {
        scanner.nextLine();
        System.out.print("enter the id of student:");
        String newid = scanner.nextLine();
        System.out.print("enter the name of student:");
        String newName = scanner.nextLine();
        System.out.print("enter the email of student:");
        String newEmail = scanner.nextLine();
        System.out.print("enter the score of student:");
        int newScore = scanner.nextInt();
        Student newStudent = new Student(newid, newName, newEmail, newScore);
        this.listStudentObject.add(newStudent);
        writeFileStudent();
        showMenu();
    }

    public void changeStudentInfo() {
        displayStudent();
        System.out.println("what id of student you want to change?");
        String idOfChange = scanner.nextLine();
        int isidExist = isidExist(idOfChange);
        while (isidExist == -1) {
            System.out.println("what id of student you want to change?");
            idOfChange = scanner.nextLine();
            isidExist = isidExist(idOfChange);
        }
        System.out.println("\nwhat value of that student you want to change?\n1.id\n2.Name\n3.Email\n4.Score");
        int choiceChange = scanner.nextInt();
        switch (choiceChange) {
            case 1: {
                System.out.println("new id:");
                String newid = scanner.nextLine();
                this.listStudentObject.get(isidExist).setid(newid);
                break;
            }
            case 2: {

                System.out.println("new full name:");
                String newName = scanner.nextLine();
                this.listStudentObject.get(isidExist).setFullName(newName);
                break;
            }
            case 3: {

                System.out.println("new mail:");
                String newMail = scanner.nextLine();
                this.listStudentObject.get(isidExist).setEmail(newMail);
                break;
            }
            case 4: {

                System.out.println("new score:");
                int newScore = scanner.nextInt();
                this.listStudentObject.get(isidExist).setScore(newScore);
                break;
            }
        }
        writeFileStudent();
        showMenu();
    }

    public void deleteStudent() {
        displayStudent();
        System.out.println("what id of student you want to delete?");
        String idOfChange = scanner.nextLine();
        int isidExist = isidExist(idOfChange);
        while (isidExist == -1) {
            System.out.println("what id of student you want to change?");
            idOfChange = scanner.nextLine();
            isidExist = isidExist(idOfChange);
        }
        System.out.print("Are you sure? y/n: ");
        String submitDelete = scanner.nextLine();
        if (submitDelete.equals(String.valueOf('y'))) {
            this.listStudentObject.remove(isidExist);
            writeFileStudent();
        } else if (submitDelete.equals(String.valueOf('n'))) {
            showMenu();
        }

    }


    public void searchStudentInfo() {
        scanner.nextLine();
        System.out.println("what value you want to search?\n1.id\n2.Name\n3.Email\n4.Score");
        int choiceSearch = scanner.nextInt();
        switch (choiceSearch) {
            case 1: {
                searchByid();
                break;
            }
            case 2: {
                searchByName();
                break;
            }
            case 3: {
                searchByMail();
                break;
            }
            case 4: {
                searchByScore();
                break;
            }
        }

    }

    public void searchByid() {
        scanner.nextLine();
        System.out.print("please enter the id: ");
        scanner.nextLine();
        String keyid = scanner.nextLine();
        int isidExist = isidExist(keyid);
        if (isidExist == -1) {
            System.out.println("No result!");
            searchByid();
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            this.listStudentObject.get(isidExist).displayStudent();
        }
    }

    public void searchByName() {
        scanner.nextLine();
        System.out.print("please enter the name: ");
        scanner.nextLine();
        String keyName = scanner.nextLine();
        ArrayList<Integer> indexOfName = isNameExist(keyName);
        if (indexOfName.size() == 0) {
            System.out.println("No result!");
            searchByName();
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            for (int i = 0; i < indexOfName.size(); i++) {
                System.out.printf("%-5s", i + 1);
                this.listStudentObject.get(i).displayStudent();
            }
        }
    }

    public void searchByMail() {
        scanner.nextLine();
        System.out.print("please enter the email: ");
        scanner.nextLine();
        String keyMail = scanner.nextLine();
        int isMailExist = isMailExist(keyMail);
        if (isMailExist == -1) {
            System.out.println("No result!");
            searchByMail();
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            this.listStudentObject.get(isMailExist).displayStudent();
        }
    }

    public void searchByScore() {
        scanner.nextLine();
        System.out.print("please enter the score: ");
        scanner.nextLine();
        int keyScore = scanner.nextInt();
        ArrayList<Integer> indexOfScore = new ArrayList<>();
        indexOfScore = isScoreExist(keyScore);
        if (indexOfScore.size() == 0) {
            System.out.println("No result!");
            searchByScore();
        } else {
            System.out.printf("%-5s%-15s%-30s%-35s%-10s\n", "#", "MA HV", "HO TEN", "EMAIL", "SCORE");
            for (int i = 0; i < indexOfScore.size(); i++) {
                System.out.printf("%-5s", i + 1);
                this.listStudentObject.get(i).displayStudent();
            }
        }
    }


    public int isidExist(String keyid) {
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            if (this.listStudentObject.get(i).getid().equals(keyid)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> isNameExist(String keyName) {
        ArrayList<Integer> indexOfName = new ArrayList<>();
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            if (this.listStudentObject.get(i).getid().equals(keyName)) {
                indexOfName.add(i);
            }
        }
        return indexOfName;
    }

    public int isMailExist(String keyMail) {
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            if (this.listStudentObject.get(i).getid().equals(keyMail)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> isScoreExist(int keyScore) {
        ArrayList<Integer> indexOfScore = new ArrayList<>();
        for (int i = 0; i < this.listStudentObject.size(); i++) {
            if (this.listStudentObject.get(i).getid().equals(keyScore)) {
                indexOfScore.add(i);
            }
        }
        return indexOfScore;
    }

    public void writeFileStudent() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hocvien.txt"))) {
            String listSorted = "";
            for (int i = 0; i < this.listStudentObject.size(); i++) {
                listSorted += "**" + this.listStudentObject.get(i).getid() + ";" + this.listStudentObject.get(i).getfullName() + ";" + this.listStudentObject.get(i).getEmail() + ";" + this.listStudentObject.get(i).getScore() + "\n";
            }
            listSorted += ".";
            bw.write(listSorted);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


