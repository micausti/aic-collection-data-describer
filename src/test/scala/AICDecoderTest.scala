import Catalogue.{ArtworkDescription, CatalogueResult, PageDetails}
import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.matchers.should.Matchers
import io.circe.literal._

class CatalogueDecoderTest extends AnyFreeSpecLike with Matchers {

  "AIC Client" - {
    import CatalogueDecoderTest._
    "should be able to decode the Catalogue Data" in {
      Catalogue.CatalogueDecoder.decodeJson(searchResultJson) shouldBe Right(catalogueExpectedResult)
    }
  }

  object CatalogueDecoderTest {
    val date_display = "1895"
    val artist_title = "stg"
    val department_title = "paintings"
    val place_of_origin = "stg"
    val medium_display = "stg"
    val style_title = "stg"
    val artworkDescriptionExpectedResult: ArtworkDescription = ArtworkDescription("1895","paintings","France","stg")
    val pageDetails: PageDetails = PageDetails(115288, 12, 9608, "https://api.artic.edu/api/v1/artworks?page=2")
    val catalogueExpectedResult = CatalogueResult(pageDetails, List(artworkDescriptionExpectedResult, artworkDescriptionExpectedResult))

    val searchResultJson =
      json"""{
            "pagination": {
            "total": 115288,
            "limit": 12,
            "offset": 0,
            "total_pages": 9608,
            "current_page": 1,
            "next_url": "https://api.artic.edu/api/v1/artworks?page=2"
            },
            "data": [
            {
            "id": 11312,
            "api_model": "artworks",
            "api_link": "https://api.artic.edu/api/v1/artworks/11312",
            "is_boosted": false,
            "title": "Woman Mending",
            "department_title": "paintings",
            "alt_titles": null,
            "thumbnail": {
            "lqip": "data:image/gif;base64,R0lGODlhBAAFAPQAAEw/QE5BRU9DSE1ESVJFSllKUFlPVFtPVV9ZX25eXmtdYXZrZYt8cIx/dYF7epJ6dYiFfJCJfJiRhcK9rQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAEAAUAAAURIMREk/M0EqIki3EMAUAUQggAOw==",
            "width": 1850,
            "height": 2250,
            "alt_text": "A work made of oil on canvas."
            },
            "artist_title": "stg",
            "place_of_origin": "stg",
            "medium_display": "stg",
            "style_title": "stg",
            "main_reference_number": "1959.636",
            "has_not_been_viewed_much": false,
            "boost_rank": null,
            "date_start": 1895,
            "date_end": 1895,
            "date_display": "1895",
            "date_qualifier_title": "",
            "date_qualifier_id": null,
            "artist_display": "Camille Pissarro\nFrench, 1830-1903",
            "place_of_origin": "France",
            "dimensions": "65.4 × 54.4 cm (25 5/8 × 21 3/8 in.)"
            },
            {
            "id": 11312,
            "api_model": "artworks",
            "api_link": "https://api.artic.edu/api/v1/artworks/11312",
            "is_boosted": false,
            "title": "Woman Mending",
            "department_title": "paintings",
            "alt_titles": null,
            "thumbnail": {
            "lqip": "data:image/gif;base64,R0lGODlhBAAFAPQAAEw/QE5BRU9DSE1ESVJFSllKUFlPVFtPVV9ZX25eXmtdYXZrZYt8cIx/dYF7epJ6dYiFfJCJfJiRhcK9rQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAEAAUAAAURIMREk/M0EqIki3EMAUAUQggAOw==",
            "width": 1850,
            "height": 2250,
            "alt_text": "A work made of oil on canvas."
            },
            "artist_title": "stg",
            "place_of_origin": "stg",
            "medium_display": "stg",
            "style_title": "stg",
            "main_reference_number": "1959.636",
            "has_not_been_viewed_much": false,
            "boost_rank": null,
            "date_start": 1895,
            "date_end": 1895,
            "date_display": "1895",
            "date_qualifier_title": "",
            "date_qualifier_id": null,
            "artist_display": "Camille Pissarro\nFrench, 1830-1903",
            "place_of_origin": "France",
            "dimensions": "65.4 × 54.4 cm (25 5/8 × 21 3/8 in.)"
            }
            ]
            }"""
  }
}
