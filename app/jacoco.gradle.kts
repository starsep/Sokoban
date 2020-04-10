tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.named("check") {
    dependsOn(":app:jacocoTestReport")
}
