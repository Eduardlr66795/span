
if [ $# -eq 0 ]
then
  echo "YOU NEED TO SUPPLY ARGUMENT TO RUN THIS SHELL :)"
else
  # Running the database - Since we make use of a database we need to spin up the container before we can start the application
  echo "Step 1/3 - Starting up the database required for the application to run"
  docker-compose up -d league_db

  # We sleep a couple of seconds to give the container time to start up
  sleep 5

  # Running the application - Now that the database has started up we can spin up the application
  echo "Step 2/3 - Running the application"
  echo "NOTE: This step is creating the container that we will use to test - This may take some time average (6min)"
  docker-compose up --build

  # API - Copy the given file over to the docker container the

  docker cp $1 league_service:/app/testFile.txt

  # Call the process file endpoint to run the file
  curl -X "POST" "http://0.0.0.0:8084/api/v1/process-file?fileName=testFile.txt" \
       -H 'Content-Type: application/json; charset=utf-8' \
       -d $'{}'

fi




