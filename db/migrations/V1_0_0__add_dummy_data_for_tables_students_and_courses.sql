-- Insert data into the courses table
INSERT INTO courses
    (name, course_start)
VALUES
    ('Mathematics', '2022-09-01'),
    ('History', '2022-09-15'),
    ('Biology', '2022-10-01'),
    ('Physics', '2022-09-01'),
    ('Chemistry', '2022-09-15'),
    ('Literature', '2022-10-01');

-- Insert data into the students table
INSERT INTO students
    (first_name, last_name, date_of_birth, average_grade, course_id)
VALUES
    ('John', 'Doe', '2000-05-15', 87.5, 1),     -- John Doe enrolled in Mathematics
    ('Jane', 'Smith', '2001-08-20', 92.3, 2),    -- Jane Smith enrolled in History
    ('Michael', 'Johnson', '2002-03-10', 79.8, 3), -- Michael Johnson enrolled in Biology
    ('Emily', 'Brown', '2000-07-10', 85.2, 4),    -- Emily Brown enrolled in Physics
    ('Daniel', 'Martinez', '2001-01-25', 78.9, 5), -- Daniel Martinez enrolled in Chemistry
    ('Sophia', 'Wilson', '2002-06-05', 91.7, 6),  -- Sophia Wilson enrolled in Literature
    ('William', 'Taylor', '2000-03-18', 88.5, 1),  -- William Taylor enrolled in Mathematics
    ('Olivia', 'Garcia', '2001-09-12', 83.6, 2),   -- Olivia Garcia enrolled in History
    ('James', 'Lopez', '2002-05-30', 76.4, 3);      -- James Lopez enrolled in Biology