ServerApp is the main class for the application which starts the HTTP server and listening on port 8081

Curl Requests:
1) Login: curl --location --request GET 'http://localhost:8081/1325/login'
2) Adding score: curl --location --request POST 'http://localhost:8081/1/score/sessionkey=37be98c6-6978-4fee-b49a-c31f7c2899b0' \
--header 'Content-Type: text/plain' \
--data-raw '7'
3) Get High score List: curl --location --request GET 'http://localhost:8081/2/highscorelist'



Creating thread pool based on this formula:
Number of threads = Number of Available Cores * (1 + Wait time / Service time)

Since we are not using any persistence like DB, the wait time will include IO =.

CachedThreadPool is not used since out application is IO intensive and creation of unlimited threads could lead to out of memory error.

