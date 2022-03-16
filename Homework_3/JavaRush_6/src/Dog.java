public class Dog {
    public static String name;

    public Dog(String name){
        this.name = name;
    }

    @Override
    protected void finalize(){
        System.out.println(name + " destroyed");
    }
}
