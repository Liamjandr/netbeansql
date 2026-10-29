CREATE DATABASE IF NOT EXISTS dmsamplederoxasdollentes;
USE dmsamplederoxasdollentes;

CREATE TABLE IF NOT EXISTS tbl_student (
    student_Id     VARCHAR(9)   NOT NULL PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    course         VARCHAR(100) NOT NULL,
    year_level     TINYINT      NOT NULL,
    contact_number VARCHAR(15)  NOT NULL,
    gmail          VARCHAR(150) NOT NULL
);

CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON dmsamplederoxasdollentes.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

INSERT INTO tbl_student (student_Id, name, course, year_level, contact_number, gmail) VALUES
('0324-0000', 'Juan Dela Cruz', 'BSCS', 2, '09171234567', 'juandelacruz@gmail.com'),
('0324-0001', 'Maria Santos',   'BSIT', 3, '09181234568', 'mariasantos@gmail.com'),
('0324-0002', 'Jose Rizal',     'BSCS', 1, '09191234569', 'joserizal@gmail.com'),
('0324-0003', 'Ana Reyes',      'BSIS', 4, '09201234570', 'anareyes@gmail.com'),
('0324-0004', 'Pedro Garcia',   'BSIT', 2, '09211234571', 'pedrogarcia@gmail.com');
