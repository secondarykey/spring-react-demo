CREATE EXTENSION PGCRYPTO;

SELECT encode(digest('passw0rd' || 'salt' ,'sha256'),'hex');

DROP FUNCTION IF EXISTS PasswordHash(VARCHAR);

CREATE OR REPLACE FUNCTION PasswordHash(VARCHAR)
RETURNS VARCHAR AS $$
  BEGIN
    RETURN encode(digest($1 || 'salt' ,'sha256'),'hex');
  END;
$$ LANGUAGE plpgsql;

