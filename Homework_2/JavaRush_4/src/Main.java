import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //2.5
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        Cat cat3 = new Cat();
        Cat cat4 = new Cat();
        Cat cat5 = new Cat();
        System.out.println("Cats count is " + Cat.getCatsCount());

        //6. Задачи на условные операторы
        task6_1(scanner);
        task6_2(scanner);
        task6_3(scanner);
        task6_4(scanner);
        task6_5(scanner);
        task6_6(scanner);

        //10. Задачи на циклы
        task10_1();
        task10_2();
        task10_3(scanner);
        task10_4();
        task10_5();

        //13. Задачи на FOR
        task13_1();
        task13_2(scanner);
        task13_3();
        task13_4();
        task13_5(scanner);

        //Additional tasks
        additional1();
        additional2(scanner);
        additional3(scanner);
        additional4(scanner);
    }
    //6.1
    public static void task6_1(Scanner scanner){
        System.out.print("Введите два числа для определения меньшего из них:\n1 число = ");
        int a = scanner.nextInt();
        System.out.print("2 число = ");
        int b = scanner.nextInt();
        System.out.println(a < b ? a : b);
    }
    //6.2
    public static void task6_2(Scanner scanner){
        System.out.println("Введите четыре числа для определения большего из них:");
        int[] array = new int[4];
        for(int i = 0; i < 4; i++){
            System.out.print((i+1) + " число = ");
            array[i] = scanner.nextInt();
        }
        Arrays.stream(array).max().ifPresent(System.out::println);
    }
    //6.3
    public static void task6_3(Scanner scanner){
        System.out.println("Введите три числа для дальнейшего их вывода в порядке убывания:");
        int[] array = new int[3];
        for(int i = 0; i < 3; i++){
            System.out.print((i+1) + " число = ");
            array[i] = scanner.nextInt();
        }
        //Stream API
        IntStream.of(array).boxed().sorted(Collections.reverseOrder()).forEach(System.out::println);

        //bubble sort
        boolean sorted = false;
        int temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] < array[i+1]) {
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    sorted = false;
                }
            }
        }
        for(int i : array){
            System.out.println(i);
        }
    }
    //6.4
    public static void task6_4(Scanner scanner){
        String name1;
        String name2;
        System.out.print("Ввведите имена для сравнения:\n1 имя = ");
        name1 = scanner.nextLine();
        System.out.print("2 имя = ");
        name2 = scanner.nextLine();
        if(name1.equals(name2)){
            System.out.println("Имена идентичны");
        } else {
            if(name1.length() == name2.length())
                System.out.println("ДЖлина имен равны");
        }
    }

    //6.5
    public static void task6_5(Scanner scanner){
        System.out.println("Введите ваше имя и возраст:\nимя = ");
        String name = scanner.nextLine();
        System.out.println("возраст = ");
        int age = scanner.nextInt();
        if(age < 18)  System.out.println("Подрасти ещё");
    }

    //6.6
    public static void task6_6(Scanner scanner){
        System.out.println("Введите ваше имя и возраст:\nимя = ");
        String name = scanner.nextLine();
        System.out.println("возраст = ");
        int age = scanner.nextInt();
        if(age > 20) System.out.println("И 18-ти достаточно");
    }
    //10.1
    public static void task10_1(){
        int i = 0;
        while(i<10){
            System.out.println(i);
            i++;
        }
    }
    //10.2
    public static void task10_2(){
        int i = 10;
        while(i>0){
            System.out.println(i);
            i--;
        }
    }
    //10.3
    public static void task10_3(Scanner scanner){
        System.out.print("Введите строки и число.\nСтрока = ");
        String string = scanner.nextLine();
        System.out.print("Число = ");
        int number = scanner.nextInt();
        while(number>0){
            System.out.println(string);
            number--;
        }
    }
    //10.4
    public static void task10_4(){
        int i = 10;
        while(i > 0){
            int j = 10;
            while (j > 0){
                System.out.print("S");
                j--;
            }
            System.out.println();
            i--;
        }
    }
    //10.5
    public static void task10_5(){
        int i = 1;
        while(i<11){
            int j = 1;
            while(j<11){
                int result = i*j;
                System.out.print((result/10 < 1 ? " " : "") + result + " ");
                j++;
            }
            System.out.println();
            i++;
        }
    }
    //13.1
    public static void task13_1(){
        for(int i = 0; i < 100; i++){
            if(i%2 == 0) System.out.println(i);
        }
    }
    //13.2
    public static void task13_2(Scanner scanner){
        System.out.println("Введите два числа.\n1 число = ");
        int m = scanner.nextInt();
        System.out.println("2 число = ");
        int n = scanner.nextInt();
        for(int i = 0; i < m; i++){
            for(int j = 0; i < n; j++){
                System.out.print(8);
            }
            System.out.println();
        }
    }
    //13.3
    public static void task13_3(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < i+1; j++){
                System.out.print(8);
            }
            System.out.println();
        }
    }
    //13.4
    public static void task13_4(){
        for(int i = 0; i < 10; i++){
            System.out.print(8);
        }
        for(int i = 0; i < 10; i++){
            System.out.println(8);
        }
    }
    //13.5
    public static void task13_5(Scanner scanner){
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        for(int i = 0; i < 10; i++){
            System.out.println(name + " любит меня.");
        }
    }
    //--------------------------------------------------
    //Additional tasks
    public static void additional1(){
        int i = 0;
        while(i < 100){
            System.out.println("Я никогда не буду работать за копейки. Амиго.");
            i++;
        }
    }
    public static void additional2(Scanner scanner){
        int[] array = new int[3];
        System.out.println("Введите три чила.\n1 число = ");
        for(int i = 0; i < 3; i++){
            System.out.print((i+1) + " число = ");
            array[i] = scanner.nextInt();
        }
        Arrays.sort(array);
        System.out.println(array[1]);
    }
    public static void additional3(Scanner scanner){
        System.out.println("Вводите числа, чтобы узнать их сумму." +
                "\nЧтобы посчитать сумму введите \"-1\"");
        int number = 0;
        List<Integer> list = new ArrayList<>();
        while(number != -1){
            number = scanner.nextInt();
            list.add(number != -1 ? number : 0);
        }
        System.out.print("Сумма = " + list.stream().mapToInt(i -> i).sum());
        list.stream().mapToInt(i -> i).sum();
    }
    public static void additional4(Scanner scanner){
        System.out.print("Введите имя и дату рождения:\nимя = ");
        String name = scanner.nextLine();
        System.out.print("год = ");
        int year = scanner.nextInt();
        System.out.print("месяц = ");
        int month = scanner.nextInt();
        System.out.print("день = ");
        int day = scanner.nextInt();
        System.out.println("Меня зовут " + name +
                "\nЯ родился " + day + "." + month + "." +year);
    }
}

//2.Задачи на видимость переменных
class Cat{
    private String name;
    private static int catsCount = 0;
    private String fullName;

    //2.5
    public Cat(){
        addNewCat();
    }

    public static int getCatsCount(){
        return catsCount;
    }

    //2.1
    public void setName(String name) {
        this.name = name;
    }

    //2.4
    public void setFullName(String firstName, String lastName){
        String fullName = firstName + " " +lastName;
        this.fullName = fullName;
    }

    //2.2
    public static void addNewCat(){
        catsCount++;
    }

    //2.3
    public static void setCatsCount(int newCatsCount){
        catsCount = newCatsCount;
    }
}

