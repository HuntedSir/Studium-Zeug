plugins {
    alias(libs.plugins.jagr)
    alias(libs.plugins.algomate)
}

exercise {
    assignmentId.set("h12")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = null
    firstName = null
    lastName = null

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

jagr {
    graders {
        val graderPublic by getting {
            graderName.set("H12-Public")
            rubricProviderName.set("h12.H12_RubricProviderPublic")
        }
    }
}
