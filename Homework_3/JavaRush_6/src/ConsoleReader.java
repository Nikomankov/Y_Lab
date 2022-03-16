import java.util.Scanner;

public class ConsoleReader {

    public static String readString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int readInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static double readDouble(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    public static boolean readBoolean(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextBoolean();
    }
}
