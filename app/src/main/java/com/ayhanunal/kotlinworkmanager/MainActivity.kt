package com.ayhanunal.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //yollanacak veri
        val data = Data.Builder().putInt("intKey",1).build()

        //calisması icin gerekli şartlar.
        val constraint = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        /*
        //islemi bir kere yapmak.
        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraint)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.MINUTES) //gecikmeli baslat.
            //.addTag("myTag")
            .build()


        //islemi baslatalım.
        WorkManager.getInstance(this).enqueue(myWorkRequest)


         */


        //islemi periyodik olarak yapmak istersek.
        val myWorkReguest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkReguest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkReguest.id)
            .observe(this, Observer {

                if(it.state == WorkInfo.State.RUNNING){
                    println("running")
                }else if (it.state == WorkInfo.State.FAILED){
                    println("failed")
                }else if (it.state == WorkInfo.State.SUCCEEDED){
                    println("succeded")
                }

            })

        //WorkManager.getInstance(this).cancelAllWork() // tum workleri iptal eder.

        /*
        //zincirleme
        val oneTimeRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraint)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeRequest).then(oneTimeRequest).then(oneTimeRequest)
            .enqueue()

            
         */




    }
}