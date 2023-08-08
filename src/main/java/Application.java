import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        start();
    }
    static Scanner input= new Scanner(System.in);
    private static void start() {
        System.out.println("***"+"~".repeat(10)+"COURSE MANAGEMENT SYSTEM"+"~".repeat(10)+"***");
        CourseServices courseServices= new CourseServices();
        courseServices.createTable();
        courseServices.addPlannedCourses();
        char selection;
        do {
            System.out.println("Lütfen yapmak istediğiniz işlemi seçiniz...");
            System.out.println("1-Yeni bir kurs ekleme");
            System.out.println("2-Yeni birden fazla kurs ekleme");
            System.out.println("3-Id ile kurs silme");
            System.out.println("4-Kurs ismi ile silme");
            System.out.println("5-Id ile kurs arama");
            System.out.println("6-Kurs ismi ile kurs arama");
            System.out.println("7-Id ile kurs bilgilerini güncelleme");
            System.out.println("8-Kurs listesinin tamamını görüntüleme");
            System.out.println("9-Kurs ismi ile filtreleme yapma");
            System.out.println("0-Kurs baslangıc tarihi ile filtreleme yapma");
            System.out.println("Q-CIKIS");
            System.out.print("Seçiminiz: ");
            selection= input.next().trim().toUpperCase().charAt(0);

            int id;
            String name;

            switch (selection){
                case '1':
                    courseServices.addCourse();
                    break;
                case '2':
                    courseServices.addManyCourse();
                    break;
                case '3':
                    id= inputID();
                    if (id!=0){
                        courseServices.deleteCourseByID(id);
                    }
                    break;
                case '4':
                    name=inputName();
                    courseServices.deleteCourseByName(name);
                    break;
                case '5':
                    id= inputID();
                    if (id!=0){
                        courseServices.findCourseByID(id);
                    }
                    break;
                case '6':
                    name=inputName();
                    courseServices.findCourseByName(name);
                    break;
                case '7':
                    id=inputID();
                    if (id!=0){
                        courseServices.updateCourseByID(id);
                    }
                    break;
                case '8':
                    courseServices.showCourses();
                    break;
                case '9':
                    name=inputName();
                    courseServices.filterCoursesByName(name);
                    break;
                case '0':
                    courseServices.filterCoursesByStartDate();
                    break;
                case 'Q':
                    System.out.println("Uygulamamızı tercih ettiğiniz için teşekkür eder; İYİ GÜNLER dileriz");
                    break;
                default:
                    System.out.println("Hatalı bir giriş yaptınız; tekrar deneyiniz..");
                    break;
            }
        }while(selection!='Q');
    }

    private static int inputID() {
        System.out.print("Lütfen işlem yapmak istediğiniz ID numarasını giriniz: ");
        int inputID = 0;
        try{
           inputID= input.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Gecersiz bir değer girdiniz. ID için numara giriniz.");
        }
        input.nextLine();
        return inputID;
    }
    private static String inputName() {
        System.out.print("Lütfen işlem yapmak istediğiniz course ismini yazınız: ");
        input.nextLine();
        return input.nextLine().trim();
    }
}
