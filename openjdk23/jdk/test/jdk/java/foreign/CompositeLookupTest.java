/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import org.testng.annotations.Test;

import java.lang.foreign.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/*
 * @test
 * @run testng CompositeLookupTest
 */
public class CompositeLookupTest {

    @Test(dataProvider = "testCases")
    public void testLookups(SymbolLookup lookup, List<Result> results) {
        for (Result result : results) {
            switch (result) {
                case Success(String name, long expectedLookupId) -> {
                    Optional<MemorySegment> symbol = lookup.find(name);
                    assertTrue(symbol.isPresent());
                    assertEquals(symbol.get().address(), expectedLookupId);
                }
                case Failure(String name) -> {
                    Optional<MemorySegment> symbol = lookup.find(name);
                    assertFalse(symbol.isPresent());
                }
            }
        }
    }

    static class TestLookup implements SymbolLookup {

        private Set<String> symbols;
        private long id;

        public TestLookup(long id, String... symbols) {
            this.id = id;
            this.symbols = Set.of(symbols);
        }

        @Override
        public Optional<MemorySegment> find(String name) {
            return symbols.contains(name) ?
                    Optional.of(MemorySegment.ofAddress(id)) : Optional.empty();
        }
    }

    sealed interface Result { }
    record Success(String name, long expectedLookupId) implements Result { }
    record Failure(String name) implements Result { }

    @DataProvider(name = "testCases")
    public Object[][] testCases() {
        return new Object[][]{
                {
                    new TestLookup(1, "a", "b", "c")
                            .or(new TestLookup(2,"d", "e", "f"))
                            .or(new TestLookup(3,"g", "h", "i")),
                    List.of(
                            new Success("a", 1),
                            new Success("b", 1),
                            new Success("c", 1),
                            new Success("d", 2),
                            new Success("e", 2),
                            new Success("f", 2),
                            new Success("g", 3),
                            new Success("h", 3),
                            new Success("i", 3),
                            new Failure("j")
                    )
                },
                {
                        new TestLookup(1, "a", "b", "c")
                                .or(new TestLookup(2,"a", "b", "c"))
                                .or(new TestLookup(3,"a", "b", "c")),
                        List.of(
                                new Success("a", 1),
                                new Success("b", 1),
                                new Success("c", 1),
                                new Failure("d")
                        )
                },
                {
                        new TestLookup(1 )
                                .or(new TestLookup(2))
                                .or(new TestLookup(3,"a", "b", "c")),
                        List.of(
                                new Success("a", 3),
                                new Success("b", 3),
                                new Success("c", 3),
                                new Failure("d")
                        )
                },
                {
                        new TestLookup(1, "a", "b", "c")
                                .or(new TestLookup(2,"d")
                                        .or(new TestLookup(3,"e"))
                                        .or(new TestLookup(4,"f")))
                                .or(new TestLookup(5,"g")
                                        .or(new TestLookup(6,"h"))
                                        .or(new TestLookup(7,"i"))),
                        List.of(
                                new Success("a", 1),
                                new Success("b", 1),
                                new Success("c", 1),
                                new Success("d", 2),
                                new Success("e", 3),
                                new Success("f", 4),
                                new Success("g", 5),
                                new Success("h", 6),
                                new Success("i", 7),
                                new Failure("j")
                        )
                },
        };
    }
}
