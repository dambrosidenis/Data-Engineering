SELECT JSON_BUILD_OBJECT('photos', JSON_AGG(nested))
FROM (
	SELECT  url, titel AS title,
            photo.personemail AS owner,
            JSON_AGG(
		        JSON_BUILD_OBJECT('firstname', person.nachname, 'lastname', person.vorname)
	        ) AS shown
	FROM photo, istabgebildet, person
	WHERE istabgebildet.photourl = photo.url AND istabgebildet.personemail = person.email
	GROUP BY photo.url
) AS nested;