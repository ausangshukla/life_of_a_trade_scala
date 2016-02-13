package com.lot.blockAmount.tests

import com.lot.test.BaseTest
import com.lot.generators.OrderFactory
import com.lot.blockAmount.model.BlockAmount
import com.lot.blockAmount.dao.BlockAmountDao
import scala.collection.mutable.ListBuffer
import com.lot.generators.BlockAmountFactory

class BlockAmountDaoTest extends BaseTest {

  "BlockAmountDao" should "save BlockAmount correctly" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)
    /*
     * Get it back from the DB
     */
    val dbBlockAmount = wait(BlockAmountDao.get(saved.id.get)).get
    /*
     * They should be the same
     */
    assert(saved == dbBlockAmount)

  }

  "BlockAmountDao" should "list BlockAmounts correctly" in {

    /*
     * Create some entities and save
     */
    val blockAmountList = new ListBuffer[BlockAmount]
    for (i <- 1 to 10) {
      val b = BlockAmountFactory.generate()
      blockAmountList += wait(BlockAmountDao.save(b))
    }

    //println(blockAmountList)
    
    /*
     * Get it back from the DB
     */
    val dbList = wait(BlockAmountDao.list)
    
    // println(dbList)
    
    /*
     * They should be the same
     */
    assert(dbList.length == blockAmountList.length)
    val mixed = blockAmountList zip dbList
    for {
      (blockAmount, dbBlockAmount) <- mixed
      x = println(s"comparing blockAmount = $blockAmount with dbBlockAmount = $dbBlockAmount")
    } yield (assert(blockAmount == dbBlockAmount))

  }

  "BlockAmountDao" should "update BlockAmount correctly" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)

    val modified = BlockAmountFactory.generate().copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    wait(BlockAmountDao.update(modified))
    /*
     * Get it back from the DB
     */
    val dbBlockAmount = wait(BlockAmountDao.get(saved.id.get)).get
    /*
     * They should be the same. We need to copy the updated_at
     */
    assert(modified.copy(updated_at = dbBlockAmount.updated_at) == dbBlockAmount)

  }

  "BlockAmountDao" should "updateWithOptimisticLocking BlockAmount correctly" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)

    val modified1 = BlockAmountFactory.generate().copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)    
    val modified2 = BlockAmountFactory.generate().copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    val rowCount1 = wait(BlockAmountDao.updateWithOptimisticLocking(modified1))
    val rowCount2 = wait(BlockAmountDao.updateWithOptimisticLocking(modified1))
    
    assert(rowCount1 == 1)
    assert(rowCount2 == 0)

  }

  "BlockAmountDao" should "delete BlockAmount correctly" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)
    /*
     * Delete it
     */
    wait(BlockAmountDao.delete(saved.id.get))
    /*
     * Get it back from the DB
     */
    val dbBlockAmount = wait(BlockAmountDao.get(saved.id.get))
    /*
     * They should be None
     */
    assert(dbBlockAmount == None)

  }
}