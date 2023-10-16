WITH RECURSIVE dep AS (
		SELECT *
		FROM department
		WHERE "parentId" = 4 OR "deptId" = 4
	UNION
		SELECT department.*
		FROM department, dep
		WHERE department."parentId" = dep."deptId"
)
SELECT *
FROM dep;

SELECT "deptId", name, MAX("totalEmpl")
FROM (
    WITH RECURSIVE dep AS (
    		SELECT *
    		FROM department
    	UNION ALL
    		SELECT department."deptId", department.name, dep."parentId", department."numEmpl"
    		FROM department
    		INNER JOIN dep ON department."parentId" = dep."deptId"
    		WHERE department."parentId" <> department."deptId"
    )
    SELECT	department."deptId",
    		department.name,
    		d."totalEmpl" + department."numEmpl" AS "totalEmpl"
    FROM department
    INNER JOIN (
    	SELECT "parentId", SUM("numEmpl") AS "totalEmpl"
    	FROM dep
    	GROUP BY "parentId"
    ) AS d
    ON department."deptId" = d."parentId"
    UNION
    SELECT "deptId", name, "numEmpl"
    FROM department
)
GROUP BY "deptId", name;
