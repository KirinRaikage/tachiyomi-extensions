package eu.kanade.tachiyomi.extension.fr.bluesolo

import eu.kanade.tachiyomi.multisrc.madara.Madara
import eu.kanade.tachiyomi.source.model.SManga
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.Locale

class BlueSolo : Madara("Blue Solo", "https://www1.bluesolo.org", "fr", dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.FRENCH)) {
    override val mangaDetailsSelectorStatus = "div.summary-heading:contains(Statut) + .summary-content"

    private fun parseStatus(status: String?) = when {
        status == null -> SManga.UNKNOWN
        status.contains("En cours de publication", ignoreCase = true) -> SManga.ONGOING
        status.contains("Achevé", ignoreCase = true) -> SManga.COMPLETED
        else -> SManga.UNKNOWN
    }

    override fun mangaDetailsParse(document: Document): SManga {
        return super.mangaDetailsParse(document).apply {
            status = parseStatus(document.select(mangaDetailsSelectorStatus).text())
        }
    }
}
