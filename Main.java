import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1
        System.out.println(nonRepeat("abracadabra"));
        System.out.println(nonRepeat("abababcac"));
        // 2
        System.out.println((bruteForce(1, 5)));
        System.out.println((bruteForce(2, 2)));
        System.out.println((bruteForce(4, 3)));
        // 3
        System.out.println(encode(new int[]{0, 31, 28, 10, 29}, "MKIIT"));
        System.out.println(Arrays.toString(decode("MTUCI", "MKIIT")));
        // 4
        System.out.println(split("()()()"));
        System.out.println(split("((()))"));
        System.out.println(split("((()))(())()()(()())"));
        System.out.println(split("((())())(()(()()))"));
        // 5
        System.out.println(shortHand("abbccc"));
        System.out.println(shortHand("vvvvaajaaaaa"));
        // 6
        System.out.println(convertToRome(8));
        System.out.println(convertToRome(1234));
        System.out.println(convertToRome(52));
        // 7
        System.out.println(uniqueSubstring("31312131"));
        System.out.println(uniqueSubstring("1111111"));
        System.out.println(uniqueSubstring("12223234333"));
        // 8
        System.out.println(labirint(new int[][]{
                {1, 3, 1},
                {1, -1, 1},
                {4, 2, 1}
        }));
        System.out.println(labirint(new int[][]{
                {2, -7, 3},
                {-4, -1, 8},
                {4, 5, 9}
        }));
        // 9
        System.out.println(numericOrder("t3o the5m 1One all6 r4ule ri2ng"));
        System.out.println(numericOrder("re6sponsibility Wit1h gr5eat power3 4comes g2reat"));
        //10
        System.out.println(fibString("CCCABDD"));
        System.out.println(fibString("ABC"));
    }

    // 1
    public static String nonRepeat(String str) {
        str = str.toLowerCase();
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        return remove(str, countMap, 0);
    }
    private static String remove(String s, Map<Character, Integer> countMap, int index) {
        if (index >= s.length()) {
            return "";
        }
        char currentChar = s.charAt(index);
        if (countMap.get(currentChar) > 3) {
            return remove(s, countMap, index + 1);
        } else {
            return currentChar + remove(s, countMap, index + 1);
        }
    }
    // 2
    public static List<String> bruteForce(int n, int k) {
        List<String> result = new ArrayList<>();

        if (n > k || k > 5 || n <= 0) {
            return result;
        }

        StringBuilder alphabet = new StringBuilder();
        for (int i = 0; i < k; i++) {
            alphabet.append(i);
        }
        combinations(result, "", alphabet.toString(), n);
        return result;
    }
    private static void combinations(List<String> result, String prefix, String alphabet, int length) {
        if (prefix.length() == length) {
            result.add(prefix);
            return;
        }
        for (int i = 0; i < alphabet.length(); i++) {
            combinations(result, prefix + alphabet.charAt(i), alphabet.substring(0, i) + alphabet.substring(i + 1), length);
        }
    }
    // 3
    public static int[] decode(String str, String key){
        int[] result = new int[str.length()];
        for(int i = 0; i < result.length; i++){
            result[i] = (str.charAt(i) ^ key.charAt(i));
        }
        return result;
    }
    public static String encode(int[] arr, String key){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++){
            sb.append((char) (arr[i] ^ key.charAt(i)));
        }
        return sb.toString();
    }
    // 4
    public static List<String> split(String str){
        List<String> res = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        int lp = 0;
        int rp = 0;
        for (char c : str.toCharArray()){
            if (c == '('){
                stack.add(c);
            }
            else {
                stack.pop();
            }
            rp++;
            if (stack.empty()) {
                res.add(str.substring(lp, rp));
                lp = rp;
            }
        }
        return res;
    }
    // 5
    public static String shortHand(String str) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= str.length(); i++) {
            if (i < str.length() && str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                result.append(str.charAt(i - 1));
                if (count > 1) {
                    result.append("*").append(count);
                }
                count = 1;
            }
        }
        return result.toString();
    }
    // 6
    public static String convertToRome(int num){
        if (num > 1502) return "";
        StringBuilder sb = new StringBuilder();
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] chars = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < nums.length; i++){
            while (num >= nums[i]){
                sb.append(chars[i]);
                num -= nums[i];
            }
        }
        return sb.toString();
    }
    // 7
    public static String uniqueSubstring(String str){
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < str.length(); i++){
            if (i % 2 == 0){
                map2.put(str.charAt(i), map2.getOrDefault(str.charAt(i), 0) + 1);
            }else {
                map1.put(str.charAt(i), map1.getOrDefault(str.charAt(i), 0) + 1);
            }
        }
        int max1 = 0, max2 = 0;
        for (var i : map1.values()){
            if (i > max1){
                max1 = i;
            }
        }
        for (var i : map2.values()){
            if (i > max2){
                max2 = i;
            }
        }
        if (max1 > max2) return "нечет";
        return "чет";
    }
    // 8
    public static List<String> labirint(int[][] maze) {
        int n = maze.length;

        if (maze[0][0] < 0 || maze[n - 1][n - 1] < 0) {
            return List.of("Прохода нет");
        }
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }
        cost[n - 1][n - 1] = maze[n - 1][n - 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (maze[i][j] < 0) continue;

                if (i > 0 && maze[i - 1][j] >= 0) {
                    cost[i - 1][j] = Math.min(cost[i - 1][j], cost[i][j] + maze[i - 1][j]);
                }
                if (j > 0 && maze[i][j - 1] >= 0) {
                    cost[i][j - 1] = Math.min(cost[i][j - 1], cost[i][j] + maze[i][j - 1]);
                }
            }
        }
        if (cost[0][0] == Integer.MAX_VALUE) {
            return List.of("Прохода нет");
        }
        List<String> path = new ArrayList<>();
        int i = 0, j = 0;
        path.add(String.valueOf(maze[i][j]));
        while (i < n - 1 || j < n - 1) {
            if (i < n - 1 && cost[i + 1][j] + maze[i][j] == cost[i][j]) {
                path.add(String.valueOf(maze[i + 1][j]));
                i++;
            } else if (j < n - 1 && cost[i][j + 1] + maze[i][j] == cost[i][j]) {
                path.add(String.valueOf(maze[i][j + 1]));
                j++;
            }
        }
        return List.of(String.join("-", path), String.valueOf(cost[0][0]));
    }
    // 9
    public static String numericOrder(String str){
        String[] words = new String[str.split(" ").length];
        for (var word : str.split(" ")){
            StringBuilder number = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            for (var c : word.toCharArray()){
                if (Character.isDigit(c)){
                    number.append(c);
                    continue;
                }
                sb.append(c);
            }
            words[Integer.parseInt(number.toString()) - 1] = sb.toString();
        }
        return String.join(" ", words);
    }
    // 10
    public static boolean fibString(String str){
        Map<Character, Integer> freq = new HashMap<>();
        for (var i : str.toCharArray()){
            freq.put(i, freq.getOrDefault(i, 0) + 1);
        }
        int count = 0;
        for (var i : freq.values()){
            if (i > 1) count++;
        }
        return checkFib(count);
    }
    private static boolean checkFib(int num){
        if (num < 1) return false;
        if (num == 1) return true;
        int a = 1, b = 1;
        while (b < num){
            int tmp = b;
            b = a + b;
            a = tmp;
        }
        return b == num;
    }
}
