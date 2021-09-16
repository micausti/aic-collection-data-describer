import Catalogue.ArtworkDescription

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

  case class MediumName(value: String)

  case class CountryName(value: String)

  case class DepartmentName(value: String)

  case class ArtworkSummary(departmentCountries: List[DepartmentCountries])

  case class DepartmentCountries(departmentName: DepartmentName, countries: List[CountryMediums])

  case class CountryMediums(countryName: CountryName, mediums: List[MediumCount]) //distinct list of mediums for each country

  case class MediumCount(mediumName: MediumName, mediumCount: Int) //a count of items for each medium
}
