package demo.factories

import io.github.setl.storage.repository.SparkRepository
import io.github.setl.transformation.Factory
import io.github.setl.util.HasSparkSession

import org.apache.spark.sql.Dataset

import demo.entities.City

class CitiesFactory extends Factory[Dataset[City]] with HasSparkSession {
  import spark.implicits._

  private[this] var cities: Dataset[City] = _

  override def read(): CitiesFactory.this.type =  this

  override def process(): CitiesFactory.this.type = {
    cities = Seq(
      City(city = "Benito Juárez", state = "CDMX"),
      City(city = "Coyoacán", state = "CDMX"),
      City(city = "Puebla", state = "Puebla")
    ).toDS()

    this
  }

  override def write(): CitiesFactory.this.type = this

  override def get(): Dataset[City] = cities

}
