import org.zaproxy.gradle.addon.AddOnPluginExtension
import org.zaproxy.gradle.addon.AddOnStatus

description = "The release quality Policy Scanner"
version = "0.0.1-SNAPSHOT"

zapAddOn {
    addOnName.set("Policy Scanner")
    addOnStatus.set(AddOnStatus.RELEASE)
    zapVersion.set("2.7.0")

    manifest {
        author.set("Group-03")
    }
}

dependencies {
    implementation("com.shapesecurity:salvation:2.7.0")
    implementation("org.apache.commons:commons-text:1.8")
    implementation("org.reflections:reflections:0.9.11")

    testImplementation("org.apache.commons:commons-lang3:3.7")
    testImplementation("junit:junit:4.11")
    testImplementation("org.mockito:mockito-core:2.7.2")

    compile ("org.reflections:reflections:0.9.11")
}


fun Project.zapAddOn(configure: AddOnPluginExtension.() -> Unit): Unit =
        (this as ExtensionAware).extensions.configure("zapAddOn", configure)


tasks {
    test {
        //dependsOn(":addOns:policyRuleImplementations:build")
        dependsOn("initZaproxyConfig")
    }

    register("initZaproxyConfig", Copy::class) {
        from("../../../zaproxy/zap/src/main/resources/org/zaproxy/zap/resources") {
            include("log4j.properties")
            include("config.xml")
        }
        into("build/zaproxy/xml")
    }
}
