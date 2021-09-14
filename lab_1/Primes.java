public class Primes {
    public static void main(String[] args) {
        for(int j = 2; j < 101; j++) {
            if (isPrime(j)) {
                System.out.println(j);
            }
        }
    }
    public static boolean isPrime(int n) {
        boolean a = true;
        for(int i = 2; i < n; i++) {
            if(n % i == 0) {
                a = false;
                break;
            }
        }
        return a;
    }
}