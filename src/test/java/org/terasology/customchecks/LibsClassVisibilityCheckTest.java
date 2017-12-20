package org.terasology.customchecks;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LibsClassVisibilityCheckTest {

    private static final String TEST_CLASSES_DIRECTORY = "src/test/java/org/terasology/customchecks";
    private Checker checker;
    private List<AuditEvent> errors;

    @Before
    public void setUp() throws Exception {
        DefaultConfiguration checkConfiguration = new DefaultConfiguration(LibsClassVisibilityCheck.class.getName());
        DefaultConfiguration treeWalkerConfiguration = new DefaultConfiguration(TreeWalker.class.getName());
        DefaultConfiguration configuration = new DefaultConfiguration("configuration");
        configuration.addAttribute("charset", StandardCharsets.UTF_8.name());
        configuration.addChild(treeWalkerConfiguration);
        treeWalkerConfiguration.addChild(checkConfiguration);
        errors = new ArrayList<>();
        checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(configuration);
        checker.addListener(new AuditListener() {
            @Override
            public void auditStarted(AuditEvent event) {

            }

            @Override
            public void auditFinished(AuditEvent event) {

            }

            @Override
            public void fileStarted(AuditEvent event) {

            }

            @Override
            public void fileFinished(AuditEvent event) {

            }

            @Override
            public void addError(AuditEvent event) {
                errors.add(event);
            }

            @Override
            public void addException(AuditEvent event, Throwable throwable) {

            }
        });
    }

    @Test
    public void shouldForbidDefaultPublicClasses() throws Exception {
        checker.process(files("PublicClass.java"));
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("PublicClass is not allowed to be public");
    }

    @Test
    public void shouldAllowPublicInterfaces() throws Exception {
        checker.process(files("PublicInterface.java"));
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldAllowPublicComponents() throws Exception {
        checker.process(files("PublicClassImplementsComponent.java"));
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldAllowPublicFactories() throws Exception {
        checker.process(files("PublicClassFactory.java"));
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldAllowClassesAnnotatedWithRegisterSystem() throws Exception {
        checker.process(files("PublicClassAnnotatedWithRegisterSystem.java"));
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldAllowPublicEvents() throws Exception {
        checker.process(files("PublicClassEvent.java"));
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldAllowPublicAbstractClasses() throws Exception {
        checker.process(files("PublicAbstractClass.java"));
        assertThat(errors.isEmpty());
    }

    @Test
    public void shouldAllowPackagePrivateClasses() throws Exception {
        checker.process(files("PackagePrivateClass.java"));
        assertThat(errors.isEmpty());
    }

    private List<File> files(String fileName) {
        return Collections.singletonList(new File(TEST_CLASSES_DIRECTORY, fileName));
    }

}