package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 2)
abstract class EmployeeDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract fun employeeDao():EmployeeDao

    companion object{
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        /**
         * INSTANCE 將保留對通過 getInstance 返回的任何資料庫的引用。
         * 這將幫助我們避免重複初始化資料庫，這是浪費的。
         * volatile 變量的值永遠不會被緩存，所有寫入和讀取都將在主內存中完成
         * 這意味著一個Thread對共享資料所做的更改對其他Thread是可見的
         */

        fun getInstance(context: Context): EmployeeDatabase {
            // 多執行緒(Multiple threads)可以同時請求資料庫，確保我們只使用同步初始化一次。 一次只能有一個執行續進入同步塊。
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    )
                        // 如果沒有 Migration 對象，則抹除並重建而不是遷移。
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}