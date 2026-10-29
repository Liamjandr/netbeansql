USE dmsamplederoxasdollentes;

-- Clear old rows and ensure schema matches
TRUNCATE TABLE tbl_student;

ALTER TABLE tbl_student
    MODIFY student_Id VARCHAR(9) NOT NULL,
    ADD COLUMN IF NOT EXISTS contact_number VARCHAR(15)  NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS gmail          VARCHAR(150) NOT NULL DEFAULT '';

-- Generate 1000 records: 0324-0000 to 0324-0999
-- Gmail = lowercase firstname + lastname (no spaces) + @gmail.com
-- Contact = 09XX-XXX-XXXX style random PH mobile numbers
INSERT INTO tbl_student (student_Id, name, course, year_level, contact_number, gmail)
SELECT
    CONCAT('0324-', LPAD(seq, 4, '0')),
    CONCAT(
        ELT(1 + (seq % 20),
            'Juan','Maria','Jose','Ana','Pedro','Luis','Carla','Miguel','Sofia','Rafael',
            'Isabel','Carlo','Angela','Mark','Patricia','John','Bianca','Paolo','Katrina','Daniel'),
        ' ',
        ELT(1 + (FLOOR(seq / 20) % 20),
            'Dela Cruz','Santos','Reyes','Garcia','Ramos','Mendoza','Torres','Flores','Gonzales','Bautista',
            'Villanueva','Aquino','Castillo','Navarro','Salazar','Domingo','Fernandez','Lopez','Rivera','Cruz')
    ),
    ELT(1 + FLOOR(RAND() * 6), 'BSCS','BSIT','BSIS','BSECE','BSBA','BSED'),
    1 + FLOOR(RAND() * 4),
    CONCAT('09', LPAD(FLOOR(RAND() * 1000000000), 9, '0')),
    CONCAT(
        LOWER(
            REPLACE(
                CONCAT(
                    ELT(1 + (seq % 20),
                        'Juan','Maria','Jose','Ana','Pedro','Luis','Carla','Miguel','Sofia','Rafael',
                        'Isabel','Carlo','Angela','Mark','Patricia','John','Bianca','Paolo','Katrina','Daniel'),
                    ELT(1 + (FLOOR(seq / 20) % 20),
                        'DelaCruz','Santos','Reyes','Garcia','Ramos','Mendoza','Torres','Flores','Gonzales','Bautista',
                        'Villanueva','Aquino','Castillo','Navarro','Salazar','Domingo','Fernandez','Lopez','Rivera','Cruz')
                ),
                ' ', ''
            )
        ),
        '@gmail.com'
    )
FROM seq_0_to_999;
