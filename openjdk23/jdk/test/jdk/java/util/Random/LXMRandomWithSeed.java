/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.util.random.RandomGeneratorFactory;

/**
 * @test
 * @summary Check that the (byte[]) constructors do not throw (see bug report)
 * @bug 8283083
 */

public class LXMRandomWithSeed {

    public static void main(String[] args) {
        byte[] seed = new byte[0x100];
        for (var i = 0; i < seed.length; ++i) {
            seed[i] = (byte) i;
        }
        var lxmFactories = RandomGeneratorFactory.all()
                .filter(factory -> factory.group().equals("LXM"))
                .toList();
        for (var lxmFactory : lxmFactories) {
            var lxmGen0 = lxmFactory.create(seed);
            var lxmGen1 = lxmFactory.create(seed);
            if (lxmGen0.nextLong() != lxmGen1.nextLong()) {
                throw new RuntimeException("%s(byte[]) is incorrect".formatted(lxmFactory.name()));
            }
        }
    }

}
