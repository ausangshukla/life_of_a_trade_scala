package com.lot.utils

import org.elasticsearch.common.settings.Settings
import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.ElasticDsl._
import com.lot.security.model.Security

object ESClient {

  def searchSecurity(term:String) {

    implicit object SecurityHitAs extends HitAs[Security] {
      override def as(hit: RichSearchHit): Security = {
        Security(Some(hit.sourceAsMap("id").toString.toLong), hit.sourceAsMap("name").toString,
          hit.sourceAsMap("ticker").toString, hit.sourceAsMap("description").toString,
          hit.sourceAsMap("price").toString.toDouble, hit.sourceAsMap("asset_class").toString,
          hit.sourceAsMap("sector").toString, hit.sourceAsMap("region").toString,
          hit.sourceAsMap("tick_size").toString.toInt, hit.sourceAsMap("liquidity").toString,
          None, None)
      }
    }

    val uri = ElasticsearchClientUri("elasticsearch://elasticsearch-service:9300")
    val settings = Settings.settingsBuilder().build()
    val client = ElasticClient.transport(settings, uri)

    val results = client.execute {
      search in "lot_dev" / "securities" query term
    }.await

    val securities: Seq[Security] = results.as[Security]
    println(securities)
  }

}