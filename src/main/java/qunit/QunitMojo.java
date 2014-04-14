package qunit;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Says "Hi" to the user.
 *
 */
@Mojo(name = "test")
public class QunitMojo extends AbstractMojo
{
    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    @Parameter(property = "project.build.directory")
    private final File outputDirectory = new File("");

    /**
     * Project's base directory
     *
     * @parameter expression="${basedir}"
     * @readonly
     * @required
     */
    @Parameter(property = "basedir")
    private final File baseDirectory = new File("");

    private File sourceDirectory;
    private File testSourceDirectory;

    private List<File> tests;

    public void execute() throws MojoExecutionException
    {
        getLog().info("qunit test plugin");

        sourceDirectory = new File(baseDirectory.getPath() + "/src/main/q");
        testSourceDirectory = new File(baseDirectory.getPath() + "/src/test/q");
        getLog().debug(sourceDirectory.toString());
        getLog().debug(testSourceDirectory.toString());

        List<File> tests = getTests();
        if (tests.isEmpty()) {
            getLog().info("No tests found to execute at " + testSourceDirectory);
        } else {
            getLog().info(tests.size() + " test(s) found to execute");
        }
    }

    public List<File> getTests() {
        if (testSourceDirectory.isDirectory()) {
            File[] files = testSourceDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches("test.*\\.q");
                }
            });
            return Arrays.asList(files);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}