import org.zaproxy.gradle.addon.AddOnPluginExtension
import org.zaproxy.gradle.addon.AddOnStatus

description = "The release quality Typosquat Scanner"
version = "0.0.1-SNAPSHOT"

zapAddOn {
    addOnName.set("Typosquat scanner")
    addOnStatus.set(AddOnStatus.RELEASE)
    zapVersion.set("2.7.0")

    manifest {
        author.set("ZAP Dev Team")
    }
}

dependencies {
    implementation("com.shapesecurity:salvation:2.7.0")
    implementation("org.apache.commons:commons-text:1.8")

    testImplementation("org.apache.commons:commons-lang3:3.7")
    testImplementation("junit:junit:4.11")
    testImplementation("org.mockito:mockito-all:1.10.8")
}


fun Project.zapAddOn(configure: AddOnPluginExtension.() -> Unit): Unit =
        (this as ExtensionAware).extensions.configure("zapAddOn", configure)

