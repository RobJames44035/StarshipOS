/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg1;

@ContainerSynthNotDoc(value={@ContaineeSynthDoc,@ContaineeSynthDoc})
@RegContainerValDoc(value={@RegContaineeNotDoc,@RegContaineeNotDoc},y=3)
@ContainerValDoc(value={@ContaineeNotDoc,@ContaineeNotDoc},x=1)
@RegContainerValNotDoc(value={@RegContaineeDoc,@RegContaineeDoc},y=4)
@ContainerValNotDoc(value={@ContaineeNotDoc,@ContaineeNotDoc},x=2)
public class C {

    @ContainerSynthNotDoc(value={@ContaineeSynthDoc})
    public void test1() {}

    @ContaineeSynthDoc @ContaineeSynthDoc
    public void test2() {}
}
