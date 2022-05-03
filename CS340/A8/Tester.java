import java.io.*;
import java.util.LinkedList;
/*
 * Write a description of class tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
*/
public class Tester
{
    public static void main(String[]args) throws IOException{
        int[] contents = {10, 20};
        //int blocksize = 52; //order 4
        int blocksize = 64; //order 5
        //int blocksize = 76; //order 6
        DBTable table = new DBTable("test1", contents, blocksize);
        char[][] tempChar = new char[2][];
        tempChar[0] = new char[10];
        tempChar[1] = new char[20];
        int total = 0;
        int total2 = 0;
        for(int i = 0 ; i < tempChar.length; i++) {
            for(int j = 0; j < tempChar[i].length; j++) {
                tempChar[i][j] = 'a';
            }
        }
        /*
        for(int i = 0; i <= 50; i += 3) {
            table.insert(i, tempChar);
        }
        
        for(int i = 2; i <= 50; i += 3) {
            table.insert(i, tempChar);
        }
        
        /*
        for(int i = 0; i <= 50; i += 3) {
            table.search(i);
        }
        for(int i = 2; i <= 50; i += 3) {
            table.search(i);
        }
        LinkedList<LinkedList<String>> list = table.rangeSearch(45, 50);
        System.out.println(list.size());
        /*
        table.insert(100, new char[10][20]);
        table.insert(50, new char[10][20]);
        table.insert(150, new char[10][20]);
        table.insert(200, new char[10][20]);
        table.insert(25, new char[10][20]);
        table.insert(75, new char[10][20]);
        table.insert(30, new char[10][20]);
        table.insert(80, new char[10][20]);
        table.insert(85, new char[10][20]);
        table.insert(81, new char[10][20]);
        table.insert(82, new char[10][20]);
        
        table.insert(20, new char[10][20]);
        table.insert(15, new char[10][20]);
        table.insert(10, new char[10][20]);
        
        table.insert(35, new char[10][20]);
        table.insert(40, new char[10][20])；
        table.insert(45, new char[10][20]);
        table.insert(44, new char[10][20]);
        table.insert(1, new char[10][20]);
        table.insert(2, new char[10][20]);
        table.insert(3, new char[10][20]);
        table.insert(4, new char[10][20]);
        table.insert(5, new char[10][20]);
        table.insert(16, new char[10][20]);
        table.insert(17, new char[10][20]);
        table.insert(21, new char[10][20]);
        table.insert(20, new char[10][20]);
        table.insert(40, new char[10][20]);
        table.insert(25, new char[10][20]);
        table.insert(15, new char[10][20]);
        table.insert(5, new char[10][20]);
        table.insert(4, new char[10][20]);
        table.search(200);
        table.search(100);
        table.search(75);
        table.search(50);
        table.search(44);
        table.search(30);
        table.search(21);
        table.search(16);
        table.search(10);
        table.search(15);
        table.search(5);
        table.search(2);
        
        table.search(-3);
        table.search(26);
        table.search(46);
        table.search(166);
        //table.print();
        //System.out.println("AFTER");
        //table.insert(3, new char[10][20]);
        
        /*
        table.insert(20, new char[10][20]);
        table.insert(40, new char[10][20]);
        
        table.insert(30, new char[10][20]);
        table.insert(10, new char[10][20]);
        table.insert(50, new char[10][20]);
        table.insert(60, new char[10][20]);
        table.insert(70, new char[10][20]);
        table.insert(80, new char[10][20]);
        table.insert(90, new char[10][20]);
        table.insert(100, new char[10][20]);
        table.insert(110, new char[10][20]);
        table.insert(60, new char[10][20]);
        table.insert(60, new char[10][20]);
        table.insert(60, new char[10][20]);
        table.insert(60, new char[10][20]);
        */
        /*
        table.insert(120, new char[10][20]);
        table.insert(130, new char[10][20]);
        table.insert(140, new char[10][20]);
        table.insert(150, new char[10][20]);
        table.insert(160, new char[10][20]);
        table.insert(170, new char[10][20]);
        table.insert(180, new char[10][20]);
        */
       /*
        table.search(40);
        table.search(20);
        table.search(30);
        table.search(10);
        table.search(50);
        table.search(60);
        table.search(-10);
        table.search(100);
        table.search(45);
        */
        /*
        table.insert(50, new char[10][20]);
        table.insert(30, new char[10][20]);
        table.insert(10, new char[10][20]);
        table.insert(5, new char[10][20]);
        table.insert(25, new char[10][20]);
        table.insert(3, new char[10][20]);
        table.insert(22, new char[10][20]);
        table.insert(11, new char[10][20]);
        table.insert(55, new char[10][20]);
        table.insert(60, new char[10][20]);
        table.insert(26, new char[10][20]);
        table.insert(12, new char[10][20]);
        table.insert(13, new char[10][20]);
        table.insert(6, new char[10][20]);
        table.insert(7, new char[10][20]);
        table.insert(35, new char[10][20]);
        table.insert(36, new char[10][20]);
        table.insert(51, new char[10][20]);
        */
        for(int i = 36 ; i >= 2; i -= 2) {
            table.insert(i, tempChar);
        }
        //System.out.println(table.remove(6));
        //System.out.println(table.remove(14));
        table.print();
        table.close();
            
        
        
        /*
            System.out.println("--------------------");
            System.out.println("Round 1 of tests");
            System.out.println("--------------------");
            
            DBTable table = new DBTable("test1", contents, blocksize);
            table.insert(20, new char[10][20]);
            
            table.search(10);
            table.search(5);
            table.search(15);
            table.search(20);
            table.search(0);
            table.search(30);
            table.print();
            
            table.close();
            
            System.out.println("--------------------");
            System.out.println("Round 2 of tests");
            System.out.println("--------------------");
            
            DBTable table2 = new DBTable("test1");
            table2.insert(15, new char[10][20]);
            table2.insert(10, new char[10][20]);
            
            table2.search(10);
            table2.search(5);
            table2.search(15);
            table2.search(20);
            table2.search(0);
            table2.search(30);
            table2.print();
            
            table2.close();
            
            System.out.println("--------------------");
            System.out.println("Round 3 of tests");
            System.out.println("--------------------");
            
            DBTable table3 = new DBTable("test1");
            table3.insert(5, new char[10][20]);
            
            table3.search(10);
            table3.search(5);
            table3.search(15);
            table3.search(20);
            table3.search(0);
            table3.search(30);
            table3.print();
            
            table3.close();
            
            
            System.out.println("--------------------");
            System.out.println("Round 4 of tests");
            System.out.println("--------------------");
            
            DBTable table4 = new DBTable("test1");
            table4.insert(25, new char[10][20]);
            table4.insert(35, new char[10][20]);
            table4.insert(30, new char[10][20]);
            table4.insert(13, new char[10][20]);
            table4.insert(12, new char[10][20]);
            table4.insert(14, new char[10][20]);
            table4.insert(4, new char[10][20]);
            table4.search(4);
            table4.search(14);
            table4.search(12);
            table4.search(13);
            table4.search(30);
            table4.search(35);
            table4.search(25);
            table4.search(5);
            table4.search(10);
            table4.search(15);
            table4.search(20)；
            table4.print();
            table4.close();
            */
    }
}
