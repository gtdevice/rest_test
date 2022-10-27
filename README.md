# DataGuard Coding Challenge
##General description

Features that were implemented in the project:
* Possibility create new instances of the entity.
* Possibility to retrieve instances of the entity.
* Possibility to retrieve instance of the entity by alias.

It is better to run project with simple run via IDE. Or build and run jar file.
./gradlew build
java -jar .\build\libs\Dguard-0.0.1-SNAPSHOT.jar

*Important note:
Unfortunately I am not able to have docker on my laptop due to restricted policies. But anyway 
I'm adding in the repo Dockerfile. I hope that it will work, if not - sorry :)


Endpoints:
* GET/ http://localhost:8080/v1/superhero/
* GET/ http://localhost:8080/v1/superhero/{alias}
* POST/ http://localhost:8080/v1/superhero/



Looking forward to meeting or some feedback!
Best Regards
Oleg