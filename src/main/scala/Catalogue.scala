import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.blaze.http.Url

object Catalogue {
  case class CatalogueResult(pagination: PageDetails, data: List[ArtworkDescription])

  case class PageDetails(total: Int, limit: Int, total_pages: Int, next_url: String)

  //todo - artist_title, style_title is not present for all records and is null if not present... figure out how to handle optional fields
  case class ArtworkDescription(date_display: String, department_title: String, place_of_origin: String, medium_display: String)

  implicit val PageDetailsDecover: Decoder[PageDetails] = deriveDecoder
  implicit val ArtworkDescriptionDecoder: Decoder[ArtworkDescription] = deriveDecoder
  implicit val CatalogueDecoder: Decoder[CatalogueResult] = deriveDecoder
}
