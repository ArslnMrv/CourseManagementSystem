import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseRepository {  //database ile bağlantı; database uzerinde işlem yapabilmek için
    private Connection con;
    private Statement st;
    private PreparedStatement prst;

    private Connection getConnection(){
        try {
            this.con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db","dev_user","password");
        } catch (SQLException e) {
            System.out.println("Connection yapılırken hata oldu. HATA: "+ e.getMessage());
        }
        return con;
    }
    private Statement getStatement(){
        try {
            this.st=con.createStatement();
        } catch (SQLException e) {
            System.out.println("Statement acılırken hata oldu. HATA: " +e.getMessage());
        }
        return st;
    }
    private PreparedStatement getPreparedStatement(String sql){
        try {
            this.prst =con.prepareStatement(sql) ;
        } catch (SQLException e) {
            System.out.println("PreparedStatement acılırken hata oldu. HATA: "+e.getMessage());
        }
        return prst;
    }
    private void closeSTandCON(Connection con, Statement st){
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                System.out.println("Statement kapatılırken hata oldu. HATA: "+e.getMessage());
            }
        }
        if (con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Connection kapatılırken hata oldu. HATA: "+e.getMessage());
            }
        }
    }
    private void closePRSTandCON(Connection con, PreparedStatement prst){
        if(prst!=null){
            try {
                prst.close();
            } catch (SQLException e) {
                System.out.println("PreparedStatement kapatılırken hata oldu. HATA: "+e.getMessage());
            }
        }
        if (con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Connection kapatılırken hata oldu. HATA: "+e.getMessage());
            }
        }
    }
    private void closeResulSET(ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("ResulSet kapatılırken hata oldu. HATA: "+e.getMessage());
            }
        }
    }
    public void createTable(){
        this.con= getConnection();
        this.st=getStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS courses" +
                    "(course_id SERIAL UNIQUE,course_name VARCHAR(20), credit SMALLINT, course_fee REAL, start_date DATE, finish_date DATE)");
            System.out.println("COURSES tablonuz oluşturuldu");
        } catch (SQLException e) {
            System.out.println("COURSES tablosu oluşturulurken hata oldu. HATA: " +e.getMessage());
        }
        finally {
            try {
                st.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void addCourse(Course newCourse) {
        this.con= getConnection();
        String query="INSERT INTO courses(course_name,credit,course_fee,start_date,finish_date) VALUES(?,?,?,?,?)";
        this.prst= getPreparedStatement(query);

        try {
            prst.setString(1, newCourse.getCourse_name());
            prst.setInt(2,newCourse.getCredit());
            prst.setDouble(3,newCourse.getCourse_fee());
            prst.setDate(4, Date.valueOf(newCourse.getStart_date()));
            prst.setDate(5, Date.valueOf(newCourse.getFinish_date()));
            prst.executeUpdate();
            System.out.println(newCourse.getCourse_name()+ " isimli kursunuz eklenmiştir.");
        } catch (SQLException e) {
            System.out.println(newCourse.getCourse_name()+" adlı kurs eklenirken hata oldu. HATA: "+ e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
        }
    }
    public void addManyCourse(List<Course> addList) {
        this.con=getConnection();
        String query="INSERT INTO courses(course_name,credit,course_fee,start_date,finish_date) VALUES(?,?,?,?,?)";
        this.prst=getPreparedStatement(query);
        int sayac = 0;

        for (Course w:addList ) {
            try {
                prst.setString(1,w.getCourse_name());
                prst.setInt(2,w.getCredit());
                prst.setDouble(3,w.getCourse_fee());
                prst.setDate(4, Date.valueOf(w.getStart_date()));
                prst.setDate(5,Date.valueOf(w.getFinish_date()));
                sayac += prst.executeUpdate();
            } catch (SQLException e) {
                System.out.println(w.getCourse_name() +" isimli kurs eklenirken hata oluştu. HATA: "+e.getMessage());
            }
        }
        if (sayac==addList.size()){
            System.out.println(addList.size()+" adet kurs basarıyla eklenmiştir.");
        }

        closePRSTandCON(con,prst);
    }
    public void showCourses() {
        this.con=getConnection();
        this.st= getStatement();
        String query="SELECT * FROM courses";
        ResultSet resultSet = null;
        try {
            resultSet= st.executeQuery(query);
            System.out.println("***"+"~".repeat(20)+"COURSES"+"~".repeat(20)+"***");
            System.out.printf("%-5s %-12s %-6s %-6s %-11s %-11s\n","Id", "Course_Name", "Credit", "Course_Fee", "Start_Date","Finish_Date");
            while (resultSet.next()){
                System.out.printf("%-5s %-12s %-6s %-10s %-11s %-11s\n",resultSet.getString(1), resultSet.getString(2),resultSet.getInt(3),
                        resultSet.getDouble(4),resultSet.getDate(5),resultSet.getDate(6));
            }
            System.out.println("~".repeat(60));
        } catch (SQLException e) {
            System.out.println("COURSES tablosu yazdırılırken hata oldu. HATA: "+e.getMessage());
        }
        finally {
            closeSTandCON(con,st);
            closeResulSET(resultSet);  //finally seklinde ekleyemedim.. 'finally'without 'try' compile time error veriyor.. diğer methodlarda vermedi...
        }
    }
    public void deleteCourseByID(int id) {
        this.con= getConnection();
        String query="DELETE FROM courses WHERE course_id= ? ";
        this.prst= getPreparedStatement(query);
        try {
            prst.setInt(1,id);
            int deleted= prst.executeUpdate();
            if (deleted>0){
                System.out.println(id+ " nolu course listenizden silinmiştir.");
            }else {
                System.out.println(id+ " nolu course listenizde bulunamamıştır.");
            }
        } catch (SQLException e) {
            System.out.println(id +" nolu kurs silinirken hata oldu. HATA: "+ e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
        }
    }
    public void deleteByName(String courseName) {
        this.con=getConnection();
        String query="DELETE FROM courses WHERE course_name= ?";
        this.prst=getPreparedStatement(query);
        try{
            prst.setString(1,courseName);
            int deleted= prst.executeUpdate();
            if(deleted>0){
                System.out.println(courseName +" adlı "+ deleted+" adet kurs/kurslar listenizden silinmiştir");
            }else{
                System.out.println(courseName+ " adlı course listenizde bulunamamıştır.");
            }
        }catch (SQLException e){
            System.out.println(courseName+" alı kurs silinirken hata oldu. HATA: "+e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
        }
    }
    public Course findCourseByID(int id) {
        this.con=getConnection();
        String query="SELECT * FROM courses WHERE course_id= ?";
        this.prst=getPreparedStatement(query);
        ResultSet resultSet = null;
        Course course= new Course();
        try {
            prst.setInt(1,id);
            resultSet= prst.executeQuery();
            if (resultSet.next()){
                course.setId(resultSet.getInt(1));
                course.setCourse_name(resultSet.getString(2));
                course.setCredit(resultSet.getInt(3));
                course.setCourse_fee(resultSet.getDouble(4));
                course.setStart_date(resultSet.getDate(5).toLocalDate());
                course.setFinish_date(resultSet.getDate(6).toLocalDate());
                System.out.println(course);  //düzenlenebilir.
            }else{
                System.out.println(id+ " nolu kurs listenizde bulunamamıstır.");
                course=null;
            }
        } catch (SQLException e) {
            System.out.println(id +" nolu course ararken hata oluştu. HATA: "+ e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
            closeResulSET(resultSet);
        }
        return course;
    }
    public List<Course> findCourseByName(String name) {
        List<Course> searchedCourses= new ArrayList<>();
        this.con=getConnection();
        String searched= "%"+name+"%";
        String query= "SELECT * FROM courses WHERE course_name ILIKE ?";
        this.prst=getPreparedStatement(query);
        ResultSet resultSet = null;

        try {
            prst.setString(1,searched);
            resultSet= prst.executeQuery();
            while(resultSet.next()){
                Course course=new Course();
                course.setId(resultSet.getInt(1));
                course.setCourse_name(resultSet.getString(2));
                course.setCredit(resultSet.getInt(3));
                course.setCourse_fee(resultSet.getDouble(4));
                course.setStart_date(resultSet.getDate(5).toLocalDate());
                course.setFinish_date(resultSet.getDate(6).toLocalDate());
                searchedCourses.add(course);
            }
            if (searchedCourses.isEmpty()){
                System.out.println("Kurs isminde "+name + " kelimesini içeren bir kurs bulunamamıstır..");
            }else {
                searchedCourses.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println(name +" isminde kurs aranırken hata oluştu. HATA: "+e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
            closeResulSET(resultSet);
        }
        return searchedCourses;
    }
    public void updateCourseByID(Course course) {
        this.con=getConnection();
        String query="UPDATE courses SET course_name=?, credit=?, course_fee=?, start_date=?, finish_date=? WHERE course_id= ?";
        this.prst=getPreparedStatement(query);

        try {
            prst.setString(1,course.getCourse_name());
            prst.setInt(2,course.getCredit());
            prst.setDouble(3,course.getCourse_fee());
            prst.setDate(4,Date.valueOf(course.getStart_date()));
            prst.setDate(5,Date.valueOf(course.getFinish_date()));
            prst.setInt(6,course.getId());
            int updated= prst.executeUpdate();
            if (updated>0){
                System.out.println(course.getId()+" nolu kurs bilgileri güncellenmiştir.");
            }
        } catch (SQLException e) {
            System.out.println(course.getId()+ " nolu kurs güncellenirken hata oluştu. HATA: "+e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
        }
    }
    public void filterCoursesByStartDate(LocalDate searchedDate) {
        List<Course> searchedCources = new ArrayList<>();
        this.con=getConnection();
        String query="SELECT * FROM courses WHERE start_date> ?";
        this.prst=getPreparedStatement(query);
        ResultSet resultSet = null;
        try {
            prst.setDate(1,Date.valueOf(searchedDate));
            resultSet= prst.executeQuery();
            while (resultSet.next()){
                Course course=new Course();
                course.setId(resultSet.getInt(1));
                course.setCourse_name(resultSet.getString(2));
                course.setCredit(resultSet.getInt(3));
                course.setCourse_fee(resultSet.getDouble(4));
                course.setStart_date(resultSet.getDate(5).toLocalDate());
                course.setFinish_date(resultSet.getDate(6).toLocalDate());
                searchedCources.add(course);
            }
            if (searchedCources.isEmpty()){
                System.out.println(searchedDate+" tarihinden sonra başlayan bir kurs bulunamamıştır.");
                System.out.println("Kurs talep bilginizi bizimle paylasınız :) www.techproeducation.com");
            }else{
                System.out.println("***"+"~".repeat(15)+"COURSES"+"~".repeat(15)+"***");
                System.out.printf("%-5s %-12s %-6s %-6s %-11s %-11s\n","Id", "Course_Name", "Credit", "Course_Fee", "Start_Date","Finish_Date");
                for (Course w:searchedCources) {
                    System.out.printf("%-5s %-12s %-6s %-10s %-11s %-11s\n",w.getId(),w.getCourse_name(),w.getCredit(),
                            w.getCourse_fee(), w.getStart_date(),w.getFinish_date());
                }
                System.out.println("~".repeat(40));
            }
        } catch (SQLException e) {
            System.out.println(searchedDate+" sonrası kurslar filtrelenirken hata oluştu. HATA: "+e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
            closeResulSET(resultSet);
        }
    }
    public void addPlannedCourses(List<Course> courses) {
        this.con= getConnection();
        String query="INSERT INTO courses VALUES(?,?,?,?,?,?) ON CONFLICT (course_id) DO NOTHING";
        this.prst=getPreparedStatement(query);

        try {
            for (Course w: courses) {
                prst.setInt(1,w.getId());
                prst.setString(2,w.getCourse_name());
                prst.setInt(3,w.getCredit());
                prst.setDouble(4,w.getCourse_fee());
                prst.setDate(5,Date.valueOf(w.getStart_date()));
                prst.setDate(6,Date.valueOf(w.getFinish_date()));
                prst.addBatch();
            }
            prst.executeBatch();
            System.out.println("Planlanmıs kurslar listenizde ekli..");
        } catch (SQLException e) {
            System.out.println("Planlanlı kurslar listeye eklenirken hata oluştu. HATA: "+ e.getMessage());
        }
        finally {
            closePRSTandCON(con,prst);
        }
    }
}
