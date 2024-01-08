package eu.kanade.tachiyomi.extension.fr.sushiscanfr

import eu.kanade.tachiyomi.multisrc.mangathemesia.MangaThemesia
import eu.kanade.tachiyomi.network.interceptor.rateLimit
import eu.kanade.tachiyomi.source.model.SManga
import okhttp3.OkHttpClient
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class SushiScanFR : MangaThemesia("Sushiscan.fr", "https://sushiscan.fr", "fr", dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.FRENCH)) {

    override val client: OkHttpClient = super.client.newBuilder()
        .rateLimit(2, 1, TimeUnit.SECONDS)
        .build()

    override val altNamePrefix = "Nom alternatif : "
    override val seriesAuthorSelector = ".imptdt:contains(Auteur) i, .fmed b:contains(Auteur)+span"
    override val seriesStatusSelector = ".imptdt:contains(Statut) i"
    override fun String?.parseStatus(): Int = when {
        this == null -> SManga.UNKNOWN
        this.contains("En Cours", ignoreCase = true) -> SManga.ONGOING
        this.contains("Terminé", ignoreCase = true) -> SManga.COMPLETED
        this.contains("Abandonné", ignoreCase = true) -> SManga.CANCELLED
        else -> SManga.UNKNOWN
    }

    override fun mangaDetailsParse(document: Document): SManga =
        super.mangaDetailsParse(document).apply {
            status = document.select(seriesStatusSelector).text().parseStatus()
        }
}
