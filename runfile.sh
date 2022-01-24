## STEP 01: Verify argument (fileLocation) present in command
if [ $# -eq 0 ]
then
  echo "YOU NEED TO SUPPLY ARGUMENT TO RUN THIS SHELL :)"
  exit
fi


## STEP 01: Copy test file onto container
echo "!!------------------------------!!"
echo "!! STEP 1/2 - COPY TEST FILE    !!"
echo "!!------------------------------!!"
docker cp $1 league_service:/app/testFile.txt


printf "\n\n"


## STEP 02: CALL SERVICE USING GIVEN FILE
echo "!!------------------------------!!"
echo "!! STEP 2/2 - PROCESS FILE      !!"
echo "!!------------------------------!!"
curl -X "POST" "http://0.0.0.0:8084/api/v1/process-file?fileName=testFile.txt" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{}'