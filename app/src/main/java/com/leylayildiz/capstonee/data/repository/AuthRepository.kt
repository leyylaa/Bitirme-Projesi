package com.leylayildiz.capstonee.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.leylayildiz.capstonee.common.Resource
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseAuth : FirebaseAuth) {
    fun isUserLoggedIn()= firebaseAuth.currentUser != null
    fun getUserId()=firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun signIn(email: String, password: String): Resource<Unit> {

        return try {
            val result= firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null){
                Resource.Success(Unit)
            }else {
                Resource.Error("Bir Hata Oluştu !!")
            }
        }catch (e:Exception){
            Resource.Error("Böyle Bir Kullanıcı Yok")
        }
    }

    suspend fun signUp(email: String, password: String): Resource<Unit> {
        return try {
            val result= firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null){
                Resource.Success(Unit)
            }else {
                Resource.Error("Bir Hata Oluştu !!")
            }
        }catch (e:Exception){
            Resource.Error("Bir Hata Oluştu !!")
        }
    }
    interface AuthRepo {
        fun currentUser(): FirebaseUser?
    }
    fun logOut()= firebaseAuth.signOut()
}