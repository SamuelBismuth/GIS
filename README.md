# Assignment-1
A project coding in java.
Project assignment 0. 
Contains the java files, the folders you need to run the project, a repport with more explanation about the project, and the documentation.
!!!! To run the project you need to change the destination of the folders in the main class. !!!!

# Authors
Samuel Bismuth, Orel Shalom.

# Explanation of the system and description of the software components
The program allows reading a folder of files and convert them into an organized csv file containing the geographical information of the phone's signal strength, browsing data, location, speed, mapping and more. In addition, the software takes this file and enables filtering of information by location, time, or list of users and displaying the information on a map, so that each sample is displayed as a point, and each router according to its MAC address will be displayed according to its strongest position.

The software does this by building a KML file (using an API) based on filtering the data and opening it with Google Earth. The file contains the time of each measurement, allowing Google Earth to display measurements on a time line.

Also, The user is active in the system. he gives the name of the folder he chooses (found in the workspace), and then chooses a name for the file he wants to create. In addition, he should choose how to filter the KML file by entering the borders of its choice and naming this file.

The software consists of three interfaces, which are its main functions: reading a file, writing a file, and filtering a file.
The software classes have classes of JUnit that test that there are no exceptions and that the code runs correctly
