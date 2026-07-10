/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 */
package com.cosmeticsmod.morecosmetics.utils;

import java.util.HashMap;
import java.util.Locale;

public class LanguageHandler {
    private static final HashMap<String, String> lang = new HashMap();

    public static String get(String key) {
        return lang.getOrDefault(key, "-");
    }

    static {
        if (Locale.getDefault().toString().toLowerCase().contains("de")) {
            lang.put("error", "Fehler");
            lang.put("warning", "Warnung");
            lang.put("until", "Bis");
            lang.put("yes", "Ja");
            lang.put("no", "Nein");
            lang.put("remove", "Entfernen");
            lang.put("wait", "Bitte warte einen Moment!");
            lang.put("loading", "Deine Spielerdaten werden noch geladen...");
            lang.put("armormode", "Die Cosmetics werden bei getragener R\u00fcstung deaktiviert.");
            lang.put("updatenametag", "Dein Nametag wurde aktualisiert!");
            lang.put("updatesuccess", "Deine Cosmetics wurden aktualisiert!");
            lang.put("updaterejected", "Die Cosmetics konnten nicht aktualisiert werden!");
            lang.put("connectionfailed", "Verbindungsaufbau fehlgeschlagen!");
            lang.put("connectionsuccess", "Verbunden!");
            lang.put("noconnection", "Nicht verbunden!");
            lang.put("nocosmetics", "Du besitzt keine Online Cosmetics!");
            lang.put("nonametag", "Du besitzt keinen Online Nametag!");
            lang.put("verifyerror", "Verifizierung fehlgeschlagen!");
            lang.put("verifyblocked", "Dein Nametag wurde gesperrt!");
            lang.put("verifypanel", "M\u00f6chtest du jetzt ins Web-Panel weitergeleitet werden?");
            lang.put("datareset", "M\u00f6chtest du deine Cosmetic Daten auf dem Client und Server wirklich l\u00f6schen?");
            lang.put("resetinfo", "Falls Probleme auftreten, k\u00f6nnte ein Reset n\u00fctzlich sein.");
            lang.put("debuginfo", "Diese Informationen helfen uns im Support, Probleme schneller zu identifizieren.");
            lang.put("redirection", "M\u00f6chtest du auf %s weitergeleitet werden?");
            lang.put("savechanges", "Sollen die \u00c4nderungen gespeichert werden?");
            lang.put("cloakcompatibility", "Erh\u00f6ht eventuell die Kompatibilit\u00e4t mit anderen Mods.");
        } else if (Locale.getDefault().toString().toLowerCase().contains("es")) {
            lang.put("error", "Error");
            lang.put("warning", "Advertencia");
            lang.put("until", "Hasta");
            lang.put("yes", "Si");
            lang.put("no", "No");
            lang.put("remove", "Eliminar");
            lang.put("wait", "\u00a1Por favor, espere un momento!");
            lang.put("loading", "Los datos se siguen cargando");
            lang.put("armormode", "Desactiva los cosm\u00e9ticos cuando la armadura est\u00e1 equipada.");
            lang.put("updatenametag", "\u00a1Tu Nametag ha sido actualizada!");
            lang.put("updatesuccess", "\u00a1Cosm\u00e9ticos actualizados con \u00e9xito!");
            lang.put("updaterejected", "\u00a1Los cosm\u00e9ticos no se pudieron actualizar!");
            lang.put("connectionfailed", "\u00a1La conexi\u00f3n ha fallado!");
            lang.put("connectionsuccess", "\u00a1Conectado!");
            lang.put("noconnection", "\u00a1No conectado!");
            lang.put("nocosmetics", "\u00a1No tienes ning\u00fan cosm\u00e9tico online!");
            lang.put("nonametag", "\u00a1No tienes un Nametag online!");
            lang.put("verifyerror", "\u00a1La verificaci\u00f3n fall\u00f3!");
            lang.put("verifyblocked", "\u00a1Tu Nametag ha sido bloqueada!");
            lang.put("verifypanel", "\u00bfLe gustar\u00eda que le remitieran al Web-Panel ahora?");
            lang.put("datareset", "\u00bfRealmente quieres borrar tus datos cosm\u00e9ticos en el cliente y en el servidor?");
            lang.put("resetinfo", "Si se producen problemas, un reinicio podr\u00eda ser \u00fatil.");
            lang.put("debuginfo", "Esta informaci\u00f3n nos ayuda a identificar los problemas m\u00e1s r\u00e1pidamente.");
            lang.put("redirection", "\u00bfQuiere ser redirigido a %s?");
            lang.put("savechanges", "\u00bfDesea guardar los cambios?");
            lang.put("cloakcompatibility", "Aumenta la compatibilidad con otros mods.");
        } else {
            lang.put("error", "Error");
            lang.put("warning", "Warning");
            lang.put("until", "Until");
            lang.put("yes", "Yes");
            lang.put("no", "No");
            lang.put("remove", "Remove");
            lang.put("wait", "Please wait a moment!");
            lang.put("armormode", "Disables Cosmetics when armor is equipped.");
            lang.put("loading", "Your player data is still being loaded...");
            lang.put("updatenametag", "Your Nametag has been updated!");
            lang.put("updatesuccess", "Successfully updated cosmetics!");
            lang.put("updaterejected", "Cosmetics could not be updated!");
            lang.put("connectionfailed", "Connection failed!");
            lang.put("connectionsuccess", "Connected!");
            lang.put("noconnection", "No connection!");
            lang.put("nocosmetics", "You don't own any online cosmetics!");
            lang.put("nonametag", "You don't own an online Nametag!");
            lang.put("verifyerror", "Verification failed!");
            lang.put("verifyblocked", "Your Nnametag has been blocked!");
            lang.put("verifypanel", "Do you want to be forwarded to the web-panel now?");
            lang.put("datareset", "Do you really want to delete your Cosmetic data on client and server?");
            lang.put("resetinfo", "In case problems occur, a reset could be useful.");
            lang.put("debuginfo", "These information helps us in support to identify problems faster.");
            lang.put("redirection", "Do you want to be redirected to %s?");
            lang.put("savechanges", "Do you want to save changes?");
            lang.put("cloakcompatibility", "Might increases compatibility with other mods.");
        }
    }
}

