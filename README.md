# FeatherLib

Some helpful code for Fabric mods

Mods that use FeatherLib:
- [Terracraft](https://github.com/SimplyCmd/Terracraft)
- [The Fabricated Project](https://github.com/SimplyCmd?tab=repositories&q=Fabricated&type=&language=&sort=)
- [The Ink Machine](https://github.com/SimplyCmd/The-Ink-Machine)
- [Quake](https://github.com/SimplyCmd/Quake)

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
    include modImplementation("curse.maven:featherlib-525225:3472201")
    include modImplementation("net.devtech:arrp:0.5.3")
}
```

## Documentation:
Please see the [wiki](https://github.com/SimplyCmd/FeatherLib/wiki) for information on how to use this library.
