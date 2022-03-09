public class Cat {
    private String name;
    private int age;
    private int weight;
    private int strength;
    private String address;
    private String color;

    public Cat(String name, int age, int weight, int strength) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.strength = strength;
    }
    public Cat(String name){
        this.name = name;
    }
    public Cat(String name, int weight, int age){
        this.name = name;
        this.weight = weight;
        this.age = age;
    }
    public Cat(String name, int age){
        this.name = name;
        this.age = age;
        this.weight = 3;
    }
    public Cat(int weight, String color){
        this.weight = weight;
        this.color = color;
    }
    public Cat(int weight, String color, String address){
        this.weight = weight;
        this.color = color;
        this.address = address;
    }

    public void initialize(int weight){
        this.name = name;
    }
    public void initialize(String name, int weight, int age){
        this.name = name;
        this.weight = weight;
        this.age = age;
    }
    public void initialize(String name, int age){
        this.name = name;
        this.age = age;
        this.weight = 3;
    }
    public void initialize(int weight, String color){
        this.weight = weight;
        this.color = color;
    }
    public void initialize(int weight, String color, String address){
        this.weight = weight;
        this.color = color;
        this.address = address;
    }

    public boolean fight(Cat anotherCat){
        double cat1Power = parameterDefinitions(this.age, this.weight, this.strength);
        double cat2Power = parameterDefinitions(anotherCat.age, anotherCat.weight, anotherCat.strength);
        return cat1Power >= cat2Power;
    }
    private double parameterDefinitions(int age, int weight, int strength){
        double ageCoefficient;
        if (age > 3){
            if(age < 6){
                ageCoefficient = 1;
            } else {
                ageCoefficient = 1 - ((age-6)*0.16);
            }
        } else {
            ageCoefficient = 1 - ((4 - age) * 0.3);
        }
        return strength/weight*ageCoefficient;
    }
}
