import ArtworkDescriptionTabulator.{ArtworkSummary, CountryMediums, CountryName, DepartmentCountries, DepartmentName, MediumCount, MediumName, Museum, artworkSummary}
import Catalogue.ArtworkDescription
import io.circe.literal.JsonStringContext
import io.circe.{Encoder, Json}

import java.time.Instant

object ArtworkDescriptionTabulator {

  def mediumCount2(departmentName: DepartmentName, country: CountryName, artworks: List[ArtworkDescription]): List[MediumCount] = {
    artworks.filter(al => (al.place_of_origin == country.value) & (al.department_title == departmentName.value)).map(_.medium_display).groupBy(identity).view.mapValues(_.size).toMap.toList.map(stg => MediumCount(MediumName(stg._1), stg._2))
  }

  def mediumCount(departmentName: DepartmentName, country: CountryName, artworks: List[ArtworkDescription]): List[MediumCount] = {
    artworks.filter(al => (al.place_of_origin == country.value) & (al.department_title == departmentName.value)).map(_.medium_display).groupBy(identity).view.mapValues(_.size).toMap.toList.map(stg => MediumCount(MediumName(stg._1), stg._2))
  }
  def countryMediums(departmentName: DepartmentName, artworks: List[ArtworkDescription]): List[CountryMediums] = {
    artworks.filter(_.department_title == departmentName.value).map(_.place_of_origin).distinct.map(c => CountryMediums(CountryName(c), mediumCount(departmentName, CountryName(c), artworks)))
  }

  def departmentCountries(artworks: List[ArtworkDescription]): List[DepartmentCountries] = {
    artworks.map(_.department_title).distinct.map(d => DepartmentCountries(DepartmentName(d), countryMediums(DepartmentName(d), artworks)))
  }

  def artworkSummary(departmentCountries: List[DepartmentCountries]): ArtworkSummary = {
    ArtworkSummary(Museum("Art Institute Chicago"),  departmentCountries)
  }


  trait Parent
  case class MediumName(value: String) extends Parent

  case class CountryName(value: String) extends Parent

  case class DepartmentName(value: String) extends Parent

  case class Museum(value: String) extends Parent


  trait Children
  case class ArtworkSummary(museum: Museum, departmentCountries: List[DepartmentCountries]) extends Children

  case class DepartmentCountries(department: DepartmentName, countryMediums: List[CountryMediums]) extends Children

  case class CountryMediums(country: CountryName, mediumCount: List[MediumCount]) extends Children

  case class MediumCount(medium: MediumName, count: Int) extends Children
}

object ArtworkSummaryEncoders {

  implicit val mediumCount: Encoder[MediumCount] = new Encoder[MediumCount] {
    override def apply(a: MediumCount): Json = Json.obj(("name", Json.fromString(a.medium.value)), ("children", Json.fromInt(a.count)))
  }

  def mediumCountList(a: List[MediumCount]) = a.map(m => Json.obj((m.medium.value, Json.fromInt(m.count))))

  def mediumCountList2(a: List[MediumCount]) = (a.map(m => Json.arr(Json.obj(("name", Json.fromString(m.medium.value)),("value", Json.fromInt(m.count))))))

//  def mediumCountList3(a: List[MediumCount]) = Json.arr(a.map(m => Json.obj(("name", Json.fromString(m.medium.value)))))
//  implicit val countryMediums: Encoder[CountryMediums] = new Encoder[CountryMediums] {
//    override def apply(a: CountryMediums): Json = {
//      def mediumCountList(a: List[MediumCount]) = a.map(m => Json.obj((m.medium.value, Json.fromInt(m.count))))
//      Json.arr(Json.obj(("name", Json.fromString(a.country.value))), Json.arr(Json.fromString("children"), mediumCountList(a.mediumCount))
//    }
//  }

  //val stg = ArtworkSummary(Museum("museum"), DepartmentCountries(DepartmentName("department"),CountryMediums(CountryName("country"), MediumCount(MediumName("medium"), 1))), )

//  implicit val ArtworkSummary: Encoder[ArtworkSummary] = new Encoder[ArtworkSummary] {
//    final def apply(a: ArtworkSummary): Json = Json.obj(
//      ("name", Json.fromString(a.museum.value)),
//      ("children", Json.arr(a.departmentCountries.map(d =>
//        Json.obj(
//          ("name", Json.fromString(d.department.value)),
//          ("children", Json.arr(d.countryMediums.map(c =>
//            Json.obj(
//              ("name", Json.fromString(c.country.value)),
//              ("children", Json.arr(c.mediumCount.map(m =>
//                Json.obj(
//                  ("name", Json.fromString(m.medium.value)),
//                  ("children", Json.fromInt(m.count))
//                ))))
//            ))))))))
//
//    )
//  }



}

