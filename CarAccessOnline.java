import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carID, String brand, String model, double basePricePerDay){
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarID(){
        return carID;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }
}

class carRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public carRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }else {
            System.out.println("Car Is Not Available For Rent");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
        }else{
            System.out.println("Car Was Not Rented.");
        }
    }

    public void Menu(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("===== Car Access Online =====");
            System.out.println("1. Rent A Car");
            System.out.println("2. Return A Car");
            System.out.println("3. Exist");
            System.out.println("Enter Your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // For Consume newline

            if(choice == 1){
                System.out.println("\n== Rent A Car ==\n");
                System.out.println("Enter Your Name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarID() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.println("\nEnter The CarID You Want To Rent: ");
                String carId = scanner.nextLine();
                System.out.println("Enter The Number Of Days Rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();   // For Consume newline

                Customer newCustomer = new Customer("CUST" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarID().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.println("Total Price: ₹"+ totalPrice);
                    System.out.println("\nConfirm Rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar Rented Successfully....!");
                    }else {
                        System.out.println("\nRental Cancelled.");
                    }
                }else {
                    System.out.println("\nInvalid Car Selection or Not Available For Rent.");
                }
            }else if(choice == 2){
                System.out.println("\n== Return A Car ==\n");
                System.out.println("Enter The CarID You Want to Return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarID().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null){
                    Customer customer = null;
                    for(Rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car Returned Successfully By " + customer.getName());
                    }else {
                        System.out.println("Car Was Not Rented or Rental Information is Missing...? ");
                    }
                }else {
                    System.out.println("Invalid CarID or Car is Not Rented.");
                }
            } else if (choice == 3) {
                break;
            }else {
                System.out.println("Invalid Choice....Please Enter A Valid Option.");
            }
        }
        System.out.println("\nThank You For Visiting: ***CarAccessOnline***  ");

    }
}
public class CarAccessOnline {
    public static void main(String[] args) {
        carRentalSystem rentalSystem = new carRentalSystem();

        Car car1 = new Car("C001", "Toyota","Fortuner",1500.0);
        Car car2 = new Car("C002","Hyundai","Creata",1200.0);
        Car car3 = new Car("C003","Mahindra","Thar",1000.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.Menu();




    }

}
