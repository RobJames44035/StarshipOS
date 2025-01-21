/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.util.List;

/*
 * The extended base interop test on SSL/TLS communication.
 */
public abstract class ExtInteropTest extends BaseInteropTest<ExtUseCase> {

    public ExtInteropTest(Product serverProduct, Product clientProduct) {
        super(serverProduct, clientProduct);
    }

    @Override
    protected abstract List<TestCase<ExtUseCase>> getTestCases();

    @Override
    protected AbstractServer.Builder createServerBuilder(ExtUseCase useCase)
            throws Exception {
        JdkServer.Builder builder
                = (JdkServer.Builder) super.createServerBuilder(useCase);
        builder.setServerNames(useCase.getServerNames());
        builder.setAppProtocols(useCase.getAppProtocols());
        builder.setNamedGroups(useCase.getNamedGroups());
        return builder;
    }

    @Override
    protected AbstractClient.Builder createClientBuilder(ExtUseCase useCase)
            throws Exception {
        JdkClient.Builder builder
                = (JdkClient.Builder) super.createClientBuilder(useCase);
        builder.setServerNames(useCase.getServerNames());
        builder.setAppProtocols(useCase.getAppProtocols());
        builder.setNamedGroups(useCase.getNamedGroups());
        return builder;
    }
}
