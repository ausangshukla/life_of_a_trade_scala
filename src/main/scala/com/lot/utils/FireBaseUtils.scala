package com.lot.utils

import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import scala.collection.JavaConversions._
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ChildEventListener
import java.util.Date
import com.lot.security.model.Security
import org.joda.time.DateTime
import java.util.HashMap

object FireBaseUtils {

  def init() = {
    val options = new FirebaseOptions.Builder()
      .setServiceAccount(new FileInputStream("src/main/resources/LifeOfATrade-Firebase-Keys.json"))
      .setDatabaseUrl("https://lifeofatrade.firebaseio.com")
      .build();
    FirebaseApp.initializeApp(options);
  }

  def test = {
    // As an admin, the app has access to read and write all data, regardless of Security Rules
    val ref = FirebaseDatabase
      .getInstance()
      .getReference("securities");

    ref.addValueEventListener(new ValueEventListener() {

      override def onDataChange(snapshot: DataSnapshot) {
        println(snapshot.getValue)
      }

      override def onCancelled(firebaseError: DatabaseError) {
        println("The read failed: " + firebaseError.getMessage)
      }

    })
    
    ref.addChildEventListener(new ChildEventListener() {

      override def onChildAdded(snapshot: DataSnapshot, prevChildKey: String) {
       println("Added: " + snapshot.getValue)
      }
      
      override def onChildChanged(snapshot: DataSnapshot, prevChildKey: String) {
       println("Changed: " + snapshot.getValue)
      }
      
      override def onChildMoved(snapshot: DataSnapshot, prevChildKey: String) {
       println("Moved: " + snapshot.getValue)
      }
      
      override def onChildRemoved(snapshot: DataSnapshot) {
       println("Removed: " + snapshot.getValue)
      }
      
      override def onCancelled(firebaseError: DatabaseError) {
        println("The read failed: " + firebaseError.getMessage)
      }
      
    })
    
    import com.lot.security.model.SecurityJsonProtocol._
    
    val sec = Security(None, "Infosys", "INFY", "Technology outsourcing company", 1092, "Equity", "Technology", "ASPAC", 1, "High", None, None)
    val map = new HashMap[String, Any]()
    map.put("ticker", sec.ticker)
    map.put("name", sec.name)
    map.put("price", sec.price)
    map.put("description", sec.description)
    ref.child(sec.ticker).setValue(map)
    
    val newRef = ref.push()
    newRef.setValue(map)
  }
}