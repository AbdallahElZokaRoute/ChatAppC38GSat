package com.route.chatappc38gsat.database

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.route.chatappc38gsat.model.AppUser
import com.route.chatappc38gsat.model.Message
import com.route.chatappc38gsat.model.Room

fun getCollectionRef(collectionPath: String): CollectionReference {
    val db = Firebase.firestore
    return db.collection(collectionPath)
}

fun addUserToFirestoreDB(
    user: AppUser,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val userCollection = getCollectionRef(AppUser.COLLECTION_NAME)
    val userDoc = userCollection.document(user.uid!!)
    userDoc.set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getUserFromFirestoreDB(
    uid: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {
    val userCollection = getCollectionRef(AppUser.COLLECTION_NAME)
    val userDoc = userCollection.document(uid)
    userDoc.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun addRoomToFirestoreDB(
    room: Room,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val roomsCollectionRef = getCollectionRef(Room.COLLECTION_NAME)
    val roomsDocRef = roomsCollectionRef.document()
    room.id = roomsDocRef.id
    roomsDocRef.set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getRoomsFromFirestoreDB(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val roomsCollectionRef = getCollectionRef(Room.COLLECTION_NAME)
    roomsCollectionRef.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getMessagesRef(roomId: String): CollectionReference {
    val roomsCollectionRef = getCollectionRef(Room.COLLECTION_NAME)
    val roomDocRef = roomsCollectionRef.document(roomId)
    return roomDocRef.collection(Message.COLLECTION_NAME)
}

fun addMessageToFirestoreDB(
    message: Message,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val messagesCollectionRef = getMessagesRef(message.roomId!!)
    val messagesDocRef = messagesCollectionRef.document()
    message.id = messagesDocRef.id
    messagesDocRef.set(message)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}

fun getMessagesFromFirestoreDB(roomId: String, snapshotListener: EventListener<QuerySnapshot>) {
    val messagesCollectionRef = getMessagesRef(roomId)
    messagesCollectionRef.orderBy("dateTime",Query.Direction.DESCENDING).addSnapshotListener(snapshotListener)

}