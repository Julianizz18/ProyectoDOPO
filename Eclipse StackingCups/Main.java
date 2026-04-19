import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tower t = new Tower(10, 20);
        t.makeVisible();

        while (true) {
            System.out.println("\n1. pushCup  2. pushLid  3. popCup  4. popLid  5. salir");
            int op = sc.nextInt();
            if (op == 1) {
                System.out.print("ID y tipo (normal/opener/hierarchical/cleaner): ");
                int id = sc.nextInt();
                String tipo = sc.next();
                t.pushCup(id, tipo);
            } else if (op == 2) {
                System.out.print("ID y tipo (normal/fearful/crazy): ");
                int id = sc.nextInt();
                String tipo = sc.next();
                t.pushLid(id, tipo);
            } else if (op == 3) {
                t.popCup();
            } else if (op == 4) {
                t.popLid();
            } else break;
        }
    }
}