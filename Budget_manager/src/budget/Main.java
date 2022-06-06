package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main{
    private static Scanner scanner;
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    private double balance = 0.0;
    public static void options(int o){
        switch (o){
            case 1:
                System.out.println("Choose your action:\n1) Add income\n2) Add purchase\n3) Show list of purchases\n4) Balance\n5) Save\n6) Load\n7) Analyze (Sort)\n0) Exit");
                break;
            case 2:
                System.out.println("Choose type of purchase:\n1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) Back");
                break;
            case 3:
                System.out.println("Choose type of purchases:\n1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) All\n6) Back");
                break;
            case 4:
                System.out.println("How do you want to sort?\n1) Sort all purchases\n2) Sort by type\n3) Sort certain type\n4) Back");
                break;
            case 5:
                System.out.println("Choose type of purchase\n1) Food\n2) Clothes\n3) Entertainment\n4) Other");
                break;
        }
    }
    public void addIncome(){
        System.out.println("Enter income:");
        double addition = Double.parseDouble(scanner.next());
        setBalance(getBalance()+addition);
        System.out.println("Income was added!");
    }
    public static void addPurchase(LinkedHashMap<String,Double> all, LinkedHashMap<String,Double> certain, String purchase, Double price){
        all.put(purchase,price);
        certain.put(purchase,price);

    }
    public static void show(LinkedHashMap<String,Double> list){
        double sum = 0.00;
        for(Map.Entry<String,Double> entry:list.entrySet()){
            System.out.printf("%s $%.2f\n",entry.getKey(),entry.getValue());
            sum+=entry.getValue();
        }
        System.out.printf("Total sum: $%.2f\n",sum);
    }
    public void save(LinkedHashMap<String,Double> one,LinkedHashMap<String,Double> two,LinkedHashMap<String,Double> three,LinkedHashMap<String,Double> four){
        File file = new File("purchases.txt");
        try(FileWriter writer = new FileWriter(file)){
            String str;
            str=String.valueOf(getBalance())+"\n";
            writer.write(str);
            if(!one.isEmpty()){
                for (Map.Entry<String, Double> entry : one.entrySet()) {
                    str = "food;" + entry.getKey() + ";" + entry.getValue() + "\n";
                    writer.write(str);
                }
            }
            if(!two.isEmpty()){
                for (Map.Entry<String, Double> entry : two.entrySet()) {
                    str = "clothes;" + entry.getKey() + ";" + entry.getValue() + "\n";
                    writer.write(str);
                }
            }
            if(!three.isEmpty()){
                for (Map.Entry<String, Double> entry : three.entrySet()) {
                    str = "ent;" + entry.getKey() + ";" + entry.getValue() + "\n";
                    writer.write(str);
                }
            }
            if(!four.isEmpty()){
                for (Map.Entry<String, Double> entry : four.entrySet()) {
                    str = "other;" + entry.getKey() + ";" + entry.getValue() + "\n";
                    writer.write(str);
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void load(File file,LinkedHashMap<String,Double> one,LinkedHashMap<String,Double> two,LinkedHashMap<String,Double> three,LinkedHashMap<String,Double> four,LinkedHashMap<String,Double> all){
        try(Scanner sc = new Scanner(file)){
            double bal = Double.parseDouble(sc.next());
            setBalance(bal);
            while(sc.hasNext()) {
                String temp = sc.nextLine();
                String[] data = temp.split(";");
                if (data[0].equals("food")) {
                    bal = Double.parseDouble(data[2]);
                    one.put(data[1], bal);
                    all.put(data[1], bal);
                } else if (data[0].equals("clothes")) {
                    bal = Double.parseDouble(data[2]);
                    two.put(data[1], bal);
                    all.put(data[1], bal);
                }else if (data[0].equals("ent")) {
                    bal = Double.parseDouble(data[2]);
                    three.put(data[1], bal);
                    all.put(data[1], bal);
                }else if (data[0].equals("other")) {
                    bal = Double.parseDouble(data[2]);
                    four.put(data[1], bal);
                    all.put(data[1], bal);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    public static void sortList(LinkedHashMap<String,Double> list){
        HashSet<String> set = new HashSet<>();
        if(!list.isEmpty()){
            double sum = 0.00;
            int i =0;
            Double[] arr = new Double[list.size()];
            for (Map.Entry<String, Double> entry : list.entrySet()) {
                arr[i]=entry.getValue();
                i++;
            }
            Arrays.sort(arr,Collections.reverseOrder());
            String out;
            for(int j = 0;j<arr.length;j++){
                for(Map.Entry<String,Double> ent : list.entrySet()){
                    out = String.format("%s $%.2f\n",ent.getKey(),ent.getValue());
                    if(arr[j].equals(ent.getValue()) && !set.contains(out)){
                        set.add(out);
                        System.out.printf(out);
                        sum+=ent.getValue();
                    }
                }
            }
            System.out.printf("Total: $%.2f\n",sum);
        }else{
            System.out.println("The purchase list is empty!");
        }
    }
    public static void typeSort(LinkedHashMap<String,Double> one,LinkedHashMap<String,Double> two,LinkedHashMap<String,Double> three,LinkedHashMap<String,Double> four){
        Map<String, Double> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        double food=0.00, ent=0.0, clothes=0.0, other=0.00, sum=0.0;
        if(!one.isEmpty()){
            for (Map.Entry<String, Double> entry : one.entrySet()) {
                food += entry.getValue();
            }
        }
        if(!two.isEmpty()){
            for (Map.Entry<String, Double> entry : two.entrySet()) {
                clothes += entry.getValue();
            }
        }
        if(!three.isEmpty()){
            for (Map.Entry<String, Double> entry : three.entrySet()) {
                ent += entry.getValue();
            }
        }
        if(!four.isEmpty()){
            for (Map.Entry<String, Double> entry : four.entrySet()) {
                other += entry.getValue();
            }
        }
        sum = food + ent + clothes + other;
        Double[] arr = new Double[]{food,ent,clothes,other};
        Arrays.sort(arr, Collections.reverseOrder());
        map.put("Food", food);
        map.put("Entertainment", ent);
        map.put("Clothes", clothes);
        map.put("Other", other);

        System.out.println("Types:");
        if(sum!=0.0){
            for (double d : arr) {
                for (Map.Entry<String, Double> entry : map.entrySet()) {
                    if (d == entry.getValue() && !set.contains(entry.getKey())) {
                        System.out.printf("%s - $%.2f\n", entry.getKey(), entry.getValue());
                        set.add(entry.getKey());
                    }
                }
            }
        }
        System.out.printf("Total sum: $%.2f\n",sum);
    }
    public static void exit() {
        System.out.println("Bye!");
    }
    public static void main(String[] args){
        Main budget = new Main();
        LinkedHashMap<String,Double> all = new LinkedHashMap<>();
        LinkedHashMap<String,Double> food = new LinkedHashMap<>();
        LinkedHashMap<String,Double> clothes = new LinkedHashMap<>();
        LinkedHashMap<String,Double> ent = new LinkedHashMap<>();
        LinkedHashMap<String,Double> other = new LinkedHashMap<>();
        boolean exit = false;
        scanner = new Scanner(System.in);
        while(!exit){
            options(1);
            int o = scanner.nextInt();
            System.out.println();
            switch (o){
                case 1:
                    budget.addIncome();
                    System.out.println();
                    break;
                case 2:
                    String name;
                    double price;
                    boolean ex1 = false;
                    while(!ex1){
                        options(2);
                        int type = scanner.nextInt();
                        System.out.println();
                        switch (type) {
                            case 1:
                                System.out.println("Enter purchase name:");
                                scanner.nextLine();
                                name = scanner.nextLine();
                                System.out.println("Enter its price:");
                                price = Double.parseDouble(scanner.next());
                                addPurchase(all, food, name, price);
                                System.out.println("Purchase was added!");
                                System.out.println();
                                break;
                            case 2:
                                System.out.println("Enter purchase name:");
                                scanner.nextLine();
                                name = scanner.nextLine();
                                System.out.println("Enter its price:");
                                price = Double.parseDouble(scanner.next());
                                addPurchase(all, clothes, name, price);
                                System.out.println("Purchase was added!");
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Enter purchase name:");
                                scanner.nextLine();
                                name = scanner.nextLine();
                                System.out.println("Enter its price:");
                                price = Double.parseDouble(scanner.next());
                                addPurchase(all, ent, name, price);
                                System.out.println("Purchase was added!");
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("Enter purchase name:");
                                scanner.nextLine();
                                name = scanner.nextLine();
                                System.out.println("Enter its price:");
                                price = Double.parseDouble(scanner.next());
                                addPurchase(all, other, name, price);
                                System.out.println("Purchase was added!");
                                System.out.println();
                                break;
                            case 5:
                                ex1 = true;
                                break;
                        }

                    }
                    break;
                case 3:
                    boolean ex = false;
                    while (!ex){
                        options(3);
                        int type = scanner.nextInt();
                        System.out.println();
                        switch (type){
                            case 1:
                                if(!food.isEmpty()){System.out.println("Food:");}
                                show(food);
                                System.out.println();
                                break;
                            case 2:
                                if(!clothes.isEmpty()){System.out.println("Clothes:");}
                                show(clothes);
                                System.out.println();
                                break;
                            case 3:
                                if(!ent.isEmpty()){System.out.println("Entertainment:");}
                                show(ent);
                                System.out.println();
                                break;
                            case 4:
                                if(!other.isEmpty()){System.out.println("Other:");}
                                show(other);
                                System.out.println();
                                break;
                            case 5:
                                if(!all.isEmpty()){
                                    System.out.println("All:");
                                }
                                show(all);
                                System.out.println();
                                break;
                            case 6:
                                ex = true;
                                break;
                        }
                    }
                    break;
                case 4:
                    double spent=0.00;
                    for(Map.Entry<String,Double> entry:all.entrySet()){
                        spent+=entry.getValue();
                    }
                    System.out.println("Balance: $"+(budget.getBalance()-spent));
                    System.out.println();
                    break;
                case 5:
                    budget.save(food,clothes,ent,other);
                    System.out.println("Purchases were saved!");
                    System.out.println();
                    break;
                case 6:
                    File file = new File("purchases.txt");
                    budget.load(file,food,clothes,ent,other,all);
                    System.out.println("Purchases were loaded!");
                    System.out.println();
                    break;
                case 7:
                    boolean ex2=false;
                    while(!ex2){
                        options(4);
                        int act = scanner.nextInt();
                        System.out.println();
                        switch (act){
                            case 1:
                                if(!all.isEmpty()){System.out.println("All:");}
                                sortList(all);
                                System.out.println();
                                break;
                            case 2:
                                typeSort(food,clothes,ent,other);
                                System.out.println();
                                break;
                            case 3:
                                options(5);
                                int type = scanner.nextInt();
                                System.out.println();
                                switch (type){
                                    case 1:
                                        sortList(food);
                                        System.out.println();
                                        break;
                                    case 2:
                                        sortList(clothes);
                                        System.out.println();
                                        break;
                                    case 3:
                                        sortList(ent);
                                        System.out.println();
                                        break;
                                    case 4:
                                        sortList(other);
                                        System.out.println();
                                        break;
                                }
                                break;
                            case 4:
                                ex2=true;
                                break;
                        }

                    }
                    break;
                case 0:
                    exit();
                    exit=true;
                    break;
            }
        }
    }
}

