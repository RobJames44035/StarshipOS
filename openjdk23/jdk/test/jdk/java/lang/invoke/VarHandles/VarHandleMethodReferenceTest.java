/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8195650
 * @summary Test linking of method references to VarHandle access methods.
 * @run testng VarHandleMethodReferenceTest
 */

import org.testng.annotations.Test;

import java.lang.invoke.*;

public class VarHandleMethodReferenceTest {

  interface R {
      void apply();
  }

  @Test
  public void testMethodReferences() {
      VarHandle vh = MethodHandles.arrayElementVarHandle(int[].class);

      // The compilation of these method references will result in the
      // placement of MethodHandles in the constant pool that reference
      // VarHandle signature polymorphic methods.
      // When those constant method handles are loaded the VarHandle invoker
      // mechanism will be used where the first argument to invocation will be
      // the bound VarHandle instance

      // VarHandle invokers are tested by other test classes so here it
      // is just necessary to check that functional objects can be successfully
      // obtained, it does not matter about the signature of the functional
      // interface

      R r;
      r = vh::get;
      r = vh::set;
      r = vh::getVolatile;
      r = vh::setVolatile;
      r = vh::getOpaque;
      r = vh::setOpaque;
      r = vh::getAcquire;
      r = vh::setRelease;

      r = vh::compareAndSet;
      r = vh::compareAndExchange;
      r = vh::compareAndExchangeAcquire;
      r = vh::compareAndExchangeRelease;
      r = vh::weakCompareAndSetPlain;
      r = vh::weakCompareAndSet;
      r = vh::weakCompareAndSetAcquire;
      r = vh::weakCompareAndSetRelease;

      r = vh::getAndSet;
      r = vh::getAndSetAcquire;
      r = vh::getAndSetRelease;
      r = vh::getAndAdd;
      r = vh::getAndAddAcquire;
      r = vh::getAndAddRelease;
      r = vh::getAndBitwiseOr;
      r = vh::getAndBitwiseOrAcquire;
      r = vh::getAndBitwiseOrRelease;
      r = vh::getAndBitwiseAnd;
      r = vh::getAndBitwiseAndAcquire;
      r = vh::getAndBitwiseAndRelease;
      r = vh::getAndBitwiseXor;
      r = vh::getAndBitwiseXorAcquire;
      r = vh::getAndBitwiseXorRelease;
  }
}
