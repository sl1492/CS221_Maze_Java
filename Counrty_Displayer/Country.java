/**
@authors Lisa Feng, Simeng Li;
*/

public class Country {
    private String countryName;
    private double co2Emissions;
    private double totalGreenhouseGasEmissions;
    private double accessToElectricity;
    private double renewableEnergy;
    private double protectedAreas;
    private double populationGrowth;
    private double populationTotal;
    private double urbanPopulationGrowth;

    public Country(String[] countryData) {
        countryName = countryData[0];
        co2Emissions = Double.parseDouble(countryData[1]);
        totalGreenhouseGasEmissions = Double.parseDouble(countryData[2]);
        accessToElectricity = Double.parseDouble(countryData[3]);
        renewableEnergy = Double.parseDouble(countryData[4]);
        protectedAreas = Double.parseDouble(countryData[5]);
        populationGrowth = Double.parseDouble(countryData[6]);
        populationTotal = Double.parseDouble(countryData[7]);
        urbanPopulationGrowth = Double.parseDouble(countryData[8]);
    }

    // add the getter and the setter for countryName here...

    // IMPLEMENT getCountryName
    public String getCountryName(){
      return countryName;
    }
    // IMPLEMENT setCountryName
    public void setCountryName(String newCountryName){
      countryName = newCountryName;
    }
    // return the corresponding value of the given featureName. The passed in featureName
    // should start with an upper-case letter: "CO2Emissions", "TotalGreenhouseGasEmissions", etc.
    // Please use equals (not ==) to compare strings.
    public double getFeature(String featureName) {

    if(featureName.equals ("CO2Emissions")){
      return co2Emissions;
    }
    else if (featureName.equals ("TotalGreenhouseGasEmissions")) {
      return totalGreenhouseGasEmissions;
    }
    else if (featureName.equals ("AccessToElectricity")) {
      return accessToElectricity;
    }
    else if (featureName.equals ("RenewableEnergy")) {
      return renewableEnergy;
    }
    else if (featureName.equals ("ProtectedAreas")) {
      return protectedAreas;
    }
    else if (featureName.equals ("PopulationGrowth")) {
      return populationGrowth;
    }
    else if (featureName.equals ("PopulationTotal")) {
      return populationTotal;
    }
    else if (featureName.equals ("UrbanPopulationGrowth")) {
      return urbanPopulationGrowth;
    }
      return 0;
  }

    // set the corresponding value of the given featureName as newValue. The passed in featureName
    // should start with an upper-case letter: "CO2Emissions", "TotalGreenhouseGasEmissions", etc.
    // Please use equals (not ==) to compare strings
    public void setFeature(String featureName, double newValue) {

      if(featureName.equals ("CO2Emissions")){
        newValue = co2Emissions;
      }
      else if (featureName.equals ("TotalGreenhouseGasEmissions")) {
        newValue = totalGreenhouseGasEmissions;
      }
      else if (featureName.equals ("AccessToElectricity")) {
        newValue = accessToElectricity;
      }
      else if (featureName.equals ("RenewableEnergy")) {
        newValue = renewableEnergy;
      }
      else if (featureName.equals ("ProtectedAreas")) {
        newValue = protectedAreas;
      }
      else if (featureName.equals ("PopulationGrowth")) {
        newValue = populationGrowth;
      }
      else if (featureName.equals ("PopulationTotal")) {
        newValue = populationTotal;
      }
      else if (featureName.equals ("UrbanPopulationGrowth")) {
        newValue = urbanPopulationGrowth;
      }
    }

    public String toString() {
        return countryName + co2Emissions + totalGreenhouseGasEmissions +
        accessToElectricity + renewableEnergy +  protectedAreas + populationGrowth
        + populationTotal + urbanPopulationGrowth; 
    }

    // Testing public methods:
    public static void main (String[] args) {
        String[] data = {"Country1", "1", "2", "3", "4", "5", "6", "7", "8"};
        Country country1 = new Country(data);
        // Testing toString:
        System.out.println(country1);
        // Or use one line:
        Country country2 = new Country(new String[] {"Country2", "9", "8", "NaN", "6", "5", "4", "3", "2"});
        // Testing toString:
        System.out.println(country2);

        // Testing getFeature (after it's been implemented)
        System.out.println(country1.getFeature("CO2Emissions")); // should show 1
        System.out.println(country1.getFeature("PopulationTotal")); // should show 7
    }
}
