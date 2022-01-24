# OVERVIEW
    Running the application is divided into 2 parts. 
    - Part 01: Building the application
    - Part 02: Running the application

### SECTION 01: BUILD
    Building the application is done using Docker. This ensures that all packages & dependencies 
    are installed when running the application. 

    The application can be build by running the following command in a terminal in the main directory 
    of the application 
    
    NOTE: Docker will have to install all the dependencies so this make take some time (+- 6min)
    
    NOTE: Once completed - The build terminal will display the results of the application,
          hence it should not be closed once the build is completed

    COMMAND:
        bash buildFile.sh


### SECTION 02: RUN
    Once the application has sucsesfully been built you can open a new terminal and run the application.
    
    NOTE: The solution makes use of a database that is cleared after each iteration. 

    COMMAND
        bash runFile.sh {path_to_file}

    EXAMPLE:
        bash runFile.sh /Users/eduard/Desktop/test_file.txt
