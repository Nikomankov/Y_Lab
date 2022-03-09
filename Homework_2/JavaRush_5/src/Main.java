public class Main {
    public static void main(String[] args) {

        Cat cat1 = new Cat("Барсик", 10,4,10);
        Cat cat2 = new Cat("Варсик", 5,5,12);
        Cat cat3 = new Cat("Гарсик", 1,1,2);

        cat1.fight(cat2);
        cat1.fight(cat3);
        cat2.fight(cat3);


    }
}
