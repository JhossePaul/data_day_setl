package demo.factories

import io.github.setl.storage.repository.SparkRepository
import io.github.setl.annotation.Delivery
import io.github.setl.transformation.Factory
import io.github.setl.util.HasSparkSession

import org.apache.spark.sql.Dataset

import demo.entities.{City, Person, PersonState}

class PeopleFactory extends Factory[Dataset[PersonState]] with HasSparkSession {
  import spark.implicits._

  private[this] var people: Dataset[PersonState] = _

  @Delivery
  private[this] val repo = SparkRepository[PersonState]

  @Delivery
  private[this] var cities: Dataset[City] = _

  override def read(): PeopleFactory.this.type =  this

  override def process(): PeopleFactory.this.type = {
    val people_ = Seq(
      Person(name = "Paul", age = 30, city = "Benito Juárez"),
      Person(name = "Otto", age = 37, city = "Coyoacán"),
      Person(name = "Laura", age = 57, city = "Puebla"),
      Person(name = "Erika", age = 30, city = "Benito Juárez")
    ).toDS()

    people = people_
      .join(cities, people_("city") === cities("city"), "inner")
      .select(
        people_("name"),
        people_("age"),
        people_("city"),
        cities("state")
      ).as[PersonState]

    this
  }


  override def write(): PeopleFactory.this.type = {
    repo.save(people)

    this
  }

  override def get(): Dataset[PersonState] = people

}
