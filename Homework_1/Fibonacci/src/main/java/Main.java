public class Main {
    public static void main(String[] args) {

        int number = 25;

        Fibonacci fib = new Fibonacci();

        long fibClassStart = System.nanoTime();
        System.out.println("Result = " + fib.getNumberByPosition(25));
        long fibClassFinish = System.nanoTime() - fibClassStart;

        long recTimeStart = System.nanoTime();
        System.out.println("Result = " + fibRecursive(number));
        long recTimeFinish = System.nanoTime()-recTimeStart;

        long fibCMStart = System.nanoTime();
        fibCycleMemory(number);
        long fibCMFinish = System.nanoTime() - fibCMStart;

        long fibCWMStart = System.nanoTime();
        fibCycleWithoutMemory(number);
        long fibCWMFinish = System.nanoTime() - fibCWMStart;

        System.out.println("Class time = " + fibClassFinish +
                "\nRecursive time = " + recTimeFinish +
                "\nCycle with memory = " + fibCMFinish +
                "\nCycle without memory = " + fibCWMFinish);

    }

    public static int fibRecursive(int num){
        if(num < 2){
            return num;
        }
        return fibRecursive(num-1) + fibRecursive(num -2);
    }

    public static int[] fibCycleMemory(int num){
        int[] output = new int[num+1];
        for(int i = 0; i <= num; i++){
            if(i < 2){
                output[i] = i == 0 ? 0 : 1;
            } else {
                output[i] = output[i-1] + output[i-2];
            }
        }
        System.out.println("Result = " + output[num]);
        return output;
    }

    public static void fibCycleWithoutMemory(int num){
        int num1 = 0;
        int num2 = 1;
        for(int i = 0; i < num-1; i++){
            num2 = num1 + num2;
            num1 = num2 - num1;
        }
        System.out.println("Result = " + num2);
    }
}
