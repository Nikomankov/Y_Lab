import java.util.ArrayList;

public class Cat {
    private String name;
    private static int catCount;
    private Cat mom;
    private Cat dad;
    public static ArrayList<Cat> cats = new ArrayList<>();

    public Cat(String name){
        cats.add(this);
        this.name = name;
        catCount ++;
    }

    public Cat(String name, Cat catsMom){
        cats.add(this);
        this.mom = catsMom;
        this.name = name;
        catCount ++;
    }
    public Cat(String name, Cat catsMom, Cat catsDad){
        cats.add(this);
        this.mom = catsMom;
        this.dad = catsDad;
        this.name = name;
        catCount ++;
    }

    public Cat getCatsMom(){
        return mom;
    }

    public  Cat getCatsDad(){
        return dad;
    }

    public String getName(){
        return name;
    }

    public static int getCatCount() {
        return catCount;
    }

    public static void setCatCount(int catCount) {
        Cat.catCount = catCount;
    }

    public static void printCatCount(){
        System.out.println(catCount);
    }

    @Override
    protected void finalize(){
        System.out.println(name + " destroyed");
        catCount --;
    }

}
