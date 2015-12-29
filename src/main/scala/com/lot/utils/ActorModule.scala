package com.lot.utils

import akka.actor.ActorSystem
import com.lot.utils.Configuration


trait ActorModule {
  val system: ActorSystem
}


trait ActorModuleImpl extends ActorModule {
  this: Configuration =>
  val system = ActorSystem("sprayingslick", config)
}