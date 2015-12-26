package com.lot

object Scratch {
  def gameResults(): Seq[(String, Int)] =
    ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil
                                                  //> gameResults: ()Seq[(String, Int)]

  def hallOfFame = for {
    result <- gameResults()
    (name, score) = result
    if (score > 5000)
  } yield name                                    //> hallOfFame: => Seq[String]
  
  
  hallOfFame                                      //> res0: Seq[String] = List(Melissa, John)
}