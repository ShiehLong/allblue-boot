package com.allblue.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/5/5 10:18
 **/
public class MyStaticMethod {
//    public static void main(String[] args) {
        /*System.out.println(3*0.1);*/
        /*System.out.println(3*0.1==0.3);
        double x = 1.32344435;
        float a = 1.32344435F;
        System.out.println(x + " " + a);
        short s = 1;
        s = (short) (s + 1);
        s += 1;
        System.out.println(s);*/
//        Integer a = new Integer(3);
//        Integer b = 3;
//        int c = 3;
//        System.out.println(a == b);
//        System.out.println(a == c);
//        countCodeLine("D:\\workspace\\allblue-boot\\src\\test\\com\\allblue\\utils\\MyStaticMethod.java");
//    }

    /*
      统计代码文件中除注释和空格之外的行数
     */

    public static int countCodeLine(String path) {
        String line;
        int countAnnotation = 0;  //注释
        int countCodeLine = 0;    //代码
        int countBlankLine = 0;    //空行
        int countTotalLine = 0;   //总数
        boolean flag = false;
        try (FileReader fr = new FileReader(path)) {
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("//")) {
                    countAnnotation++;
                } else if (line.startsWith("/*") || flag) {
                    flag = !line.endsWith("*/");
                    countAnnotation++;
                } else if (line.isEmpty())
                    countBlankLine++;
                else
                    countCodeLine++;
                countTotalLine++;
            }
            System.out.println("注释行数:" + countAnnotation);
            System.out.println("代码行数:" + countCodeLine);
            System.out.println("空行行数:" + countBlankLine);
            System.out.println("总行数:" + countTotalLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countCodeLine;
    }

    /*
    冒泡排序
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /*
    买鸡问题
     */
    public static void a() {
        int cock, hen, chicken = 0;
        for (cock = 0; cock <= 19; cock++) {
            for (hen = 0; hen <= 33; hen++) {
                chicken = 100 - cock - hen;
                int p;
                p = chicken % 3;
                if (((5 * cock + 3 * hen + chicken / 3) == 100) && (p == 0)) {
                    System.out.print("    可以买公鸡的只数：" + cock);
                    System.out.print("    可以买母鸡的只数：" + hen);
                    System.out.print("    可以买小鸡的只数：" + chicken);
                    System.out.println("\n");
                }
            }
        }
    }

    /*
    斐波那契数列
     */
    public static void b() {
        int f1 = 1, f2 = 1, f;
        int M = 30;
        System.out.println(2);
        System.out.println(2);
        for (int i = 3; i < M; i++) {
            f = f2;
            f2 = f1 + f2;
            f1 = f;
            System.out.println(f2 * 2);
        }
    }

