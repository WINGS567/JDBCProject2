package jdbcApp1;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

  public class StudentDAO {
    private Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<StudentDTO> data = new ArrayList<>();

    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_info","root","sql@123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addStudent(StudentDTO dto) {
        int count = 0;
        String query = "insert into student_info values(?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,dto.getRollNo());
            pstmt.setString(2,dto.getName());
            pstmt.setDouble(3,dto.getMarks());
            count =  pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int deleteStudent(StudentDTO s) {
        int count =0;
        String query = "delete from student_info where rollno = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,s.getRollNo());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public void updateStudent(int rollNo, StudentDTO dto) {
        String q1 = "select * from student_info where rollno = "+rollNo;
        String q2 = "update student_info set name = ?, marks= ?, where rollno = ?";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt=con.createStatement();
            rs = stmt.executeQuery(q1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(rs.getFetchSize()>0)
            {
                try {
                    PreparedStatement pstmt = con.prepareStatement(q2);
                    pstmt.setString(1,dto.getName());
                    pstmt.setDouble(2,dto.getMarks());
                    pstmt.setInt(3,rollNo);
                    pstmt.executeUpdate();
                    System.out.println("Updated Successfully!!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }else if(rollNo!=rs.getInt(1)){
                addStudent(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<StudentDTO> displayPassedStudents() {
        double avg = 0.0;
        String query1 = "select avg(marks) from student_info";
        String query2 = "select rollno, name, marks from student_info";
        try {
            pstmt = con.prepareStatement(query1);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                avg = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            pstmt = con.prepareStatement(query2);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int rollNo = rs.getInt(1);
                String name = rs.getString(2);
                int marks = rs.getInt(3);
                if (marks>avg)
                {
                    StudentDTO dto = new StudentDTO();
                    dto.setRollNo(rollNo);
                    dto.setName(name);
                    dto.setMarks(marks);
                    data.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public int massDelete() {
        int count = 0;
        String q1 = "select count(rollno) from student_info";
        try {
            pstmt = con.prepareStatement(q1);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public void massDelete1(List<StudentDTO> dto) {
        int count =0;
        data = dto;
        String q1 = "delete from student_info where rollno = ?";
        for(StudentDTO s: data)
        {
            int rollNo = s.getRollNo();
            try {
                pstmt= con.prepareStatement(q1);
                pstmt.setInt(1,rollNo);
                count = pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(count+" Deleted Successfully!!");
    }
}