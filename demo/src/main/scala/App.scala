package demo

import demo.entities.{City, Person, PersonState}
import demo.factories.{CitiesFactory, PeopleFactory}

import io.github.setl.Setl
import io.github.setl.config.ConfigLoader

object App {
  def main(args: Array[String]): Unit = {
    val cl = ConfigLoader.builder()
      .setConfigPath("local.conf")
      .setAppEnv("local")
      .getOrCreate()

    val setl: Setl = Setl.builder()
      .setConfigLoader(cl)
      .getOrCreate()

    setl
      .setSparkRepository[PersonState]("person")
      .newPipeline()
      .addStage[CitiesFactory]()
      .addStage[PeopleFactory]()
      .describe()
      .run()
      .showDiagram
  }
}

