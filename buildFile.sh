echo "!!-------------------------------!!"
echo "!!--------- !!WELCOME!! ---------!!"
echo "!!-------------------------------!!"


printf "\n\n"


## Step 1: Run DB & Migrations
echo "!!-------------------------------!!"
echo "!! Step 1/3 - DB & Migrations    !!"
echo "!!-------------------------------!!"


docker-compose up -d league_db


## We sleep a couple of seconds to give the container time to start up
sleep 5


printf "\n\n"


## Step 2: Setup application environment
echo "!!-------------------------------!!"
echo "!! Build Step 2/3 - Build App    !!"
echo "!! This will take +- 6min :(     !!"
echo "!!-------------------------------!!"
docker-compose up -d --build


printf "\n\n"


# Step 3: Setup application environment
echo "!!-------------------------------!!"
echo "!! Build Step 3/3 - Tailing logs !!"
echo "!!-------------------------------!!"
docker logs league_service -f -t
