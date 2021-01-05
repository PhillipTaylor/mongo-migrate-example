package example



import scala.reflect.ClassTag

object Hello extends App {
  println("app started")

  println("opening modern connection")
  ModernMongoScala.connect()

  println("opening legacy connection")
  LegacyMongoScala.connect()
}

/* comment out this, with dependency to see legacy code works */
object ModernMongoScala {
  import org.bson.codecs.configuration.CodecRegistries
  import org.bson.codecs.configuration.CodecRegistries.fromProviders
  import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
  import org.mongodb.scala.bson.collection.immutable.Document
  import org.mongodb.scala.connection.NettyStreamFactoryFactory
  import org.mongodb.scala.{ConnectionString, MongoClient, MongoClientSettings, MongoDatabase, WriteConcern}
  import scala.concurrent._
  import scala.concurrent.duration._

  def connect() = {
    val connectionString = "mongodb://127.0.0.1/test"
    val useSsl = false

    println(s"Connecting to Mongo using connection string: ${connectionString.replaceAll(":.*@",":*****@")}")

    val settings = MongoClientSettings.builder
      .applicationName("poo-serve")
      .codecRegistry(DEFAULT_CODEC_REGISTRY)
      .writeConcern(WriteConcern.ACKNOWLEDGED)
      .applyConnectionString(new ConnectionString(connectionString))
      .applyToSslSettings(b => b.enabled(useSsl))
      .streamFactoryFactory(NettyStreamFactoryFactory())
      .build()

    val client: MongoClient = MongoClient(settings)

    val database = client.getDatabase("test")

    println(s"Connected to mongo db using new mechanism: ${database.name}")

    val collections = Await.result(database.listCollections.toFuture, Duration.Inf)
    println(s"collections in ${database.name}: $collections")
  }
}

/* comment out this, with dependency to see modern code works */
object LegacyMongoScala {
  import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoDB}

  def connect() = {
    val client = MongoClient(MongoClientURI("mongodb://127.0.0.1/test"))
    println(client("test").collectionNames)
  }
}
