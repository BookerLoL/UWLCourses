/**
 * Write a description of class testAVL here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class testAVL
{
    public static void main(String[]args) {
        String filename = "troll.txt";
        try {
            AVLTree test = new AVLTree(filename, 0);
            test.insert(60);
            test.insert(40);
            test.insert(80);
            test.insert(10);
            test.insert(50);
            test.insert(70);
            test.insert(90);
            test.insert(100);
            test.insert(65);
            test.insert(75);
            test.insert(72);
            /*
            test.insert(100);
            test.insert(50);
            test.insert(120);
            test.insert(150);
            test.removeOne(100);
            test.removeOne(120);
            test.removeOne(50);
            test.removeOne(150);
            */
            
           /*
            test.insert(50);
            test.insert(25);
            test.insert(75);
            test.insert(10);
            test.insert(40);
            test.insert(60);
            test.insert(55);
            test.insert(65);
            test.insert(90);
            test.insert(80);
            test.insert(95);
            test.insert(5);
            test.insert(15);
            test.insert(30);
            test.insert(45); 
            
            /*
            test.removeOne(25);
            test.removeOne(40);
            test.removeOne(10);
            test.removeOne(50);
            test.removeOne(55);
           */
            /*
            test.removeOne(75);
            test.removeOne(55);
            test.removeOne(50);
            test.removeOne(25);
            test.removeOne(60);
            test.removeOne(65);
            test.removeOne(80);
            
            
            */
            //test.removeOne(45);
           
            test.print();
            test.printFile();
            //test.length();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
