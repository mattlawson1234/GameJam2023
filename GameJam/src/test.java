public class test {
    
    public static void main(String[] args){

        int firstIndex = 1 + (int)(Math.random() * 18);
        int secondIndex = 1 + (int)(Math.random() * 18);

        int tempFirstIndex = firstIndex + (int)(Math.pow(-1, (int)(Math.random() * 2)));
		int tempSecondIndex = secondIndex + (int)(Math.pow(-1, (int)(Math.random() * 2)));

        System.out.println(tempFirstIndex + " " + tempSecondIndex);

    }

}
