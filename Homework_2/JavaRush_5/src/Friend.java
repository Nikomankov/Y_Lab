public class Friend {
    private String name;
    private int age;
    private Sex sex;
    public enum Sex {MALE, FEMALE}

    public Friend(String name){
        this.name = name;
    }

    public Friend(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Friend(String name, int age, Sex sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public void initialize(String name){
        this.name = name;
    }

    public void initialize(String name, int age){
        this.name = name;
        this.age = age;
    }

    public void initialize(String name, int age, Sex sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
