import Catalogue.ArtworkDescription
import ArtworkDescriptionTabulator._
import io.circe.literal.JsonStringContext
import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.matchers.should.Matchers
import ArtworkSummaryEncoders._

class ArtworkDescriptionTabulatorTest extends AnyFreeSpecLike with Matchers {

  "ArtworkDescriptionTabulator" - {

    val tabulator = ArtworkDescriptionTabulator
    val artworkList = List(ArtworkDescription("1930", "Arts of the Americas", "United States", "Oil on canvas"), ArtworkDescription("1919/21", "Arts of the Americas", "United States", "Oil on canvas"), ArtworkDescription("Northern Song dynasty (960–1127), 12th century", "Arts of Asia", "China", "Qingbai ware; porcelain with underglaze carved decoration"), ArtworkDescription("1895", "Painting and Sculpture of Europe", "France", "Oil on canvas"), ArtworkDescription("1873", "Arts of the Americas", "Seville", "Oil on canvas"), ArtworkDescription("12th century", "Applied Arts of Europe", "Sicily", "Ivory brass, tempera, and gold leaf"), ArtworkDescription("1956", "Modern Art", "Ireland", "Oil on canvas"), ArtworkDescription("c. 1888", "Prints and Drawings", "France", "Graphite on ivory laid paper"), ArtworkDescription("1973", "Textiles", "Colombia", "Wool and horsehair; wrapped"), ArtworkDescription("1897", "Prints and Drawings", "France", "Woodcut in black on cream wove paper"), ArtworkDescription("1885", "Painting and Sculpture of Europe", "France", "Oil on canvas"), ArtworkDescription("1898", "Prints and Drawings", "France", "Woodcut in black on cream wove paper"))
    val artworkList2 = List(ArtworkDescription("1930", "Arts of the Americas", "United States", "Oil on canvas"), ArtworkDescription("1919/21", "Arts of the Americas", "United States", "Oil on canvas"), ArtworkDescription("Northern Song dynasty (960–1127), 12th century", "Arts of Asia", "China", "Qingbai ware; porcelain with underglaze carved decoration"), ArtworkDescription("1895", "Painting and Sculpture of Europe", "France", "Oil on canvas"), ArtworkDescription("1873", "Arts of the Americas", "Seville", "Oil on canvas"), ArtworkDescription("12th century", "Applied Arts of Europe", "Sicily", "Ivory brass, tempera, and gold leaf"), ArtworkDescription("1956", "Modern Art", "Ireland", "Oil on canvas"), ArtworkDescription("c. 1888", "Prints and Drawings", "France", "Graphite on ivory laid paper"), ArtworkDescription("1973", "Textiles", "Colombia", "Wool and horsehair; wrapped"), ArtworkDescription("1897", "Prints and Drawings", "France", "Woodcut in black on cream wove paper"), ArtworkDescription("1885", "Painting and Sculpture of Europe", "France", "Oil on canvas"), ArtworkDescription("1898", "Prints and Drawings", "France", "Woodcut in black on cream wove paper"))

    "get the medium Count for a country and department" in {
      val expected = List(MediumCount(MediumName("Oil on canvas"), 2))
      tabulator.mediumCount(DepartmentName("Arts of the Americas"), CountryName("United States"), artworkList) shouldBe expected
    }

    "get the country mediums for a department" in {
      val expected = List(CountryMediums(CountryName("United States"), List(MediumCount(MediumName("Oil on canvas"), 2))), CountryMediums(CountryName("Seville"), List(MediumCount(MediumName("Oil on canvas"), 1))))
      tabulator.countryMediums(DepartmentName("Arts of the Americas"), artworkList) shouldBe expected
    }

    "get the department countries for a list of artworks" in {
      val expected = List(
        DepartmentCountries(DepartmentName("Arts of the Americas"), List(CountryMediums(CountryName("United States"), List(MediumCount(MediumName("Oil on canvas"), 2))), CountryMediums(CountryName("Seville"), List(MediumCount(MediumName("Oil on canvas"), 1))))),
        DepartmentCountries(DepartmentName("Arts of Asia"), List(CountryMediums(CountryName("China"), List(MediumCount(MediumName("Qingbai ware; porcelain with underglaze carved decoration"), 1))))),
        DepartmentCountries(DepartmentName("Painting and Sculpture of Europe"), List(CountryMediums(CountryName("France"), List(MediumCount(MediumName("Oil on canvas"), 2))))),
        DepartmentCountries(DepartmentName("Applied Arts of Europe"), List(CountryMediums(CountryName("Sicily"), List(MediumCount(MediumName("Ivory brass, tempera, and gold leaf"), 1))))),
        DepartmentCountries(DepartmentName("Modern Art"), List(CountryMediums(CountryName("Ireland"), List(MediumCount(MediumName("Oil on canvas"), 1))))),
        DepartmentCountries(DepartmentName("Prints and Drawings"), List(CountryMediums(CountryName("France"), List(MediumCount(MediumName("Graphite on ivory laid paper"), 1), MediumCount(MediumName("Woodcut in black on cream wove paper"), 2))))),
        DepartmentCountries(DepartmentName("Textiles"), List(CountryMediums(CountryName("Colombia"), List(MediumCount(MediumName("Wool and horsehair; wrapped"), 1))))))

      tabulator.departmentCountries(artworkList) shouldBe expected
    }
  }

  "Artwork Summary" -  {
    val mediumCount = MediumCount(MediumName("painting"), 10)
    val mediumCountList = List(mediumCount, mediumCount, mediumCount)

    "should correctly encode the json for MediumCounty" in {
      val expected =
        json"""{
          "name": "painting",
          "children": 10
          }
            """
      ArtworkSummaryEncoders.mediumCount.apply(mediumCount) shouldBe expected
    }

    "should create a json list of Medium counts" in {
      val expected =
        json"""{
          "name": "painting",
          "children": 10
          }
            """
      ArtworkSummaryEncoders.mediumCountList(mediumCountList) shouldBe expected
    }

    "should create a json list of name with medium name" in {
      val expected =
        json"""{
          "name": "painting",
          "children": 10
          }
            """
      ArtworkSummaryEncoders.mediumCountList2(mediumCountList) shouldBe expected
    }
  }
}
