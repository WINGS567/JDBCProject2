package jdbcApp1;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    static Scanner sc = new Scanner(System.in);
    static List<StudentDTO> data = new ArrayList<>();
    public static void main(String[] args) {
        boolean status = true;
        do {
            System.out.println("Enter No of Operation to be performed!!");
            System.out.println("1. Add Student");
            System.out.println("2. Display Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display Pass Students");
            System.out.println("6. Mass Delete");
            System.out.println("7. Exit");
            int ch = sc.nextInt();
            switch (ch)
            {
                case 1: addStudent();
                    break;
                case 2: displayStudent();
                    break;
                case 3: updateStudent();
                    break;
                case 4: deleteStudent();
                    break;
                case 5: displayPassedStudents();
                    break;
                case 6: massDelete();
                    break;
                case 7: status = false;
            }
        }while (status);

    }

    private static void massDelete() {
        StudentDAO dao = new StudentDAO();
        int count = dao.massDelete();
        while (count>0)
        {
            System.out.println("Enter Student rollNo whose data to be deleted!!");
            int rollNo = sc.nextInt();
            StudentDTO dto = new StudentDTO();
            dto.setRollNo(rollNo);
            data.add(dto);
            dao.massDelete1(data);
            count--;
        }
    }

    private static void displayPassedStudents() {
        System.out.println("StudentRollNo\t\tStudentName\t\tStudentMarks");
        StudentDAO dao = new StudentDAO();
        data = dao.displayPassedStudents();
        for (StudentDTO dto : data)
        {
            System.out.println(dto);
        }
    }

    private static void deleteStudent() {
        System.out.println("Enter RollNo of Student whose data is to be deleted");
        int id = sc.nextInt();
        StudentDAO dao = new StudentDAO();
        StudentDTO dto = new StudentDTO();
        dto.setRollNo(id);
        int count=  dao.deleteStudent(dto);
        System.out.println(count+" Deleted successfully!!");
    }

    private static void updateStudent() {
        System.out.println("Enter RollNo to be Updated!!");
        int rollNo = sc.nextInt();
        System.out.println("Enter name to be Updated!!");
        String name = sc.next();
        System.out.println("Enter marks to be updated!!");
        double marks = sc.nextDouble();
        StudentDTO dto = new StudentDTO();
        dto.setName(name);
        dto.setMarks(marks);
        StudentDAO dao = new StudentDAO();
        dao.updateStudent(rollNo,dto);

    }

    private static void displayStudent() {}
        private static void addStudent() {
            System.out.println("Enter Roll No ");
            int rollNo = sc.nextInt();
            System.out.println("Enter Student Name");
            String name = sc.next();
            System.out.println("Enter Student Marks");
            double marks = sc.nextDouble();
            StudentDAO dao = new StudentDAO();
            StudentDTO dto = new StudentDTO(rollNo,name,marks);
            int count=  dao.addStudent(dto);
            System.out.println(count+" Added Successfully!!");
        }

    }

