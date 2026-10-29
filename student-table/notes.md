sudo systemctl start mariadb
sudo mariadb
mvn clean package
java -jar target/student-table-1.0.jar


# check data in the terminal
sudo mariadb -e "SELECT * FROM dmsamplederoxasdollentes.tbl_student;"
