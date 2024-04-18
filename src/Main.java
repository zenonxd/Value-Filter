import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the file path: ");
        String path = sc.next();

        List<Product> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line!=null) {
                String[] fields = line.split(",");
                String name = fields[0];
                Double price = Double.parseDouble(fields[1]);
                list.add(new Product(name, price));
                line = br.readLine();
            }

            double avg = list.stream()
                    .map(Product::getPrice)
                    .reduce(0.0, Double::sum) / list.size();
            System.out.println("Average price: " + avg);

            List<Product> names = list.stream()
                    .sorted(Comparator.comparing(Product::getName).reversed())
                    .filter(p -> p.getPrice() < avg)
                    .collect(Collectors.toList());
            names.forEach(System.out::println);
        }

        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}