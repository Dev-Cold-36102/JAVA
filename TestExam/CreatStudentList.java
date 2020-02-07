package TestExam;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatStudentList {
    private File srcStudentFile;

    public CreatStudentList(String pathStudentFile) {
        File fileStudent = new File(pathStudentFile);
        this.srcStudentFile = fileStudent;
    }

    public LinkedList listStudent() {
        LinkedList listStudentInfo = new LinkedList();
        LinkedList<Student> listStudentObject = new LinkedList<>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(this.srcStudentFile);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        scanner.useDelimiter("\\Z");
        String stringStudent = scanner.next();
        scanner.close();
        stringStudent = stringStudent.replaceAll("\\n", "_");
        String regexStudent = "\\*(.*?)_";
        Pattern patternStudent = Pattern.compile(regexStudent);
        Matcher matcherStudent = patternStudent.matcher(stringStudent);
        while (matcherStudent.find()) {
            listStudentInfo.add(matcherStudent.group(1));
            String regexStudenInfo = "\\*(.*?);(.*?);(.*?);(.*?)$";
            Pattern patternStudenInfo = Pattern.compile(regexStudenInfo);
            Matcher matcherStudenInfo = patternStudenInfo.matcher(matcherStudent.group(1));
            while (matcherStudenInfo.find()) {

               Student student = new Student(matcherStudenInfo.group(1), matcherStudenInfo.group(2), matcherStudenInfo.group(3), Integer.parseInt(matcherStudenInfo.group(4)));
                listStudentObject.add(student);
            }
        }
        return listStudentObject;
    }
}

