Custom checkstyle checks with intended usage for Terasology.

# LibsClassVisibilityCheck
`org.terasology.customchecks.LibsClassVisibilityCheck`

This treewalker check ensures a small visibility of classes.
Public classes are only allowed in the following cases:
- The class is abstract
- The class name ends with `*Factory`
- The class name ends with `*Event`
- The class implements an interface `Component`
- The class is annotated with `@RegisterSystem`

Sample usage:
```
<module name="TreeWalker">
        <module name="org.terasology.customchecks.LibsClassVisibilityCheck"/>
        ...
```