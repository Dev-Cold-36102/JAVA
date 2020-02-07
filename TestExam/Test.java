package TestExam;

import java.io.IOException;

import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws IOException {
       CreatStudentList creatStudentList = new CreatStudentList("hocvien.txt");
        LinkedList<Student> listStudentObject = creatStudentList.listStudent();
        Controller controller = new Controller(listStudentObject);
        controller.showMenu();
    }
}

