import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CourseServices {  //course methodlarını içerir
    CourseRepository courseRepo= new CourseRepository();
    Scanner input= new Scanner(System.in);

    public void createTable(){   //databasede tablo oluşturma
        courseRepo.createTable();
    }
    public void addCourse(){
        System.out.println("Lütfen eklemek istediğiniz course bilgilerini giriniz.");
        System.out.print("Course_name: ");
        String courseName= input.nextLine().trim();
        System.out.print("Credit: ");
        int credit= input.nextInt();
        System.out.print("Course_fee: ");
        double courseFee= input.nextDouble();
        System.out.println("Start_date");
        System.out.print("GUN: ");
        int gun= input.nextInt();
        System.out.print("AY: ");
        int ay= input.nextInt();
        System.out.print("YIL: ");
        int yil= input.nextInt();
        LocalDate startDate= LocalDate.of(yil,ay,gun);
        System.out.println("Finish_date");
        System.out.print("GUN: ");
        int gunF= input.nextInt();
        System.out.print("AY: ");
        int ayF= input.nextInt();
        System.out.print("YIL: ");
        int yilF= input.nextInt();
        LocalDate finishDate= LocalDate.of(yilF,ayF,gunF);
        Course newCourse= new Course(courseName,credit,courseFee,startDate,finishDate);
        courseRepo.addCourse(newCourse);
    }
    public void addManyCourse() {
        List<Course> addList= new ArrayList<>();
        char onay;
        do {
            System.out.println("Lütfen eklemek istediğiniz course bilgilerini giriniz.");
            System.out.print("Course_name: ");
            String courseName= input.next().trim();
            System.out.print("Credit: ");
            int credit= input.nextInt();
            System.out.print("Course_fee: ");
            double courseFee= input.nextDouble();
            System.out.println("Start_date");
            System.out.print("GUN: ");
            int gun= input.nextInt();
            System.out.print("AY: ");
            int ay= input.nextInt();
            System.out.print("YIL: ");
            int yil= input.nextInt();
            LocalDate startDate= LocalDate.of(yil,ay,gun);
            System.out.println("Finish_date");
            System.out.print("GUN: ");
            int gunF= input.nextInt();
            System.out.print("AY: ");
            int ayF= input.nextInt();
            System.out.print("YIL: ");
            int yilF= input.nextInt();
            LocalDate finishDate= LocalDate.of(yilF,ayF,gunF);
            Course newCourse= new Course(courseName,credit,courseFee,startDate,finishDate);
            addList.add(newCourse);

            System.out.println("Eklemek istediğiniz kurslar hazırsa ==> ekleme işlemi yapılması için 0 basınız");
            System.out.println("Kurs eklemeye devam etmek için ==> 0 dısında herhangi bir tusa basınız");
            onay=input.next().trim().charAt(0);
        }while(onay!='0');
        courseRepo.addManyCourse(addList);
    }
    public void showCourses(){
        courseRepo.showCourses();
    }
    public void deleteCourseByID(int id){
        courseRepo.deleteCourseByID(id);
    }
    public void deleteCourseByName(String courseName){
        courseRepo.deleteByName(courseName);
    }
    public Course findCourseByID(int id){
        return courseRepo.findCourseByID(id);
    }
    public List<Course> findCourseByName(String name){
         return courseRepo.findCourseByName(name);
    }
    public void updateCourseByID(int id) {
       try{
            Course course= findCourseByID(id) ;
            if (course!=null){
                System.out.println("Güncellemek istediğiniz yeni kurs bilgilerini giriniz.");
                System.out.print("Course_name: ");
                String name=input.nextLine().trim();
                System.out.print("Credit: ");
                int credit= input.nextInt();
                System.out.print("Course_Fee: ");
                double course_fee= input.nextDouble();
                System.out.println("Start_date");
                System.out.print("GUN: ");
                int gun= input.nextInt();
                System.out.print("AY: ");
                int ay= input.nextInt();
                System.out.print("YIL: ");
                int yil= input.nextInt();
                LocalDate startDate= LocalDate.of(yil,ay,gun);
                System.out.println("Finish_date");
                System.out.print("GUN: ");
                int gunF= input.nextInt();
                System.out.print("AY: ");
                int ayF= input.nextInt();
                System.out.print("YIL: ");
                int yilF= input.nextInt();
                LocalDate finishDate= LocalDate.of(yilF,ayF,gunF);

                course.setCourse_name(name);
                course.setCredit(credit);
                course.setCourse_fee(course_fee);
                course.setStart_date(startDate);
                course.setFinish_date(finishDate);

                courseRepo.updateCourseByID(course);
            }
        }catch (NullPointerException e){
           System.out.println("");
       }
    }
    public void filterCoursesByName(String name) {
        List<Course> filterList= findCourseByName(name);
        if(!filterList.isEmpty()){
            System.out.println("***"+"~".repeat(20)+"COURSES"+"~".repeat(20)+"***");
            System.out.printf("%-5s %-12s %-6s %-6s %-11s %-11s\n","Id", "Course_Name", "Credit", "Course_Fee", "Start_Date","Finish_Date");
            for (Course w:filterList ) {
                System.out.printf("%-5s %-12s %-6s %-10s %-11s %-11s\n",w.getId(),w.getCourse_name(),w.getCredit(),
                        w.getCourse_fee(), w.getStart_date(),w.getFinish_date());
            }
            System.out.println("~".repeat(60));
        }
    }
    public void filterCoursesByStartDate() {
        try{
            System.out.println("Belirttiğiniz tarihten sonra baslayan kurslar gösterilecektir.");
            System.out.println("Filtreleme yapmak istediğiniz tarihi giriniz.");
            System.out.print("GUN: ");
            int gun= input.nextInt();
            System.out.print("AY: ");
            int ay= input.nextInt();
            System.out.print("YIL: ");
            int yil= input.nextInt();
            boolean gecerliTarih= gun>0 && gun<32 && ay>0 &&ay<13;
            if (gecerliTarih){
                LocalDate searchedDate= LocalDate.of(yil,ay,gun);
                courseRepo.filterCoursesByStartDate(searchedDate);
            }else{
                System.out.println("Lütfen geçerli bir tarih bilgisi giriniz.");
            }
        }catch (InputMismatchException e){
            System.out.println("Tarih bilgisi girilirken yanlış bilgi girildi. Tekrar deneyiniz..");
        }


    }
    public void addPlannedCourses(){
        List<Course> plannedCourses= new ArrayList<>();
        Course course1= new Course(3001,"SpringMVC",10,100.05,LocalDate.of(2023,1,10),LocalDate.of(2023,2,10));
        Course course2= new Course(3002,"SpringBOOT",8,120.05,LocalDate.of(2023,2,11),LocalDate.of(2023,2,28));
        Course course3= new Course(3003,"S.Security",6,200.15,LocalDate.of(2023,3,3),LocalDate.of(2023,3,12));
        Course course4= new Course(3004,"Java",26,159.99,LocalDate.of(2022,11,3),LocalDate.of(2023,2,12));
        Course course5= new Course(3005,"SQL",6,175.55,LocalDate.of(2023,1,3),LocalDate.of(2023,3,12));
        Course course6= new Course(3006,"React",12,255.85,LocalDate.of(2023,6,3),LocalDate.of(2023,7,12));
        Course course7= new Course(3007,"HTML",6,125.99,LocalDate.of(2023,3,3),LocalDate.of(2023,3,22));
        Course course8= new Course(3008,"CSS",5,125.99,LocalDate.of(2023,4,3),LocalDate.of(2023,4,22));
        Course course9= new Course(3009,"JavaScript",10,199.99,LocalDate.of(2023,1,10),LocalDate.of(2023,2,10));
        Course course10= new Course(3010,"Java",26,159.99,LocalDate.of(2023,5,3),LocalDate.of(2023,5,31));
        Course course11= new Course(3011,"Java",26,159.99,LocalDate.of(2023,8,12),LocalDate.of(2023,10,12));
        plannedCourses.add(course1);
        plannedCourses.add(course2);
        plannedCourses.add(course3);
        plannedCourses.add(course4);
        plannedCourses.add(course5);
        plannedCourses.add(course6);
        plannedCourses.add(course7);
        plannedCourses.add(course8);
        plannedCourses.add(course9);
        plannedCourses.add(course10);
        plannedCourses.add(course11);

        courseRepo.addPlannedCourses(plannedCourses);
    }

}
