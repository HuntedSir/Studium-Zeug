plugins {
    alias(libs.plugins.algomate)
    alias(libs.plugins.jagr.gradle)
}

exercise {
    assignmentId.set("h09")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "ab60myce"
    firstName = "Alexander"
    lastName = "Borodin"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

dependencies {
    implementation(libs.algoutils.student)
    testImplementation(libs.junit.core)
}

jagr {
    graders {
        val graderPublic by getting {
            graderName.set("H09-Public")
            rubricProviderName.set("h09.H09_RubricProvider")
            configureDependencies {
                implementation(libs.algoutils.tutor)
            }
        }
    }
}
