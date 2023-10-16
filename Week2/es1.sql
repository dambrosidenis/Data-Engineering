SELECT XMLELEMENT(
	NAME photos,
	XMLAGG(nester.nested)
) FROM (
    SELECT XMLELEMENT(
    	NAME photo,
		XMLATTRIBUTES(url, titel AS title, photo.personemail AS owner),
		XMLAGG(
    		XMLELEMENT(
                NAME shown,
                XMLATTRIBUTES(vorname AS firstname, nachname AS lastname))
    	)
    ) AS nested
    FROM photo, istabgebildet, person
    WHERE istabgebildet.photourl = photo.url AND istabgebildet.personemail = person.email
    GROUP BY photo.url
) AS nester;