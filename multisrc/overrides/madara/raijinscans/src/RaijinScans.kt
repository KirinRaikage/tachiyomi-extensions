package eu.kanade.tachiyomi.extension.fr.raijinscans

import eu.kanade.tachiyomi.multisrc.madara.Madara
import eu.kanade.tachiyomi.network.interceptor.rateLimit
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Tim

class RaijinScans : Madara(
    "Raijin Scans",
    "https://raijinscans.com",
    "fr",
    SimpleDateFormat("dd/MM/yyyy", Locale("fr"))
) {
    
    override val client: OkHttpClient = super.client.newBuilder()
        .rateLimit(1, 2, TimeUnit.SECONDS)
        .build()
}