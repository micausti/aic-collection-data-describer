import Catalogue.CatalogueResult
import cats.effect.{ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import cats.implicits._
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp with EffectfulLogging {

  override implicit val contextShift: ContextShift[IO] = IO.contextShift(global)
  override implicit val timer: Timer[IO] = IO.timer(global)

  case class Resources(aicClient: AICClient)


  def getCatalogueData(resource: Resources): IO[CatalogueResult] = {
    for {
      searchResult <- resource.aicClient.getCataloguedata
      _ <- logger.info(s"paginaiton details ${searchResult.pagination}")
      _ <- logger.info(s"artworks ${searchResult.data}")
    } yield searchResult
  }

  def buildResources(implicit contextShift: ContextShift[IO]): Resource[IO, Resources] =
    for {
      http4sClient <- BlazeClientBuilder[IO](global).resource
      aicClient = AICClient(http4sClient)
    } yield Resources(aicClient)


  def run(args: List[String]): IO[ExitCode] = {

    logger.info(s"starting application") >> buildResources.use(getCatalogueData).as(ExitCode.Success)

  }
}