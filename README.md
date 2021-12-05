# SimplyLib

Some helpful codes for my mods

## Adding to your project:
Add the following to your `build.gradle`:
```gradle
repositories {
    //...
    maven {
	url "https://www.cursemaven.com"
	content { includeGroup "curse.maven" }
    }
    maven { url 'https://storage.googleapis.com/devan-maven/' }
}

dependencies {
    //...
    include modImplementation("curse.maven:simplylib-525225:3549894")
    include modImplementation("net.devtech:arrp:0.5.3")
}
```
