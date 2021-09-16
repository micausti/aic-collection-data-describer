import Catalogue.CatalogueResult
import cats.effect.{ContextShift, IO}
import org.http4s.{EntityDecoder, Uri}
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import cats.implicits._

trait AICClient {
  def getCataloguedata: IO[CatalogueResult]
}

object AICClient extends EffectfulLogging {

  implicit val CatalogueEntityDecoder: EntityDecoder[IO, CatalogueResult] = jsonOf

  def apply(client: Client[IO])(implicit cs: ContextShift[IO]): AICClient =
    new AICClient {
      override def getCataloguedata: IO[CatalogueResult] = {
        val url = "https://api.artic.edu/api/v1/artworks?page=1"
        //val urlWithQuery = url.withQueryParam("page", "1")
        client.expect[CatalogueResult](url)
      }
    }}
