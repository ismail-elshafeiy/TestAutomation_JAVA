package javaPractise.basics.general;

public class Comments {
    /**
     * Notes (Tips & Tricks):
     *  - Keep Comments Up to Date, Always update comments when changing code logic
     *  - Use Comments to Explain Why, Not What
     *  - Avoid Redundant Comments
     *  - Don’t leave chunks of dead code :(
     *  - Use Consistent Formatting Like  ----- section logic ----- Make comments easy to read
     *  - Review Comments Like You Review Code
     *  - Ask your self: Does this comment help the next reader (including future me)?
     *  - Use TODO & FIXME Tags to Highlight Important/issue Tasks
     */

    // This is a single-line comment

    /*
     * This is a multi-line comment
     * It can span multiple lines
     */

    /**
     * This is a Javadoc comment
     * It is used to document classes, methods, and fields
     *
     * @return void
     * @throws Exception If an error occurs
     * @Param args Command line arguments
     * @version 1.0
     * @author Your Name
     * @implNote This method is an implementation note
     * @implSpec This method is a specification note
     * @apiNote This method is an API note
     * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/javadoc/index.html">Javadoc Guide</a>
     * @since 1.0
     * @deprecated This method is deprecated and will be removed in future versions
     */
    public void javaDoc() {
        // This method is documented with Javadoc
    }

    // TODO: This method needs to be implemented
    public void toDoComment() {
        // Implementation goes here
    }

    // FIXME: This method needs to be fixed
    public void fixMeComment() {
        // Fix the implementation here
    }
}
