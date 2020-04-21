package homework;

public class Main {

    public static void main(String[] args) {
        //Manager menago = new Manager(Dane.getImie(), Dane.getNazwisko(), Dane.getManagerCommision(), Dane.getManagerCommision(),10);
        Manager menago = Manager.generateManager(10);
        System.out.println(menago.printAll());

    }

}

abstract class Employee {
    private String name;
    private String surname;

    public float calculateSalary() { return 1500f; };

    public Employee(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName(){
        return this.name;
    }


    public String getSurname(){
        return this.surname;
    }


    public String toString() {
        return this.getClass().getSimpleName()+": "+getName()+" "+getSurname()+". Wynagrodzenie: "+calculateSalary()+"PLN";
    }

}

class Serviceman extends Employee {
    private int hoursDone;
    private float hourlyWage;

    @Override
    public float calculateSalary() {
        return (float)Math.ceil(this.hoursDone * this.hourlyWage);
    }

    public Serviceman(String name, String surname, int hoursDone, float hourlyWage) {
        super(name, surname);
        this.hoursDone = hoursDone;
        this.hourlyWage = hourlyWage;
    }

    public int getHoursDone() {
        return hoursDone;
    }



    public static Serviceman generateServiceman() {
        return new Serviceman(Dane.getImie(),Dane.getNazwisko(),Dane.getHoursDone(),Dane.getHourlyWage());
    }

}

class Dealer extends Employee {
    private int salesDone;
    private float commision;

    @Override
    public float calculateSalary() {
        return (float)Math.ceil(this.salesDone * commision);
    }

    public Dealer(String name, String surname, int salesDone, float commision) {
        super(name, surname);
        this.salesDone = salesDone;
        this.commision = commision;
    }

    public int getSalesDone() {
        return salesDone;
    }


    public static Dealer generateDealer() {
        return new Dealer(Dane.getImie(),Dane.getNazwisko(),Dane.getSalesDone(),Dane.getCommision());
    }
}

class Manager extends Employee {
    Employee[] staff;
    int dealersCommision;
    int servicemenCommision;

    public Manager(String name, String surname, int dealersCommision, int servicemenCommision, int howManyEmployees) {
        super(name, surname);
        this.staff = generateListOfEmployees(howManyEmployees);
        this.dealersCommision = dealersCommision;
        this.servicemenCommision = servicemenCommision;

    }

    public String printAll() {
        String print = "";
        for (Employee a: staff) {
                if(a != null){
                    print += (a.toString()+"\n");
            }

        }
        return this.toString()+"\n"+print;
    }

    @Override
    public float calculateSalary() {
        int dealerDeals = 0;
        int servicemenHours = 0;

        for(int i = 0; i < staff.length; ++i){

            if(staff[i] != null) {
                dealerDeals += (staff[i] instanceof Dealer) ?  ((Dealer)staff[i]).getSalesDone() : 0;
                servicemenHours += (staff[i] instanceof  Serviceman) ? ((Serviceman)staff[i]).getHoursDone() : 0;
                /*if (staff[i] instanceof  Dealer) {
                    dealerDeals += ((Dealer)staff[i]).getSalesDone();
                } else {
                    servicemenHours += ((Serviceman)staff[i]).getHoursDone();
                }*/
            }


        }

        return (this.dealersCommision * dealerDeals)+(servicemenCommision * servicemenHours);
    }

    public static Employee[] generateListOfEmployees(int howMany) {
        Employee[] toReturn = new Employee[10];


        for(int i = 0; i < howMany && i < toReturn.length; ++i){
                int type = (int)(Math.random()*2);
                toReturn[i] = type == 0 ? Serviceman.generateServiceman() : Dealer.generateDealer();
                /*if (type == 0 ) {
                    toReturn[i] = Serviceman.generateServiceman();
                } else {
                    toReturn[i] = Dealer.generateDealer();
                }*/
        }

        return toReturn;

    }

    public static Manager generateManager(int howMany){
        return new Manager(Dane.getImie(), Dane.getNazwisko(), Dane.getManagerCommision(), Dane.getManagerCommision(), howMany);
    }
}

final class Dane {
    private static final String[] imie = {"Daniel", "Wiktor", "Maciej", "Wojtek", "Czarek", "Bartek", "Julia", "Wiktoria", "Asia", "Tytus", "Domino", "Baltazar", "Jan"};
    private static final String[] nazwisko = {"Popek","Kowalski", "Baggins", "Szwarny", "Krasny", "Pawlak", "Krzynówek", "Skisło", "Borubar", "Bomba", "Torpeda", "Głuś"};

    public static String getImie() {
        return imie[(int)(Math.random() * imie.length)];
    }

    public static String getNazwisko() {
        return nazwisko[(int)(Math.random() * nazwisko.length)];
    }

    public static int getSalesDone() {
        return (int)(5+(Math.random()*30));
    }

    public static int getCommision() {
        return (int)(Math.random()*800);
    }

    public static int getHoursDone() {
        return (int)(80+(Math.random()*240));
    }

    public static float getHourlyWage() {
        return (float)(12.76f+(Math.random()*30f));
    }

    public static int getManagerCommision() {
        return (int)(20+(Math.random()*120));
    }


}