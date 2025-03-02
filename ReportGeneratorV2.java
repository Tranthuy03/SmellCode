import java.util.*;

class Owner {
    private final String name;

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Location {
    private final String address;

    public Location(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}

class Property {
    private final String name;
    private final double rentAmount;
    private final Owner owner;
    private final Location location;

    public Property(String name, double rentAmount, Owner owner, Location location) {
        this.name = name;
        this.rentAmount = rentAmount;
        this.owner = owner;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public Owner getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isPremiumProperty() {
        return rentAmount > 2000;
    }

    public void printPropertyDetails() {
        System.out.println("Property: " + name);
        System.out.println("Rent Amount: $" + rentAmount);
        System.out.println("Owner: " + owner.getName());
        System.out.println("Location: " + location.getAddress());
    }
}

class FinancialCalculator {
    public static double calculateTotalRent(List<Property> properties) {
        return properties.stream().mapToDouble(Property::getRentAmount).sum();
    }

    public static double calculateYearlyRent(Property property) {
        return property.getRentAmount() * 12;
    }
}

class FinancialReport {
    private final String reportTitle;
    private final List<Property> properties;

    public FinancialReport(String reportTitle, List<Property> properties) {
        this.reportTitle = reportTitle;
        this.properties = properties;
    }

    public void generateReport() {
        System.out.println("Financial Report: " + reportTitle);
        System.out.println("----------------------------");

        for (Property property : properties) {
            property.printPropertyDetails();
            System.out.println(property.isPremiumProperty() ? "This is a premium property." : "This is a standard property.");
            System.out.println("Yearly Rent: $" + FinancialCalculator.calculateYearlyRent(property));
            System.out.println("--------------------");
        }

        double totalRent = FinancialCalculator.calculateTotalRent(properties);
        System.out.println("Total Rent Amount: $" + totalRent);
    }
}

public class ReportGeneratorV2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Property> properties = new ArrayList<>();

        System.out.print("Enter report title: ");
        String reportTitle = scanner.nextLine();

        System.out.print("Enter the number of properties: ");
        int numProperties = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numProperties; i++) {
            System.out.println("\nEntering details for property " + (i + 1) + ":");
            System.out.print("Property Name: ");
            String propertyName = scanner.nextLine();

            System.out.print("Rent Amount: ");
            double rentAmount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Owner Name: ");
            String ownerName = scanner.nextLine();
            Owner owner = new Owner(ownerName);

            System.out.print("Location Address: ");
            String locationAddress = scanner.nextLine();
            Location location = new Location(locationAddress);

            properties.add(new Property(propertyName, rentAmount, owner, location));
        }

        FinancialReport financialReport = new FinancialReport(reportTitle, properties);
        financialReport.generateReport();

        scanner.close();
    }
}
