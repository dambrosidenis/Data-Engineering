CREATE TABLE myFiles (
	key varchar(255) PRIMARY KEY,
	content jsonb
);

INSERT INTO myFiles (key, content)
SELECT 'photos' AS key, JSONB_BUILD_OBJECT('photos', JSON_AGG(photos)) AS content
FROM (
	SELECT  url, titel AS title,
            photo.personemail AS owner,
            JSON_AGG(
		        JSON_BUILD_OBJECT('firstname', person.nachname, 'lastname', person.vorname)
	        ) AS shown
	FROM photo, istabgebildet, person
	WHERE istabgebildet.photourl = photo.url AND istabgebildet.personemail = person.email
	GROUP BY photo.url
) AS photos;

SELECT photo->>'url' AS url, photo->>'owner' AS owner
FROM (
	SELECT JSONB_ARRAY_ELEMENTS(content->'photos') AS photo
	FROM myFiles
	WHERE key = 'photos'
)
WHERE photo->'shown' @> '[{ "lastname": "Lea", "firstname": "Meyer" }]'::jsonb;