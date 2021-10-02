import ArtworkDescriptionTabulator.{Children, Parent}
import io.circe.{Encoder, Json}
import io.circe.generic.semiauto.deriveEncoder

//object ZoomableSunbust {
//
//  implicit val ZoomableSunbust: Encoder[ZoomableSunburst] = new Encoder[ZoomableSunburst] {
//    final def apply(a: ZoomableSunburst): Json = Json.obj(
//      ("foo", Json.fromString(a.name)),
//      ("bar", Json.fromInt(a.bar))
//    )
//  }
//
//}
