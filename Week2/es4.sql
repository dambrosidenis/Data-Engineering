SELECT name, "numEmpl", MAX("numEmpl") OVER (PARTITION BY "parentId") AS "maxNumEmpl"
FROM department;