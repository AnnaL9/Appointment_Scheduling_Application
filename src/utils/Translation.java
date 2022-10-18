package utils;

import java.util.Locale;

public class Translation {
    private static String DEFAULT_LANGUAGE = Locale.getDefault().getDisplayLanguage().toLowerCase();

    /**
     * Translates message to English or French
     * @param messageID message ID to translate
     * @return translated String
     */
    public static String translate(int messageID) {
        boolean isEnglish = Translation.DEFAULT_LANGUAGE.contains("english");

        switch (messageID) {
            case 1:
                return isEnglish ? "Error" : "Erreur";
            case 2:
                return isEnglish ? "The username or password you have entered is incorrect" :
                        "Le nom d'utilisateur ou le mot de passe que vous avez entré est incorrect";
            case 3:
                return isEnglish ? "Error querying database." : "Erreur lors de l'interrogation de la base de données.";
            case 4:
                return isEnglish ? "There was an error logging in." : "Une erreur s'est produite lors de la connexion.";
            case 5:
                return isEnglish ? "Scheduler Log-in" : "Connexion du Planificateur";
            case 6:
                return isEnglish ? "Username:" : "Nom d'utilisateur:";
            case 7:
                return isEnglish ? "Password:" : "Mot de passe:";
            case 8:
                return isEnglish ? "Log-in" : "Connexion";

        }
        return "";
    }
}
