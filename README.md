# Detekt custom rules

This repository contains Detekt custom rules and configuration that are based on Deloitte Digital's Kotlin Style Guide.

## Adding To Your Project
Make sure to add [detekt][detekt_quick_start] to your project by applying the following configuration to your app-level build.gradle file:

   ```
      plugins {
         id("io.gitlab.arturbosch.detekt").version("1.19.0")
      }
   ```
 
### Custom Rules
1. Copy the jar file (`build/libs/dd-detekt-custom-rules-1.0.jar`) from this repo.
2. Go to the project folder of the project where you want to use these custom rules.
3. In your project folder, create a `detekt` folder and paste the jar file here.
4. Navigate to the app-level build.gradle file and add the jar file as dependency:

    ```
      dependencies {
          detektPlugins files("$rootDir/detekt/dd-detekt-custom-rules-1.0.jar")
      }
    ```

### Custom Configurations
1. Copy `detekt.yml` file from the this repo.
2. Go to the project folder of the project where you want to use the custom configuration and paste detekt.yml file inside the detekt folder.
3. Navigate to the app-level build.gradle file and apply the following configuration:

    ```
    detekt {
        config = files("$rootDir/detekt/detekt.yml")
    }
    ```
### Detekt Plugin
5. If you're using the Detekt Plugin on your IDE, go to Preferences > Tools > Detekt.
6. Set Plugin Jars to the path of your jar file and Configuration Files to the path of your configuration file.


## Development Workflow
1. Create a branch from master and follow this naming convention for your branch: `rule/your-custom-rule` (E.g. `rule/collapsible-if-statements`).
2. From the project folder, create your custom rules inside `src/main/kotlin/com/dd/detektcustomrules/rules`.
3. Create a test for your custom rule.
4. Add the new rule to `DDRuleSetProvider`.
5. Open detekt.yml and under custom, add your custom rule and set active to true

    ```yaml
    custom:
      MyRule:
        active: true
    ```

5. Commit and push your changes.
6. Create a Pull Request.
7. Once your Request is merged, run `.gradlew build` in your terminal. This should create a jar file.


## Best Practices
- Use the visit method that gets you the closest to the PSI node that you want to check for violation. E.g. If your custom rule checks function names, use visitNamedFunction instead of visitDeclaration.
- Although we can't avoid traversing through a PSI node, it's always best to keep the number of loops/iteration over a collection as minimal as possible to prevent performance overhead.
- When calling the report method to report a violation, make sure to use a message that best describes the violation and add some information that will help the developers solve it.
- When writing tests, make sure all possible scenarios are covered.

## Documentation
- You can find the documentation about how to write custom [rules here][custom_rule_documentation].
- Kotlin PSI Classes - [link here][kotlin_psi_classes]
- Sample Detekt Rules - [link here][sample_detekt_rules]

[sample_detekt_rules]: https://github.com/detekt/detekt/tree/main/detekt-rules-style/src/main/kotlin/io/gitlab/arturbosch/detekt/rules/style

[kotlin_psi_classes]: https://github.com/JetBrains/kotlin/tree/master/compiler/psi/src/org/jetbrains/kotlin/psi

[create_template]: https://github.com/detekt/detekt-custom-rule-template/generate

[maven_central]: https://search.maven.org/

[custom_rule_documentation]: https://detekt.github.io/detekt/extensions.html

[jitpack]: https://jitpack.io/

[detekt_quick_start]: https://detekt.dev/index.html#quick-start-with-gradle
