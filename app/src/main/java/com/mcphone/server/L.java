package com.mcphone.server;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/** Kleiner UI-Wortschatz für die wichtigsten Oberflächen. Deutsch ist Standard. */
final class L {
    private static final String[] LANGS = {"de", "en", "es", "fr", "tr"};
    private static final Map<String, String[]> WORDS = new HashMap<>();
    static {
        add("Deine Server", "Your servers", "Tus servidores", "Vos serveurs", "Sunucuların");
        add("Verwalte deine Minecraft-Welten an einem Ort.", "Manage your Minecraft worlds in one place.", "Administra tus mundos de Minecraft en un solo lugar.", "Gérez vos mondes Minecraft au même endroit.", "Minecraft dünyalarını tek yerden yönet.");
        add("Noch keine Server", "No servers yet", "Aún no hay servidores", "Aucun serveur", "Henüz sunucu yok");
        add("Erstelle deinen ersten Server. Du kannst mehrere Profile mit eigenen Einstellungen, Plugins und Config-Dateien verwalten.", "Create your first server. Each profile has its own settings, plugins and config files.", "Crea tu primer servidor. Cada perfil tiene sus propios ajustes, plugins y archivos de configuración.", "Créez votre premier serveur. Chaque profil possède ses paramètres, plugins et fichiers de configuration.", "İlk sunucunu oluştur. Her profilin ayarları, eklentileri ve yapılandırma dosyaları ayrıdır.");
        add("+  Server erstellen", "+  Create server", "+  Crear servidor", "+  Créer un serveur", "+  Sunucu oluştur");
        add("Noch nicht eingerichtet", "Not configured yet", "Aún no configurado", "Pas encore configuré", "Henüz kurulmadı");
        add("Öffnen", "Open", "Abrir", "Ouvrir", "Aç");
        add("Einrichten", "Set up", "Configurar", "Configurer", "Kur");
        add("Name", "Rename", "Nombre", "Nom", "Ad");
        add("Plugins", "Plugins", "Plugins", "Plugins", "Eklentiler");
        add("Configs", "Configs", "Configuraciones", "Configs", "Yapılandırma");
        add("Server benennen", "Rename server", "Renombrar servidor", "Renommer le serveur", "Sunucuyu yeniden adlandır");
        add("Speichern", "Save", "Guardar", "Enregistrer", "Kaydet");
        add("Abbrechen", "Cancel", "Cancelar", "Annuler", "İptal");
        add("Server starten", "Start server", "Iniciar servidor", "Démarrer le serveur", "Sunucuyu başlat");
        add("Server stoppen", "Stop server", "Detener servidor", "Arrêter le serveur", "Sunucuyu durdur");
        add("Liste", "Servers", "Servidores", "Serveurs", "Sunucular");
        add("Zurück zur Serverliste", "Back to server list", "Volver a la lista", "Retour à la liste", "Sunucu listesine dön");
        add("Plugin-Installer", "Plugin installer", "Instalador de plugins", "Installateur de plugins", "Eklenti yükleyici");
        add("JAR-Downloadlink", "JAR download link", "Enlace de descarga JAR", "Lien de téléchargement JAR", "JAR indirme bağlantısı");
        add("Plugin installieren", "Install plugin", "Instalar plugin", "Installer le plugin", "Eklentiyi yükle");
        add("Installierte JAR-Dateien", "Installed JAR files", "Archivos JAR instalados", "Fichiers JAR installés", "Yüklü JAR dosyaları");
        add("Noch keine Plugins installiert.", "No plugins installed yet.", "Aún no hay plugins instalados.", "Aucun plugin installé.", "Henüz eklenti yüklenmedi.");
        add("Configs bearbeiten", "Edit configs", "Editar configuraciones", "Modifier les configs", "Yapılandırmaları düzenle");
        add("Wähle eine Config-Datei zum Anzeigen oder Bearbeiten.", "Choose a config file to view or edit.", "Elige un archivo de configuración para ver o editar.", "Choisissez un fichier de configuration à afficher ou modifier.", "Görüntülemek veya düzenlemek için bir dosya seç.");
        add("Bearbeiten", "Edit", "Editar", "Modifier", "Düzenle");
        add("+  Config-Datei erstellen", "+  Create config file", "+  Crear archivo de configuración", "+  Créer un fichier de config", "+  Yapılandırma dosyası oluştur");
        add("Änderungen speichern", "Save changes", "Guardar cambios", "Enregistrer les modifications", "Değişiklikleri kaydet");
        add("Dateiliste", "File list", "Lista de archivos", "Liste des fichiers", "Dosya listesi");
        add("Einstellungen", "Settings", "Ajustes", "Paramètres", "Ayarlar");
        add("Sprache", "Language", "Idioma", "Langue", "Dil");
        add("Erscheinungsbild", "Appearance", "Apariencia", "Apparence", "Görünüm");
        add("Hell", "Light", "Claro", "Clair", "Açık");
        add("Dunkel", "Dark", "Oscuro", "Sombre", "Koyu");
        add("Zurück", "Back", "Volver", "Retour", "Geri");
        add("Import von Handy", "Import from phone", "Importar desde el móvil", "Importer depuis le téléphone", "Telefondan içe aktar");
        add("Mehrere JAR-Dateien auswählen", "Select multiple JAR files", "Selecciona varios archivos JAR", "Sélectionner plusieurs fichiers JAR", "Birden fazla JAR seç");
        add("Nur HTTPS-Links werden akzeptiert.", "Only HTTPS links are accepted.", "Solo se aceptan enlaces HTTPS.", "Seuls les liens HTTPS sont acceptés.", "Yalnızca HTTPS bağlantıları kabul edilir.");
        add("Neuer Server", "New server", "Nuevo servidor", "Nouveau serveur", "Yeni sunucu");
        add("Gib deinem Server einen Namen.", "Give your server a name.", "Ponle un nombre a tu servidor.", "Donnez un nom à votre serveur.", "Sunucuna bir ad ver.");
        add("Weiter", "Next", "Siguiente", "Suivant", "İleri");
        add("Server-Name (MOTD)", "Server name (MOTD)", "Nombre del servidor (MOTD)", "Nom du serveur (MOTD)", "Sunucu adı (MOTD)");
        add("Arbeitsspeicher für den Server", "Memory for the server", "Memoria para el servidor", "Mémoire du serveur", "Sunucu belleği");
        add("Max. Spieler", "Max players", "Jugadores máx.", "Joueurs max.", "Maks. oyuncu");
        add("Schwierigkeit", "Difficulty", "Dificultad", "Difficulté", "Zorluk");
        add("Spielmodus", "Game mode", "Modo de juego", "Mode de jeu", "Oyun modu");
        add("Sichtweite (Chunks)", "View distance (chunks)", "Distancia de visión (chunks)", "Distance d'affichage (chunks)", "Görüş mesafesi (chunk)");
        add("Software wird geladen …", "Loading software …", "Cargando software …", "Chargement du logiciel …", "Yazılım yükleniyor …");
        add("Installieren", "Install", "Instalar", "Installer", "Yükle");
        add("Installation", "Installation", "Instalación", "Installation", "Kurulum");
        add("Version", "Version", "Versión", "Version", "Sürüm");
        add("Spiel", "Game", "Juego", "Jeu", "Oyun");
        add("Leistung", "Performance", "Rendimiento", "Performances", "Performans");
        add("Software", "Software", "Software", "Logiciel", "Yazılım");
        add("Befehl, z. B. list", "Command, e.g. list", "Comando, p. ej. list", "Commande, ex. list", "Komut, ör. list");
        add("Spieler", "Players", "Jugadores", "Joueurs", "Oyuncular");
        add("Laufzeit", "Uptime", "Tiempo activo", "Durée", "Çalışma süresi");
        add("Adresse", "Address", "Dirección", "Adresse", "Adres");
        add("Setup", "Setup", "Configuración", "Configuration", "Kurulum");
        add("Tipp: Akku-Optimierung für diese App ausschalten →", "Tip: disable battery optimization for this app →", "Consejo: desactiva la optimización de batería →", "Astuce : désactivez l'optimisation de la batterie →", "İpucu: bu uygulama için pil optimizasyonunu kapat →");
        add("Server lädt das Plugin beim nächsten Start.", "The server loads this plugin on next start.", "El servidor cargará el plugin al iniciar.", "Le serveur chargera le plugin au prochain démarrage.", "Sunucu eklentiyi sonraki açılışta yükler.");
        add("Wähle die Sprache der App-Oberfläche.", "Choose the app interface language.", "Elige el idioma de la interfaz.", "Choisissez la langue de l'interface.", "Uygulama arayüzü dilini seç.");
        add("Hell oder dunkel – Schrift und Akzentfarbe bleiben gleich.", "Light or dark — the font and accent color stay the same.", "Claro u oscuro: la fuente y el color de acento no cambian.", "Clair ou sombre : la police et la couleur d'accent restent identiques.", "Açık veya koyu — yazı tipi ve vurgu rengi aynı kalır.");
        add("Füge einen direkten HTTPS-Link zu einer .jar-Datei ein. Nutze nur Plugins aus Quellen, denen du vertraust.", "Paste a direct HTTPS link to a .jar file. Only use plugins from trusted sources.", "Pega un enlace HTTPS directo a un archivo .jar. Usa solo plugins de fuentes fiables.", "Collez un lien HTTPS direct vers un fichier .jar. Utilisez uniquement des plugins de sources fiables.", "Bir .jar dosyasının doğrudan HTTPS bağlantısını ekle. Yalnızca güvenilir kaynaklardan eklenti kullan.");
        add("Noch keine Server", "No servers yet", "Aún no hay servidores", "Aucun serveur", "Henüz sunucu yok");
        add("● offline", "● offline", "● desconectado", "● hors ligne", "● çevrimdışı");
        add("● online", "● online", "● conectado", "● en ligne", "● çevrimiçi");
        add("● startet", "● starting", "● iniciando", "● démarrage", "● başlatılıyor");
        add("● anderer Server aktiv", "● another server active", "● otro servidor activo", "● autre serveur actif", "● başka sunucu etkin");
        add("Anderer Server läuft", "Another server is running", "Otro servidor está activo", "Un autre serveur fonctionne", "Başka bir sunucu çalışıyor");
        add("Server läuft nicht – erst starten.", "Server is not running — start it first.", "El servidor no está activo: inícialo primero.", "Le serveur n'est pas démarré — démarrez-le d'abord.", "Sunucu çalışmıyor — önce başlat.");
        add("Dieser Server läuft nicht – erst starten.", "This server is not running — start it first.", "Este servidor no está activo: inícialo primero.", "Ce serveur n'est pas démarré — démarrez-le d'abord.", "Bu sunucu çalışmıyor — önce başlat.");
        add("Stoppe zuerst den anderen Server.", "Stop the other server first.", "Detén primero el otro servidor.", "Arrêtez d'abord l'autre serveur.", "Önce diğer sunucuyu durdur.");
        add("Port", "Port", "Puerto", "Port", "Bağlantı noktası");
        add("Online-Modus (Account-Prüfung)", "Online mode (account authentication)", "Modo online (autenticación de cuenta)", "Mode en ligne (authentification du compte)", "Çevrimiçi mod (hesap doğrulama)");
        add("PvP", "PvP", "PvP", "JcJ", "PvP");
        add("Server-Einstellungen", "Server settings", "Ajustes del servidor", "Paramètres du serveur", "Sunucu ayarları");
        add("Der Server ist bereit", "The server is ready", "El servidor está listo", "Le serveur est prêt", "Sunucu hazır");
        add("Noch keine Dateien in diesem Ordner.", "No files in this folder yet.", "Aún no hay archivos en esta carpeta.", "Aucun fichier dans ce dossier pour le moment.", "Bu klasörde henüz dosya yok.");
        add("Bitte stoppe diesen Server vor der Installation.", "Stop this server before installing.", "Detén este servidor antes de instalar.", "Arrêtez ce serveur avant l'installation.", "Yüklemeden önce bu sunucuyu durdur.");
        add("Plugins benötigen Paper; Vanilla unterstützt das Plugin-System nicht.", "Plugins require Paper; Vanilla does not support plugins.", "Los plugins requieren Paper; Vanilla no admite plugins.", "Les plugins nécessitent Paper ; Vanilla ne prend pas en charge les plugins.", "Eklentiler Paper gerektirir; Vanilla eklentileri desteklemez.");
        add("Dateien vom Handy importieren", "Import files from phone", "Importar archivos del teléfono", "Importer des fichiers du téléphone", "Telefondan dosya aktar");
        add("Du kannst mehrere .jar-Dateien gleichzeitig auswählen.", "You can select multiple .jar files at once.", "Puedes seleccionar varios archivos .jar a la vez.", "Vous pouvez sélectionner plusieurs fichiers .jar à la fois.", "Birden fazla .jar dosyasını aynı anda seçebilirsin.");
        add("Server-Software", "Server software", "Software del servidor", "Logiciel serveur", "Sunucu yazılımı");
        add("Dieser Assistent richtet einen Minecraft-Server ein, der direkt auf deinem Handy läuft. Du wählst Software, Version und die wichtigsten Einstellungen – den Rest übernimmt die App.", "This wizard sets up a Minecraft server that runs on your phone. Choose the software, version and main settings; the app handles the rest.", "Este asistente configura un servidor de Minecraft en tu móvil. Elige el software, la versión y los ajustes principales; la app se encarga del resto.", "Cet assistant configure un serveur Minecraft sur votre téléphone. Choisissez le logiciel, la version et les principaux paramètres ; l'application s'occupe du reste.", "Bu sihirbaz telefonunda çalışan bir Minecraft sunucusu kurar. Yazılımı, sürümü ve temel ayarları seç; gerisini uygulama halleder.");
        add("Der offizielle Mojang-Server. Reines Minecraft ohne Plugins.", "The official Mojang server. Vanilla Minecraft without plugins.", "El servidor oficial de Mojang. Minecraft puro sin plugins.", "Le serveur officiel Mojang. Minecraft sans plugins.", "Resmî Mojang sunucusu. Eklentisiz saf Minecraft.");
        add("Optimierter Server mit besserer Performance – die bessere Wahl fürs Handy. Plugins möglich.", "Optimized server with better performance — a better choice for phones. Plugins supported.", "Servidor optimizado con mejor rendimiento: ideal para móviles. Admite plugins.", "Serveur optimisé plus performant, mieux adapté aux téléphones. Plugins pris en charge.", "Daha yüksek performanslı, telefonlar için daha uygun sunucu. Eklentiler desteklenir.");
        add("Server starten", "Start server", "Iniciar servidor", "Démarrer le serveur", "Sunucuyu başlat");
        add("Fertig", "Finish", "Terminar", "Terminer", "Bitir");
        add("Versionen werden geladen …", "Loading versions …", "Cargando versiones …", "Chargement des versions …", "Sürümler yükleniyor …");
        add("Erneut versuchen", "Try again", "Intentar de nuevo", "Réessayer", "Yeniden dene");
        add("Eine Linux-Umgebung (Alpine mit Java) und die Server-Datei werden automatisch geladen und eingerichtet. Mit der Installation akzeptierst du die Minecraft-EULA (aka.ms/MinecraftEULA).", "A Linux environment (Alpine with Java) and the server file are downloaded and configured automatically. Installing means you accept the Minecraft EULA (aka.ms/MinecraftEULA).", "El entorno Linux (Alpine con Java) y el archivo del servidor se descargan y configuran automáticamente. Al instalar aceptas la EULA de Minecraft.", "L'environnement Linux (Alpine avec Java) et le fichier serveur sont téléchargés et configurés automatiquement. L'installation implique l'acceptation de l'EULA Minecraft.", "Linux ortamı (Java ile Alpine) ve sunucu dosyası otomatik indirilip yapılandırılır. Kurulum Minecraft EULA'sını kabul ettiğin anlamına gelir.");
    }
    private static void add(String de, String en, String es, String fr, String tr) { WORDS.put(de, new String[]{de, en, es, fr, tr}); }
    static String t(Context c, String text) {
        if (text == null) return null;
        String[] row = WORDS.get(text);
        if (row == null) return text;
        String lang = AppSettings.language(c);
        for (int i = 0; i < LANGS.length; i++) if (LANGS[i].equals(lang)) return row[i];
        return text;
    }
    static String languageName(String code) {
        switch (code) { case "en": return "English"; case "es": return "Español"; case "fr": return "Français"; case "tr": return "Türkçe"; default: return "Deutsch"; }
    }
}
