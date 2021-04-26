#Description
This spring boot application reads the purchase records from a CSV file in the following syntax.
<date>,<Name>,<Amount>

It only processes records for last 3 months.
Finally, it prints the reward points earned for each customer per month and total as the output.


#Generate jar file
````
mvn package
````


#Run unit tests
````
mvn test
````

#Test with a CSV file:
````
mvn spring-boot:run -Dspring-boot.run.arguments="data.csv"
````
