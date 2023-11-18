import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

/*
@authors Lisa Feng, Simeng Li;
*/

public class CountryDisplayer {

    private List<Country> countries;
    Country country;

    public CountryDisplayer(String filePath) {
      File inputFile = new File(filePath);
      Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
          scanner.nextLine();
          countries = new ArrayList<Country>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Country country = new Country(line.split(","));
            countries.add(country);
        }
        scanner.close();
    }

    private double getDataEntry(int index, String columnName) {
       return countries.get(index).getFeature(columnName);
    }

    private int findMaxIndex(String columnName) {

      int maxIndex = 0;
      Double max = countries.get(0).getFeature(columnName);

      for(int i = 0; i < countries.size(); i++) {
        if (countries.get(i).getFeature(columnName) > max) {
          maxIndex = i;
          max = countries.get(i).getFeature(columnName);
        }
      }
      return maxIndex;
    }

    public void sortCountryList(String columnName) {
       List<Country> sortedCountryList = new ArrayList<>();
       int maxInd;

       while (countries.size() > 0) {
         maxInd = findMaxIndex(columnName);
         sortedCountryList.add(countries.get(maxInd));
         countries.remove(countries.get(maxInd));
     }
       countries = sortedCountryList;
     }

    public void displayTextCountries() {
      for(Country country : countries) {
        System.out.println(country);
      }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
           System.out.println("Enter the contents you want.");
        }else if (args.length == 1) {
           String filePath = args[0];
           CountryDisplayer countryDisplay = new CountryDisplayer(filePath);
           countryDisplay.displayTextCountries();
        }else {
           String filePath = args[0];
           String columnName = args[1];
           CountryDisplayer countryDisplay = new CountryDisplayer(filePath);
           countryDisplay.sortCountryList(columnName);
           countryDisplay.displayTextCountries();
         }
     }
}
