package com.codoacodo23650.tpgrupo14.entities;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class Utils {

    // Método para hashear el pass
    public static String hashPassword(String plainTextPassword) {
                return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    // Método para validar un hash
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {

        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
