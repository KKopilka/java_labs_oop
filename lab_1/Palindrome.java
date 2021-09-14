public class Palindrome {

    public static boolean isPalindrome(String a) {
        int s = 0;
        for (int i = 0; i < a.length() / 2; i++) {
            if (a.charAt(i) == a.charAt(a.length() - 1 - i)) {
                s = s + 1;
            }
        }
        return s == a.length() / 2;
    }

    public static void main(String[] args) {
        String s1 = "java Palindrome madam racecar apple kayak song noon";
        String[] s = s1.split(" ");
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i] + " = " + isPalindrome(s[i]));
        }
    }
}