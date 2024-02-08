package ru.dev.gamedev.honest_investor.di

//@Module
//@InstallIn(SingletonComponent::class)
//object WorkManagerInitializer : Initializer<WorkManager> {
//
//    @Provides
//    @Singleton
//    override fun create(@ApplicationContext context: Context): WorkManager {
//        val configuration = Configuration.Builder().build()
//        WorkManager.initialize(context, configuration)
//        Log.d("Hilt Init", "WorkManager initialized by Hilt this time")
//        return WorkManager.getInstance(context)
//    }
//
//    override fun dependencies(): List<Class<out Initializer<*>>> {
//        // No dependencies on other libraries.
//        return emptyList()
//    }
//}