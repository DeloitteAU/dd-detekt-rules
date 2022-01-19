# Detekt custom rule

This repository contains all the custom detekt rules that are based on Deloitte Digital's [Kotlin Style Guide][kotlin_style_guide].

## Development Workflow
1. Create a branch from master and follow this naming convention for your branch: `rule/your-custom-rule` (E.g. `rule/collapsible-if-statements`).
2. From the project folder, create your custom rules inside `src/main/kotlin/com/dd/detektcustomrules/rules`.
3. Create a test for your custom rule.
4. Add the new rule to `DDRuleSetProvider`.
5. Commit and push your changes.
6. Create a Pull Request.


## Best Practices
- Use the visit method that gets you the closest to the PSI node that you want to check for violation. E.g. If your custom rule checks function names, use visitNamedFunction instead of visitDeclaration.
- Although we can't avoid traversing through a PSI node, it's always best to keep the number of loops/iteration over a collection as minimal as possible to prevent performance overhead.
- When calling the report method to report a violation, make sure to use a message that best describes the violation and add some information that will help the developers solve it.
- When writing tests, make sure all possible scenarios are covered.


## How to use these rules in your project
1. Run `.gradlew build` in your terminal. This should create a jar file.
2. Locate the jar file (`build/libs/dd-detekt-custom-rules-1.0.jar`) and copy it.
3. Go to the project folder of the project where you want to use these custom rules, and paste the jar file here.
4. To enable these custom rules in your project, add the jar file as dependency in your build.gradle file:

    ```
      dependencies {
          detektPlugins("path-of-your-jar-file/dd-detekt-custom-rules-1.0.jar")
      }
    ```
5. If you're using the Detekt Plugin in your IDE, go to Preferences > Tools > Detekt and in the Plugin Jars textbox, add the path to your jar file.
6. Remember that, by default, all custom rules are disabled. To activate a rule you need to write something like this in
   your yaml configuration:

    ```yaml
    MyRuleSet:
      MyRule:
        active: true
    ```


## Documentation
- You can find the documentation about how to write custom [rules here][custom_rule_documentation].
- Kotlin PSI Classes - [link here][kotlin_psi_classes]
- Sample Detekt Rules - [link here][sample_detekt_rules] 

[sample_detekt_rules]: https://github.com/detekt/detekt/tree/main/detekt-rules-style/src/main/kotlin/io/gitlab/arturbosch/detekt/rules/style

[kotlin_psi_classes]: https://github.com/JetBrains/kotlin/tree/master/compiler/psi/src/org/jetbrains/kotlin/psi

[kotlin_style_guide]: https://hub.deloittedigital.com.au/wiki/display/MCD/Kotlin+Style+Guide

[create_template]: https://github.com/detekt/detekt-custom-rule-template/generate

[maven_central]: https://search.maven.org/

[custom_rule_documentation]: https://detekt.github.io/detekt/extensions.html

[jitpack]: https://jitpack.io/