    /*
    求素数
     */
    public static void c() {
        int count = 0;
        for (int i = 101; i < 200; i += 2) {
            boolean flag = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
                System.out.println(i);
            }
        }
        System.out.println(count);
    }

    /*
    反转字符串
     */
    public static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1)
            return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    /*
    二维数组从左至右，从上至下一次递增，求二维数组内是否存在一个值
     */
    public static boolean YoungMatrix(int[][] A, int key) {
        int i = 0, j = A[0].length - 1;
        int temp = A[i][j];
        while (true) {
            if (temp == key) {
                return true;
            } else if (temp < key && i < A.length - 1) {
                temp = A[++i][j];
            } else if (temp > key && j > 0) {
                temp = A[i][--j];
            } else {
                return false;
            }
        }
    }

    /*
    将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。
     */
    public static void d(int n) {
        int k = 2;
        while (n >= k) {
            if (n == k) {
                System.out.println(k);
                break;
            } else if (n % k == 0) {
                System.out.println(k);
                n = n / k;
            } else {
                k++;
            }
        }
    }

    /*
    输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数
     */
    public static void countString(String source) {
        int countLetter = 0;
        int countSpace = 0;
        int countNum = 0;
        int countOther = 0;
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                countLetter++;
            } else if (Character.isDigit(chars[i])) {
                countNum++;
            } else if (Character.isSpaceChar(chars[i])) {
                countSpace++;
            } else {
                countOther++;
            }
        }
        System.out.println(countLetter);
        System.out.println(countSpace);
        System.out.println(countNum);
        System.out.println(countOther);
    }

    /*
    求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)
     */
    public static void e(int a, int n) {
        int sum = 0, b = 0;
        for (int i = 0; i < n; i++) {
            b += a;
            sum += b;
            a = a * 10;
        }
        System.out.println(sum);
    }

    /*
      一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，
      求它在 第10次落地时，共经过多少米？第10次反弹多高？
     */

    public static void f() {
        double h = 100;
        double s = 100;
        for (int i = 1; i <= 10; i++) {
            h = h / 2;
            s = s + 2 * h;
        }
        System.out.println(s);
        System.out.println(h);
    }

    /*
    有1、2、3、4四个数字，能组成多少个互不相同且一个数字中无重复数字的三位数？并把他们都输入
     */
    public static void g() {
        int count = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int k = 1; k < 5; k++) {
                    if (i != j && j != k && i != k) {
                        count++;
                        System.out.println(i * 100 + j * 10 + k);
                    }
                }
            }
        }
        System.out.println(count);
    }

    /*
    有一分数序列：2/1，3/2，5/3，8/5，13/8，21/13...求出这个数列的前20项之和。
     */
    public static void h() {
        double sum = 0, ver = 2, y = 1, x;
        for (int i = 1; i <= 20; i++) {
            sum += ver / y;
            System.out.println("值:" + ver + "/" + y);
            x = ver;
            ver += y;
            y = x;
        }
        System.out.println(sum);
    }

    /*
      打印出如下图案（菱形）
       *
      ***
     *****
    *******
     *****
      ***
       *
     */
    public static void i() {
        int H = 7, W = 7;//高和宽必须是相等的奇数
        for (int i = 0; i < (H + 1) / 2; i++) {
            for (int j = 0; j < W / 2 - i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k < (i + 1) * 2; k++) {
                System.out.print('*');
            }
            System.out.println();
        }
        for (int i = 1; i <= H / 2; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= W - 2 * i; k++) {
                System.out.print('*');
            }
            System.out.println();
        }
    }

    /*
    求1+2!+3!+...+20!的和
     */
    public static void j() {
        long sum = 0, ver = 1;
        for (int i = 1; i <= 20; i++) {
            ver = ver * i;
            System.out.println(ver);
            sum += ver;
        }
        System.out.println(sum);
    }

    /*
    利用递归方法求5!
     */
    public static long fac(long i) {
        if (i == 1) return 1;
        else {
            return i * fac(i - 1);
        }
    }

    /*
       一个5位数，判断它是不是回文数。即12321是回文数，个位与万位相同，十位与千位相同
        */
    public static boolean k(int num) {
        if (num < 0 || (num != 0 && num % 10 == 0))
            return false;
        int ver = 0;
        while (num > ver) {
            ver = ver * 10 + num % 10;
            num = num / 10;
            System.out.println(ver + "---" + num);
        }
        return (num == ver || num == ver / 10);
    }

    /*
    有一个已经排好序的数组。现输入一个数，要求按原来的规律将它插入数组中。
     */

    public static int[] sort(int[] a, int b) {
        int[] c = new int[a.length + 1];
        for (int i = 0; i < c.length; i++) {
            if (i == c.length - 1) {
                c[i] = b;
            } else if (a[i] < b) {
                c[i] = a[i];
            } else {
                c[i] = b;
                System.arraycopy(a, i, c, i + 1, a.length - i);
                break;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};

        int[] arr2 = sort(arr1, 7);
        for (int a : arr2)
            System.out.println(a);
    }
}