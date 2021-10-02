//import io.circe.literal.JsonStringContext
//import org.scalatest.freespec.AnyFreeSpecLike
//import org.scalatest.matchers.should.Matchers
//
//class ZoomableSubburstEncoderTest extends AnyFreeSpecLike with Matchers {
//
//  "Zoomable Sunburst" - {
//    "should be able to encode the Zoomable Sunburst to the correct json format" in {
//      import ZoomableSunburstTest._
//      ZoomableSunbust.ZoomableSunburst.apply() shouldBe expectedJson
//    }
//  }
//
//  object ZoomableSunburstTest {
//
//
//    val expectedJson = json"""{
//      "name": "departmentCountries",
//      "children": [
//      {
//        "name": "Arts of the Americas",
//        "children": [
//        {
//          "name": "United States",
//          "children": [
//          {"name": "Oil on canvas", "value": 2}
//          ]
//        },
//        {
//          "name": "Seville",
//          "children": [
//          {"name": "Oil on canvas", "value": 1}
//          ]
//        }
//        ]
//      }""""
//  }
//}
