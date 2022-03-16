import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
//        Cat cat = new Cat("Ryzhik");
//        cat = new Cat("Chernysh");
//        try {
//            cat.finalize();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        cat = null;
//
//        for(int i = 0; i < 50000; i++){
//            cat = new Cat("Cat" + i);
//        }
//
//        Cat.printCatCount();
//        Cat.setCatCount(0);
//
//        //additional task
//        for(int i = 0; i < 10; i++){
//            cat = new Cat("Cat" + i);
//        }
//        System.out.println(Cat.getCatCount());

        Cat cat1 = new Cat("c1");
        Cat cat2 = new Cat("c2");
        Cat cat3 = new Cat("c3");
        Cat cat4 = new Cat("c4");
        Cat cat5 = new Cat("c5");
        Cat cat6 = new Cat("c6");
        Cat cat7 = new Cat("c7");
        Cat cat8 = new Cat("c8");
        Cat cat9 = new Cat("c9");
        Cat cat10 = new Cat("c10");

        for(Cat c : Cat.cats){
            System.out.println(c.getName());
        }

        KissMyShinyMetalAss k = new KissMyShinyMetalAss();
        System.out.println(k);

        ArrayList<Cat> family = new ArrayList<>();

        Cat granny = new Cat("Granny");
        family.add(granny);
        Cat grandpa = new Cat("Grandpa");
        family.add(grandpa);
        Cat mom = new Cat("Mom", granny, grandpa);
        family.add(mom);
        Cat dad = new Cat("Dad", granny, grandpa);
        family.add(dad);
        Cat son = new Cat("Son", mom, dad);
        family.add(son);
        Cat daughter = new Cat("Daughter", mom, dad);
        family.add(daughter);

        for(Cat c : family){
            System.out.println("Cat name is " + c.getName() +
                    (c.getCatsMom() != null ? ", mother is " + c.getCatsMom().getName() : ", no mother") +
                    (c.getCatsDad() != null ? ", father is " + c.getCatsDad().getName() : ", no father"));
        }

        Scanner scanner = new Scanner(System.in);
        int[] array = new int[5];
        for (int i = 0; i < 5; i++){
            System.out.print("Enter number: ");
            array[i] = scanner.nextInt();
        }

        boolean isSorted = false;
        int buf;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length-1; i++) {
                if(array[i] > array[i+1]){
                    isSorted = false;

                    buf = array[i];
                    array[i] = array[i+1];
                    array[i+1] = buf;
                }
            }
        }
        System.out.println(Arrays.toString(array));






    }

    public String  maxNumber(int num1, int num2){
        return "Max is " + Math.max(num1,num2);
    }
}
