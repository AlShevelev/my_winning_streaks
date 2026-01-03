plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidLibrary {
        namespace = "com.shevelev.mywinningstreaks.storage"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    val xcfName = "storageKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.datastore)
                implementation(libs.datastore.preferences)
                implementation(libs.datetime)

                api(libs.koin.core)

                implementation(project(":coreentities"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android)
            }
        }

        iosMain {
            dependencies {
                dependencies {
                    implementation(libs.sqldelight.native)
                }
            }
        }
    }
}

sqldelight {
    databases {
        create("AppStorageDatabase") {
            packageName.set("com.shevelev.mywinningstreaks.storage.api.database")
        }
    }
}