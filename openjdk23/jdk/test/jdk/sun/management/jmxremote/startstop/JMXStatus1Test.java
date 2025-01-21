/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A flavor of {@linkplain JMXStatusTest} test where the test application
 * is started without the management agent initialized.
 */
public class JMXStatus1Test extends JMXStatusTest {
    @Override
    protected List<String> getCustomVmArgs() {
        return Collections.emptyList();
    }

    @Override
    protected Pattern getDefaultPattern() {
        return JMXStatusTest.DISABLED_AGENT_STATUS;
    }
}