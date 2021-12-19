CREATE ALIAS PasswordHash AS "
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CODE
String hashPassword(String pw) {

    MessageDigest md;
	try {
		md = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException e) {
	    return "Generate Error";
	}

	String target = pw+ "salt";
    md.update(target.getBytes());
    byte[] cipher_byte = md.digest();

	StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
	for(byte b: cipher_byte) {
       sb.append(String.format("%02x", b&0xff) );
	}
	return sb.toString();
}
";