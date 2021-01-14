# Mutual_Fund_Returns
Spring boot application to calucalate the returns for a Mutaul Fund based on Schema, investment period and Horizon

HOW TO RUN THIS SPRING BOOT APPLICATION TO GET THE MF RETURNS
-------------------------------------------------------------

1. YOU CAN EXECUTE THE PROJECT BY EXPORTING IT INTO YOU DESIRED IDE AND RUNNING IT.
2. ALTERNATE WAY: 
	a. EXECUTE THE ./target/demo-0.0.1-SNAPSHOT.jar
	b. java -jar demo-0.0.1-SNAPSHOT.jar

3. OPEN YOUR DESIRED BROWSER AND ENTER THE URL IN FOLLOWING FORMAT:

	http://localhost:8080/app?scheme=<SCHEME_NUMBER>&period=<PERIOD>&horizon=<HORIZON>
	
	e.g.
	http://localhost:8080/app?scheme=102885&period=1&horizon=1
